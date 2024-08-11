package dev.havlicektomas.scratchcard.home.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.havlicektomas.scratchcard.common.domain.ScratchCard
import dev.havlicektomas.scratchcard.ui.theme.Gold
import dev.havlicektomas.scratchcard.ui.theme.ScratchCardTheme

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onNavigate = onNavigate
    )
}

@Composable
fun HomeScreen(
    state: HomeScreenState,
    onNavigate: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 48.dp, bottom = 16.dp)
                    .padding(horizontal = 16.dp)
                    .height(160.dp),
                colors = CardDefaults.cardColors(
                    containerColor = when (state) {
                        HomeScreenState.Unscratched -> Color.Gray
                        is HomeScreenState.Scratched  -> Gold
                        is HomeScreenState.Activated -> Gold
                    }
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (state) {
                        HomeScreenState.Unscratched -> {}
                        is HomeScreenState.Scratched  -> {
                            CodeText(
                                text = state.card.code ?: "",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }
                        is HomeScreenState.Activated -> {
                            CodeText(
                                text = state.card.code ?: "",
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Text(text = "Activated")
                        }
                    }
                }
            }

            when (state) {
                HomeScreenState.Unscratched -> {
                    HomeButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = "Scratch",
                        onNavigate = onNavigate,
                    )
                }
                is HomeScreenState.Scratched  -> {
                    HomeButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        text = "Activate",
                        onNavigate = onNavigate,
                    )
                }
                is HomeScreenState.Activated -> {}
            }
        }
    }
}

@Composable
fun CodeText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        modifier = modifier,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
fun HomeButton(
    modifier: Modifier = Modifier,
    text: String,
    onNavigate: () -> Unit = {}
) {
    Button(
        onClick = onNavigate,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenUnscratchedPreview() {
    ScratchCardTheme {
        HomeScreen(
            state = HomeScreenState.Unscratched
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenScratchedPreview() {
    ScratchCardTheme {
        HomeScreen(
            state = HomeScreenState.Scratched(
                card = ScratchCard(code = "test-test-test-test-test-test-test-test-test-test-test-test")
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenActivatedPreview() {
    ScratchCardTheme {
        HomeScreen(
            state = HomeScreenState.Activated(
                card = ScratchCard(
                    code = "test-test-test-test-test-test-test-test-test-test-test-test",
                    activated = true
                )
            )
        )
    }
}