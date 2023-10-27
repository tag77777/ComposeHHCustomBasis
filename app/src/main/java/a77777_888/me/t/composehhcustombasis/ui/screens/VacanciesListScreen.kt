package a77777_888.me.t.composehhcustombasis.ui.screens

import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.components.ErrorMessage
import a77777_888.me.t.composehhcustombasis.ui.components.LoadingMessage
import a77777_888.me.t.composehhcustombasis.ui.theme.AppTypography
import a77777_888.me.t.composehhcustombasis.ui.theme.header
import a77777_888.me.t.composehhcustombasis.ui.utils.dpToSp
import a77777_888.me.t.composehhcustombasis.ui.utils.toPatternDateStringFromISO8601String
import a77777_888.me.t.data.remote.EmptyListException
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.Pager
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Composable
fun SearchListScreen(
    navigateToVacancy: (id: String) -> Unit,
    addFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    deleteFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    favoritesListFlow: Flow<List<IVacanciesListItem>>,
    cansel: () -> Unit,
    pagerFlow: Flow<Pager<Int, VacanciesListItem>?>
) {
    val scope = rememberCoroutineScope()
    val favoritesList = favoritesListFlow.collectAsState(initial = emptyList()).value

    val pager = pagerFlow.collectAsState(initial = null)

    val lazyPagingItems: LazyPagingItems<VacanciesListItem>? =
        pager.value?.flow?.collectAsLazyPagingItems()

    LaunchedEffect(key1 = pager, block = {
        lazyPagingItems?.retry()
    })

    lazyPagingItems?.let {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            state = rememberLazyListState()
        ) {
            items(
                count = lazyPagingItems.itemCount,
                key = lazyPagingItems.itemKey { it.id }
            ) { index ->
                val vacancyItem = lazyPagingItems[index]
                if (vacancyItem != null)
                    VacanciesItemScreen(
                        navigateToVacancy = navigateToVacancy,
                        scope = scope,
                        addFavorite = addFavorite,
                        deleteFavorite = deleteFavorite,
                        favoritesList = favoritesList,
                        item = vacancyItem
                    )
                else Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .requiredHeight(200.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }
            }


        }

        lazyPagingItems.apply {
            if (loadState.append is LoadState.Loading || loadState.refresh is LoadState.Loading)
                Box(modifier = Modifier.fillMaxSize()) {
                    LoadingMessage()
                }


            val errorLoadState =
                (loadState.append as? LoadState.Error) ?: (loadState.refresh as? LoadState.Error)
            errorLoadState?.let {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {

                    if (it.error is EmptyListException) {
                        Text(
                            text = stringResource(id = R.string.empty_list),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 20.dpToSp,
                            fontStyle = FontStyle.Italic,
                        )
                    } else {
                        ErrorMessage(
                            tryAgain = { lazyPagingItems.refresh() },
                            cansel = cansel,
                            message = stringResource(id = R.string.error_oops)
                        )
                    }
                }


            }

        }
    }


}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesListScreen(
    navigateToVacancy: (id: String) -> Unit,
    addFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    deleteFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    favoritesListFlow: Flow<List<IVacanciesListItem>>
) {
    val scope = rememberCoroutineScope()
    val favoritesList = favoritesListFlow.collectAsState(initial = emptyList()).value

    if (favoritesList.isEmpty())
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stringResource(id = R.string.empty_list),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 20.dpToSp,
                fontStyle = FontStyle.Italic,
            )
        }
    else
        LazyColumn(
            state = rememberLazyListState()
        ) {
            stickyHeader {
                Text(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    text = stringResource(id = R.string.favorites),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp),
                    style = header(),
                    textAlign = TextAlign.Center
                )
            }
            items(
                count = favoritesList.size,
                key = { index -> favoritesList[index].id }
            ) { index ->
                VacanciesItemScreen(
                    navigateToVacancy = navigateToVacancy,
                    scope = scope,
                    addFavorite = addFavorite,
                    deleteFavorite = deleteFavorite,
                    favoritesList = favoritesList,
                    item = (favoritesList[index] as? VacanciesListItem)
                        ?: VacanciesListItem(favoritesList[index])
                )
            }
        }
}


@Composable
fun VacanciesItemScreen(
    navigateToVacancy: (id: String) -> Unit,
    scope: CoroutineScope,
    addFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    deleteFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    favoritesList: List<IVacanciesListItem>,
    item: VacanciesListItem

) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
            .clickable {
                navigateToVacancy(item.id)
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        )
    ) {

        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.hh_bg),
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.lighting(
                    MaterialTheme.colorScheme.surfaceVariant,
                    Color.Transparent
                )
            )

            with(item) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navigateToVacancy(item.id) }
                ) {
                    // name && favorite
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = name,
                            textAlign = TextAlign.Center,
                            style = AppTypography.titleMedium,
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        )

                        Button(
                            onClick = {
                                if (favoritesList.any { it.id == item.id })
                                    deleteFavorite(item, scope)
                                else addFavorite(item, scope)
                            },
                            modifier = Modifier
                                .background(color = Color.White, shape = CircleShape)
                                .size(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Red
                            ),
                            contentPadding = PaddingValues(
                                horizontal = 0.dp,
                                vertical = 10.dp
                            )
                        ) {
                            Icon(
                                imageVector = if (favoritesList.any { it.id == item.id }) Icons.Default.Favorite
                                else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.Red,
                            )
                        }
                    }

                    // counters && salary
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    ) {
                        counters?.let {
                            Text(
                                text = stringResource(R.string.responds, it.responses, it.total_responses),
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))
                        salary?.let {
                            Text(
                                text = salary.toString(),
                                style = AppTypography.titleSmall,
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        employer.logoUrls?.let {
                            AsyncImage(
                                model = (employer.logoUrls!!.logo90),
                                contentDescription = null,
                                modifier = Modifier
                                    .requiredSize(56.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        }

                        Column(
                            modifier = Modifier.padding(start = 16.dp)
                        ) {
                            Text(
                                text = employer.name,
                                style = AppTypography.titleSmall,
                            )
                            Text(text = stringResource(R.string.placed, area.name))
                        }
                    }
                    // publishedAt
                    Text(
                        text = publishedAt.toPatternDateStringFromISO8601String(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp),
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}

