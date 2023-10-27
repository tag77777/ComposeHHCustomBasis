package a77777_888.me.t.composehhcustombasis.ui.components

import a77777_888.me.t.composehhcustombasis.R
import a77777_888.me.t.composehhcustombasis.ui.utils.dpToSp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun LoadingMessage() {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(32.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.please_wait),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                fontSize = 20.dpToSp,
                fontStyle = FontStyle.Italic,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .align(Alignment.CenterHorizontally)
            )
        }

}

@Composable
fun ErrorMessage(
    tryAgain:() -> Unit,
    cansel:() -> Unit,
    message: String,
) {
    Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            fontSize = 20.dpToSp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = cansel
            ) {
                Text(text = stringResource(R.string.go_out))
            }
            Button(
                onClick = tryAgain,
            ) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun PreviewErrorMessage() {
    ErrorMessage(
        message = stringResource(id = R.string.error_oops),
        tryAgain = {  },
        cansel = {}
    )
}