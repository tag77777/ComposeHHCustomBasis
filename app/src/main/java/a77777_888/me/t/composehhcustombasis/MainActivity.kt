package a77777_888.me.t.composehhcustombasis

import a77777_888.me.t.composehhcustombasis.ui.components.AppNavigator
import a77777_888.me.t.composehhcustombasis.ui.components.drawer.DrawerContent
import a77777_888.me.t.composehhcustombasis.ui.navigation.Destination
import a77777_888.me.t.composehhcustombasis.ui.screens.EmployerScreen
import a77777_888.me.t.composehhcustombasis.ui.screens.FavoritesListScreen
import a77777_888.me.t.composehhcustombasis.ui.screens.SearchListScreen
import a77777_888.me.t.composehhcustombasis.ui.screens.VacancyScreen
import a77777_888.me.t.composehhcustombasis.ui.theme.ComposeHHCustomBasisTheme
import a77777_888.me.t.composehhcustombasis.ui.theme.background
import a77777_888.me.t.composehhcustombasis.ui.theme.header
import a77777_888.me.t.composehhcustombasis.ui.utils.UiPreferencesStore
import a77777_888.me.t.composehhcustombasis.ui.viewmodel.MainViewModel
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var uiPreferenceStore: UiPreferencesStore

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme by uiPreferenceStore.userPreferencesFlow
                .collectAsState(initial = isSystemInDarkTheme())
            val toggleTheme: () -> Unit = {
                lifecycleScope.launch {
                    uiPreferenceStore.updateThemeChanges(!isDarkTheme)
                }
            }
            val isExpandedScreen = calculateWindowSizeClass(this).widthSizeClass ==
                    WindowWidthSizeClass.Expanded

            ComposeHHCustomBasisTheme(darkTheme = isDarkTheme) {
                Box(
                    modifier = Modifier
                        .background(background(isDark = isDarkTheme))
                        .fillMaxSize()
                ) {
                    Main(
                        toggleTheme = toggleTheme,
                        isDarkTheme = isDarkTheme,
                        cancel = { finish() },
                        isExpandedScreen = isExpandedScreen
                    )
                }
            }
        }
    }

}


@Composable
fun Main(
    toggleTheme: () -> Unit,
    isDarkTheme: Boolean,
    cancel: () -> Unit,
    isExpandedScreen: Boolean,
) {
    val viewModel = hiltViewModel<MainViewModel>()

    val favoritesList = viewModel.favoritesListFlow.collectAsStateWithLifecycle(initialValue = listOf()).value

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    if (viewModel.uiState.masterNavController == null) {
        viewModel.uiState.masterNavController = rememberNavController()
    }
    val masterNavController = viewModel.uiState.masterNavController!!
    val navBackStackEntry by masterNavController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: Destination.SearchList.route

    if (viewModel.uiState.detailsNavController == null) {
        viewModel.uiState.detailsNavController = rememberNavController()
    }
    val detailsNavController = viewModel.uiState.detailsNavController!!

    var currentVacancyId: String by viewModel.uiState

    fun navigateTo(navHostController: NavHostController, destination: Destination) {
        navHostController.navigate(destination.route) {
            launchSingleTop = true
            if (navHostController == masterNavController) restoreState = true
        }

    }

    fun navigateToVacancy(id: String)  {
        currentVacancyId = id
        navigateTo(
            if (isExpandedScreen) detailsNavController else masterNavController,
            Destination.VacancyDetails
        )
    }

    fun  NavGraphBuilder.details(navHostController: NavHostController): NavGraphBuilder {
        composable(Destination.VacancyDetails.route) {
                VacancyScreen(
                    vacancyId = currentVacancyId,
                    isExpandedScreen = isExpandedScreen,
                    navigateToDestination = { destination ->
                        navigateTo(navHostController, destination)
                    },
                    addFavorite = viewModel::addFavorite,
                    deleteFavorite = viewModel::deleteFavorite,
                    favoritesListFlow = viewModel.favoritesListFlow,
                    cancel = cancel,
                    loadVacancy = viewModel::loadVacancy,
                    vacancyStateFlow = viewModel.vacancyStateFlow
                )

        }
        composable(Destination.EmployerDetails("{employerId}").route) {
            val id = it.arguments?.getString("employerId")?.ifBlank { null }
            id?.let { employerId ->
                EmployerScreen(
                    id = employerId,
                    cancel = cancel,
                    loadEmployer = viewModel::loadEmployer,
                    employerStateFlow = viewModel.employerStateFlow
                )
            }
        }
        return this
    }

    fun NavGraphBuilder.main(): NavGraphBuilder {
        composable(Destination.SearchList.route) {
            SearchListScreen(
                navigateToVacancy = ::navigateToVacancy,
                addFavorite = viewModel::addFavorite,
                deleteFavorite = viewModel::deleteFavorite,
                cansel = cancel,
                favoritesListFlow = viewModel.favoritesListFlow,
                pagerFlow = viewModel.vacanciesListPagerFlow,
            )
        }
        composable(Destination.Favorites.route) {
            FavoritesListScreen(
                navigateToVacancy = ::navigateToVacancy,
                addFavorite = viewModel::addFavorite,
                deleteFavorite = viewModel::deleteFavorite,
                favoritesListFlow = viewModel.favoritesListFlow
            )
        }

        if (!isExpandedScreen)
            details(masterNavController)

        return this
    }

    ModalDrawer(
        drawerBackgroundColor = MaterialTheme.colorScheme.scrim,
        drawerState = drawerState,
        gesturesEnabled = false,
        drawerContent = {
            Text(
                text = stringResource(id = R.string.search),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 32.dp),
                style = header(),
                textAlign = TextAlign.Center
            )

            DrawerContent(
                viewModel = viewModel,
                navigateToSearchList = { navigateTo(masterNavController, Destination.SearchList) },
                scope = scope,
                drawerState = drawerState
            )
        }

    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(background(isDark = isDarkTheme))
        ) {

            AnimatedVisibility(visible = isExpandedScreen) {
                AppNavigator(
                    isVertical = true,
                    selectedDestination = selectedDestination,
                    navigateToDestination = {
                        navigateTo(masterNavController, it)
                    },
                    toggleTheme = toggleTheme,
                    isDarkTheme = isDarkTheme,
                    favoritesListSize = favoritesList.size,
                    onSearch = {
                        scope.launch { drawerState.open() }
                    }
                )
            }

            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    AnimatedVisibility(visible = !isExpandedScreen) {
                        AppNavigator(
                            isVertical = false,
                            selectedDestination = selectedDestination,
                            navigateToDestination = {
                                navigateTo(masterNavController, it)
                            },
                            toggleTheme = toggleTheme,
                            isDarkTheme = isDarkTheme,
                            favoritesListSize = favoritesList.size,
                            onSearch = {
                                scope.launch { drawerState.open() }
                            }
                        )
                    }
                }
            ) { contentPadding ->
                if (isExpandedScreen)
                    Row(
                        modifier = Modifier.padding(contentPadding)
                    ) {
                        NavHost(
                            navController = masterNavController,
                            startDestination = Destination.SearchList.route,
                            builder = { main() },
                            modifier = Modifier.weight(1f)
                        )

                        NavHost(
                            navController = detailsNavController,
                            startDestination = Destination.VacancyDetails.route,
                            builder = { details(detailsNavController) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                else
                    Box(modifier = Modifier.padding(contentPadding)) {
                        NavHost(
                            navController = masterNavController,
                            startDestination = Destination.SearchList.route,
                            builder = { main() }
                        )
                    }

            }

        }
    }


}


