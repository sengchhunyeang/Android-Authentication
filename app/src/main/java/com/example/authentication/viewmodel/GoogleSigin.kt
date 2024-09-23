//package com.example.authentication.viewmodel
//
//import android.annotation.SuppressLint
//import android.content.Context
//import android.os.Message
//import androidx.credentials.CredentialManager
//import androidx.credentials.GetCredentialRequest
//import androidx.credentials.R
//import androidx.lifecycle.ViewModel
//import com.google.android.libraries.identity.googleid.GetGoogleIdOption
//import com.google.firebase.auth.AuthResult
//import com.google.firebase.auth.FirebaseAuth
//import java.security.MessageDigest
//import java.util.UUID
//import java.util.concurrent.Flow
//
//class GoogleSigIn : ViewModel() {
//    @SuppressLint("PrivateResource")
//    private suspend fun googleSigIn(context: Context): kotlinx.coroutines.flow.Flow<Result<AuthResult>> {
//        val firebaseAuth=FirebaseAuth.getInstance()
//        return {
//            try {
//                val credentialManager:CredentialManager=CredentialManager.create(context)
//                val ranNote:String=UUID.randomUUID().toString()
//                val bytes:ByteArray=ranNote.toByteArray()
//                val md: MessageDigest=MessageDigest.getInstance("SHA-256")
//                val digest:ByteArray=md.digest(bytes)
//                val hashtable:String=digest.fold(""){str,it->str+"%02x".format(it)}
//                val googleIdOption: GetGoogleIdOption =GetGoogleIdOption.Builder()
//                    .setFilterByAuthorizedAccounts(false)
//                    .setServerClientId(context.getString(R.string.android_credentials_TYPE_PASSWORD_CREDENTIAL))
//                    .setAutoSelectEnabled(true)
//                    .build()
//                val request:GetCredentialRequest=GetCredentialRequest.Builder()
//                    .addCredentialOption(googleIdOption)
//                    .build()
//                val result=credentialManager.getCredential(context, request)
//                val credential=result.credential
//            }
//        }
//    }
//}