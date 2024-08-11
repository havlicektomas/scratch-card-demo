package dev.havlicektomas.scratchcard.common.data

import dev.havlicektomas.scratchcard.common.data.compute.ScratchCardCodeProvider
import dev.havlicektomas.scratchcard.common.data.database.ScratchCardEntity
import dev.havlicektomas.scratchcard.common.data.database.ScratchCardLocalDatasource
import dev.havlicektomas.scratchcard.common.data.remote.ScratchCardRemoteDatasource
import dev.havlicektomas.scratchcard.common.domain.ScratchCard
import dev.havlicektomas.scratchcard.common.domain.ScratchCardRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScratchCardRepoImpl @Inject constructor(
    private val localDatasource: ScratchCardLocalDatasource,
    private val remoteDatasource: ScratchCardRemoteDatasource,
    private val codeProvider: ScratchCardCodeProvider
): ScratchCardRepo {

    companion object {
        const val MINIMUM_VALUE = 277028
    }

    override fun getScratchCard(): Flow<List<ScratchCard>> {
        return localDatasource.getCards().map { entities ->
            entities.map { entity ->
                entity.toDomain()
            }
        }
    }

    override suspend fun scratchCard() {
        val code = codeProvider.computeCode()
        saveToDb(
            code = code,
            activated = false,
            activationError = null
        )
    }

    override suspend fun activateCard(code: String) {
        val response = remoteDatasource.activate(code)
        if (response.isSuccessful) {
            response.body()?.let {
                if (isValidResponse(it.android)) {
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

    private fun isValidResponse(response: String): Boolean {
        val value = response.toIntOrNull()
        return value != null && value > MINIMUM_VALUE
    }

    private fun saveToDb(
        code: String,
        activated: Boolean,
        activationError: String?
    ) {
        localDatasource.deleteCards()
        localDatasource.insertCard(
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