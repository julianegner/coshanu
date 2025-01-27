package game

enum class GameMode(val message: String, val amount: Int) {
    SINGLE_ELEMENT("Single Element", 1),
    TWO_ELEMENTS("Two Elements", 2),
}
