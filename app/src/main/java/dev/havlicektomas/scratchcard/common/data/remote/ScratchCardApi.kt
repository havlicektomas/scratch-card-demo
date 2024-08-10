package dev.havlicektomas.scratchcard.common.data.remote

import kotlinx.serialization.Serializable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ScratchCardApi {

    @GET("version")
    suspend fun activate(
        @Query("code") code: String
    ): Response<ActivationResponse>
}

@Serializable
data class ActivationResponse(
    val android: String
)