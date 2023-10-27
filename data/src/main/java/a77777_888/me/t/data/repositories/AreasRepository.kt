package a77777_888.me.t.data.repositories

import a77777_888.me.t.domain.dataentities.areas.Areas

object AreasRepository{
    var areas: List<Areas>? = null

    fun getNameById(id: String): String? = areas?.getNameById(id)
}

fun List<Areas>.getNameById(id: String): String? {
    forEach {
        return if (it.id == id) it.name else it.areas.getNameById(id)
    }
    return null
}

