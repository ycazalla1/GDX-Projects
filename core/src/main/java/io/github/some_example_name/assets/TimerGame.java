package io.github.some_example_name.assets;

public class TimerGame {

    private float elapsedTime;  // tiempo transcurrido en segundos
    private boolean running;    // si el timer está activo

    public TimerGame() {
        elapsedTime = 0f;
        running = false;
    }

    // Inicia o reinicia el timer
    public void start() {
        elapsedTime = 0f;
        running = true;
    }

    // Pausa el timer
    public void pause() {
        running = false;
    }

    // Reanuda el timer
    public void resume() {
        running = true;
    }

    // Detiene el timer y opcionalmente lo reinicia
    public void stop() {
        running = false;
    }

    // Actualiza el tiempo; llamar en render() con delta
    public void update(float delta) {
        if (running) {
            elapsedTime += delta;
        }
    }

    // Obtiene el tiempo transcurrido
    public float getElapsedTime() {
        return elapsedTime;
    }

    // Comprueba si ha pasado un tiempo determinado
    public boolean hasReached(float seconds) {
        return elapsedTime >= seconds;
    }

}
