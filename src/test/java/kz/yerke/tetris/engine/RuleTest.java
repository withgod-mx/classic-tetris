package kz.yerke.tetris.engine;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RuleTest {

    FigureBuilder figureBuilder = new FigureBuilder();

    Rule rule = new Rule();

    @Test
    public void moveTestZeroColumn() {


        int[][] desk = new int[20][10];
        ///FigureBuilder figureBuilder = new FigureBuilder();

        int startPosition = (20 % figureBuilder.getShapes().get(4).getShape().length) + 1;
        // move(int[][] desk, int[][] shape, int column, int position)
        Rule rule = new Rule();
        //int[][] deskResult = rule.move(figureBuilder.getShapes().get(4).getShape(), 0, startPosition);
        //printResult(deskResult);
    }

    @Test
    public void moveTestFourColumn() {
        //FigureBuilder figureBuilder = new FigureBuilder();
        int startPosition = (20 % figureBuilder.getShapes().get(4).getShape().length) + 1;
        Rule rule = new Rule();
        int column = 4;
        //int[][] deskResult = rule.move(figureBuilder.getShapes().get(4).getShape(), column, startPosition);
        //printResult(deskResult);
        System.out.println("Next column");
        //deskResult = rule.move(figureBuilder.getShapes().get(4).getShape(), column + 1, startPosition);
        //printResult(deskResult);
    }

    @Test
    public void moveTwoColumn() {
        Rule rule = new Rule();
        int[][] d = {
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0}
        };
        int startPosition = (20 % figureBuilder.getShapes().get(4).getShape().length) + 1;
        rule.desk = d;
        //int[][] result = rule.move(figureBuilder.getShapes().get(4).getShape(), 3, 3);
        //printResult(result);
    }

    @Test
    public void checkMoveTest() {
        Rule rule = new Rule();

        int[][] d = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 1, 0, 0, 1, 1, 1, 1, 0},
            {0, 0, 1, 1, 1, 0, 0, 0, 0, 0}
        };

        rule.desk = d;
        assertTrue(rule.checkBottomPoint(18, 2, figureBuilder.getShapes().get(4).getShape()));
    }


    @Test
    public void figureMoveLeft() {
        int index = 2;
        System.out.println("Beginer state");
        printResult(figureBuilder.getShapes().get(index).getShape());

        int[][] shape = rule.figureMoveLeftPosition(figureBuilder.getShapes().get(index).getShape());
        System.out.println("Change state");
        printResult(shape);

    }

    @Test
    public void figureMoveRight() {
        int index = 2;
        System.out.println("Beginer state");
        printResult(figureBuilder.getShapes().get(index).getShape());

        int[][] shape = rule.figureMoveRightPosition(figureBuilder.getShapes().get(index).getShape());
        System.out.println("Change state");
        printResult(shape);

        System.out.println("second Change state");
        shape = rule.figureMoveRightPosition(shape);
        printResult(shape);

        System.out.println("Finish Change state");
        shape = rule.figureMoveRightPosition(shape);
        printResult(shape);

    }

    @Test
    public void clearPreviousPositionShapeTest() {
        int[][] d = {
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 0, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 0, 0, 0, 0, 0}

        };
        rule.desk = d;
        //rule.clearPreviousPositionShape(null, figureBuilder.getShapes().get(2).getShape());
        printResult(rule.desk);
    }

    @Test
    public void figureRotateTest() {
        printResult(figureBuilder.getShapes().get(1).getShape());
        int[][] shape = rule.figureRotate(figureBuilder.getShapes().get(1).getShape());
        System.out.println("beforeRotate");
        printResult(shape);

    }



    public void printResult(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j ++) {
                System.out.print(arr[i][j] + ", ");
            }
            System.out.println();
        }
    }

}
