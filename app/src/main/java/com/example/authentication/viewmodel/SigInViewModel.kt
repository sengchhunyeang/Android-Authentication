package com.example.authentication.viewmodel

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.authentication.view.SigInResult
import com.example.authentication.view.SigInState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.lang.reflect.Modifier

class SigInViewModel : ViewModel() {
    private val _states = MutableStateFlow(SigInState())
    val state: StateFlow<SigInState> = _states.asStateFlow()

    // Function to handle sign-in results
    fun onSigInResult(result: SigInResult) {
        _states.update { currentState ->
            currentState.copy(
                isSigInState = true, // Update sign-in success state
                sigInError = result.errorMessage // Update error message if any
            )
        }
    }

    // Function to reset state (e.g., after sign-out)
    fun resetState() {
        _states.value = SigInState() // Reset to initial state
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
            Toast.makeText(
                context, error, Toast.LENGTH_SHORT
            ).show()
        }
    }
    Box(
        modifier = androidx.compose.ui.Modifier
            .fillMaxSize()
            .padding(),
    ){
        Button(onClick = { onSigInClick }) {
            Text(text = "SignIn")
        }
    }

}