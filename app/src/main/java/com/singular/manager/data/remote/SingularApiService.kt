package com.singular.manager.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface SingularApiService {
    @POST
    suspend fun sendSingularEvent(
        @Url url: String,
        @Body body: Map<String, Any>
    ): retrofit2.Response<Void>
}
