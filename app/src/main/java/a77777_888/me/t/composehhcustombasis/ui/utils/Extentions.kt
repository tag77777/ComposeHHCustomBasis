package a77777_888.me.t.composehhcustombasis.ui.utils

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.google.gson.internal.bind.util.ISO8601Utils
import java.text.ParsePosition
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.toPatternDateStringFromISO8601String(
//    pattern: String = "d MMMM yyyy, HH:mm"
    pattern: String = "d MMMM, HH:mm"
): String =
    try {
        val data = ISO8601Utils.parse(this, ParsePosition(0))
        SimpleDateFormat(pattern).format(data)
    } catch (e: Exception){ "" }
        // required API level 26
//         LocalDateTime.parse(this.dropLast(5))
//            .format(DateTimeFormatter.ofPattern(pattern))


@Stable
inline val Int.dpToSp: TextUnit
    @Composable
    get() = with(LocalDensity.current) { Dp(this@dpToSp.toFloat()).toSp()}

@Suppress("DEPRECATION")
fun String.parseHtml(): String =
    (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    else Html.fromHtml(this)).toString()

