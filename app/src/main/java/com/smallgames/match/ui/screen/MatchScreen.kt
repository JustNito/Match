package com.smallgames.match.ui.screen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.smallgames.match.R
import com.smallgames.match.model.FruitModel
import com.smallgames.match.ui.theme.MatchTheme
import com.smallgames.match.utils.Fruits
import com.smallgames.match.utils.GameStatus
import com.smallgames.match.utils.intToTime
import com.smallgames.match.viewmodel.MatchViewModel

@Composable
fun MatchScreen(matchViewModel: MatchViewModel){
    Match(
        fruits = matchViewModel.fruits,
        onCardClick = matchViewModel::onCardClick,
        time = matchViewModel.time,
        gameStatus = matchViewModel.gameStatus
    )
    if(matchViewModel.gameStatus == GameStatus.EndGame) {
        GameOver(
            playAgain = matchViewModel::restartGame,
            time = matchViewModel.time,
            bestTime = matchViewModel.bestTime
        )
    }
}

@Composable
fun Match(
    fruits: List<FruitModel>,
    onCardClick: (Int) -> Unit,
    time: Int,
    gameStatus: GameStatus
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Timer(
            time = time
        )
        FruitCards(
            gameStatus = gameStatus,
            fruits = fruits,
            onCardClick = onCardClick
        )
    }
}

@Composable
fun Timer(
    modifier: Modifier = Modifier,
    time: Int,
) {
    Text(
        modifier = modifier,
        color =
            if(time != 0)
                MaterialTheme.colors.secondaryVariant
            else
                Color.Black,
        text = (intToTime(time)),
        style = MaterialTheme.typography.h2.copy(fontWeight = FontWeight.Bold)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FruitCards(
    fruits: List<FruitModel>,
    onCardClick: (Int) -> Unit,
    gameStatus: GameStatus
) {
    val configuration = LocalConfiguration.current
    LazyVerticalGrid(
        modifier = Modifier.padding(4.dp),
        cells = GridCells.Fixed( count =
            if(configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                8
            else
                4
        ),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(fruits) { fruit ->
            FruitCard(
                fruit = fruit,
                gameStatus = gameStatus,
                onCardClick = onCardClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FruitCard(
    fruit: FruitModel,
    gameStatus: GameStatus,
    onCardClick: (Int) -> Unit
) {
    Card(
        onClick = {
            if(gameStatus != GameStatus.ShowCards && !fruit.showFruit)
                onCardClick(fruit.id)
        },
        modifier = Modifier.size(width = 50.dp, height = 80.dp),
        backgroundColor =
            if(fruit.showFruit)
                MaterialTheme.colors.secondary
            else
                MaterialTheme.colors.primary,
        border = BorderStroke(
            width = 6.dp,
            color = if(fruit.showFruit)
                        MaterialTheme.colors.secondaryVariant
                     else
                        MaterialTheme.colors.primaryVariant
        )
    ) {
        if(fruit.showFruit) {
            FruitImage(
                modifier = Modifier.padding(8.dp),
                fruit = fruit.fruit
            )
        } else {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    color = MaterialTheme.colors.secondary,
                    text = "?",
                    style = MaterialTheme.typography.h3
                )
            }
        }
    }
}

@Composable
fun FruitImage(modifier: Modifier = Modifier, fruit: Fruits) {
    Image(
        modifier = modifier,
        painter = painterResource(id = when(fruit) {
            Fruits.AVOCADO -> R.drawable.avocado
            Fruits.BANANA -> R.drawable.banana
            Fruits.BLUEBERRY -> R.drawable.blueberry
            Fruits.CHERRY -> R.drawable.cherry
            Fruits.GRAPE -> R.drawable.grape
            Fruits.KIWI -> R.drawable.kiwi
            Fruits.LEMON -> R.drawable.lemon
            Fruits.ORANGE -> R.drawable.orange
            Fruits.PEACH -> R.drawable.peach
            Fruits.PEAR -> R.drawable.pear
            Fruits.PINEAPPLE -> R.drawable.pineapple
            Fruits.POMEGRANATE -> R.drawable.pomegranate
        }),
        contentDescription = "fruit image"
    )
}

@Composable
fun GameOver(
    playAgain: () -> Unit,
    time: Int,
    bestTime: Int
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Black.copy(alpha = 0.7F)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.h3.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colors.secondaryVariant,
                        offset = Offset(5.0f, 10.0f),
                        blurRadius = 3f
                    )
                ),
                color = MaterialTheme.colors.secondary,
                text = "Time: ${intToTime(time)}"
            )
            Text(
                style = MaterialTheme.typography.h5.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colors.secondaryVariant,
                        offset = Offset(5.0f, 10.0f),
                        blurRadius = 3f
                    )
                ),
                color = MaterialTheme.colors.secondary,
                text = "Best time: ${intToTime(bestTime)}"
            )
            Button(onClick = playAgain) {
                Text(
                    text = "Play Again",
                    color = MaterialTheme.colors.secondary
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFruitCardFront() {
    MatchTheme {
        FruitCard(
            fruit = FruitModel(
                id = 0, fruit = Fruits.AVOCADO, showFruit = true
            ),
            onCardClick = {},
            gameStatus = GameStatus.GameInProgress
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewFruitCardBack() {
    MatchTheme {
        FruitCard(
            fruit = FruitModel(
                id = 0, fruit = Fruits.AVOCADO, showFruit = false
            ),
            onCardClick = {},
            gameStatus = GameStatus.GameInProgress
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGameOver() {
    MatchTheme() {
        GameOver(playAgain = { /*TODO*/ }, time = 1000, bestTime = 1500)
    }
}