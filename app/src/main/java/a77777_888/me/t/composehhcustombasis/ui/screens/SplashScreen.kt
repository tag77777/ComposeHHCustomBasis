package a77777_888.me.t.composehhcustombasis.ui.screens

import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.components.ErrorMessage
import a77777_888.me.t.composehhcustombasis.ui.components.LoadingMessage
import a77777_888.me.t.composehhcustombasis.ui.utils.dpToSp
import a77777_888.me.t.composehhcustombasis.ui.viewmodel.DataLoadState
import a77777_888.me.t.data.repositories.AreasRepository
import a77777_888.me.t.domain.dataentities.areas.Areas
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SplashScreen(
    goToMainActivity: () -> Unit,
    getAreas: () -> Unit,
    cancel: () -> Unit,
    areasStateFlow: Flow<DataLoadState<List<Areas>>>
) {
    val areasState by areasStateFlow.collectAsState(initial = DataLoadState())

    var getAreasIsStarted by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val hhAnimatable = remember { Animatable(0f) }
        val titleAnimatable = remember { Animatable(0f) }

        LaunchedEffect(key1 = 0) {
            delay(500)
            hhAnimatable.animateTo(1f, tween(durationMillis = 1500))
            titleAnimatable.animateTo(1f, tween(durationMillis = 2000))
            delay(2000)
            getAreasIsStarted = true
            getAreas()
        }


        Box(
            modifier = Modifier
                .size(300.dp)
                .align(Alignment.Center)
                .offset(y = (-96).dp)
                .graphicsLayer(alpha = hhAnimatable.value)
        ) {
            Text(
                text = "hh.ru",
                modifier = Modifier
                    .align(Alignment.Center)
                    .drawBehind {
                        drawCircle(
                            color = Color.Red,
                            radius = 255f
                        )
                    },
                fontSize = 64.dpToSp,
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
            )
        }

        Text(
            text = "Custom Basis",
            style = TextStyle(
                fontSize = 32.dpToSp,
                fontWeight = FontWeight.Bold,
                color = Color.White, //MaterialTheme.colorScheme.background,
                shadow = Shadow(
                    color = contentColorFor(MaterialTheme.colorScheme.background),
                    blurRadius = 50f
                )
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .offset(y = 48.dp)
                .padding(vertical = 24.dp)
                .graphicsLayer(alpha = titleAnimatable.value)
        )

        Box(
            modifier = Modifier
                .requiredHeight(120.dp)
                .align(Alignment.BottomCenter)
        ) {


            when {
                areasState.content != null -> {
                    AreasRepository.areas = areasState.content
                    goToMainActivity()
                }

                areasState.error != null -> {
                    ErrorMessage(
                        tryAgain = { getAreas() },
                        cansel = { cancel() },
                        message = areasState.error!!.ifBlank { stringResource(id = R.string.error_oops) }
                    )
                }

                getAreasIsStarted -> LoadingMessage()

                else -> {}
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSplash() {
    SplashScreen(
        {}, {}, {},
        flowOf(DataLoadState(error = stringResource(id = R.string.error_oops)))
    )
}

