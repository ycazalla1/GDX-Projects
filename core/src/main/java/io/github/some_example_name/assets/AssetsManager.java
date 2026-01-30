package io.github.some_example_name.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetsManager {
    public static BitmapFont font;
    public static Texture fonsMenu, fonsJoc, player;
    public static TextureAtlas startButtonAtlas, optionsButtonAtlas, exitButtonAtlas,
                                optionsIconButtonAtlas, pauseButtonAtlas, menuButtonAtlas,
                                backButtonAtlas;
    public static TextureAtlas notesIconAtlas, pistesIconAtlas;

    public static void load() {
        // Carraguem les imatges
        fonsMenu = new Texture(Gdx.files.internal("img/menugame.png"));
        fonsJoc = new Texture(Gdx.files.internal("img/disco.png"));
        player = new Texture(Gdx.files.internal("img/PJ/pj_f_1.png"));


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

        // --------------- ICONS ---------------
        notesIconAtlas = new TextureAtlas(
            Gdx.files.internal("icons/notes/notes.atlas")
        );

        pistesIconAtlas = new TextureAtlas(
            Gdx.files.internal("icons/pistes/pistes.atlas")
        );

        // Carraguem la font

        //Carreguem la música i el so/música
    }

    public static void dispose() {
        // Alliberem els recursos gràfics i d'audio
        if (fonsMenu != null) fonsMenu.dispose();
        if (startButtonAtlas != null) startButtonAtlas.dispose();
        if (player != null) player.dispose();
    }

}
