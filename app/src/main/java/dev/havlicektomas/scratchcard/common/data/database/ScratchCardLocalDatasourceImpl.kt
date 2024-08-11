package dev.havlicektomas.scratchcard.common.data.database

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ScratchCardLocalDatasourceImpl @Inject constructor(
    private val cardDao: ScratchCardDao
): ScratchCardLocalDatasource {

    override fun insertCard(cardEntity: ScratchCardEntity) {
        cardDao.insertCard(cardEntity)
    }

    override fun deleteCards() {
        cardDao.deleteCards()
    }

    override fun getCards(): Flow<List<ScratchCardEntity>> = cardDao.getCard()
}