package kz.yerke.tetris.model;

public class GameInfo {

    private int rowPosition;
    private int columnPosition;
    private int score;
    private int level;
    private int previousColumnPosition;
    public int previousRowPosition;

    private int speedTimer;

    public GameInfo(int rowPosition, int columnPosition, int score, int level, int speedTimer) {
        this.rowPosition = rowPosition;
        this.columnPosition = columnPosition;
        this.score = score;
        this.level = level;
        this.speedTimer = speedTimer;
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
