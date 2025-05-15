package ui

import app.lexilabs.basic.sound.AudioByte
import coshanu.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

// https://github.com/LexiLabs-App/basic-sound

/*
Mouse Click Sound.mp3 by Pixeliota -- https://freesound.org/s/678248/ -- License: Creative Commons 0
result-7.mp3 by DZeDeNZ -- https://freesound.org/s/522246/ -- License: Creative Commons 0
CONFIRM_SOUND.mp3 by ciapaqua -- https://freesound.org/s/637109/ -- License: Creative Commons 0
DENY_SOUND.mp3 by ciapaqua -- https://freesound.org/s/637108/ -- License: Creative Commons 0
Pot falling to the floor by oldhiccup -- https://freesound.org/s/689494/ -- License: Creative Commons 0

Card Sounds.mp3 by henrygillard -- https://freesound.org/s/575387/ -- License: Creative Commons 0
card-flipping-75622 (1)-[AudioTrimmer.com] by ajaysingh318 -- https://freesound.org/s/793598/ -- License: Creative Commons 0
D3_cards mixing.mp3 by Iamgiorgio -- https://freesound.org/s/371349/ -- License: Creative Commons 0

 */
enum class SoundBytes(val soundResourceUri: String) {
    CLICK("files/678248__pixeliota__mouse-click-sound.mp3"),
    WON("files/522246__dzedenz__result-7.mp3"),
    CONFIRM("files/637109__ciapaqua__confirm_sound.mp3"),
    DENY("files/637108__ciapaqua__deny_sound.mp3"),
    LOST("files/689494__oldhiccup__pot-falling-to-the-floor.mp3"),
    START_GAME_SINGLE_ELEMENT("files/575387__henrygillard__card-sounds.mp3"),
    START_GAME_TWO_ELEMENTS("files/793598__ajaysingh318__card-flipping-75622-1-audiotrimmer.mp3"),
    START_GAME_TWO_ELEMENTS_WITH_TIMER("files/371349__iamgiorgio__d3_cards-mixing.mp3"),
}

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


