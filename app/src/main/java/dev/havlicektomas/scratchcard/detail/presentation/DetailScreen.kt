package dev.havlicektomas.scratchcard.detail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.havlicektomas.scratchcard.common.domain.ScratchCard
import dev.havlicektomas.scratchcard.ui.theme.Gold
import dev.havlicektomas.scratchcard.ui.theme.ScratchCardTheme

@Composable
fun DetailScreenRoot(
    viewModel: DetailViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    DetailScreen(
        state = state,
        onNavigate = onNavigate,
        onScratch = { viewModel.scratch() },
        onActivate = { viewModel.activate(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    state: DetailScreenState,
    onNavigate: () -> Unit = {},
    onScratch: () -> Unit = {},
    onActivate: (String) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = onNavigate
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                title = {
                    Text(text = "")
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(240.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (state) {
                        DetailScreenState.Loading -> Color.Transparent
                        is DetailScreenState.Success -> {
                            if (state.card.code == null) Color.Gray else Gold
                        }
                    }
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (state) {
                        DetailScreenState.Loading -> {
                            CircularProgressIndicator()
                        }
                        is DetailScreenState.Success -> {
                            Text(
                                text = state.card.code ?: "",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (state.card.activated) {
                                Text(text = "Activated")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            when (state) {
                DetailScreenState.Loading -> {
                    DetailButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .padding(bottom = 32.dp),
                        text = "Loading",
                        enabled = false
                    )
                }
                is DetailScreenState.Success -> {
                    if (!state.card.activated) {
                        DetailButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 32.dp),
                            text = if (state.card.code == null) "Scratch" else "Activate",
                            enabled = true
                        ) {
                            if (state.card.code == null) {
                                onScratch()
                            } else {
                                onActivate(state.card.code)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DetailButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    onClick: () -> Unit = {}
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenUnscratchedPreview() {
    ScratchCardTheme {
        DetailScreen(
            state = DetailScreenState.Success(
                card = ScratchCard()
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenLoadingPreview() {
    ScratchCardTheme {
        DetailScreen(
            state = DetailScreenState.Loading
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenScratchedPreview() {
    ScratchCardTheme {
        DetailScreen(
            state = DetailScreenState.Success(
                card = ScratchCard(
                    code = "test-test-test-test-test-test-test-test-test-test-test-test"
                )
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenActivatedPreview() {
    ScratchCardTheme {
        DetailScreen(
            state = DetailScreenState.Success(
                card = ScratchCard(
                    code = "test-test-test-test-test-test-test-test-test-test-test-test",
                    activated = true
                )
            )
        )
    }
}