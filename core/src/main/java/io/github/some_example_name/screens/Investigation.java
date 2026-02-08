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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import io.github.some_example_name.assets.AssetsManager;
import io.github.some_example_name.assets.DialogBox;
import io.github.some_example_name.assets.Killer;

public class Investigation implements Screen {

    private Game joc;
    private Screen screen;
    private Stage stage;
    private Batch batch;
    private OrthographicCamera camara;
    private Skin skinBack, skinVeraKiller, skinElenaKiller, skinVictorKiller, skinTobiasKiller,
        skinDialog;
    private Table table;
    private Button btnBack;
    private Killer killer;
    private DialogBox db;
    private int intentsInvestigation = 2;
    private final int WIDTH_MAP = 1024, HEIGHT_MAP = 768, WIDTH_KILLER = 180, HEIGHT_KILLER = 600,
        POSITION_INITIAL = 150;



    public Investigation(Game joc, Screen screen, Killer killer) {
        this.joc = joc;
        this.screen = screen;
        this.killer = killer;

        // Crear dimensions del joc
        camara = new OrthographicCamera(WIDTH_MAP, HEIGHT_MAP);
        //Fer servir coordenades Y-Down
        camara.setToOrtho(false);

        // Crear viewport amb les mateixes dimensions
        StretchViewport viewport = new StretchViewport(WIDTH_MAP, HEIGHT_MAP, camara);

        // Crear l'stage i assingar viewport
        stage = new Stage(viewport);
        batch = stage.getBatch();
    }

    @Override
    public void show() {
        // Input para Scene2D
        Gdx.input.setInputProcessor(stage);

        // --------- SKINS ---------
        // Skin Back Button
        skinBack = new Skin();
        skinBack.addRegions(AssetsManager.backButtonAtlas);
        skinBack.load(Gdx.files.internal(
            "buttons/button_back/button_back.json"
        ));

        // Skin Vera
        skinVeraKiller = new Skin();
        skinVeraKiller.addRegions(AssetsManager.killerVeraAtlas);
        skinVeraKiller.load(Gdx.files.internal(
            "buttons/killers/vera_button_killer.json"
        ));

        // Skin Elena
        skinElenaKiller = new Skin();
        skinElenaKiller.addRegions(AssetsManager.killerElenaAtlas);
        skinElenaKiller.load(Gdx.files.internal(
            "buttons/killers/elena_button_killer.json"
        ));

        // Skin Victor
        skinVictorKiller = new Skin();
        skinVictorKiller.addRegions(AssetsManager.killerVictorAtlas);
        skinVictorKiller.load(Gdx.files.internal(
            "buttons/killers/victor_button_killer.json"
        ));

        // Skin Tobias
        skinTobiasKiller = new Skin();
        skinTobiasKiller.addRegions(AssetsManager.killerTobiasAtlas);
        skinTobiasKiller.load(Gdx.files.internal(
            "buttons/killers/tobias_button_killer.json"
        ));

        skinDialog = new Skin(Gdx.files.internal("fonts/quantico_regular.json"),
            AssetsManager.dialogTextAtlas);
        db = new DialogBox(skinDialog);

        // --------- BOTONS ---------
        // Back button
        btnBack = new Button(skinBack);
        btnBack.setSize(180, 100);
        btnBack.setPosition(
            450,
            40
        );

        // Acció del botó Back
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joc.setScreen(screen);
            }
        });

        // Botons Killers
        showKillers();

        stage.addActor(btnBack);

        // Screen Action
        stage.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (db.hasParent()) {
                    db.onScreenClick();
                    return true;
                }
                return false;
            }
        });
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

    private void showKillers() {

        Skin[] skinsKillers = {
            skinVeraKiller,
            skinElenaKiller,
            skinVictorKiller,
            skinTobiasKiller
        };

        for (int i = 0; i < skinsKillers.length; i++) {
            Button btnKiller = new Button(skinsKillers[i]);
            btnKiller.setSize(WIDTH_KILLER, HEIGHT_KILLER);
            btnKiller.setPosition(
                POSITION_INITIAL + ((WIDTH_KILLER + 10) * i),
                POSITION_INITIAL);
            int indexI = i;
            btnKiller.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (indexI == 0) {
                        intentsInvestigation = isKiller("Vera", intentsInvestigation);
                    } else if (indexI == 1) {
                        intentsInvestigation = isKiller("Elena", intentsInvestigation);
                    } else if (indexI == 2) {
                        intentsInvestigation = isKiller("Victor", intentsInvestigation);
                    } else {
                        intentsInvestigation = isKiller("Tobias", intentsInvestigation);
                    }
                }
            });
            stage.addActor(btnKiller);
        }
    }

    private int isKiller(String name, int intents) {



        if (name.equals(killer.getNom())) {
            String[] info = {
                name + " és l'assassí. Enhorabona es resolt el cas."
            };
            db.typeTextMultiple(0, 1, info);
            stage.addActor(db);
        } else {
            intents--;
            String[] info = {
                name + "no és l'assassí. Et queden " + intents + " intents. Has perdut.",
                name + " no és l'assassí. Et queda " + intents + " intent."
            };
            if (intents == 0) {
                db.typeTextMultiple(0,1, info);
                stage.addActor(db);
            } else {
                db.typeTextMultiple(1,2, info);
                stage.addActor(db);
            }
        }

        return intents;
    }
 }
