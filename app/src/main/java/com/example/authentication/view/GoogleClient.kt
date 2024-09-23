package com.example.authentication.view

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.example.authentication.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.tasks.await

data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

class GoogleClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    suspend fun signIn(): IntentSender? {
        return try {
            val result = oneTapClient.beginSignIn(buildSignInRequest()).await()
            result.pendingIntent.intentSender
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }
    }

    suspend fun getSignInResultFromIntent(intent: Intent): SignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.let {
                    it.photoUrl?.toString()?.let { it1 ->
                        it.displayName?.let { it2 ->
                            UserData(
                                userId = it.uid,
                                username = it2,
                                profileUrl = it1
                            )
                        }
                    }
                },
                errorMessage = null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            SignInResult(null, e.localizedMessage)
        }
    }

    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(context.getString(R.string.web_client_id))
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }
}
