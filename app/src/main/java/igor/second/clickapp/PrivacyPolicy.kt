package igor.second.clickapp

import android.content.Intent
import android.net.Uri
import androidx.annotation.StringRes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

@Composable
fun PrivacyPolicy(@StringRes text: Int){
    val context = LocalContext.current
    val intent = remember { Intent(
        Intent.ACTION_VIEW,
        Uri.parse("https://www.termsfeed.com/live/60dafd01-4c8f-4d92-a03f-9cbd5d7f5c10")) }
    TextButton(onClick = { context.startActivity(intent) }) {
        Text(
            stringResource(id = text),
            style = MaterialTheme.typography.labelSmall)
    }
}