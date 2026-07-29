package com.singular.manager.domain.util

import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import android.util.Base64

object HmacSha256Util {

    fun calculateHmac(data: String, key: String): String {
        return try {
            val secretKeySpec = SecretKeySpec(key.toByteArray(), "HmacSHA256")
            val mac = Mac.getInstance("HmacSHA256")
            mac.init(secretKeySpec)
            val hmacBytes = mac.doFinal(data.toByteArray())
            Base64.encodeToString(hmacBytes, Base64.NO_WRAP)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}
