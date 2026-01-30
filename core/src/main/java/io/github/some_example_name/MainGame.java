package io.github.some_example_name;

import com.badlogic.gdx.Game;

import io.github.some_example_name.assets.AssetsManager;
import io.github.some_example_name.screens.Menu;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainGame extends Game {

    @Override
    public void create() {
        AssetsManager.load();
        setScreen(new Menu(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        AssetsManager.dispose();
    }
}
