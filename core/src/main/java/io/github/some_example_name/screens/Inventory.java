package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.ArrayList;

import io.github.some_example_name.assets.AssetsManager;
import io.github.some_example_name.assets.Killer;

public class Inventory implements Screen {

    private Game joc;
    private Screen screen;
    private Stage stage;
    private Batch batch;
    private OrthographicCamera camara;
    private Skin skinBack, skinOptions, skinNotes;
    private Table table;
    private Button btnBack;
    private Image nota1, nota2, nota3, nota4, nota5, nota6, nota7, nota8, nota9, nota10, nota11, nota12;
    private Image[][] imgNotes = new Image[3][5];
    private ArrayList<Label> pistes = new ArrayList<Label>();
    private Killer killer;
    private int notes = 0, pistesComptador = 0;
    private final int WIDTH_PISTES = 100, HEIGHT_PISTES = 150;
    private static final int POSITION_X = 120, POSITION_Y = 160,
                            X_INITIAL = 50, Y_INITIAL = 450;
    private final int TOTAL_PISTES = 12;


    public Inventory(Game joc, Screen screen, Killer killer) {
        this.joc = joc;
        this.screen = screen;
        this.killer = killer;

        // Crear dimensions del joc
        camara = new OrthographicCamera(1024, 768);
        //Fer servir coordenades Y-Down
        camara.setToOrtho(false);

        // Crear viewport amb les mateixes dimensions
        StretchViewport viewport = new StretchViewport(1024, 768, camara);

        // Crear l'stage i assingar viewport
        stage = new Stage(viewport);
        batch = stage.getBatch();

        // ---------- FONS ----------
        Image fons = new Image(AssetsManager.fonsInventory);
        fons.setSize(
            stage.getViewport().getWorldWidth(),
            stage.getViewport().getWorldHeight()
        );
        stage.addActor(fons);

        table = new Table();
        table.setFillParent(false);
        table.top().left();
//        table.setPosition(450, 0);
//        table.setWidth(600);
//        table.setHeight(400);
        table.setBounds(700, 750, 600, 0);
        stage.addActor(table);

        notes();

    }

    public void incrementarNotes() {
        if (notes >= TOTAL_PISTES) return;

        if (notes < TOTAL_PISTES) {


            int fila, columna;

            // Determinar fila y columna segons l'ordre
            if (notes < 5) {                  // Primera fila (5 columnes)
                fila = 0;
                columna = notes;
                //notes(fila, columna);
            } else if (notes < 10) {          // Segona fila (5 columnes)
                fila = 1;
                columna = notes - 5;
                //notes(fila, columna);
            } else {                          // Última fila (2 columnes)
                fila = 2;
                columna = notes - 10;
                //notes(fila, columna);
            }

            if (imgNotes[fila][columna] != null) {
                imgNotes[fila][columna].setVisible(true); // Mostrar la nota
                this.notes++;
            }

            if (notes == 3 || notes == 6 || notes == 9 || notes == 12) {
                showPistes();
            }

        }
    }

    public int getNotes(){
        return this.notes;
    }

    private static int positionsXNotes(int columna) {
        int x = POSITION_X * columna;
        return x;
    }

    private static int positionsYNotes(int fila) {
        int y = POSITION_Y * fila;
        return y;
    }

    @Override
    public void show() {

        // Input para Scene2D
        Gdx.input.setInputProcessor(stage);



        // --------- SKINS ---------
        // Back button
        skinBack = new Skin();
        skinBack.addRegions(AssetsManager.backButtonAtlas);
        skinBack.load(Gdx.files.internal(
            "buttons/button_back/button_back.json"
        ));

        /*// Options button
        skinOptions = new Skin();
        skinOptions.addRegions(AssetsManager.optionsButtonAtlas);
        skinOptions.load(Gdx.files.internal(
            "buttons/button_options/button_options.json"
        ));*/

        // --------- BOTONS ---------
        // Back button
        btnBack = new Button(skinBack);
        btnBack.setSize(180, 100);
        btnBack.setPosition(
            400,
            650
        );

        // Acció del botó Back
        btnBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                joc.setScreen(screen);
                //dispose();
            }
        });

        stage.addActor(btnBack);
    }

    @Override
    public void render(float delta) {
        // Pinta la pantalla
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        batch.begin();
//
//        batch.draw(AssetsManager.fonsInventory,0,0, stage.getViewport().getWorldWidth(),
//            stage.getViewport().getWorldHeight());
//
//        batch.end();

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

    private void notes() {

//        Image imgNotes = new Image(new TextureRegion(AssetsManager.pistesImg));
//        imgNotes.setSize(WIDTH_PISTES, HEIGHT_PISTES);
//        imgNotes.setPosition(
//            X_INITIAL + positionsXNotes(j), // Cada 120 px és la següent casella (X)
//            Y_INITIAL - positionsYNotes(i) // Cada 160 px és la següent casella (Y)
//        );
//        imgNotes.setVisible(false);
//        stage.addActor(imgNotes);

        for (int i = 0; i < 3; i++) {

            int columns;

            if (i == 2) {
                columns = 2;   // Última fila
            } else {
                columns = 5;   // Resta de files
            }

            for (int j = 0; j < columns; j++) {
                imgNotes[i][j] = new Image(new TextureRegion(AssetsManager.pistesImg));
                imgNotes[i][j].setSize(WIDTH_PISTES, HEIGHT_PISTES);
                imgNotes[i][j].setPosition(
                    X_INITIAL + positionsXNotes(j), // Cada 120 px és la següent casella (X)
                    Y_INITIAL - positionsYNotes(i) // Cada 160 px és la següent casella (Y)
                );
                imgNotes[i][j].setVisible(false);
                stage.addActor(imgNotes[i][j]);
            }
        }
    }

    private void showPistes() {

        Skin skinPistes = new Skin(Gdx.files.internal("fonts/quantico_regular.json"),
            AssetsManager.dialogTextAtlas);

        String[] p = killer.getPistes();

        pistes.add(new Label(p[pistesComptador], skinPistes));
        pistes.get(pistes.size() - 1).setWrap(true);
        table.add(pistes.get(pistes.size() - 1))
            .width(300) // Ample
            .height(180); // Altura
        table.row(); // Nova fila);

        pistesComptador++;

        //pistes.setPosition(100, 100);

        //stage.addActor(pistes.get(pistes.size() - 1));
    }
}
