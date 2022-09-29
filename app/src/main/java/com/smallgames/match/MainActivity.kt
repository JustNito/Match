package com.smallgames.match

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.smallgames.match.ui.screen.MatchScreen
import com.smallgames.match.ui.theme.MatchTheme
import com.smallgames.match.viewmodel.MatchViewModel
import com.smallgames.match.viewmodel.MatchViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var matchViewModelFactory: MatchViewModelFactory
    val matchViewModel: MatchViewModel by viewModels {
        matchViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            MatchTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background)
                ) {
                    MatchScreen(matchViewModel)
                }
            }
        }
    }
}

