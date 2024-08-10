package dev.havlicektomas.scratchcard.common.domain

data class ScratchCard(
    val code: String? = null,
    val activated: Boolean = false,
    val activationError: String? = null
)
