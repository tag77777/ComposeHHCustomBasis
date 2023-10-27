package a77777_888.me.t.composehhcustombasis.ui

import a77777_888.me.t.composehhcustombasis.MainActivity
import a77777_888.me.t.composehhcustombasis.ui.screens.SplashScreen
import a77777_888.me.t.composehhcustombasis.ui.theme.ComposeHHCustomBasisTheme
import a77777_888.me.t.composehhcustombasis.ui.theme.background
import a77777_888.me.t.composehhcustombasis.ui.theme.md_theme_dark_background
import a77777_888.me.t.composehhcustombasis.ui.theme.md_theme_light_background
import a77777_888.me.t.composehhcustombasis.ui.utils.UiPreferencesStore
import a77777_888.me.t.composehhcustombasis.ui.viewmodel.MainViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {
    @Inject
    lateinit var uiPreferenceStore: UiPreferencesStore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme by uiPreferenceStore.userPreferencesFlow
                .collectAsState(initial = isSystemInDarkTheme())

            ComposeHHCustomBasisTheme(isDarkTheme) {
                Surface(
                    color = Color.Transparent,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(background(isDark = isDarkTheme)),
                ) {
                    val viewModel: MainViewModel = hiltViewModel()

                    val systemUiController = rememberSystemUiController(window)
                    SideEffect {
                            systemUiController.setNavigationBarColor(
                                color = if (isDarkTheme) md_theme_dark_background else md_theme_light_background
                            )
                    }

                    SplashScreen(
                        goToMainActivity = {
                            val intent = Intent(this, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        },
                        getAreas = viewModel::loadAreas,
                        cancel = { finish() },
                        areasStateFlow = viewModel.areasStateFlow
                    )
                }
            }
        }
    }
}