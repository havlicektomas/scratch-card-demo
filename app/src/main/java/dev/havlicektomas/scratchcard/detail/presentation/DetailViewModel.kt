package dev.havlicektomas.scratchcard.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.havlicektomas.scratchcard.common.domain.ScratchCard
import dev.havlicektomas.scratchcard.common.domain.ScratchCardRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repo: ScratchCardRepo
): ViewModel() {

    private val _state = MutableStateFlow<DetailScreenState>(DetailScreenState.Success(ScratchCard()))
    val state: StateFlow<DetailScreenState>
        get() = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getScratchCard().collect { cards ->
                if (cards.isNotEmpty()) {
                    _state.update {
                        DetailScreenState.Success(card = cards.first())
                    }
                }
            }
        }
    }

    fun scratch() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { DetailScreenState.Loading }
            repo.scratchCard()
        }
    }

    fun activate(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { DetailScreenState.Loading }
            repo.activateCard(code)
        }
    }
}