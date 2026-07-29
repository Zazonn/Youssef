package com.singular.manager.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val t1 = System.nanoTime()
        println("[SEND] %s %s%n%s".format(request.url, request.method, request.headers))

        val response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            println("[ERROR] HTTP Error: %s".format(e.message))
            throw e
        }

        val t2 = System.nanoTime()
        println("[DEBUG] Received response for %s in %.1fms%n%s".format(
            response.request.url,
            (t2 - t1) / 1e6,
            response.headers
        ))

        val responseBody = response.peekBody(Long.MAX_VALUE)
        println("[DEBUG] Response Body: %s".format(responseBody.string()))

        return response
    }
}
