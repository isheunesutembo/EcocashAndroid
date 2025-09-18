package com.example.ecocash

sealed class PaymentError {
    data class NetworkError(val message: String) : PaymentError()
    data class ServerError(val code: Int, val message: String) : PaymentError()
    data class UnknownError(val message: String) : PaymentError()
}