package dev.havlicektomas.scratchcard.common.data.database

import kotlinx.coroutines.flow.Flow

interface ScratchCardLocalDatasource {
    fun insertCard(cardEntity: ScratchCardEntity)
    fun deleteCards()
    fun getCards(): Flow<List<ScratchCardEntity>>
}