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
    import com.badlogic.gdx.maps.objects.RectangleMapObject;
    import com.badlogic.gdx.maps.tiled.TiledMap;
    import com.badlogic.gdx.maps.tiled.TmxMapLoader;
    import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.scenes.scene2d.Actor;
    import com.badlogic.gdx.scenes.scene2d.InputEvent;
    import com.badlogic.gdx.scenes.scene2d.Stage;
    import com.badlogic.gdx.scenes.scene2d.ui.Button;
    import com.badlogic.gdx.scenes.scene2d.ui.Label;
    import com.badlogic.gdx.scenes.scene2d.ui.Skin;
    import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
    import com.badlogic.gdx.utils.viewport.FitViewport;

    import io.github.some_example_name.assets.*;

    public class Joc implements Screen {
        private Game joc;
        private Stage worldStage, uiStage;
        private Batch batch;
        private OrthographicCamera worldCamera, uiCamera;
        private TiledMap map;
        private Skin skinOptionsIcon, skinInventory, skinPistes, skinNomFont, skinTextFont, skinTimer;
        private Label timerLabel;
        private Button btnOptionsIcon, btnInventory;
        private Button btnPistes1, btnPistes2, btnPistes3, btnPistes4, btnPistes5,
            btnPistes6, btnPistes7, btnPistes8, btnPistes9, btnPistes10,
            btnPistes11, btnPistes12;
        private Button[] btns;
        private Killer[] killers = {
            new Elena(0),
            new Vera(0),
            new Victor(1),
            new Tobias(1)
        };;
        private OrthogonalTiledMapRenderer mapRenderer;
        private TimerGame timerGame;
        private static final float TOTAL_TIME = 180f; // 3 minuts en segons
        private Player player;
        private DialogBox db;
        private Inventory inventory;
        private InputMultiplexer multiplexer;
        public static final float WORLD_WIDTH = 3000;
        public static final float WORLD_HEIGHT = 2000;
        //private CollisionManager collisionManager;
        private float targetX;
        private float targetY;
        private boolean dialogShownTile15 = false;

        public Joc(Game joc) {
            this.joc = joc;
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
            //worldCamera.zoom = 0.3f;

            // --- CÀMERA DE UI (FIXA) ---
            uiCamera = new OrthographicCamera();
            uiCamera.setToOrtho(false, 1024, 768);

            FitViewport uiViewport = new FitViewport(1724, 768, uiCamera);
            uiStage = new Stage(uiViewport);

            // Per rebre clics els botons
            multiplexer = new InputMultiplexer();
            //multiplexer.addProcessor(uiStage);
            //multiplexer.addProcessor(worldStage);

            // ---- MAPA TILED ----
            map = new TmxMapLoader().load("escenarios/disco/discoInici.tmx");
            mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

            // ------- PERSONATGE -------
            player = new Player();
            worldStage.addActor(player);

            this.inventory = new Inventory(this.joc, this, killers[showKillers()]);

            skinPistes = new Skin();
            skinPistes.addRegions(AssetsManager.pistesIconAtlas);
            skinPistes.load(Gdx.files.internal(
                "icons/pistes/pistes.json"
            ));

            pistes();
        }

        @Override
        public void show() {

            // Input para Scene2D
           // Gdx.input.setInputProcessor(worldStage);


            Actor worldInput = new Actor();
            worldInput.setBounds(
                0, 0,
                worldStage.getWidth(),
                worldStage.getHeight()
            );

//            // ---- MAPA TILED ----
//            map = new TmxMapLoader().load("escenarios/disco/discoInici.tmx");
//            mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

            // Inicializar el gestor de colisiones
            //collisionManager = new CollisionManager("escenarios/disco/disoMapPrueba.tmx");

            // --------- SKINS ---------
            skinOptionsIcon = new Skin();
            skinOptionsIcon.addRegions(AssetsManager.optionsIconButtonAtlas);
            skinOptionsIcon.load(Gdx.files.internal(
                "buttons/button_options_icon/button_options_icon.json"
            ));

            skinInventory = new Skin();
            skinInventory.addRegions(AssetsManager.notesIconAtlas);
            skinInventory.load(Gdx.files.internal(
                "icons/notes/notes.json"
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
            btnInventory = new Button(skinInventory);
            btnInventory.setSize(150, 170);
            btnInventory.setPosition(
                0,
                0
            );

            // Acció del botó Notes
            btnInventory.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.log("INVENTORY", "Botó Notes");
                    Gdx.app.log("INVENTORY", "Notes: " + inventory.getNotes());
                    joc.setScreen(inventory);
                    //dispose();
                }
            });

//            // Agregar un actor de fondo para ver si está capturando los eventos
//            Actor background = new Actor();
//            background.setBounds(0, 0, uiStage.getWidth(), uiStage.getHeight());
//            background.setTouchable(Touchable.enabled);
//            background.addListener(new ClickListener() {
//                @Override
//                public void clicked(InputEvent event, float x, float y) {
//                    Gdx.app.log("BACKGROUND", "Click en fondo de UI en: " + x + ", " + y);
//                }
//            });
//            uiStage.addActor(background);
            uiStage.addActor(btnOptionsIcon);
            uiStage.addActor(btnInventory);

            // Pistes Icon
            //pistes();

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

            uiStage.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Gdx.app.log("UI_STAGE", "Touch down en UI: " + x + ", " + y);
                    return false;
                }
            });

            //multiplexer.addProcessor(new InputAdapter() {
            worldStage.addListener(new ClickListener() {
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                    Vector2 uiCoords = new Vector2(Gdx.input.getX(), Gdx.input.getY());

                    uiStage.screenToStageCoordinates(uiCoords);

                    Actor hitActor = uiStage.hit(uiCoords.x, uiCoords.y, true);
                    if (hitActor != null) {

                        return false;
                    }

                    if (db.hasParent()) {
                        db.onScreenClick();
                        return true; // consumimos para que no siga al mundo
                    }

                    // Click en el mundo → mover jugador
                    float targetX = x - player.getWidth() / 2f;
                    float targetY = y - (player.getHeight() - 95) / 2f;

                    if (canMove(targetX, targetY)) {
                        player.moveTo(targetX, targetY);
                    } else {
                        if (inventory.getNotes() == 0) {
                            db.typeTextMultiple(0, 1);
                            uiStage.addActor(db);
                        }
                        Gdx.app.log("MOVIMIENTO", "No se puede mover");
                    }

                    // 4️⃣ Dialogos especiales
                    if (((int)(player.getX() / 32)) <= 15 && !dialogShownTile15) {
                        dialogShownTile15 = true;
                        db.typeTextMultiple(1, 5);
                        uiStage.addActor(db);
                        inventory.incrementarNotes();
                        db.setOnFinishCallback(() -> {
                            Gdx.app.log("INVENTORY", "Notes: " + inventory.getNotes());
                            if (inventory.getNotes() == 1) showAllNotes();
                            else if (inventory.getNotes() == 2) setTimerLabel(0);
                            map = new TmxMapLoader().load("escenarios/disco/disoMapPrueba.tmx");
                            mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);
                        });
                    }

                    return true; // click consumido
                }
            });


            multiplexer.addProcessor(uiStage);
            multiplexer.addProcessor(worldStage);
            Gdx.input.setInputProcessor(multiplexer);
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

//            if (Gdx.input.justTouched()) {
//                if (db.hasParent()) {
//                    db.onScreenClick();
//                } else {
//
//                    // ---------- UI ----------
//                    Vector2 uiTouch = new Vector2(
//                        Gdx.input.getX(),
//                        Gdx.input.getY()
//                    );
//                    uiStage.screenToStageCoordinates(uiTouch);
//
//                    Actor uiHit = uiStage.hit(uiTouch.x, uiTouch.y, true);
//
//                    // Si es toca la UI, Scene2D s'encarrega
//                    if (uiHit != null) {
//                        Gdx.app.log("TOUCH", "UI");
//                        return;
//                    }
//
//                    // ---------- MÓN ----------
//                    Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//                    worldStage.getCamera().unproject(touch);
//
//                    targetX = touch.x - player.getWidth() / 2f;
//                    targetY = touch.y - (player.getHeight() - 95) / 2f;
//
//                    if (canMove(targetX, targetY)) {
//
//                        player.moveTo(targetX, targetY);
//
////                        if (db.isFinished()) {
////                            db.remove(); // elimina del stage
////                        }
//
//                    } else {
//                        //db.setText(DialogBox.dialogs[0]);
//                        if (inventory.getNotes() == 0) {
//                            db.typeTextMultiple(0, 1);
//                            uiStage.addActor(db);
//                        }
//                        Gdx.app.log("MOVIMIENTO", "No se puede mover");
//                    }
//
//                    if (((int)(player.getX() / 32)) <= 15 && !dialogShownTile15) {
//                        dialogShownTile15 = true;
//                        db.typeTextMultiple(1, 5);
//                        uiStage.addActor(db);
//                        db.setOnFinishCallback(() -> {
//                            inventory.incrementarNotes();
//                            if (inventory.getNotes() == 1) {
//                                showAllPistes();
//                            } else if (inventory.getNotes() == 2) setTimerLabel(delta);
//                            map = new TmxMapLoader().load("escenarios/disco/disoMapPrueba.tmx");
//                            mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);
//                        });
//                    }
//
//                    Gdx.app.log("MOVIMIENTO",
//                        "Objetivo: " + targetX + ", " + targetY +
//                            " | Tile: " + ((int)(targetX/32)) + ", " + ((int)(targetY/32)));
//                }
//            }


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

        private void setTimerLabel(float delta) {
            timerGame.update(delta);
            float remainingTime = TOTAL_TIME - timerGame.getElapsedTime();

            if (remainingTime < 0) {
                remainingTime = 0;
            }

            // Convertir a minutos y segundos
            int minutes = (int)(remainingTime / 60);
            int seconds = (int)(remainingTime % 60);

            // Format MM:SS
            timerLabel.setText(String.format("%02d:%02d", minutes, seconds));
        }

        private void showAllNotes() {
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

        private int showKillers() {

            int k = 0;

            int[][] posicions = {
                {600, 1000},
                {600, 860},
                {935, 1000},
                {935, 860}
            };

            int killer = (int) (Math.random() * 3);

            for (int i = 0; i < killers.length; i++) {
                killers[i].setPosition(posicions[i][0], posicions[i][1]);

                if (i == killer) {
                    k = i;
                    killers[i].setIsKiller(true);
                    Gdx.app.log("ASSASSÍ", "NOM " + killers[i].getNom());
                }

                // Añadir al stage
                worldStage.addActor(killers[i]);
            }

            return k;
        }
    }
