package kz.yerke.tetris.engine;

import kz.yerke.tetris.model.Figures;

import java.util.HashMap;
import java.util.Map;

public class FigureBuilder {

    private Map<Integer, Figures> shapes = new HashMap<>();

    public FigureBuilder() {
        int[][] j = {{0, 1, 0}, {0, 1, 0}, {1, 1, 0}};
        int[][] i = {{0 ,1, 0, 0}, {0 ,1, 0, 0}, {0 ,1, 0, 0}, {0 ,1, 0, 0}};
        int[][] q = {{1, 1}, {1, 1}};
        int[][] l = {{0, 1, 0}, {0, 1, 0}, {0, 1, 1}};
        int[][] z = {{0, 0, 0}, {1, 1, 0}, {0, 1, 1}};
        int[][] t = {{0, 0, 0}, {0, 1, 0}, {1, 1, 1}};
        int[][] s = {{0, 0, 0}, {0, 1, 1}, {1, 1, 0}};

        shapeConstructor("J", 1, j, 3);
        shapeConstructor("I", 2, i, 4);
        shapeConstructor("Q", 3, q, 2);
        shapeConstructor("L", 4, l, 3);
        shapeConstructor("Z", 5, z, 3);
        shapeConstructor("T", 6, t, 3);
        shapeConstructor("S", 7, s, 2);

    }

    private void shapeConstructor(String name, int index, int[][] shape, int rowCount) {
        Figures figures = new Figures(index, shape, name, rowCount);
        shapes.put(index, figures);
    }

    public Map<Integer, Figures> getShapes() {
        return shapes;
    }
}
