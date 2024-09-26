package com.example.authentication.firebase

data class SigInResult(
    val data:UserData?,
    val errorMessage:String?
)
data class UserData(
    val userId:String,
    val username:String,
    val profileUrl:String
)