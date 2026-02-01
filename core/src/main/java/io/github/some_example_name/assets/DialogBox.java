package io.github.some_example_name.assets;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Timer;

public class DialogBox extends Table {

    private Label nameLabel;
    private Label textLabel;
    private Image portrait;
    private int indexI, indexF;
    private boolean finished;
    private Runnable onFinishCallback;
    public static String[] dialogs = {
        "He de trobar el meu marit, em va dir que estava en els sofàs de l'esquerra.",
        "Oooh déu meu!! És mort.",
        "Hi ha una nota al seu costat, què serà?",
        "Sembla una nota... Segurament hi haurà més d'una.",
        "Penso trobar-les totes i descobrir quí és l'assassí!!! Me les pagarà!!!",
        "Oooh, una nova nota!",
        "En aquesta nota diu, que només tinc tres minuts per descobrir quí és l'assassí.",
        "He trobat tres notes, això em porta a deduïr la següent pista."
    };

    public DialogBox(Skin skinNom, Skin skinText) {
        setFillParent(true);
        bottom().pad(20);

        // Fons table
//        TextureRegionDrawable dialogBoxDrawable = new TextureRegionDrawable(
//            new TextureRegion(AssetsManager.dialogBox));

        NinePatch ninePatch = new NinePatch(AssetsManager.dialogBox, 10, 10, 10, 10);
        NinePatchDrawable dialogBoxDrawable = new NinePatchDrawable(ninePatch);

        //portrait = new Image(AssetsManager.player4K);
        nameLabel = new Label("Vincent", skinNom);
        textLabel = new Label("", skinText);
        textLabel.setWrap(true);

        Table textTable = new Table();
        // Tamaño fijo del cuadro (IMPORTANTE)
        textTable.setSize(600, 400);
        textTable.setBackground(dialogBoxDrawable);

    // Anclar contingut
        textTable.top().left();

        textTable.setDebug(true);

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

        textTable.row(); // tancar fila

        //add(portrait).size(64).padRight(10);
        add(textTable);
    }

    public void setText(String text) {
        textLabel.setText(text);
    }

    public boolean isFinished() {
        return finished;
    }

    public void typeTextMultiple(int inici, int fi) {
        indexI = inici;
        indexF = fi;
        nextDialog();
    }

    private void nextDialog() {
        if (indexI >= indexF) {
            remove();
            if (onFinishCallback != null) {
                onFinishCallback.run(); // ejecuta lo que haya que hacer al terminar
            }
            return;
        }
        textLabel.setText("");
        typeText(dialogs[indexI]);
    }

    public void onScreenClick() {
        if (finished) {
            indexI++;
            nextDialog();
        } else {
            // Completa el texto actual instantáneamente
            textLabel.setText("");
            textLabel.setText(dialogs[indexI]);
            finished = true;
        }
    }

    public void typeText(String fullText) {
        textLabel.setText("");
        finished = false;
        Timer.schedule(new Timer.Task() {
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
        }, 0, 0.03f);
    }

    public void setOnFinishCallback(Runnable callback) {
        this.onFinishCallback = callback;
    }

}
