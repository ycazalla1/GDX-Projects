package io.github.some_example_name.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    // Animacions quiet
    private Animation<TextureRegion> idleFront, idleLeft, idleRight, idleBack;
    // Animacions de moviment
    private Animation<TextureRegion> walkFront, walkLeft, walkRight, walkBack;
    private Animation<TextureRegion> currentAnimation;
    private boolean moving = false;
    public static final String PLAYER_NAME = "Penny";
    private long walkingSoundId = -1;
    private float stateTime = 0f;
    private float frameDurationIdle = 0.30f, frameDurationWalk = 0.15f;
    private float speed = 200f;
    private Vector2 direction = new Vector2();
    private Vector2 target = new Vector2();
    // Colisions
    private Rectangle bounds;

    public Player() {

        // Dividir el spritesheet
        TextureRegion[][] frames = TextureRegion.split(
            AssetsManager.player,
            64, // ample del frame
            64  // alçada del frame
        );

        bounds = new Rectangle(
            getX(),
            getY(),
            getWidth(),
            getHeight() - 5
        );

        // ---------- CREACIÓ ANIMACIONS ----------
        // Animacions quiet
        idleFront = new Animation<>(frameDurationIdle, frames[24][0], frames[24][1]);
        idleBack = new Animation<>(frameDurationIdle, frames[22][0], frames[22][1]);
        idleLeft = new Animation<>(frameDurationIdle, frames[23][0], frames[23][1]);
        idleRight = new Animation<>(frameDurationIdle, frames[25][0], frames[25][1]);

        // Animacions de moviment
        walkFront = new Animation<>(frameDurationWalk, frames[10][0], frames[10][1], frames[10][2],
            frames[10][3], frames[10][4], frames[10][5], frames[10][6], frames[10][7], frames[10][8]);
        walkBack = new Animation<>(frameDurationWalk, frames[8][0], frames[8][1], frames[8][2],
            frames[8][3], frames[8][4], frames[8][5], frames[8][6], frames[8][7], frames[8][8]);
        walkLeft = new Animation<>(frameDurationWalk, frames[9][0], frames[9][1], frames[9][2],
            frames[9][3], frames[9][4], frames[9][5], frames[9][6], frames[9][7], frames[9][8]);
        walkRight = new Animation<>(frameDurationWalk, frames[11][0], frames[11][1], frames[11][2],
            frames[11][3], frames[11][4], frames[11][5], frames[11][6], frames[11][7], frames[11][8]);


        currentAnimation = idleBack;

        setSize(90, 130);
        setPosition(725, 600);
    }

    public static String[] dialogsPlayer = {
        "He de trobar el meu marit, em va dir que estava en els sofàs de l'esquerra.",
        "Oooh déu meu!! És mort.",
        "Hi ha una nota al seu costat, què serà?",
        "Sembla una nota... Segurament hi haurà més d'una.",
        "Penso trobar-les totes i descobrir qui és l'assassí!!! Me les pagarà!!!",
        "Oooh, una nova nota!",
        "En aquesta nota diu, que només tinc tres minuts per descobrir qui és l'assassí.",
        "He trobat tres notes, això em porta a deduir la següent pista."
    };

    public void moveTo(float targetX, float targetY) {

        if (walkingSoundId == -1) {
            walkingSoundId = SoundGame.walkingSound.loop();
            SoundGame.walkingSound.setVolume(walkingSoundId, 1.0f);
        }

        target.set(targetX, targetY);

        direction.set(
            targetX - getX(),
            targetY - getY()
        );

        // Aturar el personatge quan arriba al punt senyalat
        if (direction.len() < 5f) {
            moving = false;
            SoundGame.walkingSound.stop(walkingSoundId);
            walkingSoundId = -1;
            return;
        }

        direction.nor();
        moving = true;

        // Canviar animació segons direcció
        if (Math.abs(direction.x) > Math.abs(direction.y)) {
            currentAnimation = direction.x > 0 ? walkRight : walkLeft;
        } else {
            currentAnimation = direction.y > 0 ? walkBack : walkFront;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (!moving) return;

        float moveX = direction.x * speed * delta;
        float moveY = direction.y * speed * delta;

        setPosition(getX() + moveX, getY() + moveY);

        // Comprovar si ha arribat el personatge al punt senyalat
        if (Vector2.dst(getX(), getY(), target.x, target.y) < 5f) {
            setPosition(target.x, target.y);
            SoundGame.walkingSound.stop(walkingSoundId);
            walkingSoundId = -1;
            moving = false;

            if (Math.abs(direction.x) > Math.abs(direction.y)) {
                currentAnimation = direction.x > 0 ? idleRight : idleLeft;
            } else {
                currentAnimation = direction.y > 0 ? idleBack : idleFront;
            }
        }

        // Actualitzar colisió si usas Rectangle
        if (bounds != null) {
            bounds.setPosition(getX(), getY());
        }
//        super.act(delta);
//
//        if (!direction.isZero()) {
//            moveBy(direction.x * speed * delta,
//                direction.y * speed * delta);
//        }
//
//        bounds.setPosition(getX(), getY());
//        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
    }

    public Rectangle getBounds() {
        bounds.setPosition(getX(), getY());
        return bounds;
    }
}
