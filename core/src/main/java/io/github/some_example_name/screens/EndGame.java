package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.some_example_name.assets.AssetsManager;
import io.github.some_example_name.assets.SoundGame;

public class EndGame implements Screen {

    private Game joc;
    private Screen screen;
    private Stage stage;
    private Batch batch;
    private OrthographicCamera camara;
    private boolean win;
    private Image titleGame;
    private Label lblCredits;
    private final String credits = "Fet per:\nAndrea Esplugas i Yamila Cazalla";

    public EndGame(Game joc, Screen screen, boolean win) {
        this.joc = joc;
        this.screen = screen;
        this.win = win;

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
        if (win) {
            SoundGame.winSound.play(1f);
        } else {
            SoundGame.loseSound.play(0.2f);
        }

        // Input para Scene2D
        Gdx.input.setInputProcessor(stage);

        // ---------- TITLE ----------
        titleGame = new Image(AssetsManager.titleGame);
        titleGame.setSize(800, 1000);
        titleGame.setPosition(130, 0);
        stage.addActor(titleGame);

        // ---------- CREDITS ----------
        Skin skinCredits = new Skin();
        skinCredits.addRegions(AssetsManager.creditsAtlas);
        skinCredits.load(Gdx.files.internal(
            "fonts/credits/quantico_bold_credits.json"
        ));

        lblCredits = new Label(credits, skinCredits);
        lblCredits.setPosition(390, 150);
        lblCredits.setFontScale(2);
        lblCredits.setAlignment(Align.center);
        stage.addActor(lblCredits);

        stage.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                joc.setScreen(new Menu(joc));
                if (SoundGame.musicGame != null && SoundGame.musicGame.isPlaying()) {
                    SoundGame.musicGame.stop(); // detiene la música
                }
                //dispose();
                return true;
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f,  0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

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
