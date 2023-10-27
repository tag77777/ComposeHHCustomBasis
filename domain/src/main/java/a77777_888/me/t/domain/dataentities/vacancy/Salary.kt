package a77777_888.me.t.domain.dataentities.vacancy

data class Salary(
    val currency: String,
    val from: Int?,
    val gross: Boolean,
    val to: Int?
) {
    override fun toString(): String {
        val sb = StringBuilder()
        if (from != null) sb.append("от $from ")
        if (to != null) sb.append("до $to ")
        sb.append(
            if (currency.contentEquals("RUR")) "рублей" else currency
        )
        return sb.toString()
    }
}