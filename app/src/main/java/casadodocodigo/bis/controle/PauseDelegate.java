package casadodocodigo.bis.controle;

/**
 * Created by vsaueia on 10/14/16.
 */

public interface PauseDelegate {
    void resumeGame();

    void quitGame();

    void pauseGameAndShowLayer();
}
