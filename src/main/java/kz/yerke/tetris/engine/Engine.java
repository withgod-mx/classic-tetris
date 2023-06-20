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
        }

    }

    @Deprecated
    public void startGame() throws InterruptedException, IOException {
        boolean lose = false;
        int columnPosition = 0;
        Figures figures = getRandomFigure(0);
        int rowPosition = (10 / figures.getShape().length);
        int[][] currentDesk = new int[20][10];
        int timer = 500;
        int nextFigureIndex = random.nextInt(7) + 1;
        drawNextShape(nextFigureIndex);
        while (!lose) {
            KeyStroke keyStroke = terminal.pollInput();
            rule.clearPreviousPositionShape(gameInfo, figures.getShape());
            if(keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowRight)) {
                if (rowPosition + figures.getShape()[0].length == 10) {
                    figures.setShape(rule.figureMoveRightPosition(figures.getShape()));
                } else {
                    rowPosition++;
                }
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowLeft)) {
                if (rowPosition == 0) {
                    figures.setShape(rule.figureMoveLeftPosition(figures.getShape()));
                } else {
                    rowPosition--;
                }
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowUp)) {
                figures.setShape(rule.figureRotate(figures.getShape()));
            } else if (keyStroke != null && (keyStroke.getKeyType() == KeyType.ArrowDown)) {
                timer = 0;
            }

            boolean move = rule.checkBottomPoint(columnPosition, rowPosition, figures.getShape());
            if (move) {
                currentDesk = rule.move(figures.getShape(), columnPosition , rowPosition);
                columnPosition++;
            } else {
                if (columnPosition == 0) {
                   lose = true;
                   rowPosition = 3;
                }
                columnPosition = 0;
                figures = getRandomFigure(nextFigureIndex);
                nextFigureIndex = random.nextInt(7) + 1;
                drawNextShape(nextFigureIndex);
                timer = 500;
                crashLines(rule.desk);
            }

            calculateAndDraw(currentDesk);

            Thread.sleep(timer);

            if (columnPosition == 20) {
                columnPosition = 0;
                figures = getRandomFigure(nextFigureIndex);
                nextFigureIndex = random.nextInt(7) + 1;
                drawNextShape(nextFigureIndex);
                rowPosition = 3;
                rule.setPreviousColumnPosition(-1);
                rule.setPreviousRowPosition(-1);
                timer = 500;
                crashLines(rule.desk);
            }
        }

        if (lose) {
            lostAnimation();
        }
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
        index = 2;
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
