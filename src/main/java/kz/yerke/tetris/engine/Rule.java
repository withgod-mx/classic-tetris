package kz.yerke.tetris.engine;

import kz.yerke.tetris.model.Figures;
import kz.yerke.tetris.model.GameInfo;
import kz.yerke.tetris.model.ObjectMove;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    public int[][] desk = new int[20][10];
    public int previousRowPosition = -1;
    public int previousColumnPosition = -1;

    public void setPreviousRowPosition(int previousRowPosition) {
        this.previousRowPosition = previousRowPosition;
    }

    public void setPreviousColumnPosition(int previousColumnPosition) {
        this.previousColumnPosition = previousColumnPosition;
    }

    public List<Integer> checkColumnForDestroy() {
        List<Integer> columns = new ArrayList<>();
        for (int i = 0; i < desk.length; i++) {
            int columnSum = 0;

            for (int j = 0; j < desk[i].length; j++) {
                columnSum += desk[i][j];
                if (desk[i][j] == 0) {
                    break;
                }
            }

            if (columnSum == 10) {
                columns.add(i + 1);
            }
        }
        return columns;
    }

    public int[][] move(int[][] shape, int columnPosition, int rowPosition) {

        if(previousRowPosition < 0) {
            previousRowPosition = rowPosition;
        }

        if (previousColumnPosition < 0) {
            previousColumnPosition = columnPosition;
        }

        if (columnPosition < 0) {
            return desk;
        }

        //clearPreviousPositionShape(columnPosition - 1, shape);

        for (int i = shape.length - 1; i >= 0; i--) {

            if (columnPosition < 0) {
                previousRowPosition = rowPosition;
                previousColumnPosition = columnPosition;
                return desk;
            }

            for (int j = 0; j < shape[i].length; j++) {
                if (desk[columnPosition][rowPosition + j] < 1)
                    desk[columnPosition][rowPosition + j] = shape[i][j];
            }

            columnPosition--;

        }

        previousRowPosition = rowPosition;
        previousColumnPosition = columnPosition;
        return desk;
    }

    public void clearPreviousPositionShape(int columnPosition, int[][] shape) {
        if (columnPosition < 0) {
            return;
        }

        System.out.println("columnPosition: " + columnPosition + "; prevCol:" + previousColumnPosition + "; prevRow: " + previousRowPosition);
        for (int i = shape.length - 1; i >= 0; i--) {

            if (columnPosition >= 0) {
                for (int j = 0; j < shape[i].length; j++) {
                    //System.out.println("columnPosition: " + columnPosition + "; i: " + i);
                    int pont = desk[columnPosition][previousRowPosition + j];
                    if ((shape[i][j] == 1) && (pont == 1)) {
                        desk[columnPosition][previousRowPosition + j] = 0;
                    }
                }
            }
            columnPosition--;
        }
    }

    public boolean checkBottomPoint(int columnPosition, int rowPosition, int[][] shape) {
        int lastPositionOnShape = shape.length - 1;

        for (int i = 0; i < shape[lastPositionOnShape].length; i++) {
            if (desk[columnPosition][rowPosition + i] + shape[lastPositionOnShape][i] > 1) {
                return false;
            }
        }

        return true;
    }

    public int[][] figureMoveLeftPosition(int[][] shape) {

        int index = 0;
        for (int i = 0; i < shape.length; i++) {
            if (shape[i][index] == 1) {
                return shape;
            }
        }

        for (int i = 0; i < shape.length; i++) {
            for (int j = index; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    shape[i][j - 1] = shape[i][j];
                    shape[i][j] = 0;
                }
            }
        }

        return shape;
    }

    public int[][] figureMoveRightPosition(int[][] shape) {
        int index = shape[0].length - 1;
        for (int i = 0; i < shape.length; i++) {
            if (shape[i][index] == 1) {
                return shape;
            }
        }

        for (int i = 0; i < shape.length; i++) {
            for (int j = index; j >= 0; j--) {
                if (shape[i][j] == 1) {
                    shape[i][j + 1] = shape[i][j];
                    shape[i][j] = 0;
                }
            }
        }
        return shape;
    }

    public int[][] figureRotate(int[][] shape) {
        int[][] temp = new int[shape.length][shape[0].length];
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                temp[j][i] = shape[i][(shape[i].length - 1) - j];
            }
        }
        return temp;
    }

    public List<Integer> checkCrashLine() {
        List<Integer> lineCrash = new ArrayList<>();
        for (int i = 0; i < desk.length; i++) {
            int sumLine = 0;
            for (int j = 0; j < desk[i].length; j++) {
                if (desk[i][j] == 1) {
                    sumLine++;
                } else {
                    break;
                }
            }
            if (sumLine == 10) {
                lineCrash.add(i);
            }
        }
        return lineCrash;
    }

    public void crashLineToListAndUpdate(List<Integer> lineCrash) {
        for (Integer line: lineCrash) {
            for (int i = line; i > 0; i --) {
                for (int j = 0; j < desk[i].length; j++) {
                    if(i - 1 >= 0) {
                        desk[i][j] = desk[i - 1][j];
                    }
                }
            }
        }
    }

    public boolean moveAndCheckNextFigure(GameInfo gameInfo, Figures figure, ObjectMove objectMove) {

        if (objectMove == ObjectMove.LEFT) {
            if (gameInfo.getRowPosition() == 0) {
                figure.setShape(figureMoveLeftPosition(figure.getShape()));
            } else {
                boolean moveNextLeft = moveFigureInDesk(figure.getShape(), objectMove);
                if (moveNextLeft)
                    gameInfo.setRowPosition(gameInfo.getRowPosition() + 1);
            }
        } else if (objectMove == ObjectMove.RIGHT) {
            if (gameInfo.getRowPosition() + figure.getShape()[0].length == 10) {
                figure.setShape(figureMoveRightPosition(figure.getShape()));
            }
        }

        if (gameInfo.getColumnPosition() + (figure.getShape().length - 1) >= desk.length - 1) {
            figure.setShape(figureMoveDown(figure.getShape()));
            return checkNextLine(figure.getShape(), gameInfo);
        }

        for (int i = gameInfo.getColumnPosition() + (figure.getShape().length - 1); i <= gameInfo.getColumnPosition(); i--) {

        }

        if (columnPosition < 0) {
            return desk;
        }

        //clearPreviousPositionShape(columnPosition - 1, shape);

        for (int i = shape.length - 1; i >= 0; i--) {

            if (columnPosition < 0) {
                previousRowPosition = rowPosition;
                previousColumnPosition = columnPosition;
                return desk;
            }

            for (int j = 0; j < shape[i].length; j++) {
                if (desk[columnPosition][rowPosition + j] < 1)
                    desk[columnPosition][rowPosition + j] = shape[i][j];
            }

            columnPosition--;

        }
    }

    private boolean moveFigureInDesk(int[][] shape, ObjectMove objectMove) {
        if()
    }

    private boolean checkNextLine(int[][] shape, GameInfo gameInfo) {
        if (desk)
        return false;
    }

    private int[][] figureMoveDown(int[][] shape) {

        for (int i = 0; i < shape[shape.length - 1].length; i ++) {
            if(shape[shape.length - 1][i] == 1) {
                return shape;
            }
        }

        int[] cash = new int[shape[0].length - 1];

        for (int i = shape.length - 1; i >= 0; i--) {
            if (i > 0) {
                shape[i] = shape[i - 1];
            } else {
                shape[i] = cash;
            }
        }

        return shape;
    }
}
