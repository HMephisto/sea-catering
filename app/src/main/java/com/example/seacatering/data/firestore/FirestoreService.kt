package com.example.seacatering.data.firestore

import android.util.Log
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.model.SubscriptionWithMenu
import com.example.seacatering.domain.model.SubscriptionWithMenuAndUser
import com.example.seacatering.domain.model.Testimony
import com.example.seacatering.domain.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FirestoreService(private val db: FirebaseFirestore) {
    suspend fun createUser(user: User): Status =
        try {
            db.collection("users").document(user.id).set(user).await()
            Status.Success
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to Create User")
        }

    suspend fun getUserData(userId: String): Status =
        try {
            val result = db.collection("users").document(userId).get().await()
            val user = result.toObject(User::class.java)
            Status.SuccessWithData(user)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to Create User")
        }

    suspend fun getAllMenu(): Status =
        try {
            val result = db.collection("menus").get().await()
            val menus = result.toObjects(Menu::class.java)
            Status.SuccessWithData(menus)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get Menu Data")
        }

    suspend fun getMenuDetail(menuId: String): Status =
        try {
            val result = db.collection("menus").document(menuId).get().await()
            val menu = result.toObject(Menu::class.java)
            Status.SuccessWithData(menu)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get Menu Data")
        }

    suspend fun createSubscription(subscription: Subscription): Status =
        try {
            db.collection("subscriptions").document(subscription.id).set(subscription).await()
            Status.Success
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to Create Subscription")
        }

    suspend fun getSubscription(userId: String): Status =
        try {
            val resultList = mutableListOf<SubscriptionWithMenu>()
            val result =
                db.collection("subscriptions").whereEqualTo("user_id", userId)
                    .whereNotEqualTo("status", "Canceled").get().await()
            for (subscription in result) {
                val menuId = subscription.getString("plan_id")

                if (menuId != null) {
                    val resultMenu = db.collection("menus").document(menuId).get().await()
                    val menu = resultMenu.toObject(Menu::class.java)?.copy(id = resultMenu.id)
                    if (menu != null) {
                        val combined = SubscriptionWithMenu(
                            subscription.toObject(Subscription::class.java),
                            menu
                        )
                        resultList.add(combined)
                    }
                }
            }
            Log.e("status", userId)
            Status.SuccessWithData(resultList)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get Subscription Data")
        }

    suspend fun togglePauseSubscription(
        subscriptionId: String,
        startDate: Timestamp,
        endDate: Timestamp,
        pause: Boolean
    ): Status =
        try {
            var updates = mapOf<String, Any>()
            if (pause) {
                updates = mapOf(
                    "status" to "Paused",
                    "pause_period_start" to startDate,
                    "pause_period_end" to endDate
                )
            } else {
                updates = mapOf(
                    "status" to "Active",
                    "pause_period_end" to endDate
                )
            }
            db.collection("subscriptions").document(subscriptionId).update(updates).await()
            Status.Success
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get change status")
        }

    suspend fun cancelSubscription(subsciprtionId: String, endDate: String): Status =
        try {
            val updates = mapOf(
                "status" to "Canceled",
                "end_date" to endDate
            )
            db.collection("subscriptions").document(subsciprtionId).update(updates).await()
            Status.Success
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get change status")
        }

    suspend fun addTestimony(testimony: Testimony): Status =
        try {
            db.collection("testimonials").document(testimony.id).set(testimony).await()
            Status.Success
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to create testimony")
        }

    suspend fun getTestimony(): Status =
        try {
            val result = db.collection("testimonials").limit(5).get().await()
            val testimonials = result.toObjects(Testimony::class.java)
            Status.SuccessWithData(testimonials)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get testimony")
        }

    suspend fun getAllSubscription(status: String = ""): Status =
        try {
            var result: QuerySnapshot
            val resultList = mutableListOf<SubscriptionWithMenuAndUser>()
            if (status != "") {
                result = db.collection("subscriptions").whereEqualTo("status", status).get().await()
            } else {
                result = db.collection("subscriptions").get().await()
            }

            for (subscription in result) {
                val menuId = subscription.getString("plan_id")
                val userId = subscription.getString("user_id")
                if (menuId != null) {
                    val resultMenu = db.collection("menus").document(menuId).get().await()
                    val menu = resultMenu.toObject(Menu::class.java)?.copy(id = resultMenu.id)
                    if (menu != null) {
                        if (userId != null) {
                            val resultUser = db.collection("users").document(userId).get().await()
                            val user =
                                resultUser.toObject(User::class.java)?.copy(id = resultUser.id)
                            if (user != null) {
                                val combined = SubscriptionWithMenuAndUser(
                                    subscription.toObject(Subscription::class.java),
                                    menu,
                                    user
                                )
                                resultList.add(combined)
                            }
                        }

                    }
                }
            }
            Status.SuccessWithData(resultList)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get subscriptions Data")
        }

    suspend fun getNewSubscriptionsData(startDate: Timestamp, endDate: Timestamp): Status =
        try {
            val result = db.collection("subscriptions")
                .whereGreaterThanOrEqualTo("created_at", startDate)
                .whereLessThanOrEqualTo("created_at", endDate)
                .get()
                .await()
            val createdAtList = result.documents.mapNotNull { it.getTimestamp("created_at") }
            Log.e("result", createdAtList.toString())
            Status.SuccessWithData(createdAtList)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get subscriptions Data")
        }

    suspend fun getReactivationData(startDate: Timestamp, endDate: Timestamp): Status =
        try {
            val result = db.collection("subscriptions")
                .whereGreaterThanOrEqualTo("pause_period_end", startDate)
                .whereLessThanOrEqualTo("pause_period_end", endDate)
                .whereEqualTo("status", "Active")
                .get()
                .await()

            val createdAtList = result.documents.mapNotNull { it.getTimestamp("pause_period_end") }
            Status.SuccessWithData(createdAtList)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get subscriptions Data")
        }

    suspend fun getActiveSubscriptions(): Status =
        try {
            val sdf = SimpleDateFormat("yyyy-MM", Locale.getDefault()) // e.g., "2025-06"
            val mrrMap = mutableMapOf<String, Float>()
            val allSubscriptions = mutableListOf<Pair<Date, Float>>()

            val result = db.collection("subscriptions")
                .whereEqualTo("status", "Active")
                .get()
                .await()


            for (doc in result.documents) {
                val createdAt = doc.getTimestamp("created_at")?.toDate() ?: continue
                val totalPrice = doc.getDouble("total_price")?.toFloat() ?: continue
                allSubscriptions.add(Pair(createdAt, totalPrice))
            }

            allSubscriptions.sortBy { it.first }

            val seenMonths = sortedSetOf<String>()

            for ((createdAt) in allSubscriptions) {
                val month = sdf.format(createdAt)
                seenMonths.add(month)
            }

            val monthList = seenMonths.toList()
            for (i in monthList.indices) {
                val currentMonth = monthList[i]
                var mrr = 0f

                for ((createdAt, price) in allSubscriptions) {
                    val subMonth = sdf.format(createdAt)
                    val subIndex = monthList.indexOf(subMonth)
                    if (subIndex <= i) {
                        mrr += price
                    }
                }

                mrrMap[currentMonth] = mrr
            }
            Status.SuccessWithData(mrrMap)
        } catch (e: Exception) {
            Status.Failure(e.message ?: "Failed to get subscriptions Data")
        }
}
