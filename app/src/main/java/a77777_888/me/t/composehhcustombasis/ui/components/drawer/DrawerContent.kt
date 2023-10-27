package a77777_888.me.t.composehhcustombasis.ui.components.drawer

import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.viewmodel.MainViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.DrawerState
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerContent(
    viewModel: MainViewModel,
    navigateToSearchList: () -> Unit,
    scope: CoroutineScope,
    drawerState: DrawerState,
){
    val fABOnClickEvent = remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Search(fABOnClickEvent = fABOnClickEvent, settings = viewModel.searchSettings)
        FloatingActionButton(
            onClick = {
                fABOnClickEvent.value = true
                navigateToSearchList()
                scope.launch { drawerState.close() }
            },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .offset(x = (-48).dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        }

        FloatingActionButton(
            onClick = {
                scope.launch { drawerState.close() }
            },
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
                .offset(x = 48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.search)
            )
        }
    }
}