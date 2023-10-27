package a77777_888.me.t.domain.dataentities.areas

data class Areas(
    var areas: ArrayList<Areas>,
    var id: String,
    var name: String,
    var parent_id: String?
) {
    override fun toString(): String {
        return this.name
    }
}

fun List<Areas>.startWith(prefix: String, capacity: Int): List<Areas> {
    val resultList = mutableListOf<Areas>()

    fun List<Areas>.addToResult() {
        if (resultList.size >= capacity) return
        this.forEach {
            if (it.name.startsWith(prefix, true)) resultList.add(it)
            if (resultList.size >= capacity) return
            if (it.areas.isNotEmpty()) it.areas.addToResult()
        }

    }

    this.addToResult()

    return resultList
}
