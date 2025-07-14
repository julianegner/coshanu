package ui

import app.lexilabs.basic.sound.SoundBoard
import app.lexilabs.basic.sound.SoundByte
import app.lexilabs.basic.sound.play
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
class SoundBoardTT(val context: Any) {

    var soundBoard = SoundBoard(this.context)

    var soundCounter: Int = 0

    init {
        loadSoundboard()
    }

    private fun loadSoundboard() {
        SoundBytes.entries.forEach { sound ->
            println("adding sound file: ${sound.soundResourceUri}")
            println("local path: ${Res.getUri(sound.soundResourceUri)}")
            soundBoard.load(
                SoundByte(
                    name = sound.name,
                    localPath = Res.getUri(sound.soundResourceUri)
                )
            )
        }
        try {
            soundBoard.powerUp()
        } catch (e: Exception) {
            println("Error powering up sound board: ${e.message}")
            e.printStackTrace()
        }
    }

    fun play(sound: SoundBytes) {
        soundCounter += 1
        // if (soundCounter % 50 == 0) {
        //     println("Soundboard PowerDown and PowerUp to reset the sound board.")
        //     soundBoard.mixer.
        //     soundBoard.powerDown()
        //     soundBoard = SoundBoard(this.context)
        //     loadSoundboard()
        // }
        println("***")
        println("Sound counter: $soundCounter")
        println("Playing sound: ${sound.name} from resource: ${sound.soundResourceUri}")
        println("***")
        soundBoard.mixer.play(sound.name)
    }
}


