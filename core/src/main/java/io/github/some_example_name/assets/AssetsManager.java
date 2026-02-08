package io.github.some_example_name.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsManager {
    public static BitmapFont font;
    public static Texture killerVera, killerElena, killerVictor, killerTobias;
    public static Texture fonsMenu, fonsJoc, player, fonsInventory, player4K, dialogBoxInfo,
        dialogBoxTalking, pistesImg, bolaDisco;
    public static TextureAtlas startButtonAtlas, optionsButtonAtlas, exitButtonAtlas,
                                optionsIconButtonAtlas, pauseButtonAtlas, menuButtonAtlas,
                                backButtonAtlas;
    public static TextureAtlas killerVeraAtlas, killerElenaAtlas, killerVictorAtlas, killerTobiasAtlas;
    public static TextureAtlas notesIconAtlas, pistesIconAtlas;
    public static TextureAtlas dialogNomAtlas, dialogTextAtlas;
    public static TextureAtlas timerAtlas;

    public static void load() {
        // Carraguem les imatges
        fonsMenu = new Texture(Gdx.files.internal("img/menugame.png"));
        fonsJoc = new Texture(Gdx.files.internal("img/disco.png"));
        fonsInventory = new Texture(Gdx.files.internal("img/inventory.png"));

        // Player
        player = new Texture(Gdx.files.internal("img/PJ/pj_f_investic_1.png"));

        // Killers
        killerVera = new Texture(Gdx.files.internal("img/PJ/pj_f_1.png"));
        killerElena = new Texture(Gdx.files.internal("img/PJ/pj_f_2.png"));
        killerVictor = new Texture(Gdx.files.internal("img/PJ/pj_m_1.png"));
        killerTobias = new Texture(Gdx.files.internal("img/PJ/pj_m_2.png"));

        player4K = new Texture(Gdx.files.internal("img/PJ/pj_f_investic_4k.png"));
        dialogBoxTalking = new Texture(Gdx.files.internal("img/dialog/pj_f_investic_dialog.png"));
        dialogBoxInfo = new Texture(Gdx.files.internal("img/dialog/dialog_box_info.png"));
        pistesImg = new Texture(Gdx.files.internal("img/notes/pistes.png"));

        bolaDisco = new Texture(Gdx.files.internal("img/bola_disco.png"));

        // --------------- BUTTONS ---------------
        // Button start
        startButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_start/button_start.atlas")
        );

        // Button menu
        menuButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_menu/button_menu.atlas")
        );

        // Button exit
        exitButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_exit/button_exit.atlas")
        );

        // Button options
        optionsButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_options/button_options.atlas")
        );

        // Button options icon
        optionsIconButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_options_icon/button_options_icon.atlas")
        );

        // Button pause
        pauseButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_pause/button_pause.atlas")
        );

        backButtonAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/button_back/button_back.atlas")
        );

        killerVeraAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/killers/vera_button_killer.atlas")
        );

        killerElenaAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/killers/elena_button_killer.atlas")
        );

        killerVictorAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/killers/victor_button_killer.atlas")
        );

        killerTobiasAtlas = new TextureAtlas(
            Gdx.files.internal("buttons/killers/tobias_button_killer.atlas")
        );

        // --------------- ICONS ---------------
        notesIconAtlas = new TextureAtlas(
            Gdx.files.internal("icons/notes/notes.atlas")
        );

        pistesIconAtlas = new TextureAtlas(
            Gdx.files.internal("icons/pistes/pistes.atlas")
        );

        // --------------- DIALOG ---------------
        // Carraguem la font
        dialogNomAtlas = new TextureAtlas(
            Gdx.files.internal("fonts/quantico_bold.atlas")
        );

        dialogTextAtlas = new TextureAtlas(
            Gdx.files.internal("fonts/quantico_regular.atlas")
        );

        // --------------- TIMER ---------------
        timerAtlas = new TextureAtlas(
            Gdx.files.internal("fonts/quantico_bold.atlas")
        );

        //Carreguem la música i el so/música
    }

    public static void dispose() {
        // Alliberem els recursos gràfics i d'audio
        if (fonsMenu != null) fonsMenu.dispose();
        if (fonsJoc != null) fonsJoc.dispose();
        if (killerVera != null) killerVera.dispose();
        if (startButtonAtlas != null) startButtonAtlas.dispose();
        if (optionsButtonAtlas != null) optionsButtonAtlas.dispose();
        if (exitButtonAtlas != null) exitButtonAtlas.dispose();
        if (optionsIconButtonAtlas != null) optionsIconButtonAtlas.dispose();
        if (pauseButtonAtlas != null) pauseButtonAtlas.dispose();
        if (menuButtonAtlas != null) menuButtonAtlas.dispose();
        if (backButtonAtlas != null) backButtonAtlas.dispose();
        if (notesIconAtlas != null) notesIconAtlas.dispose();
        if (pistesIconAtlas != null) pistesIconAtlas.dispose();
    }

}
