package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.some_example_name.assets.AssetsManager;

public class Options implements Screen {

    private Game joc;
    private Stage stage;
    private Batch batch;
    private OrthographicCamera camara;
    private Skin skinHome, skinBack;
    private Button btnMenu, btnBack;

    public Options(Game joc) {
        this.joc = joc;

        // Crear dimensions del joc
        camara = new OrthographicCamera(1024, 768);
        //Fer servir coordenades Y-Down
        camara.setToOrtho(false);

        // Crear viewport amb les mateixes dimensions
        StretchViewport viewport = new StretchViewport(1024, 768, camara);

        // Crear l'stage i assingar viewport
        stage = new Stage(viewport);
        batch = stage.getBatch();
    }


    @Override
    public void show() {
        // Input para Scene2D
        Gdx.input.setInputProcessor(stage);

        // ---------- FONS ----------
        Image fondo = new Image(AssetsManager.fonsMenu);
        fondo.setSize(
            stage.getViewport().getWorldWidth(),
            stage.getViewport().getWorldHeight()
        );
        stage.addActor(fondo);

        // ---------- SKIN ----------
        skinHome = new Skin();
        skinHome.addRegions(AssetsManager.menuButtonAtlas);
        skinHome.load(Gdx.files.internal(
            "buttons/button_menu/button_menu.json"
        ));

        skinBack = new Skin();
        skinBack.addRegions(AssetsManager.backButtonAtlas);
        skinBack.load(Gdx.files.internal(
            "buttons/button_back/button_back.json"
        ));

        // --------- BOTONS ---------
        // Menu Button
        btnMenu = new Button(skinHome);
        btnMenu.setSize(250, 150);
        btnMenu.setPosition(
            512 - 100,
            384 - 40
        );

        // Back Button
        btnBack = new Button(skinBack);
        btnBack.setSize(250, 150);
        btnBack.setPosition(
            512 - 100,
            384 - 40
        );

        stage.addActor(btnMenu);
        stage.addActor(btnBack);
    }

    @Override
    public void render(float delta) {
        // Pinta la pantalla
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        batch.draw(AssetsManager.fonsMenu,0,0, stage.getViewport().getWorldWidth(),
            stage.getViewport().getWorldHeight());

        batch.end();

        stage.act(delta);
        stage.draw();
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
