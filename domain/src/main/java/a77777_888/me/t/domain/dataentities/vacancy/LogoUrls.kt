package a77777_888.me.t.domain.dataentities.vacancy

import com.google.gson.annotations.SerializedName

data class LogoUrls(
    @SerializedName("240") val logo240: String,
    @SerializedName("90") val logo90: String,
    val original: String
)

//data class LogoUrls(
//    val `90`: String,
//    val original: String
//)