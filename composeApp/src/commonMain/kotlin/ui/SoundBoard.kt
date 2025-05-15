package ui

import app.lexilabs.basic.sound.AudioByte
import coshanu.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

// https://github.com/LexiLabs-App/basic-sound

/*
Mouse Click Sound.mp3 by Pixeliota -- https://freesound.org/s/678248/ -- License: Creative Commons 0
 */
enum class SoundBytes(val soundResourceUri: String) {
    CLICK("files/678248__pixeliota__mouse-click-sound.mp3"),
    FANFARE("files/fanfare.mp3") // this does not exist (yet)
}

// todo add appropriate sounds
    // todo add sound on win
    // todo add sound on loose
    // todo add sound on start game, depending on game mode



@OptIn(ExperimentalResourceApi::class)
class SoundBoard(platformContext: Any) {
    // todo replace AudioByte with SoundBoard
    private val audioByte = AudioByte()

    fun play(sound: SoundBytes) {
        if (UiStateHolder.soundActive.value) {
            audioByte.play(audioByte.load("", Res.getUri(sound.soundResourceUri)))
        }
    }


    fun release() = audioByte.release()
}


