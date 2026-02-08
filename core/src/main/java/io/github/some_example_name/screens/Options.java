package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.some_example_name.assets.AssetsManager;

public class Options implements Screen {

    private Game joc;
    private Screen screen;
    private Stage stage;
    private Batch batch;
    private OrthographicCamera camara;
    private Skin skinHome, skinBack, skinLblTitle;
    private Button btnMenu, btnBack;
    private Label lblTitle;

    public Options(Game joc, Screen screen) {
        this.joc = joc;
        this.screen = screen;

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

        skinLblTitle = new Skin();
        skinLblTitle.addRegions(AssetsManager.dialogNomAtlas);
        skinLblTitle.load(Gdx.files.internal(
            "fonts/quantico_bold.json"
        ));

        // --------- LABEL ----------
        lblTitle = new Label("OPTIONS", skinLblTitle);
        lblTitle.setSize(250, 150);
        lblTitle.setFontScale(4);
        lblTitle.setPosition(
            320,
            560
        );

        // --------- BOTONS ---------
        // Menu Button
        btnMenu = new Button(skinHome);
        btnMenu.setSize(250, 150);
        btnMenu.setPosition(
            512 - 376,
            384 - 75
        );

        // Acció del botó Menú
        btnMenu.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joc.setScreen(new Menu(joc));
            }
        });

        // Back Button
        btnBack = new Button(skinBack);
        btnBack.setSize(250, 150);
        btnBack.setPosition(
            512 + 135,
            384 - 75
        );

        // Acció del botó Back
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joc.setScreen(screen);
            }
        });

        stage.addActor(lblTitle);
        stage.addActor(btnMenu);
        stage.addActor(btnBack);
    }

    @Override
    public void render(float delta) {
        // Pinta la pantalla
        Gdx.gl.glClearColor(0.89f, 0.80f,  0.64f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
