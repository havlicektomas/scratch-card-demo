package dev.havlicektomas.scratchcard.common.data.remote

import retrofit2.Response

interface ScratchCardRemoteDatasource {
    suspend fun activate(code: String): Response<ActivationResponse>
}