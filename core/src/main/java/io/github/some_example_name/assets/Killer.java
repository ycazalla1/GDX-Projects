package io.github.some_example_name.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Killer extends Actor {

    // Animacions quiet
    private Animation<TextureRegion> idleFront, idleLeft, idleRight, idleBack;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime = 0f;
    private float frameDuration = 0.30f;
    protected boolean isKiller;
    protected String nom;
    protected String pistes[], dialogs[];
    protected TextureRegion[][] frames;
    protected Texture textureDialog;

    /**
     * Constructor de la classe Killer
     *
     * @param texture
     * @param idle
     * @param nom
     */
    public Killer(Texture texture, int idle, String nom) {

        this.nom = nom;

         frames = TextureRegion.split(
            texture,
            64, // ample del frame
            64  // alçada del frame
        );

        // ---------- CREACIÓ ANIMACIONS ----------
        // Animacions quiet
        idleFront = new Animation<>(frameDuration, frames[24][0], frames[24][1]);
        idleBack = new Animation<>(frameDuration, frames[22][0], frames[22][1]);
        idleLeft = new Animation<>(frameDuration, frames[23][0], frames[23][1]);
        idleRight = new Animation<>(frameDuration, frames[25][0], frames[25][1]);

        currentAnimation = setCurrentAnimation(idle);

        setSize(90, 130);
    }

    /**
     * Mètode getter per obtenir el nom del killer
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    public Texture getTextureDialog() {
        return textureDialog;
    }

    /**
     * Mètode getter per obtenir les pistes del killer
     *
     * @return
     */
    public String[] getPistes() {
        return pistes;
    }

    /**
     * Mètode getter per obtenir els diàlegs del killer
     *
     * @return
     */
    public String[] getDialogs() {
        return dialogs;
    }

    /**
     * Mètode setter per indicar qui és el killer
     *
     * @param isKiller
     */
    public void setIsKiller(boolean isKiller) {
        this.isKiller = isKiller;
    }

    /**
     * Mètode per indicar l'animació actual del killer
     *
     * @param idle
     * @return
     */
    protected Animation<TextureRegion> setCurrentAnimation(int idle) {
        if (idle == 0) {
            return idleRight;
        } else if (idle == 1) {
            return idleLeft;
        } else if (idle == 2) {
            return idleFront;
        } else if (idle == 3) {
            return idleBack;
        }

        return null;
    }

    /**
     * Mètode per dibuixar l'animació del killer
     *
     * @param batch
     * @param parentAlpha The parent alpha, to be multiplied with this actor's alpha, allowing the parent's alpha to affect all
     *           children.
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);

        batch.draw(frame, getX(), getY(), getWidth(), getHeight());
    }

}
