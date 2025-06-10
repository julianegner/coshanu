package ui

import app.lexilabs.basic.sound.SoundBoard
import app.lexilabs.basic.sound.SoundByte
import app.lexilabs.basic.sound.play
import coshanu.composeapp.generated.resources.Res
import isPlatformJava
import org.jetbrains.compose.resources.ExperimentalResourceApi

import java.util.jar.JarFile
import java.io.BufferedInputStream

import javax.sound.sampled.AudioFormat
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.AudioSystem.getAudioInputStream
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.SourceDataLine

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

    val soundBoard = SoundBoard(this.context)

    init {
        SoundBytes.entries.forEach { sound ->
            println("adding sound file: ${sound.soundResourceUri}")
            println("local path: ${Res.getUri(sound.soundResourceUri)}")
            soundBoard.load(
                SoundByte(
                    name = sound.name,
                    // localPath = "/composeResources/coshanu.composeapp.generated.resources/" + sound.soundResourceUri
                    localPath = Res.getUri(sound.soundResourceUri)
                    // localPath = "/composeResources/coshanu.composeApp.generated.resources/files/678248__pixeliota__mouse-click-sound.mp3"
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

fun playAudioFromJar(jarFilePathWithEntry: String) {
    try {
        val parts = jarFilePathWithEntry.split("!")
        if (parts.size != 2) {
            println("Invalid JAR file path format: $jarFilePathWithEntry")
            return
        }

        val jarFilePath = parts[0].substringAfter("jar:file:")
        val entryPath = parts[1].removePrefix("/")

        val jarFile = JarFile(jarFilePath)
        val jarEntry = jarFile.getJarEntry(entryPath)
        if (jarEntry == null) {
            println("File not found in JAR: $entryPath")
            return
        } else {
            println("Found entry in JAR: $entryPath")
        }

        val inputStream = BufferedInputStream(jarFile.getInputStream(jarEntry))
        // val audioInputStream = AudioSystem.getAudioInputStream(inputStream)

        getAudioInputStream(inputStream).use { `in` ->
            val outFormat = getOutFormat(`in`.format)
            val info = Info(SourceDataLine::class.java, outFormat)
            AudioSystem.getLine(info).use { line ->
                (line as? SourceDataLine)?.let { l ->
                    l.open(outFormat)
                    l.start()
                    stream(getAudioInputStream(outFormat, `in`), l)
                    l.drain()
                    l.stop()
                }
            }
        }

        /*
        val clip: Clip = AudioSystem.getClip()
        clip.open(audioInputStream)
        clip.start()
        println("Playing audio: $entryPath")
        Thread.sleep(clip.microsecondLength / 1000)
         */
    } catch (e: Exception) {
        println("Error playing audio: ${e.message}")
        e.printStackTrace()
    }
}

    fun play(sound: SoundBytes) {
        if (isPlatformJava) {
            playAudioFromJar(Res.getUri(sound.soundResourceUri))
        } else {
            soundBoard.mixer.play(sound.name)
        }
    }

    // fun powerDown() = soundBoard.powerDown()

    /*
    // todo replace AudioByte with SoundBoard because AudioByte has a memory leak
    // AudioByte' is deprecated. AudioByte greedily eats up memory. Switch to the newer channel-based implementation, SoundBoard(context = Any?).
    private val audioByte = AudioByte()

    fun play(sound: SoundBytes) {
        if (UiStateHolder.soundActive.value) {
            audioByte.play(audioByte.load("", Res.getUri(sound.soundResourceUri)))
        }
    }


    fun release() = audioByte.release()
     */


    private fun getOutFormat(inFormat: AudioFormat): AudioFormat {
        val ch = inFormat.channels
        val rate = inFormat.sampleRate
        return AudioFormat(AudioFormat.Encoding.PCM_SIGNED, rate, 16, ch, ch * 2, rate, false)
    }

    private fun stream(`in`: AudioInputStream, line: SourceDataLine) {
        val buffer = ByteArray(65536)
        var n = 0
        while (n != -1) {
            line.write(buffer, 0, n)
            n = `in`.read(buffer, 0, buffer.size)
        }
    }
}


