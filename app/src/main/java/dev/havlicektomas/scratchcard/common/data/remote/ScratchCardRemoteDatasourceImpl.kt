package dev.havlicektomas.scratchcard.common.data.remote

import retrofit2.Response
import javax.inject.Inject

class ScratchCardRemoteDatasourceImpl @Inject constructor(
    private val cardApi: ScratchCardApi
): ScratchCardRemoteDatasource {

    override suspend fun activate(code: String): Response<ActivationResponse> {
        return cardApi.activate(code)
    }
}