package com.example.ecocash
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException


object EcoCash {

    private var apiUrl: String = SANDBOX_URL
    private var apiKey: String? = null
    private val client = OkHttpClient()

    private const val SANDBOX_URL =
        "https://developers.ecocash.co.zw/api/ecocash_pay/api/v2/payment/instant/c2b/sandbox"
    private const val LIVE_URL =
        "https://developers.ecocash.co.zw/api/ecocash_pay/api/v2/payment/instant/c2b/live"


    fun init(key: String, environment: Environment = Environment.SANDBOX) {
        apiKey = key
        apiUrl = when (environment) {
            Environment.SANDBOX -> SANDBOX_URL
            Environment.LIVE -> LIVE_URL
        }
    }

    enum class Environment {
        SANDBOX, LIVE
    }

    interface PaymentCallback {
        fun onSuccess(response: String)
        fun onError(error: PaymentError)
    }

    fun makePayment(
        customerMsisdn: String,
        amount: Double,
        reason: String,
        currency: String,
        sourceReference: String,
        callback: PaymentCallback
    ) {
        val key = apiKey ?: throw IllegalStateException("EcoCash SDK not initialized. Call init() first.")

        val json = JSONObject().apply {
            put("customerMsisdn", customerMsisdn)
            put("amount", amount)
            put("reason", reason)
            put("currency", currency)
            put("sourceReference", sourceReference)
        }

        val body = json.toString()
            .toRequestBody("application/json".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(apiUrl)
            .addHeader("X-API-KEY", key)
            .addHeader("Content-Type", "application/json")
            .post(body)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onError(PaymentError.NetworkError(e.message ?: "Network failure"))
            }

            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (it.isSuccessful) {
                        val responseBody = it.body?.string() ?: ""
                        callback.onSuccess(responseBody)
                    } else {
                        callback.onError(PaymentError.ServerError(it.code, it.message))
                    }
                }
            }
        })
    }
}
