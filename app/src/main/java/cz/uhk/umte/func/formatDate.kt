package cz.uhk.umte.func

fun formatDate(date: String): String {
    val split = date.split("-")

    if (split.size == 3) {
        return "${split[2]}.${split[1]}.${split[0]}"
    }

    return date
}