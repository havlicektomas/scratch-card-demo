package dev.havlicektomas.scratchcard.home.presentation

import dev.havlicektomas.scratchcard.common.domain.ScratchCard

sealed interface HomeScreenState {
    data object Unscratched: HomeScreenState
    data class Scratched(val card: ScratchCard): HomeScreenState
    data class Activated(val card: ScratchCard): HomeScreenState
}
