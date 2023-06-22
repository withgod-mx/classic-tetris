package kz.yerke.tetris.engine;

import kz.yerke.tetris.model.GameInfo;

public class MoveDown implements Runnable {

    GameInfo gameInfo;

    public MoveDown(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(0);

            gameInfo.setColumnPosition(gameInfo.getColumnPosition() + 1);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
