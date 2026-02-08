package io.github.some_example_name.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class DialogBox extends Table {

    private Label nameLabel;
    private Label textLabel, infoLabel;
    private Image portrait;
    private int indexI, indexF;
    private String name;
    String[] dialogs;
    private boolean finished;
    private Runnable onFinishCallback;
    private Timer.Task typingTask;
    //private Texture textureDialog = AssetsManager.dialogBoxTalking;

    public DialogBox(Skin skinNom, Skin skinText, Texture textureDialog) {
        setFillParent(true);
        bottom().pad(20);

        NinePatch ninePatch = new NinePatch(textureDialog,
            10, 10, 10, 10);
        NinePatchDrawable dialogBoxDrawable = new NinePatchDrawable(ninePatch);

        //portrait = new Image(AssetsManager.player4K);
        nameLabel = new Label("", skinNom);
        textLabel = new Label("", skinText);
        textLabel.setWrap(true);

        Table textTable = new Table();
        // Mida fixe de la taula
        textTable.setSize(600, 400);
        textTable.setBackground(dialogBoxDrawable);

        // Anclar contingut
        textTable.top().left();

        // Mostra les dimensions reals de la taula dibuixades amb línies
        textTable.setDebug(false);

        // Columna 1 → Nom
        nameLabel.setAlignment(Align.center);
        textTable.add(nameLabel)
            .width(133)
            //.height(300)
            .padTop(225);

        // Columna 2 → Text
        textTable.add(textLabel)
            .width(410)
            .height(300)
            .left()
            .top()
            .padLeft(50)
            .expandY();

        textTable.row(); // Tancar fila

        add(textTable);
    }

    public DialogBox(Skin skinText) {
        setFillParent(true);
        bottom().pad(20);

        NinePatch ninePatch = new NinePatch(AssetsManager.dialogBoxInfo,
            10, 10, 10, 10);
        NinePatchDrawable dialogBoxDrawable = new NinePatchDrawable(ninePatch);

        textLabel = new Label("", skinText);
        textLabel.setWrap(true);

        Table textTable = new Table();
        // Mida fixe de la taula
        textTable.setSize(600, 400);
        textTable.setBackground(dialogBoxDrawable);

        // Anclar contingut
        textTable.top().left();

        // Mostra les dimensions reals de la taula dibuixades amb línies
        textTable.setDebug(true);

        // Columna 1 → Missatge
        textLabel.setAlignment(Align.center);
        textTable.add(textLabel)
            .width(410)
            .height(300)
            .left()
            .top()
            .padLeft(50)
            .expandY();

        textTable.row(); // Tancar fila

        add(textTable);
    }

    /**
     * Mètode per establir el text del dialog box
     *
     * @param text
     */
    public void setText(String text) {
        this.textLabel.setText(text);
    }

//    public void setTextureDialog(Texture texture) {
//        this.textureDialog = texture;
//    }

    public void setName(String name) {
        this.nameLabel.setText(name);
    }

    /**
     * Mètode per saber si ha finalitzat el diàleg
     *
     * @return finished
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Mètode per establir varios textos del dialog box
     *
     * @param inici
     * @param fi
     */
    public void typeTextMultiple(int inici, int fi, String[] dialogs) {
        indexI = inici;
        indexF = fi;
        this.dialogs = dialogs;
        nextDialog();
    }

    /**
     * Mètode per passar al següent diàleg, s'hi ni ha
     */
    private void nextDialog() {
        if (indexI >= indexF) {
            remove();
            if (onFinishCallback != null) {
                onFinishCallback.run(); // ejecuta lo que haya que hacer al terminar
            }
            return;
        }
        this.textLabel.setText("");
        typeText(dialogs[indexI]);
    }

    /**
     * Mètode perquè quan cliquis mostri tot el missatge de cop o passi el següent,
     * depenent si encara no ha finalitzat el text
     */
    public void onScreenClick() {
        if (finished) {
            indexI++;
            nextDialog();
        } else {
            // Completa el texto actual instantáneamente
            if (typingTask != null) {
                typingTask.cancel();
            }
            this.textLabel.setText(dialogs[indexI]);
            finished = true;
        }
    }

    /**
     * Mètode per establir l'animació del text del dialog box
     *
     * @param fullText
     */
    public void typeText(String fullText) {
        textLabel.setText("");
        finished = false;

        if (typingTask != null) {
            typingTask.cancel();
        }

        typingTask = new Timer.Task() {
            int index = 0;

            @Override
            public void run() {
                if (index < fullText.length()) {
                    textLabel.setText(textLabel.getText() +
                        String.valueOf(fullText.charAt(index)));
                    index++;
                } else {
                    finished = true;
                    cancel();
                }
            }
        };

        Timer.schedule(typingTask, 0, 0.03f);
    }

    /**
     * Mètode perquè s'executin altres accions abans de que desapareixi el diàleg
     *
     * @param callback
     */
    public void setOnFinishCallback(Runnable callback) {
        this.onFinishCallback = callback;
    }

}
