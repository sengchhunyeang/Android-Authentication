package com.example.authentication.view

data class SigInResult(
    val data: com.example.authentication.view.UserData,
    val errorMessage:String?
)
data class UserData(
    val userId:String,
    val username:String,
    val profileUrl:String
)