package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;

import io.github.some_example_name.assets.AssetsManager;
import io.github.some_example_name.assets.DialogBox;
import io.github.some_example_name.assets.Player;

public class Joc implements Screen {
    private Game joc;
    private Stage worldStage, uiStage;
    private Batch batch;
    private OrthographicCamera worldCamera, uiCamera;
    private TiledMap map;
    private Skin skinOptionsIcon, skinNotes, skinPistes, skinNomFont, skinTextFont;
    private Button btnOptionsIcon, btnNotes;
    private Button[] btns;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;
    private DialogBox db;
    public static final float WORLD_WIDTH = 3000;
    public static final float WORLD_HEIGHT = 2000;
    //private CollisionManager collisionManager;
    private float targetX;
    private float targetY;
    private boolean moving = false;



    public Joc(Game joc) {

        this.joc = joc;

        // --- CÀMERA DEL MÓN ---
        worldCamera = new OrthographicCamera();

        // Mida lògica del món visible
        worldCamera.setToOrtho(false, 1024, 768);

        FitViewport viewport = new FitViewport(1724, 768, worldCamera);
        worldStage = new Stage(viewport);

        worldStage.getViewport().setCamera(worldCamera);

        batch = worldStage.getBatch();

        // Zoom inicial
        worldCamera.zoom = 0.3f;

        // --- CÀMERA DE UI (FIXA) ---
        uiCamera = new OrthographicCamera();
        uiCamera.setToOrtho(false, 1024, 768);

        FitViewport uiViewport = new FitViewport(1024, 768, uiCamera);
        uiStage = new Stage(uiViewport);

        // Per rebre clics els botons
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(uiStage);
        multiplexer.addProcessor(worldStage);
        Gdx.input.setInputProcessor(multiplexer);


//        this.joc = joc;
//
//        camara = new OrthographicCamera();
//
//
//        camara.setToOrtho(false, 100, 100);
//
//
//        // Crear viewport amb les mateixes dimensions
//        FitViewport viewport = new FitViewport(3024, 768, camara);
//
//        stage = new Stage(viewport);
//
//
//
//        batch = stage.getBatch();
    }

    @Override
    public void show() {

        // Input para Scene2D
        Gdx.input.setInputProcessor(worldStage);

        // ---------- FONS ----------
        /*Image fons = new Image(AssetsManager.fonsJoc);
        fons.setSize(
            WORLD_WIDTH,
            WORLD_HEIGHT
        );
        stage.addActor(fons);*/

        // ---- MAPA TILED ----
        map = new TmxMapLoader().load("escenarios/disco/discoInici.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

        // Inicializar el gestor de colisiones
        //collisionManager = new CollisionManager("escenarios/disco/disoMapPrueba.tmx");

        // --------- SKINS ---------
        skinOptionsIcon = new Skin();
        skinOptionsIcon.addRegions(AssetsManager.optionsIconButtonAtlas);
        skinOptionsIcon.load(Gdx.files.internal(
            "buttons/button_options_icon/button_options_icon.json"
        ));

        skinNotes = new Skin();
        skinNotes.addRegions(AssetsManager.notesIconAtlas);
        skinNotes.load(Gdx.files.internal(
            "icons/notes/notes.json"
        ));

        skinPistes = new Skin();
        skinPistes.addRegions(AssetsManager.pistesIconAtlas);
        skinPistes.load(Gdx.files.internal(
            "icons/pistes/pistes.json"
        ));

        // --------- BOTONS ---------
        // Options Icon button
        btnOptionsIcon = new Button(skinOptionsIcon);
        btnOptionsIcon.setSize(100, 120);
        btnOptionsIcon.setPosition(
            924,
            648
        );

        // Notes Icon
        btnNotes = new Button(skinNotes);
        btnNotes.setSize(120, 120);
        btnNotes.setPosition(
            0,
            0
        );

        // Pistes Icon
        btns = showPistes();

        // ------- PERSONATGE -------
        player = new Player();
        worldStage.addActor(player);
        uiStage.addActor(btnOptionsIcon);
        uiStage.addActor(btnNotes);

        // --------- DIÀLEG ---------
        skinNomFont = new Skin(Gdx.files.internal("fonts/quantico_bold.json"), AssetsManager.dialogNomAtlas);
        skinTextFont = new Skin(Gdx.files.internal("fonts/quantico_regular.json"), AssetsManager.dialogTextAtlas);
        db = new DialogBox(skinNomFont, skinTextFont);
        //uiStage.addActor(db);
    }

    @Override
    public void render(float delta) {
        // Pinta la pantalla
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldCamera.position.set(
            player.getX() + player.getWidth() / 2,
            player.getY() + player.getHeight() / 2,
            0
        );
        worldCamera.update();


        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            worldStage.getCamera().unproject(touch);

            targetX = touch.x - player.getWidth() / 2f;
            targetY = touch.y - (player.getHeight() - 95) / 2f;

            // --- Limitar dentro del mapa ---
            //TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collisions");

//            if (layer != null) {
//                float mapWidth = layer.getWidth() * 32;   // TMX tile width
//                float mapHeight = layer.getHeight() * 32; // TMX tile height
//
//                targetX = MathUtils.clamp(targetX, 0, mapWidth - player.getWidth());
//                targetY = MathUtils.clamp(targetY, 0, mapHeight - player.getHeight());
//            }

//            float dx = targetX - player.getX();
//            float dy = targetY - player.getY();

            if (canMove(targetX, targetY)) {
                player.moveTo(targetX, targetY);

                if (db.isFinished()) {
                    db.remove(); // elimina del stage
                }

            } else {
                //db.setText(DialogBox.dialogs[0]);
                db.typeText(DialogBox.dialogs[0]);
                uiStage.addActor(db);
                Gdx.app.log("MOVIMIENTO", "No se puede mover");
            }

            Gdx.app.log("MOVIMIENTO",
                "Objetivo: " + targetX + ", " + targetY +
                    " | Tile: " + ((int)(targetX/32)) + ", " + ((int)(targetY/32)));

        }


        // ---- DIBUIXAR MAPA ----
        mapRenderer.setView(worldCamera);
        mapRenderer.render();

        worldStage.act(delta);
        worldStage.draw();
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        worldStage.getViewport().update(width, height, true);
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
        if (map != null) map.dispose();
        if (mapRenderer != null) mapRenderer.dispose();
        if (worldStage != null) worldStage.dispose();
    }

    // Prova 2 - canMove
    /*private boolean canMove(float nextX, float nextY) {
        float w = player.getWidth();
        float footHeight = 10f; // Altura de los pies

        // Coordenadas de la zona de los pies
        float left = nextX;
        float right = nextX + w - 1;
        float bottom = nextY;               // base del sprite
        float top = bottom + footHeight - 1; // altura de los pies

        Rectangle nextBounds = new Rectangle(
            left,
            right,
            bottom,
            top
        );

        MapLayer collisionLayer = map.getLayers().get("Collisions");
        if (collisionLayer == null) return true;

        for (MapObject obj : collisionLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                Rectangle rect =
                    ((RectangleMapObject) obj).getRectangle();

                if (nextBounds.overlaps(rect)) {
                    return false;
                }
            }
        }

        // Revisar esquinas de los pies
        if (isTileBlocked(left, bottom) ||       // esquina inferior izquierda
            isTileBlocked(right, bottom) ||      // esquina inferior derecha
            isTileBlocked(left, top) ||          // esquina superior izquierda del pie
            isTileBlocked(right, top)) {         // esquina superior derecha del pie
            return false;
        }

        // Puntos intermedios opcionales (mayor precisión)
        if (isTileBlocked(left + w/2, bottom) ||
            isTileBlocked(left + w/2, top)) {
            return false;
        }

        return true; // Si no colisiona, se puede mover
    }*/

    // Prova 3 - canMove
    private boolean canMove(float nextX, float nextY) {

        float footHeight = 10f;

        Rectangle playerFeet = new Rectangle(
            nextX,
            nextY,
            player.getWidth(),
            footHeight
        );

        MapLayer collisionLayer = map.getLayers().get("Collisions");
        if (collisionLayer == null) return true;

        for (MapObject obj : collisionLayer.getObjects()) {
            if (obj instanceof RectangleMapObject) {
                Rectangle rect =
                    ((RectangleMapObject) obj).getRectangle();

                if (playerFeet.overlaps(rect)) {
                    return false;
                }
            }
        }

        return true;
    }

    // Prova 3 - isTileBlocked
    private boolean isTileBlocked(float x, float y) {

        TiledMapTileLayer layer =
            (TiledMapTileLayer) map.getLayers().get("Collisions");

        if (layer == null) {
            Gdx.app.log("ERROR", "No existe la capa 'Collisions'");
            return false;
        }

        // Pruebas
        TiledMapTileLayer.Cell cell1 = layer.getCell(9, 37);
        TiledMapTileLayer.Cell cell2 = layer.getCell(9, 41);

        if (cell1 != null && cell1.getTile() != null) {
            int gid1 = cell1.getTile().getId();
            Gdx.app.log("TILE_DEBUG", "GID CON COLLISION = " + gid1);
        }

        if (cell2 != null && cell2.getTile() != null) {
            int gid1 = cell2.getTile().getId();
            Gdx.app.log("TILE_DEBUG", "GID SIN COLLISION = " + gid1);
        }

        float tileWidth = layer.getTileWidth();
        float tileHeight = layer.getTileHeight();

//        int tileX = (int)(x / tileWidth);
//        int tileY = (int)(y / tileHeight);
        int tileX = (int)((x + 0.1f) / tileWidth);
        int tileY = (int)((y + 0.1f) / tileHeight);


        // Fuera del mapa = colisión
        if (tileX < 0 || tileX >= layer.getWidth() ||
            tileY < 0 || tileY >= layer.getHeight()) {
            return true;
        }

        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        // Celda vacía = no colisión
        if (cell == null || cell.getTile() == null) {
            return false;
        }

        MapProperties props = cell.getTile().getProperties();

        // Propiedad collision
        return props.containsKey("collision") &&
            Boolean.parseBoolean(props.get("collision").toString());
    }

    private Button[] showPistes() {
        final int WIDTH_PISTES = 30;
        final int HEIGHT_PISTES = 50;

        Button btnPistes1, btnPistes2, btnPistes3, btnPistes4, btnPistes5,
            btnPistes6, btnPistes7, btnPistes8, btnPistes9, btnPistes10,
            btnPistes11, btnPistes12;

        btnPistes1 = new Button(skinPistes);
        btnPistes1.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes1.setPosition(
            380,
            850
        );
        worldStage.addActor(btnPistes1);

        btnPistes2 = new Button(skinPistes);
        btnPistes2.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes2.setPosition(
            512,
            1080
        );
        //worldStage.addActor(btnPistes2);

        btnPistes3 = new Button(skinPistes);
        btnPistes3.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes3.setPosition(
            330,
            1345
        );
        //worldStage.addActor(btnPistes3);

        btnPistes4 = new Button(skinPistes);
        btnPistes4.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes4.setPosition(
            1039,
            655
        );
        //worldStage.addActor(btnPistes4);

        btnPistes5 = new Button(skinPistes);
        btnPistes5.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes5.setPosition(
            545,
            1240
        );
        //worldStage.addActor(btnPistes5);

        btnPistes6 = new Button(skinPistes);
        btnPistes6.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes6.setPosition(
            940,
            990
        );
        //worldStage.addActor(btnPistes6);

        btnPistes7 = new Button(skinPistes);
        btnPistes7.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes7.setPosition(
            1360,
            1270
        );
        //worldStage.addActor(btnPistes7);

        btnPistes8 = new Button(skinPistes);
        btnPistes8.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes8.setPosition(
            650,
            1370
        );
        //worldStage.addActor(btnPistes8);

        btnPistes9 = new Button(skinPistes);
        btnPistes9.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes9.setPosition(
            944,
            1340
        );
        //worldStage.addActor(btnPistes9);

        btnPistes10 = new Button(skinPistes);
        btnPistes10.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes10.setPosition(
            1370,
            1040
        );
        //worldStage.addActor(btnPistes10);

        btnPistes11 = new Button(skinPistes);
        btnPistes11.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes11.setPosition(
            1189,
            775
        );
        //worldStage.addActor(btnPistes11);

        btnPistes12 = new Button(skinPistes);
        btnPistes12.setSize(WIDTH_PISTES, HEIGHT_PISTES);
        btnPistes12.setPosition(
            365,
            665
        );
        //worldStage.addActor(btnPistes12);

        return new Button[]{btnPistes2, btnPistes3, btnPistes4, btnPistes5,
            btnPistes6, btnPistes7, btnPistes8, btnPistes9, btnPistes10, btnPistes11, btnPistes12};
    }
}
