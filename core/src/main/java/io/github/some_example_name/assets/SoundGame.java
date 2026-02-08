package io.github.some_example_name.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class SoundGame {

    public static Sound walkingSound, notesSound, winSound, loseSound;
    public static Music musicGame;


    /**
     * Mètode estàtic per carregar tots els sons a l'iniciar el joc
     */
    public static void loadSound() {
        walkingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/pasos.wav"));
        notesSound = Gdx.audio.newSound(Gdx.files.internal("sounds/obtencion_nota.wav"));
        winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/logro.wav"));
        loseSound = Gdx.audio.newSound(Gdx.files.internal("sounds/perder.wav"));
    }

    public static void loadMusic(){
        musicGame = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
    }

    /**
     * Lliberar recursos al tancar el joc
     */
    public static void dispose() {
        if (musicGame != null) musicGame.dispose();
        if (walkingSound != null) walkingSound.dispose();
        if (notesSound != null) notesSound.dispose();
        if (winSound != null) winSound.dispose();
        if (loseSound != null) loseSound.dispose();
    }

}
