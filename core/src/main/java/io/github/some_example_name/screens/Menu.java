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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;



import io.github.some_example_name.assets.AssetsManager;


public class Menu implements Screen {

    private Game joc;
    private Stage stage;
    private Batch batch;
    private OrthographicCamera camara;
    private Skin skinStart, skinExit, skinOptions;
    private Button btnPlay, btnExit;

    public Menu(Game joc) {

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

        // --------- SKINS ---------
        // Start button
        skinStart = new Skin();
        skinStart.addRegions(AssetsManager.startButtonAtlas);
        skinStart.load(Gdx.files.internal(
            "buttons/button_start/button_start.json"
        ));

        // Exit button
        skinExit = new Skin();
        skinExit.addRegions(AssetsManager.exitButtonAtlas);
        skinExit.load(Gdx.files.internal(
            "buttons/button_exit/button_exit.json"
        ));

        // --------- BOTONS ---------
        // Start button
        btnPlay = new Button(skinStart);
        btnPlay.setSize(250, 150);
        btnPlay.setPosition(
            512 - 381,
            384 - 75
        );

        // Acció del botó Play
        btnPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joc.setScreen(new Joc(joc));
                dispose();
            }
        });

        // Exit button
        btnExit = new Button(skinExit);
        btnExit.setSize(250, 150);
        btnExit.setPosition(
            512 + 131,
            384 - 75
        );

        // Acció del botó Exit
        btnExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(btnPlay);
        stage.addActor(btnExit);
//        skin = new Skin(Gdx.files.internal("buttons/start-button/start_button.json"));
//        btnPlay = new Button(skin);
//        //btnPlay.setSize(200, 80);
//        btnPlay.setPosition(
//            0,   // centro X
//            0     // centro Y
//        );
//        //btnOptions = new Button(skin);
//        //btnExit = new Button(skin);
//
//        stage.addActor(btnPlay);
//        //stage.addActor(btnOptions);
//        //stage.addActor(btnExit);
//
//        btnPlay.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                // Acció del botó
//                joc.setScreen(new Joc(joc, batch, camara));
//                dispose();
//            }
//        });
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

        // Si es fa click a la pantalla, canviem la pantalla
//        if (Gdx.input.isTouched()) {
//            joc.setScreen(new Joc(joc,batch,camara));
//            dispose();
//        }
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
