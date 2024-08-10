package dev.havlicektomas.scratchcard.common.domain

import kotlinx.coroutines.flow.Flow

interface ScratchCardRepo {
    fun getScratchCard(): Flow<List<ScratchCard>>
    suspend fun scratchCard()
    suspend fun activateCard(code: String)
}