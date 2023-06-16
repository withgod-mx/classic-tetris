package kz.yerke.tetris.model;

public class Figures {
    private int index;
    private int[][] shape;
    private String name;

    private int rowCount;

    public Figures(int index, int[][] shape, String name, int rowCount) {
        this.index = index;
        this.shape = shape;
        this.name = name;
        this.rowCount = rowCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }
}
