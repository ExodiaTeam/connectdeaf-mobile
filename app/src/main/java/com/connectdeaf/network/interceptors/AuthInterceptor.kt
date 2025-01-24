package com.connectdeaf.network.interceptors

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val getToken: () -> String?) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if (request.url.encodedPath.contains("/api/auth/login")) {
            return chain.proceed(request)
        }

        val token = getToken()
        val requestBuilder = chain.request().newBuilder()

        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
