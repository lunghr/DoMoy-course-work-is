package com.example.domoycoursework.auth.utils

fun String.isEmail(): Boolean {
    return this.contains("@") && this.contains(".")
}

fun String.isPhoneNumber(): Boolean {
    return this.matches(Regex("^[+]?[0-9]{10,15}$"))
}