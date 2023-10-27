package a77777_888.me.t.composehhcustombasis.ui.screens

import a77777_888.me.t.composehhcustombasis.MainActivity
import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.components.ErrorMessage
import a77777_888.me.t.composehhcustombasis.ui.components.LoadingMessage
import a77777_888.me.t.composehhcustombasis.ui.theme.AppTypography
import a77777_888.me.t.composehhcustombasis.ui.theme.header
import a77777_888.me.t.composehhcustombasis.ui.utils.parseHtml
import a77777_888.me.t.composehhcustombasis.ui.viewmodel.DataLoadState
import a77777_888.me.t.domain.dataentities.employer.EmployerResponseEntity
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import coil.compose.AsyncImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Composable
fun EmployerScreen(
    id: String,
    cancel: () -> Unit,
    loadEmployer: (id: String, scope: CoroutineScope) -> Unit,
    employerStateFlow: Flow<DataLoadState<EmployerResponseEntity>>
) {
    val employerState by employerStateFlow.collectAsState(initial = DataLoadState())

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = id) {
        loadEmployer(id, this)
    }


    when {
        employerState.content != null ->
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000))
            ) {
                val context = LocalContext.current

                Surface(
                    modifier = Modifier
                        .padding(4.dp)
                        ,
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = MaterialTheme.shapes.small,
                    shadowElevation = 16.dp
                ) {
                    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {
                        Text(
                            text = stringResource(id = R.string.employer),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 32.dp),
                            style = header(),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )

                        with(employerState.content!!) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(start = 16.dp, top = 16.dp)
                            ) {
                                logoUrls?.let {
                                    AsyncImage(
                                        model = (logoUrls!!.logo90),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .requiredSize(56.dp)
                                            .clip(RoundedCornerShape(4.dp))
                                    )
                                }

                                Column(
                                    modifier = Modifier.padding(start = 16.dp),
                                ) {
                                    Text(
                                        text = name,
                                        style = AppTypography.titleSmall,
                                    )
                                    Text(text = area.name)
                                }
                            }

                            type?.let {
                                Text(
                                    text = when (type!!) {
                                        "company" -> stringResource(R.string.company)
                                        "agency" -> stringResource(R.string.agency)
                                        "project_director" -> stringResource(R.string.project_director)
                                        "private_recruiter" -> stringResource(R.string.private_recruiter)
                                        else -> type!!
                                    },
                                    modifier = Modifier.padding(start = 32.dp)
                                )
                            }
                            siteUrl?.ifBlank { null }?.let {
                                Button(
                                    onClick = {
                                        val uri = Uri.parse(it)
                                        val intent = Intent(Intent.ACTION_VIEW, uri)
                                        startActivity(context, intent, null)
                                    },
                                    modifier = Modifier.padding(start = 8.dp)
                                ) {
                                    Text(text = siteUrl!!)
                                }
                            }
                            Spacer(modifier = Modifier.height(32.dp))
                            description?.let {
                                Text(
                                    text = description!!.parseHtml(),
                                    modifier = Modifier.padding(horizontal = 8.dp)
                                )
                            }

                            Button(
                                onClick = {
                                    val uri = Uri.parse(alternateUrl)
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    startActivity(context, intent, null)
                                },
                                modifier = Modifier.padding(24.dp)
                            ) {
                                Text(text = stringResource(R.string.view_in_browser))
                            }
                        }
                    }

                    val activity = LocalContext.current as MainActivity
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

                }

            }
        employerState.error != null ->
            ErrorMessage(
                tryAgain = { loadEmployer(id, scope) },
                cansel = cancel,
                message = employerState.error!!.message
            )
        else -> LoadingMessage()
    }
}
