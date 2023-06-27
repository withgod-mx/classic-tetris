package kz.yerke.tetris.engine;

import kz.yerke.tetris.model.GameInfo;

public class MoveDown extends Thread {

    GameInfo gameInfo;

    Rule rule;

    public MoveDown(GameInfo gameInfo, Rule rule) {
        this.gameInfo = gameInfo;
        this.rule = rule;
    }

    @Override
    public void run() {
        try {
            while (!gameInfo.isFinishGame()) {
                gameInfo.setMove(true);
                System.out.println("Go");
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
