package com.example.seacatering.data.firestore

import android.util.Log
import com.example.seacatering.domain.model.Menu
import com.example.seacatering.domain.model.Status
import com.example.seacatering.domain.model.Subscription
import com.example.seacatering.domain.model.SubscriptionWithMenu
import com.example.seacatering.domain.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FirestoreService(private val db: FirebaseFirestore) {
    suspend fun createUser(user: User): Status =
        try {
            db.collection("users").document(user.id).set(user).await()
            Status.Success
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
                db.collection("subscriptions").whereEqualTo("user_id", userId).whereNotEqualTo("status", "Canceled").get().await()
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

    suspend fun togglePauseSubscription(subscriptionId: String, startDate: String, endDate: String, pause: Boolean): Status =
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
}
