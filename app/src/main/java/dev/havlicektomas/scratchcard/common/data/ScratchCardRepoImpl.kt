package dev.havlicektomas.scratchcard.common.data

import android.util.Log
import dev.havlicektomas.scratchcard.common.data.database.ScratchCardDao
import dev.havlicektomas.scratchcard.common.data.database.ScratchCardEntity
import dev.havlicektomas.scratchcard.common.data.remote.ScratchCardApi
import dev.havlicektomas.scratchcard.common.domain.ScratchCard
import dev.havlicektomas.scratchcard.common.domain.ScratchCardRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ScratchCardRepoImpl @Inject constructor(
    private val cardDao: ScratchCardDao,
    private val cardApi: ScratchCardApi
): ScratchCardRepo {

    override fun getScratchCard(): Flow<List<ScratchCard>> {
        return cardDao.getCard().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun scratchCard() {
        delay(2.seconds)
        cardDao.deleteCards()
        cardDao.insertCard(
            ScratchCardEntity(
                code = UUID.randomUUID().toString(),
                activated = false,
                activationError = null
            )
        )
    }

    override suspend fun activateCard(code: String) {
        val response = cardApi.activate(code)
        if (response.isSuccessful) {
            Log.d("ScratchCardRepoImpl", "activateCard - response: ${response.body()}")
            response.body()?.let {
                val value = it.android.toInt()
                if (value > 277028) {
                    saveToDb(
                        code = code,
                        activated = true,
                        activationError = null
                    )
                } else {
                    saveToDb(
                        code = code,
                        activated = false,
                        activationError = "Activation error"
                    )
                }
            }
        } else {
            saveToDb(
                code = code,
                activated = false,
                activationError = "Network error"
            )
        }
    }

    private fun saveToDb(
        code: String,
        activated: Boolean,
        activationError: String?
    ) {
        cardDao.deleteCards()
        cardDao.insertCard(
            ScratchCardEntity(
                code = code,
                activated = activated,
                activationError = activationError
            )
        )
    }

    private fun ScratchCardEntity.toDomain() = ScratchCard(
        code = code,
        activated = activated,
        activationError = activationError
    )
}