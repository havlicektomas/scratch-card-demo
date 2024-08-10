package dev.havlicektomas.scratchcard.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.havlicektomas.scratchcard.common.domain.ScratchCardRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: ScratchCardRepo
): ViewModel() {

    private val _state = MutableStateFlow<HomeScreenState>(HomeScreenState.Unscratched)
    val state: StateFlow<HomeScreenState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getScratchCard().collect { cards ->
                if (cards.isNotEmpty()) {
                    val card = cards.first()
                    if (card.activated) {
                        _state.update {
                            HomeScreenState.Activated(card)
                        }
                    } else {
                        _state.update {
                            HomeScreenState.Scratched(card)
                        }
                    }
                } else {
                    _state.update {
                        HomeScreenState.Unscratched
                    }
                }
            }
        }
    }
}