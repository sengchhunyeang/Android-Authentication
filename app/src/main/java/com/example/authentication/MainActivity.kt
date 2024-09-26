package com.example.loginwithgoogle

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.authentication.firebase.GoogleClient
import com.example.authentication.viewmodel.SigInScreen
import com.example.authentication.viewmodel.SigInViewModel
import com.google.android.gms.auth.api.identity.Identity
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val googleClient by lazy {
        GoogleClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: SigInViewModel = viewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleClient.getSignInResultFromIntent(result.data!!)
                            viewModel.onSigInResult(signInResult)
                        }
                    } else {
                        Log.e("GoogleSignIn", "Sign-In result failed with code: ${result.resultCode}")
                    }
                }
            )

            SigInScreen(state = state, onSigInClick = {
                lifecycleScope.launch {
                    val signInIntentSender = googleClient.signIn()
                    launcher.launch(IntentSenderRequest.Builder(signInIntentSender!!).build())
                }
            })
        }
    }
}

