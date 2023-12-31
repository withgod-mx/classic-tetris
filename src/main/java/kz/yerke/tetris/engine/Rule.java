package kz.yerke.tetris.engine;

import kz.yerke.tetris.model.Figures;
import kz.yerke.tetris.model.GameInfo;

import java.util.ArrayList;
import java.util.List;

public class Rule {

    public int[][] desk = new int[20][10];

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

    public void clearPreviousPositionShape(GameInfo gameInfo, int[][] shape) {
        int columnPosition = gameInfo.getPreviousColumnPosition();
        if (columnPosition < 0) {
            return;
        }

        for (int i = shape.length - 1; i >= 0; i--) {

            if (columnPosition >= 0) {
                for (int j = 0; j < shape[i].length; j++) {
                    int pont = desk[columnPosition][gameInfo.getPreviousRowPosition() + j];
                    if ((shape[i][j] == 1) && (pont == 1)) {
                        desk[columnPosition][gameInfo.getPreviousRowPosition() + j] = 0;
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
        for (Integer line : lineCrash) {
            for (int i = line; i > 0; i--) {
                for (int j = 0; j < desk[i].length; j++) {
                    if (i - 1 >= 0) {
                        desk[i][j] = desk[i - 1][j];
                    }
                }
            }
        }
    }


    private int getFreeLineByFigure(int[][] shape) {
        int index = 0;
        for (int i = shape.length - 1; i >= 0; i--) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    return index;
                }
            }
            index++;
        }
        return index;
    }

    public boolean checkFigureDownLine(int[][] shape, GameInfo gameInfo) {

        for (int i = 0; i < shape[shape.length - 1].length; i++) {
            if(gameInfo.getRowPosition() + i < 10) {
                if(desk[gameInfo.getColumnPosition()][gameInfo.getRowPosition() + i] == 1) {
                    if (shape[shape.length - 2][i] == 1) {
                        return false;
                    }
                }
            }
        }

        for (int i = 0; i < shape[shape.length - 1].length; i++) {
            if (shape[shape.length - 1][i] == 1) {
                return false;
            }
        }
        return true;
    }

    public boolean checkMoveRight(GameInfo gameInfo, int[][] shape) {
        boolean isMove = true;
        int col = col = gameInfo.getColumnPosition();
        int row = 0;

        if(col == 0) {
            return true;
        }

        if (gameInfo.getRowPosition() + shape[0].length + 1 < 9) {
            row = gameInfo.getRowPosition() + shape[0].length;
        } else {
            row = 9;
        }

        for (int i = 0; i < shape.length; i++) {
            if (shape[(shape.length - 1) - i][shape[i].length - 1] == 1) {
                if(col - i >= 0) {
                    if (desk[col - i][row] == 1) {
                        return false;
                    }
                }
            } else {
                if (col - i < 0) {
                    return true;
                }
                if (desk[col - i][row] == 1) {
                    if(shape[i][shape.length - 2] == 1)
                        return false;
                }
            }
        }

        return isMove;
    }

    public boolean checkMoveLeft(GameInfo gameInfo, int[][] shape) {
        boolean isMove = true;
        int col = 0;
        int row = 0;
        col = gameInfo.getColumnPosition();

        if(col == 0) {
            return true;
        }

        if (gameInfo.getRowPosition() > 0) {
            row = gameInfo.getRowPosition();
        }

        for (int i = 0; i < shape.length; i++) {
            if (shape[(shape.length - 1) - i][0] == 1) {
                if (desk[col - i][row - 1] == 1) {
                    return false;
                }
            } else {
                if (col - i < 0) {
                    return true;
                }
                if (desk[col - i][row] == 1) {
                    if(shape[(shape.length - 1) - i][1] == 1)
                        return false;
                }
            }
        }

        return isMove;
    }

    private int[][] figureMoveDown(int[][] shape, GameInfo gameInfo) {

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

    public void move(GameInfo gameInfo, int[][] shape) {
        int col = gameInfo.getColumnPosition();
        for (int i = shape.length - 1; i >= 0; i--) {
            if (col < 0) {
                return;
            }
            for (int j = 0; j < shape[i].length; j++) {
                if (desk[col][gameInfo.getRowPosition() + j] == 0) {
                    desk[col][gameInfo.getRowPosition() + j] = shape[i][j];
                }
            }
            col--;
        }
    }

    public void dropDown(GameInfo gameInfo, Figures figure) {

        int freeLine = getFreeLineByFigure(figure.getShape());

        if (gameInfo.getColumnPosition() == desk.length - 1) {
            boolean checkDownMove = checkFigureDownLine(figure.getShape(), gameInfo);
            if (checkDownMove) {
                figure.setShape(figureMoveDown(figure.getShape(), gameInfo));
            }
            gameInfo.setMoveDown(checkDownMove);
        } else if (gameInfo.getColumnPosition() < desk.length - 1) {

            int[] shapeLine = figure.getShape()[(figure.getShape().length - 1) - freeLine]; ///checkFinishFigureLine(figure.getShape());

            for (int i = 0; i < shapeLine.length; i++) {

                if (freeLine == 0) {
                    if(shapeLine[i] == 1) {
                        if (desk[gameInfo.getColumnPosition() + 1][gameInfo.getRowPosition() + i] == 1) {
                            gameInfo.setMoveDown(false);
                            return;
                        }
                    }
                } else {
                    if (desk[(gameInfo.getColumnPosition() + 1) - freeLine][gameInfo.getRowPosition() + i] == 1) {
                        if (shapeLine[i] == 1) {
                            gameInfo.setMoveDown(false);
                            return;
                        }
                    }
                }

            }
            gameInfo.setColumnPosition(gameInfo.getColumnPosition() + 1);
        }
    }
}
