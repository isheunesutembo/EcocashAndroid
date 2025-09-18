# 📱 EcoCash Android SDK

A simple Android SDK for integrating **EcoCash C2B (Customer-to-Business) Payments** into your mobile applications.  
Supports both **Sandbox** and **Live** environments.  

---

## 🚀 Features
- ✅ Easy initialization with **API Key**  
- ✅ Built-in support for **Sandbox** and **Live** environments  
- ✅ Asynchronous network calls using **OkHttp**  
- ✅ Callback-based success & error handling  

---

## 📦 Installation

### Option 1: Local (Maven Local)
```gradle
repositories {
    mavenLocal()
    google()
    mavenCentral()
}

dependencies {
    implementation "com.github.yourusername:ecocashsdk:1.0.0"
}

dependencyResolutionManagement {
    repositories {
        maven { url 'https://jitpack.io' }
        google()
        mavenCentral()
    }
}
dependencies {
    implementation 'com.github.yourusername:EcoCashSDK:1.0.0'
}
⚡ Usage
Initialize the SDK
EcoCash.init(
    key = "YOUR_API_KEY",
    environment = EcoCash.Environment.SANDBOX // or LIVE
)

Make a Payment
EcoCash.makePayment(
    customerMsisdn = "263771234567",
    amount = 10.0,
    reason = "Payment for Order",
    currency = "USD",
    sourceReference = "ORDER123",
    callback = object : EcoCash.PaymentCallback {
        override fun onSuccess(response: String) {
            Log.d("EcoCash", "Payment success: $response")
        }

        override fun onError(error: PaymentError) {
            Log.e("EcoCash", "Payment failed: $error")
        }
    }
)

🔑 Environments

Sandbox → Testing environment

Live → Production environment

EcoCash.init("YOUR_API_KEY", EcoCash.Environment.SANDBOX) // testing
EcoCash.init("YOUR_API_KEY", EcoCash.Environment.LIVE)    // l

