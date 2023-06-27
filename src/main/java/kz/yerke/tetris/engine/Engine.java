package kz.yerke.tetris.engine;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import kz.yerke.tetris.gui.WindowDraw;
import kz.yerke.tetris.model.Figures;
import kz.yerke.tetris.model.GameInfo;
import kz.yerke.tetris.model.ObjectMove;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Engine {
    private Terminal terminal;
    private WindowDraw windowDraw;
    TerminalPosition startPosition = new TerminalPosition(0, 0);

    GameInfo gameInfo;

    final Random random = new Random();

    Rule rule = new Rule();

    public Engine() {

    }

    public Engine(Terminal terminal) {
        this.terminal = terminal;
        this.windowDraw = new WindowDraw(terminal, startPosition);
        this.gameInfo = new GameInfo(3, 0, 0, 1, 500);
    }

    public void calculateAndDraw(int[][] currentDesk) throws IOException {
        for (int i = 0; i < currentDesk.length; i ++) {
            int rowPoint = 25;
            for (int j = 0; j < currentDesk[i].length; j ++) {
                rowPoint += 2;
                if (currentDesk[i][j] == 0) {
                    windowDraw.draw(" .", rowPoint, i + 1);
                } else {
                    windowDraw.draw("[]", rowPoint, i + 1);
                }
            }
        }
    }

    public void startNew() throws IOException, InterruptedException {
        boolean lose = false;
        Figures figures = getRandomFigure(0);
        int nextFigureIndex = random.nextInt(7) + 1;

        drawNextShape(nextFigureIndex);
        MoveDown moveDown = new MoveDown(gameInfo, rule);
        while (!lose) {
            if(gameInfo.isFinishGame()) {
                gameInfo.setFinishGame(false);
                moveDown.start();
            }

            gameInfo.setPreviousShape(figures.getShape());
            gameInfo.setPreviousRowPosition(gameInfo.getRowPosition());
            gameInfo.setPreviousColumnPosition(gameInfo.getColumnPosition());
            KeyStroke keyStroke = terminal.pollInput();
            if(keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowLeft)) {
                if (gameInfo.getRowPosition() == 0) {
                    rule.clearPreviousPositionShape(gameInfo, figures.getShape());
                    figures.setShape(moveFigure(figures, ObjectMove.LEFT));
                } else if (gameInfo.getRowPosition() > 0) {
                    if (rule.checkMoveLeft(gameInfo, figures.getShape()))
                        gameInfo.setRowPosition(gameInfo.getRowPosition() - 1);
                }
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowRight)) {
                if (gameInfo.getRowPosition() + figures.getShape()[0].length == 10) {
                    rule.clearPreviousPositionShape(gameInfo, figures.getShape());
                    figures.setShape(moveFigure(figures, ObjectMove.RIGHT));
                } else {
                    if (rule.checkMoveRight(gameInfo, figures.getShape())) {
                        gameInfo.setRowPosition(gameInfo.getRowPosition() + 1);
                    }
                }
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowUp)) {
                rule.clearPreviousPositionShape(gameInfo, figures.getShape());
                figures.setShape(rule.figureRotate(figures.getShape()));
                gameInfo.setPreviousShape(figures.getShape());
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowDown)) {
                if (gameInfo.getColumnPosition() > 3)
                    gameInfo.setMove(true);
            }

            if (gameInfo.isMove()) {
                rule.dropDown(gameInfo, figures);
                gameInfo.setMove(false);
            }

            rule.clearPreviousPositionShape(gameInfo, gameInfo.getPreviousShape());
            rule.move(gameInfo, figures.getShape());
            if (!gameInfo.isMoveDown() && (gameInfo.getColumnPosition() == 0)) {
                lose = true;
            } else if (!gameInfo.isMoveDown()) {
                gameInfo.setColumnPosition(0);
                gameInfo.setRowPosition(3);
                gameInfo.setPreviousColumnPosition(0);
                gameInfo.setPreviousColumnPosition(0);
                crashLines(rule.desk);
                figures = getRandomFigure(nextFigureIndex);
                nextFigureIndex = random.nextInt(7) + 1;
                gameInfo.setMoveDown(true);
            }

            calculateAndDraw(rule.desk);
            drawNextShape(nextFigureIndex);

            if (lose) {
                gameInfo.setFinishGame(true);
                lostAnimation();
                updateParameter();
            }

        }
    }

    private int[][] moveFigure(Figures figures, ObjectMove objectMove) {
        switch (objectMove) {
            case LEFT -> {
                return rule.figureMoveLeftPosition(figures.getShape());
            }
            case RIGHT -> {
                return rule.figureMoveRightPosition(figures.getShape());
            }
        }
        return figures.getShape();
    }

    public void gameOverEvent() throws IOException {
        boolean close = false;
        while (!close) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null && (keyStroke.getKeyType() == KeyType.Enter)) {
                close = true;
            }
        }
    }

    private void updateParameter() {
        rule.desk = new int[20][10];
        gameInfo.setPreviousShape(new int[1][1]);
        gameInfo.setPreviousRowPosition(0);
        gameInfo.setColumnPosition(0);
        gameInfo.setScore(0);
        gameInfo.setLevel(1);
        gameInfo.setRowPosition(3);
        gameInfo.setMoveDown(true);
        gameInfo.setSpeedTimer(1000);
    }

    private void drawNextShape(int index) throws IOException {
        Figures figures = getRandomFigure(index);

        windowDraw.draw("          ", 13, 10);
        windowDraw.draw("          ", 13, 11);
        windowDraw.draw("          ", 13, 12);
        windowDraw.draw("          ", 13, 13);

        for (int i = 0; i < figures.getShape().length; i++) {
            int rowPoint = 13;
            for (int j = 0; j < figures.getShape()[i].length; j++) {
                rowPoint += 2;
                if(figures.getShape()[i][j] == 1)
                    windowDraw.draw("[]", rowPoint, i + 10);
            }
        }
    }

    private void crashLines(int[][] desk) throws IOException, InterruptedException {
        List<Integer> lineCrash = rule.checkCrashLine();
        rule.crashLineToListAndUpdate(lineCrash);
        crashAnimation(lineCrash);
        updateScore();
    }

    private void updateScore() throws IOException {
        windowDraw.draw(String.valueOf(gameInfo.getScore()), 16, 3);
    }

    private void crashAnimation(List<Integer> lineCrash) throws IOException, InterruptedException {

        for (Integer line: lineCrash) {
            int rowPoint = 25;
            for (int i = 0; i < 10; i++) {
                rowPoint += 2;
                windowDraw.draw(" .", rowPoint, line + 1);
                gameInfo.setScore(gameInfo.getScore() + 4);
                Thread.sleep(50);
            }

            if ((gameInfo.getScore() > 199) && (gameInfo.getScore() < 400)) {
                gameInfo.setSpeedTimer(800);
                gameInfo.setLevel(2);
            } else if ((gameInfo.getScore() > 400) && (gameInfo.getScore() < 600)) {
                gameInfo.setSpeedTimer(600);
                gameInfo.setLevel(3);
            } else if ((gameInfo.getScore() > 600) && (gameInfo.getScore() < 800)) {
                gameInfo.setSpeedTimer(500);
                gameInfo.setLevel(4);
            } else if ((gameInfo.getScore() > 800) && (gameInfo.getScore() < 1000)) {
                gameInfo.setSpeedTimer(400);
                gameInfo.setLevel(5);
            }

        }

    }

    public int changeAndCheckPosition(int position) {
        if(position < 0) {
            position++;
        } else if (position < 22) {
            position--;
        }
        return position;
    }

    public Figures getRandomFigure(int index) {
        //index = 2;
        if(index == 0) {
            index = random.nextInt(7) + 1;
        }
        FigureBuilder figureBuilder = new FigureBuilder();
        return figureBuilder.getShapes().get(index);
    }

    private void lostAnimation() throws IOException, InterruptedException {

        for (int i = 19; i >= 0; i--) {
            int rowPoint = 25;
            for (int j = 0; j < 10; j++) {
                rowPoint += 2;
                windowDraw.draw("[]", rowPoint, i + 1);
                Thread.sleep(10);
            }
        }
    }

}
