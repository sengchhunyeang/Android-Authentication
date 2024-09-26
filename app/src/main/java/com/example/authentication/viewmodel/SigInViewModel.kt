package com.example.authentication.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.authentication.firebase.SignInResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
data class SigInState(
    val isSigInState: Boolean = false,
    val sigInError: String? = null
)

class SigInViewModel : ViewModel() {
    private val _states = MutableStateFlow(SigInState())
    val state: StateFlow<SigInState> = _states.asStateFlow()

    fun onSigInResult(result: SignInResult) {
        Log.d("SignInResult", "Sign-in data: ${result.data}, Error: ${result.errorMessage}")
        _states.update { currentState ->
            currentState.copy(
                isSigInState = result.data != null,
                sigInError = result.errorMessage
            )
        }
        fun resetState() {
            _states.value = SigInState()
        }
    }
}
@Composable
fun SigInScreen(
    state: SigInState,
    onSigInClick: () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = state.sigInError) {
        state.sigInError?.let { error ->
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding()
    ) {
        Button(onClick = { onSigInClick() }) {
            Text(text = "Sign In with Google")
        }
    }
}