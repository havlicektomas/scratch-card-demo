package dev.havlicektomas.scratchcard.detail.presentation

import dev.havlicektomas.scratchcard.common.domain.ScratchCard

sealed interface DetailScreenState {
    data object Loading: DetailScreenState
    data class Success(val card: ScratchCard): DetailScreenState
}