package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class Inventory implements Screen {

    private Game joc;
    private int notes = 0;
    private Label pistes;

    public Inventory(Game joc) {
        this.joc = joc;
    }

    public void incrementarNotes() {
        this.notes++;
    }

    public int getNotes(){
        return this.notes;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
