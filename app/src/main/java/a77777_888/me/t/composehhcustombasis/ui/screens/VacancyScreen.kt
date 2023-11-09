package a77777_888.me.t.composehhcustombasis.ui.screens

import a77777_888.me.t.composehhcustombasis.MainActivity
import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.components.ErrorMessage
import a77777_888.me.t.composehhcustombasis.ui.components.LoadingMessage
import a77777_888.me.t.composehhcustombasis.ui.navigation.Destination
import a77777_888.me.t.composehhcustombasis.ui.theme.AppTypography
import a77777_888.me.t.composehhcustombasis.ui.theme.header
import a77777_888.me.t.composehhcustombasis.ui.utils.dpToSp
import a77777_888.me.t.composehhcustombasis.ui.utils.parseHtml
import a77777_888.me.t.composehhcustombasis.ui.utils.toPatternDateStringFromISO8601String
import a77777_888.me.t.composehhcustombasis.ui.viewmodel.DataLoadState
import a77777_888.me.t.domain.dataentities.IVacanciesListItem
import a77777_888.me.t.domain.dataentities.IVacancy
import a77777_888.me.t.domain.dataentities.vacancies.VacanciesListItem
import a77777_888.me.t.domain.dataentities.vacancy.VacancyResponseEntity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Composable
fun VacancyScreen(
    vacancyId: String,
    isExpandedScreen: Boolean,
    navigateToDestination: (Destination) -> Unit,
    addFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    deleteFavorite: (item: IVacanciesListItem, scope: CoroutineScope) -> Unit,
    favoritesListFlow: Flow<List<IVacanciesListItem>>,
    cancel: () -> Unit,
    loadVacancy: (id: String, scope: CoroutineScope) -> Unit,
    vacancyStateFlow: Flow<DataLoadState<VacancyResponseEntity>>
) {
    if (vacancyId.isBlank()) return

    val scope = rememberCoroutineScope()
    val vacancyState by vacancyStateFlow.collectAsState(initial = DataLoadState())
    val favoritesList = favoritesListFlow.collectAsState(initial = emptyList()).value

     LaunchedEffect(key1 = vacancyId) {
         loadVacancy(vacancyId, this)
     }

    val activity = LocalContext.current as MainActivity

    when {
        vacancyState.content != null ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000, easing = LinearOutSlowInEasing)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000))
            ) {
                Surface(
                    modifier = Modifier
                        .padding(4.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small,
                    shadowElevation = 16.dp
                ) {

                    Column(modifier = Modifier
                        .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = stringResource(id = R.string.vacancy),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 32.dp),
                            style = header(),
                            textAlign = TextAlign.Center
                        )

                        with(vacancyState.content!!) {

                            // name
                            Text(
                                text = name,
                                style = AppTypography.titleLarge,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp),
                                textAlign = TextAlign.Center
                            )

                            // salary
                            salary?.let {
                                Text(
                                    text = salary.toString(),
                                    modifier = Modifier
                                        .padding(start = 8.dp, end = 16.dp, top = 8.dp)
                                        .align(Alignment.End)
                                    ,
                                    style = AppTypography.titleSmall,
                                )
                            }
                            // employer
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = stringResource(R.string.employer_))
                                Button(
                                    onClick = { navigateToDestination(Destination.EmployerDetails(employer.id))},
                                ) {
                                    Text(
                                        text = employer.name,
                                        fontSize = 16.dpToSp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))
                            }
                            // area && data
                            Text(
                                text = stringResource(
                                    R.string.posted_in,
                                    placedArea.name,
                                    publishedAt.toPatternDateStringFromISO8601String()
                                ),
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                            // experience
                            Text(
                                text = stringResource(R.string.required_experience, experience.name),
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                            // employment && Schedule && acceptTemporary
                            Text(
                                text = employment.name +
                                        if (schedule == null) "" else schedule!!.name +
                                                if (acceptTemporary != null && acceptTemporary!!)
                                                    stringResource(R.string.accept_temporary)
                                                else "",
                                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
                            )
                            Text(
                                text = description.parseHtml(),
                                modifier = Modifier
                                    .padding(top = 24.dp)
                                    .padding(horizontal = 8.dp)
                            )
                            val context = LocalContext.current
                            Button(
                                onClick = {
                                    val uri = Uri.parse(alternateUrl)
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    startActivity(context ,intent, null)
                                },
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(text = stringResource(R.string.view_in_the_browser))
                            }

                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!isExpandedScreen)
                            FloatingActionButton(
                                onClick = {
                                    activity.onBackPressed()
                                },
                                shape = CircleShape,
                                contentColor = MaterialTheme.colorScheme.onPrimary,
                                containerColor = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .size(64.dp)
                                    .padding(start = 16.dp, top = 16.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = stringResource(id = R.string.go_back)
                                )
                            }

                        Spacer(modifier = Modifier.weight(1f))

                        val vacancyListItem = VacanciesListItem(vacancyState.content as IVacancy)
                        FloatingActionButton(
                            onClick = {
                                if (favoritesList.any { it.id == vacancyId }) deleteFavorite(
                                    vacancyListItem,
                                    scope
                                )
                                else addFavorite(vacancyListItem, scope)
                            },
                            shape = CircleShape,
                            contentColor = Color.Red,
                            containerColor = Color.White,
                            modifier = Modifier
                                .size(64.dp)
                                .padding(end = 16.dp, top = 16.dp)
                        ) {
                            Icon(
                                imageVector = if (favoritesList.any { it.id == vacancyId }) Icons.Default.Favorite
                                else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(id = R.string.favorites),
                            )
                        }


                    }


                }
            }

        vacancyState.error != null ->
            ErrorMessage(
                tryAgain = { loadVacancy(vacancyId, scope) },
                cansel = cancel,
                message = vacancyState.error!!.ifBlank { stringResource(id = R.string.error_oops) }
            )
        else -> LoadingMessage()
    }


}
