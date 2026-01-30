package io.github.some_example_name.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
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
import com.badlogic.gdx.math.MathUtils;

import io.github.some_example_name.assets.AssetsManager;
import io.github.some_example_name.assets.Player;

public class Joc implements Screen {
    private Game joc;
    private Stage worldStage, uiStage;
    private Batch batch;
    private OrthographicCamera worldCamera, uiCamera;
    private TiledMap map;
    private Skin skinOptionsIcon, skinNotes, skinPistes;
    private Button btnOptionsIcon, btnNotes, btnPistes1;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Player player;
    public static final float WORLD_WIDTH = 3000;
    public static final float WORLD_HEIGHT = 2000;
    private CollisionManager collisionManager;
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
        //worldCamera.zoom = 0.02f;

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
        map = new TmxMapLoader().load("escenarios/disco/disoMapPrueba.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, 1f);

        // Inicializar el gestor de colisiones
        collisionManager = new CollisionManager("escenarios/disco/disoMapPrueba.tmx");

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
        btnPistes1 = new Button(skinPistes);
        btnPistes1.setSize(250, 150);
        /*btnPistes1.setPosition(
            512 - 381,
            384 - 75
        );*/

        // ------- PERSONATGE -------
        player = new Player();
        worldStage.addActor(player);
        uiStage.addActor(btnOptionsIcon);
        uiStage.addActor(btnNotes);
    }

    @Override
    public void render(float delta) {
        // Pinta la pantalla
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

    private boolean isBlocked(float x, float y) {
        TiledMapTileLayer layer =
            (TiledMapTileLayer) map.getLayers().get("Collisions");

        int tileX = (int)(x / layer.getTileWidth());
        int tileY = (int)(y / layer.getTileHeight());

        if (tileX < 0 || tileY < 0 ||
            tileX >= layer.getWidth() ||
            tileY >= layer.getHeight()) {
            return true; // fuera del mapa = bloqueado
        }

        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        if (cell == null) return false;

        MapProperties props = cell.getTile().getProperties();
        return props.containsKey("collision");
    }

    /*private boolean playerCollides(float x, float y) {
        float w = player.getWidth();
        float h = player.getHeight();

        //Rectangle r = player.getBoundingRectangle();

        return isBlocked(x, y) ||
            isBlocked(x + w, y) ||
            isBlocked(x, y + h) ||
            isBlocked(x + w, y + h);
    }*/

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

    // Prova 1 - canMove
//    private boolean canMove(float nextX, float nextY) {
//        Rectangle bounds = player.getBounds();
//        bounds.setPosition(nextX, nextY);
//
//        float w = player.getWidth();
//        float h = player.getHeight();
//        float foot = 10f;
//        Gdx.app.log("altura", "Altura: " + player.getHeight());
//
//
//        // Esquinas
//        if (isTileBlocked(nextX, nextY) ||
//            isTileBlocked(nextX + w - 1, nextY) ||
//            isTileBlocked(nextX, nextY + h - 1) ||
//            isTileBlocked(nextX + w - 1, nextY + foot - 1)) {
//            return false;
//        }
//
//        // Puntos intermedios (opcional, para mayor precisión)
//        if (isTileBlocked(nextX + w/2, nextY) ||
//            isTileBlocked(nextX, nextY + h/2) ||
//            isTileBlocked(nextX + w/2, nextY + h - 1) ||
//            isTileBlocked(nextX + w - 1, nextY + foot/2)) {
//            return false;
//        }
//
//        return true;
//    }

    // Prova 2 - canMove
    private boolean canMove(float nextX, float nextY) {
        float w = player.getWidth();
        float h = player.getHeight();
        float footHeight = 10f; // Altura de los pies

        // Coordenadas de la zona de los pies
        float left = nextX;
        float right = nextX + w - 1;
        float bottom = nextY;               // base del sprite
        float top = bottom + footHeight - 1; // altura de los pies

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
    }

    // Prova 3
    private boolean isTileBlocked(float x, float y) {

        TiledMapTileLayer layer =
            (TiledMapTileLayer) map.getLayers().get("Collisions");

        if (layer == null) {
            Gdx.app.log("ERROR", "No existe la capa 'Collisions'");
            return false;
        }

        // Pruebas
        /*TiledMapTileLayer.Cell cell1 = layer.getCell(7, 41);
        TiledMapTileLayer.Cell cell2 = layer.getCell(8, 40);

        if (cell1 != null && cell1.getTile() != null) {
            int gid1 = cell1.getTile().getId();
            Gdx.app.log("TILE_DEBUG", "GID CON COLLISION = " + gid1);
        }

        if (cell2 != null && cell2.getTile() != null) {
            int gid1 = cell2.getTile().getId();
            Gdx.app.log("TILE_DEBUG", "GID SIN COLLISION = " + gid1);
        }*/

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


// Prova 1 - Render
//    @Override
//    public void render(float delta) {
//        // Pinta la pantalla
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
////        if (Gdx.input.justTouched()) {
////
////            Vector3 touch = new Vector3(
////                Gdx.input.getX(),
////                Gdx.input.getY(),
////                0
////            );
////            stage.getCamera().unproject(touch);
////            player.moveTo(touch.x, touch.y);
////
////            //Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
////        }
//
//        worldCamera.position.set(
//            player.getX() + player.getWidth() / 2,
//            player.getY() + player.getHeight() / 2,
//            0
//        );
//        worldCamera.update();
//
//
//        if (Gdx.input.justTouched()) {
//            Vector3 touch = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
//            worldStage.getCamera().unproject(touch);
//
//            float targetX = touch.x - player.getWidth() / 2f;
//            float targetY = touch.y - (player.getHeight() - 185) / 2f;
//
//            // --- Limitar dentro del mapa ---
//            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collisions");
////            map = new TmxMapLoader().load("escenarios/disco/discoMap.tmx");
////            for (MapLayer layers : map.getLayers()) {
////                Gdx.app.log("CAPAS", "Nombre: " + layers.getName() + ", Tipo: " + layers.getClass().getSimpleName());
////            }
//            if (layer != null) {
//                float mapWidth = layer.getWidth() * 32;   // TMX tile width
//                float mapHeight = layer.getHeight() * 32; // TMX tile height
//
//                targetX = MathUtils.clamp(targetX, 0, mapWidth - player.getWidth());
//                targetY = MathUtils.clamp(targetY, 0, mapHeight - player.getHeight());
//            }
//
//            Gdx.app.log("MOVIMIENTO",
//                "Objetivo: " + targetX + ", " + targetY +
//                    " | Tile: " + ((int)(targetX/32)) + ", " + ((int)(targetY/32)));
//
//
//            /*float px = player.getX();
//            float py = player.getY();
//            float w = player.getWidth();
//            float h = player.getHeight();
//
////         player.moveTo(targetX, targetY);
//           // Només es mou quan el tile de destí no té colisió
//            if (!isTileBlocked(px, py) &&
//                !isTileBlocked(px + w, py) &&
//                !isTileBlocked(px, py + h) &&
//                !isTileBlocked(px + w, py + h)) {
//                player.moveTo(targetX, targetY);
//            } else {
//                Gdx.app.log("COLISIÓN", "¡No se puede mover aquí!");
//            }*/
//
//            /*if (canMove(targetX, targetY)) {
//                player.moveTo(targetX, targetY);
//            } else {
//                Gdx.app.log("COLISIÓN", "¡No se puede mover aquí!");
//            }*/
//
//        }
//
//
//        // ---- DIBUIXAR MAPA ----
//        mapRenderer.setView(worldCamera);
//        mapRenderer.render();
//
//        /*batch.begin();
//
//        batch.draw(AssetsManager.fonsJoc,0,0, stage.getViewport().getWorldWidth(),
//            stage.getViewport().getWorldHeight());
//
//        batch.end();*/
//
//        worldStage.act(delta);
//        worldStage.draw();
//        uiStage.act(delta);
//        uiStage.draw();
//    }

    // Prova 1
//    private boolean isTileBlocked(float x, float y) {
//        // Convertir coordenades del món a coordenades de tile
//        /*int tileX = (int) (x / 32);
//        int tileY = (int) (y / 32);
//
//
//        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Capa de patrones 1");
//        if (layer == null) return false;
//
//
//        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
//        if (cell == null || cell.getTile() == null) return false;
//
//
//        // Si el tile té objectes (colisions), bloqueja
//        return cell.getTile().getObjects().getCount() > 0;*/
//
//        // Obtener la capa
//        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collisions");
//        if (layer == null) return false;
//
//        // Convertir coordenadas a tiles (32x32 según tu TMX)
//        int tileX = (int) (x / 32);
//        int tileY = (int) (y / 32);
//
//        int invertedY = layer.getHeight() - 1 - tileY;
//        if (invertedY < 0 || invertedY >= layer.getHeight()) {
//            return true; // fuera del mapa = colisión
//        }
//
//        // Obtener la capa
//        //TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collisions");
//        if (layer == null) {
//            Gdx.app.log("ERROR", "No se encontró la capa 'Collisions'");
//            return false;
//        }
//
//        //int invertedY = layer.getHeight() - 1 - tileY;
//
//        Gdx.app.log("DEBUG",
//            "Mundo: " + x + ", " + y +
//                " | Tile: " + tileX + ", " + tileY +
//                " | Invertido: " + tileX + ", " + invertedY);
//
//        /*// Obtener la celda
//        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);
//        if (cell == null) return false; // Tile vacío = sin colisión
//
//        // SOLUCIÓN: Verificar si el tile tiene un ID específico
//        // Los IDs de tiles con colisión son los que viste en la imagen
//        int tileId = cell.getTile().getId();
//
//        // Lista de IDs que tienen colisión (según tu imagen)
//        // Estos son los números que viste en las propiedades
//        int[] collisionIds = {
//            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25,
//            26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 42, 44, 46, 47, 49, 51, 52, 53,
//            56, 58, 59, 60, 62, 63, 65, 67, 70, 72, 74, 78, 79, 81, 85, 86, 88, 92, 94, 96, 97, 98, 99
//        };
//
//        // Verificar si el ID está en la lista
//        for (int id : collisionIds) {
//            if (tileId == id) {
//                return true; // ¡COLISIÓN!
//            }
//        }*/
//
//        // 4. Verificar límites
//        if (tileX < 0 || tileX >= layer.getWidth() ||
//            invertedY < 0 || invertedY >= layer.getHeight()) {
//            return true; // Fuera del mapa = colisión
//        }
//
//        // 5. Obtener celda
//        TiledMapTileLayer.Cell cell = layer.getCell(tileX, invertedY);
//
//        // 6. Si no hay celda, es vacío
//        if (cell == null) {
//            Gdx.app.log("DEBUG", "Celda vacía en [" + tileX + "," + invertedY + "]");
//            return false;
//        }
//
//        // Verificar propiedad "collision"
//        MapProperties props = cell.getTile().getProperties();
//        if (props.containsKey("collision")) {
//            Object val = props.get("collision");
//            if (val instanceof Boolean) return (Boolean) val;
//            if (val instanceof String) return Boolean.parseBoolean(val.toString());
//        }
//
//        // 2. Verificar si el tile tiene objectgroup
//        if (cell.getTile().getObjects() != null && cell.getTile().getObjects().getCount() > 0) {
//            return true;
//        }
//
//        // 7. Si no hay tile, es vacío
//        if (cell.getTile() == null) {
//            Gdx.app.log("DEBUG", "Tile null en [" + tileX + "," + invertedY + "]");
//            return false;
//        }
//
//        // 8. Obtener propiedades del tile
//        MapProperties properties = cell.getTile().getProperties();
//        int tileId = cell.getTile().getId();
//
//        Gdx.app.log("DEBUG",
//            "Tile ID: " + tileId +
//                " | Propiedades: " + properties.getKeys());
//
//        // 9. Verificar propiedad "collision"
//        Object collisionValue = properties.get("collision");
//        //Object collisionValue = cell.getTile().getProperties().get("collision");
//
//        if (collisionValue != null) {
//            // Puede ser Boolean o String "true"/"false"
//            if (collisionValue instanceof Boolean) {
//                boolean hasCollision = (Boolean) collisionValue;
//                Gdx.app.log("DEBUG", "Colisión (bool): " + hasCollision);
//                return hasCollision;
//            } else if (collisionValue instanceof String) {
//                String collisionStr = (String) collisionValue;
//                boolean hasCollision = collisionStr.equalsIgnoreCase("true");
//                Gdx.app.log("DEBUG", "Colisión (string): " + hasCollision + " (" + collisionStr + ")");
//                return hasCollision;
//            }
//        } else {
//            return false;
//        }
//
//        // 10. Si no tiene propiedad, usar regla basada en ID
//        Gdx.app.log("DEBUG", "Sin propiedad 'collision', usando ID: " + tileId);
//
//        // Regla simple basada en tu mapa:
//        // - 0 = vacío (sin colisión)
//        // - 361 = suelo (sin colisión)
//        // - otros = con colisión
//        if (tileId == 0 || tileId == 361) {
//            return false; // Sin colisión
//        }
//
//        if (tileX < 0 || tileX >= layer.getWidth() || invertedY < 0 || invertedY >= layer.getHeight()) {
//            return true;
//        }
//
//        return true;
//    }


    // Prova 2
//    private boolean isTileBlocked(float worldX, float worldY) {
//        // Convertir coordenadas del mundo a tiles (32x32)
//        int tileX = (int) (worldX / 32);
//        int tileY = (int) (worldY / 32);
//
//        // Obtener la capa
////        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("Collisions");
////        if (layer == null) {
////            Gdx.app.log("ERROR", "No se encontró la capa 'Collisions'");
////            return false;
////        }
//        boolean blocked = collisionManager.hasCollision(tileX, tileY);
//
//        Gdx.app.log("COLISION_DEBUG",
//            String.format("Mundo(%.1f,%.1f) -> Tile(%d,%d) -> Colisión: %b",
//                worldX, worldY, tileX, tileY, blocked));
//
//        return blocked;

//        // Invertir Y (Tiled usa Y desde arriba, LibGDX desde abajo)
//        int mapHeight = layer.getHeight();
//        int invertedY = mapHeight - 1 - tileY;
//
//        // Verificar límites
//        if (tileX < 0 || tileX >= layer.getWidth() ||
//            invertedY < 0 || invertedY >= layer.getHeight()) {
//            return true; // Fuera del mapa = colisión
//        }
//
//        // Obtener la celda
//        TiledMapTileLayer.Cell cell = layer.getCell(tileX, invertedY);
//
//        if (cell == null) {
//            // Celda vacía = sin colisión
//            Gdx.app.log("DEBUG", "Celda vacía en [" + tileX + "," + invertedY + "]");
//            return false;
//        }
//
//        // Obtener el tile
//        if (cell.getTile() == null) {
//            Gdx.app.log("DEBUG", "Tile null en [" + tileX + "," + invertedY + "]");
//            return false;
//        }
//
//        // Obtener el ID del tile (¡IMPORTANTE!)
//        int tileId = cell.getTile().getId();
//        Gdx.app.log("DEBUG",
//            "Posición: [" + tileX + "," + invertedY +
//                "] | Tile ID: " + tileId);
//
//        // Aquí está la clave: tu TMX tiene firstgid="1"
//        // Esto significa que el ID 0 en el TMX corresponde al tile vacío
//        // Los IDs 1, 2, 3... en el TMX corresponden a los tiles 0, 1, 2... en el TSX
//
//        // Si tileId == 0, es vacío (sin colisión)
//        if (tileId == 0) {
//            return false;
//        }
//
//        // Si tileId >= 1, convertirlo a ID del TSX
//        int tsxTileId = tileId - 1; // Restar firstgid
//
//        Gdx.app.log("DEBUG",
//            "Tile ID en TMX: " + tileId +
//                " | ID en TSX: " + tsxTileId);
//
//        // Ahora usa tsxTileId para buscar en tu sistema de colisiones
//        return tieneColisionEnTSX(tsxTileId);
//    }

//    private boolean tieneColisionEnTSX(int tsxTileId) {
//        // Aquí necesitas cargar las colisiones de tu TSX
//
//        // Ejemplo: si tienes un mapa de booleanos
//        boolean[] colisionesPorTile = new boolean[384]; // 384 tiles en tu TSX
//
//        // Inicializar con los tiles que tienen colisión
//        // Según tu TSX, casi todos los tiles tienen collision=true
//        for (int i = 0; i < colisionesPorTile.length; i++) {
//            colisionesPorTile[i] = true; // Por defecto, todos con colisión
//        }
//
//        // Marcar algunos sin colisión (como los 361 que mencionas)
//        if (tsxTileId == 361) {
//            return false; // Sin colisión
//        }
//
//        // También marcar otros que no tengan colisión
//        // Por ejemplo, tiles con propiedad collision=false
//        if (tsxTileId == 105 || tsxTileId == 106 || tsxTileId == 107 ||
//            tsxTileId == 108 || tsxTileId == 109 || tsxTileId == 110) {
//            return false;
//        }
//
//        // Para tiles con elipses (collision=false en tu TSX)
//        if (tsxTileId == 203 || tsxTileId == 205 || tsxTileId == 228 ||
//            tsxTileId == 229) {
//            return false;
//        }
//
//        // Por defecto, todos los demás tienen colisión
//        return true;
//    }

}
