package dev.havlicektomas.scratchcard.common.data.compute

interface ScratchCardCodeProvider {
    suspend fun computeCode(): String
}