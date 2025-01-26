package game

enum class GameState(val message: String) {
    RESTART("starting again"),
    STARTING("starting"),
    WON("You Won!!"),
    LOST("Sorry, you lost!"),
    RUNNING("running..."),
    LEVEL_CHANGE("changing level")
}
