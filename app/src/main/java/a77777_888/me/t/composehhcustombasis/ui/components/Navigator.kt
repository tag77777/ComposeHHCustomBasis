package a77777_888.me.t.composehhcustombasis.ui.components

import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.navigation.Destination
import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brightness3
import androidx.compose.material.icons.filled.Brightness5
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@SuppressLint("UnusedContentLambdaTargetStateParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator(
    isVertical: Boolean,
    selectedDestination: String,
    navigateToDestination: (Destination) -> Unit,
    onSearch: () -> Unit = {},
    toggleTheme: () -> Unit,
    isDarkTheme: Boolean,
    favoritesListSize: Int
){
    val navigationItemsList: List<NavigationItem> =
        listOf(
            NavigationItem(
                label = R.string.search,
                icon = { Icon( Icons.Default.Search, contentDescription = stringResource(R.string.search)) },
                onClick = onSearch
            ),
            NavigationItem(
                label = R.string.toggle_theme,
                icon = {
                    Icon(
                        if (!isDarkTheme) Icons.Default.Brightness3
                        else Icons.Default.Brightness5,
                        contentDescription = stringResource(id = R.string.toggle_theme)
                    )
                },
                onClick = toggleTheme
            ),
            NavigationItem(
                label = R.string.list,
                selected = selectedDestination == Destination.SearchList.route,
                icon = { Icon( Icons.Default.List, contentDescription = stringResource(R.string.list)) },
                onClick = { navigateToDestination(Destination.SearchList) }
            ),
            NavigationItem(
                label = R.string.favorites,
                selected = selectedDestination == Destination.Favorites.route,
                icon = {
                if (favoritesListSize > 0)
                    BadgedBox(
                        badge = {
                            AnimatedContent(
                                targetState = favoritesListSize,
                                label = "badge",
                                transitionSpec = transitionSpec,
                                ) {
                                Badge(
                                    containerColor = MaterialTheme.colorScheme.secondary, //Color(0xffff0800),
                                    contentColor = Color.White
                                ) {
                                    Text(text = it.toString())
                                }
                            }

                        }
                    ) {
                        Icon( Icons.Default.Favorite, contentDescription = stringResource(R.string.favorites))
                    }
                else
                     Icon( Icons.Default.Favorite, contentDescription = stringResource(R.string.favorites))
            },
                onClick = { navigateToDestination(Destination.Favorites) }
            ),

        )


    if (isVertical)
        AppNavigationRail(
            cornerRadius = 16.dp,
            width = 56.dp,
            containerColor = MaterialTheme.colorScheme.scrim,

        ) {
            Spacer(modifier = Modifier.weight(1f))
            navigationItemsList.take(2).map { navigationItem ->
                NavigationRailItem(
                    selected = navigationItem.selected,
                    onClick = navigationItem.onClick,
                    icon = navigationItem.icon,
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = Color.White,
                        unselectedIconColor = MaterialTheme.colorScheme.primary, //Color.Red,
                        indicatorColor = MaterialTheme.colorScheme.primary, //Color.Red
                    )
                )
            }
            Spacer(modifier = Modifier.weight(0.5f))
            navigationItemsList.takeLast(2).map { navigationItem ->
                NavigationRailItem(
                    selected = navigationItem.selected,
                    onClick = navigationItem.onClick,
                    icon = navigationItem.icon,
                    colors = NavigationRailItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,//Color.White,
                        unselectedIconColor = MaterialTheme.colorScheme.primary, //Color.Red,
                        indicatorColor = MaterialTheme.colorScheme.primary, //Color.Red
                    )
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }

    else
        AppNavigationBar(
            cornerRadius = 16.dp,
            height = 64.dp,
            containerColor = MaterialTheme.colorScheme.scrim,

        ) {
            navigationItemsList.map {navigationItem ->
                NavigationBarItem(
                    selected = navigationItem.selected,
                    onClick = navigationItem.onClick,
                    icon = navigationItem.icon,
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.onPrimary,//Color.White,
                        unselectedIconColor = MaterialTheme.colorScheme.primary, //Color.Red,
                        indicatorColor = MaterialTheme.colorScheme.primary, //Color.Red
                    ),
                )
            }
        }
}


data class NavigationItem(
    val label: Int,
    val selected: Boolean = false,
    val icon: @Composable () -> Unit,
    val onClick: () -> Unit
)

@Composable
fun AppNavigationBar(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 0.dp,
    height: Dp = 80.dp,
    containerColor: Color = NavigationBarDefaults.containerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    tonalElevation: Dp = NavigationBarDefaults.Elevation,
    windowInsets: WindowInsets = NavigationBarDefaults.windowInsets,
    content: @Composable RowScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(topStart = cornerRadius, topEnd = cornerRadius),
        color = containerColor,
        contentColor = contentColor,
        tonalElevation = tonalElevation,
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .windowInsetsPadding(windowInsets)
                .height(height)
                .selectableGroup(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            content = content
        )
    }
}

@Composable
fun AppNavigationRail(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 0.dp,
    width: Dp = 80.dp,
    verticalPadding: Dp = 4.dp,
    headerPadding: Dp = 8.dp,
    containerColor: Color = NavigationRailDefaults.ContainerColor,
    contentColor: Color = MaterialTheme.colorScheme.contentColorFor(containerColor),
    header: @Composable (ColumnScope.() -> Unit)? = null,
    windowInsets: WindowInsets = NavigationRailDefaults.windowInsets,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        shape = RoundedCornerShape(topEnd = cornerRadius, bottomEnd = cornerRadius),
        color = containerColor,
        contentColor = contentColor,
        modifier = modifier,
    ) {
        Column(
            Modifier
                .fillMaxHeight()
                .windowInsetsPadding(windowInsets)
                .widthIn(width)
                .padding(vertical = verticalPadding)
                .selectableGroup(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(verticalPadding)
        ) {
            if (header != null) {
                header()
                Spacer(Modifier.height(headerPadding))
            }
            content()
        }
    }
}

private val transitionSpec: AnimatedContentTransitionScope<Int>.() -> ContentTransform  = {
    if (targetState > initialState) {
        slideInVertically { height -> height } + fadeIn() togetherWith
                slideOutVertically { height -> -height } + fadeOut()
    } else {
        slideInVertically { height -> -height } + fadeIn() togetherWith
                slideOutVertically { height -> height } + fadeOut()
    }
        .using(SizeTransform(clip = false))
}


@Preview(showBackground = true)
@Composable
fun PreviewVerticalAppNavigator() {
    AppNavigator(
        isVertical = true,
        selectedDestination = Destination.Favorites.route,
        navigateToDestination = {},
        onSearch = {},
        toggleTheme = {},
        isDarkTheme = false,
        favoritesListSize = 3
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewHorizontalAppNavigator() {
    AppNavigator(
        isVertical = false,
        selectedDestination = Destination.Favorites.route,
        navigateToDestination = {},
        onSearch = {},
        toggleTheme = {},
        isDarkTheme = false,
        favoritesListSize = 3
    )
}

