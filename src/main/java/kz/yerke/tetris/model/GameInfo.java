package kz.yerke.tetris.model;

public class GameInfo {

    private int rowPosition;
    private int columnPosition;
    private int score;
    private int level;
    private int previousColumnPosition;
    public int previousRowPosition;

    private int[][] previousShape;

    private int speedTimer = 1000;

    private boolean moveDown = true;

    private boolean move = true;

    private boolean finishGame = true;

    public GameInfo(int rowPosition, int columnPosition, int score, int level, int speedTimer) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.score = score;
        this.level = level;
        this.speedTimer = speedTimer;
    }

    public boolean isMove() {
        return move;
    }

    public void setMove(boolean move) {
        this.move = move;
    }

    public boolean isFinishGame() {
        return finishGame;
    }

    public void setFinishGame(boolean finishGame) {
        this.finishGame = finishGame;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public int[][] getPreviousShape() {
        return previousShape;
    }

    public void setPreviousShape(int[][] previousShape) {
        this.previousShape = previousShape.clone();
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getSpeedTimer() {
        return speedTimer;
    }

    public void setSpeedTimer(int speedTimer) {
        if (speedTimer < 0) {
            this.speedTimer = 0;
        } else
            this.speedTimer = speedTimer;
    }

    public int getPreviousColumnPosition() {
        return previousColumnPosition;
    }

    public void setPreviousColumnPosition(int previousColumnPosition) {
        this.previousColumnPosition = previousColumnPosition;
    }

    public int getPreviousRowPosition() {
        return previousRowPosition;
    }

    public void setPreviousRowPosition(int previousRowPosition) {
        this.previousRowPosition = previousRowPosition;
    }
}
