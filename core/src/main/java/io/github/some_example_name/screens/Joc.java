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
    import com.badlogic.gdx.scenes.scene2d.InputEvent;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.ui.Button;
    import com.badlogic.gdx.scenes.scene2d.ui.Label;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;
    import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
    import com.badlogic.gdx.utils.viewport.FitViewport;

    import io.github.some_example_name.assets.AssetsManager;
    import io.github.some_example_name.assets.DialogBox;
    import io.github.some_example_name.assets.Player;
    import io.github.some_example_name.assets.TimerGame;

    public class Joc implements Screen {
        private Game joc;
        private Stage worldStage, uiStage;
        private Batch batch;
        private OrthographicCamera worldCamera, uiCamera;
        private TiledMap map;
        private Skin skinOptionsIcon, skinNotes, skinPistes, skinNomFont, skinTextFont, skinTimer;
        private Label timerLabel;
        private Button btnOptionsIcon, btnNotes;
        private Button btnPistes1, btnPistes2, btnPistes3, btnPistes4, btnPistes5,
            btnPistes6, btnPistes7, btnPistes8, btnPistes9, btnPistes10,
            btnPistes11, btnPistes12;
        private Button[] btns;
        private OrthogonalTiledMapRenderer mapRenderer;
        private TimerGame timerGame;
        private static final float TOTAL_TIME = 180f; // 3 minuts en segons
        private Player player;
        private DialogBox db;
        private Inventory inventory;
        public static final float WORLD_WIDTH = 3000;
        public static final float WORLD_HEIGHT = 2000;
        //private CollisionManager collisionManager;
        private float targetX;
        private float targetY;
        private boolean dialogShownTile15 = false;



        public Joc(Game joc) {

            this.joc = joc;
            this.inventory = new Inventory(this.joc);
            this.timerGame = new TimerGame();

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
            pistes();

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

            // --------- TIMER ---------
            skinTimer = new Skin(Gdx.files.internal("fonts/quantico_bold.json"), AssetsManager.timerAtlas);
            timerLabel = new Label("03:00", skinTimer);
            timerLabel.setPosition(40, 700);
            //uiStage.addActor(timerLabel);
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
                if (db.hasParent()) {
                    db.onScreenClick();
                } else {
                    Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
                    worldStage.getCamera().unproject(touch);

                    targetX = touch.x - player.getWidth() / 2f;
                    targetY = touch.y - (player.getHeight() - 95) / 2f;

                    if (canMove(targetX, targetY)) {

                        player.moveTo(targetX, targetY);

//                        if (db.isFinished()) {
//                            db.remove(); // elimina del stage
//                        }

                    } else {
                        //db.setText(DialogBox.dialogs[0]);
                        if (inventory.getNotes() == 0) {
                            db.typeTextMultiple(0, 1);
                            uiStage.addActor(db);
                        }
                        Gdx.app.log("MOVIMIENTO", "No se puede mover");
                    }

                    if (((int)(player.getX() / 32)) <= 15 && !dialogShownTile15) {
                        dialogShownTile15 = true;
                        db.typeTextMultiple(1, 5);
                        uiStage.addActor(db);
                        db.setOnFinishCallback(() -> {
                            inventory.incrementarNotes();
                            if (inventory.getNotes() == 1) {
                                showAllPistes();
                            }
                            map = new TmxMapLoader().load("escenarios/disco/disoMapPrueba.tmx");
                            mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);
                        });
                    }

                    Gdx.app.log("MOVIMIENTO",
                        "Objetivo: " + targetX + ", " + targetY +
                            " | Tile: " + ((int)(targetX/32)) + ", " + ((int)(targetY/32)));
                }
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

        private void pistes() {
            final int WIDTH_PISTES = 30;
            final int HEIGHT_PISTES = 50;

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
            btnPistes2.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes2.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes2);

            btnPistes3 = new Button(skinPistes);
            btnPistes3.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes3.setPosition(
                330,
                1345
            );
            btnPistes3.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes3.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes3);

            btnPistes4 = new Button(skinPistes);
            btnPistes4.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes4.setPosition(
                1039,
                655
            );
            btnPistes4.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes4.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes4);

            btnPistes5 = new Button(skinPistes);
            btnPistes5.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes5.setPosition(
                545,
                1240
            );
            btnPistes5.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes5.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes5);

            btnPistes6 = new Button(skinPistes);
            btnPistes6.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes6.setPosition(
                940,
                990
            );
            btnPistes6.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes6.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes6);

            btnPistes7 = new Button(skinPistes);
            btnPistes7.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes7.setPosition(
                1360,
                1270
            );
            btnPistes7.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes7.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes7);

            btnPistes8 = new Button(skinPistes);
            btnPistes8.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes8.setPosition(
                650,
                1370
            );
            btnPistes8.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes8.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes8);

            btnPistes9 = new Button(skinPistes);
            btnPistes9.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes9.setPosition(
                944,
                1340
            );
            btnPistes9.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes9.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes9);

            btnPistes10 = new Button(skinPistes);
            btnPistes10.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes10.setPosition(
                1370,
                1040
            );
            btnPistes10.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes10.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes10);

            btnPistes11 = new Button(skinPistes);
            btnPistes11.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes11.setPosition(
                1189,
                775
            );
            btnPistes11.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes11.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes11);

            btnPistes12 = new Button(skinPistes);
            btnPistes12.setSize(WIDTH_PISTES, HEIGHT_PISTES);
            btnPistes12.setPosition(
                365,
                665
            );
            btnPistes12.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    btnPistes12.remove();
                    accionBtns();
                }
            });
            //worldStage.addActor(btnPistes12);
        }

        private void accionBtns() {
            inventory.incrementarNotes();
            if (inventory.getNotes() == 2) {
                db.typeTextMultiple(6, 7);
                uiStage.addActor(db);
                timerGame.start();
                uiStage.addActor(timerLabel);
            } else {
                db.typeTextMultiple(5, 6);
                uiStage.addActor(db);
            }
        }

        private void showAllPistes() {
            btnPistes1.remove();
            worldStage.addActor(btnPistes2);
            worldStage.addActor(btnPistes3);
            worldStage.addActor(btnPistes4);
            worldStage.addActor(btnPistes5);
            worldStage.addActor(btnPistes6);
            worldStage.addActor(btnPistes7);
            worldStage.addActor(btnPistes8);
            worldStage.addActor(btnPistes9);
            worldStage.addActor(btnPistes10);
            worldStage.addActor(btnPistes11);
            worldStage.addActor(btnPistes12);
        }
    }
