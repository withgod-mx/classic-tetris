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

    public void start() throws IOException, InterruptedException {
        boolean lose = false;
        Figures figures = getRandomFigure(0);
        int nextFigureIndex = random.nextInt(7) + 1;
        drawNextShape(nextFigureIndex);
        gameInfo.setPreviousShape(figures.getShape());
        int timer = 500;
        while (!lose) {
            KeyStroke keyStroke = terminal.pollInput();
            rule.clearPreviousPositionShape(gameInfo, gameInfo.getPreviousShape());
            gameInfo.setPreviousShape(figures.getShape());
            boolean isMove = moveNext(keyStroke, figures);
            calculateAndDraw(rule.desk);

            if ((!isMove) && (gameInfo.getColumnPosition() == 0)) {
                lose = true;
            } else if (!isMove) {
                gameInfo.setColumnPosition(0);
                gameInfo.setRowPosition(3);
                gameInfo.setPreviousColumnPosition(0);
                gameInfo.setPreviousColumnPosition(0);
                crashLines(rule.desk);
                figures = getRandomFigure(nextFigureIndex);
                nextFigureIndex = random.nextInt(7) + 1;
            }
            Thread.sleep(timer);
            drawNextShape(nextFigureIndex);
        }

        if (lose) {
            lostAnimation();
            updateParameter();
        }

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

    }

    private boolean moveNext(KeyStroke keyStroke, Figures figure) {
        boolean isMove = true;
        if(keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowLeft)) {
            isMove = rule.moveAndCheckNextFigure(gameInfo, figure, ObjectMove.LEFT);
        } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowRight)) {
            isMove = rule.moveAndCheckNextFigure(gameInfo, figure, ObjectMove.RIGHT);
        } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowUp)) {
            figure.setShape(rule.figureRotate(figure.getShape()));
        } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowDown)) {

        } else {
            isMove = rule.moveAndCheckNextFigure(gameInfo, figure, ObjectMove.NONE);
        }

        return isMove;
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
        //index = 5;
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
