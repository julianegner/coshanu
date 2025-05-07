package ui

import app.lexilabs.basic.sound.AudioByte
import coshanu.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

// import <your_package>.generated.resources.Res
// import org.jetbrains.compose.resources.Resource.Res

/*
todo get sound files and try the sound board
when running, use the exisiting switch befor running any sounds
 */

@OptIn(ExperimentalResourceApi::class)
class SoundBoard(platformContext: Any) {
    //
    private val audioByte: AudioByte = AudioByte()

    private val click: Any = audioByte.load(platformContext, Res.getUri("files/678248__pixeliota__mouse-click-sound.mp3"))
    private val fanfare: Any = audioByte.load(platformContext, Res.getUri("files/fanfare.mp3"))

    fun click() = audioByte.play(click)
    fun fanfare() = audioByte.play(fanfare)
    fun release() = audioByte.release()
}

/*
Mouse Click Sound.mp3 by Pixeliota -- https://freesound.org/s/678248/ -- License: Creative Commons 0
 */
