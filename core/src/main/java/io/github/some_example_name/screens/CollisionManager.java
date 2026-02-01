//package io.github.some_example_name.screens;
//
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.maps.tiled.TmxMapLoader;
//import com.badlogic.gdx.utils.Array;
//import com.badlogic.gdx.utils.ObjectMap;
//
//public class CollisionManager {
//    private TiledMap map;
//    private TiledMapTileLayer collisionLayer;
//    private ObjectMap<Integer, Boolean> collisionMap; // Key: TSX tile ID, Value: has collision
//
//    public CollisionManager(String mapPath) {
//        map = new TmxMapLoader().load(mapPath);
//        collisionLayer = (TiledMapTileLayer) map.getLayers().get("Collisions");
//
//        // Inicializar el mapa de colisiones
//        collisionMap = new ObjectMap<>();
//
//        // Preprocesar todas las colisiones del TSX
//        // En tu TSX, casi todos los tiles tienen collision=true
//        // Los que tienen collision=false son específicos
//        loadCollisionsFromTSX();
//    }
//
//    private void loadCollisionsFromTSX() {
//        // Por defecto, todos los tiles tienen colisión
//        // Esto es porque en tu TSX la mayoría tienen collision=true
//
//        // Tiles SIN colisión (basado en tu TSX)
//        Array<Integer> noCollisionTiles = new Array<>();
//
//        // Tiles con collision=false en el TSX
//        noCollisionTiles.addAll(105, 106, 107, 108, 109, 110); // Tiles con collision=false
//
//        // Tiles con elipses (collision=false)
//        noCollisionTiles.addAll(180, 181, 203, 205, 228, 229);
//
//        // Tiles vacíos o especiales
//        noCollisionTiles.addAll(146, 170, 194, 218, 242, 266, 290, 314, 338);
//
//        // Para todos los tiles (0-383), asumir que tienen colisión
//        // excepto los de la lista anterior
//        for (int i = 0; i < 384; i++) {
//            collisionMap.put(i, !noCollisionTiles.contains(i, false));
//        }
//
//        // Tile 361 es especial - según tu comentario, es "suelo" sin colisión
//        collisionMap.put(361, false);
//    }
//
//    public boolean hasCollision(int tileX, int tileY) {
//        if (collisionLayer == null) {
//            return false;
//        }
//
//        // Convertir coordenadas Y (Tiled usa Y desde arriba)
//        int mapHeight = collisionLayer.getHeight();
//        int invertedY = mapHeight - 1 - tileY;
//
//        // Verificar límites
//        if (tileX < 0 || tileX >= collisionLayer.getWidth() ||
//            invertedY < 0 || invertedY >= collisionLayer.getHeight()) {
//            return true; // Fuera del mapa = colisión
//        }
//
//        // Obtener la celda
//        TiledMapTileLayer.Cell cell = collisionLayer.getCell(tileX, invertedY);
//
//        if (cell == null || cell.getTile() == null) {
//            return false; // Celda vacía = sin colisión
//        }
//
//        // Obtener el ID del tile en el TMX
//        int tmxtileId = cell.getTile().getId();
//
//        // IMPORTANTE: El TMX tiene firstgid="1"
//        // Esto significa: tmxtileId 0 = vacío, 1 = tile 0 del TSX, 2 = tile 1 del TSX, etc.
//        if (tmxtileId == 0) {
//            return false; // Tile vacío en el TMX
//        }
//
//        // Convertir a ID del TSX
//        int tsxtileId = tmxtileId - 1;
//
//        // Verificar si este tile tiene colisión
//        Boolean hasCollision = collisionMap.get(tsxtileId);
//
//        // Si no está en el mapa, asumir que tiene colisión (por seguridad)
//        return hasCollision != null ? hasCollision : true;
//    }
//
//    public void dispose() {
//        if (map != null) {
//            map.dispose();
//        }
//    }
//}
