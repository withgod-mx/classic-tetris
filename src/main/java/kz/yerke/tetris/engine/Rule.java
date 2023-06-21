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

        if (previousRowPosition < 0) {
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

    public boolean moveAndCheckNextFigure(GameInfo gameInfo, Figures figure, ObjectMove objectMove) {

        gameInfo.setPreviousRowPosition(gameInfo.getRowPosition());
        gameInfo.setPreviousColumnPosition(gameInfo.getColumnPosition());
        if (objectMove == ObjectMove.LEFT) {
            if (gameInfo.getRowPosition() == 0) {
                figure.setShape(figureMoveLeftPosition(figure.getShape()));
            } else {
                boolean moveNextLeft = moveFigureInDesk(figure.getShape(), objectMove, gameInfo);
                if (moveNextLeft) {
                    gameInfo.setRowPosition(gameInfo.getRowPosition() - 1);
                }
            }
        } else if (objectMove == ObjectMove.RIGHT) {
            if (gameInfo.getRowPosition() + figure.getShape()[0].length == 10) {
                figure.setShape(figureMoveRightPosition(figure.getShape()));
            } else {
                boolean moveNextRight = moveFigureInDesk(figure.getShape(), objectMove, gameInfo);
                if (moveNextRight) {
                    gameInfo.setRowPosition(gameInfo.getRowPosition() + 1);
                }
            }
        } else if (objectMove == ObjectMove.NONE) {
            moveFigureInDesk(figure.getShape(), objectMove, gameInfo);
        }


        if (gameInfo.getColumnPosition() == desk.length - 1) {
            boolean checkDownMove = checkFigureDownLine(figure.getShape(), gameInfo);
            if (checkDownMove) {
                figure.setShape(figureMoveDown(figure.getShape(), gameInfo));
            }
            return checkDownMove;
        } else if (gameInfo.getColumnPosition() < desk.length - 1) {


            int[] shapeLine = figure.getShape()[figure.getShape().length - 1]; ///checkFinishFigureLine(figure.getShape());

            if (gameInfo.getColumnPosition() == desk.length - 1) {
                return false;
            }

            for (int i = 0; i < shapeLine.length; i++) {

                if(shapeLine[i] == 1) {
                    if (desk[gameInfo.getColumnPosition() + 1][gameInfo.getRowPosition() + i] == 1) {
                        return false;
                    }
                } else {
                    if (desk[gameInfo.getColumnPosition()][gameInfo.getRowPosition() + i] == 1) {
                        if (figure.getShape()[figure.getShape().length - 2][i] == 1)
                            return false;
                    }
                }

            }
            gameInfo.setColumnPosition(gameInfo.getColumnPosition() + 1);
        }



        return true;

    }

    /*private int[] checkFinishFigureLine(int[][] shape) {
        for (int i = shape.length - 1; i >= 0; i--) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] == 1) {
                    return shape[i];
                }
            }
        }
        return shape[0];
    }*/

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

    private boolean moveFigureInDesk(int[][] shape, ObjectMove objectMove, GameInfo gameInfo) {
        boolean move = true;
        if (objectMove != null && ((objectMove == ObjectMove.LEFT) && (gameInfo.getRowPosition() > 0))) {
            move = checkMoveLeft(gameInfo, shape);
        } else if (objectMove != null && ((objectMove == ObjectMove.RIGHT) && (gameInfo.getRowPosition() <= 10))) {
            move = checkMoveRight(gameInfo, shape);
        }


        moveAndCheckMoveRightOrLeft(gameInfo, shape);

        return move;
    }

    private boolean checkMoveRight(GameInfo gameInfo, int[][] shape) {
        boolean isMove = true;
        int col = 0;
        int row = 0;
        if ((gameInfo.getColumnPosition() > 0) && (gameInfo.getColumnPosition() < 19)) {
            col = gameInfo.getColumnPosition() + 1;
        } else if (gameInfo.getColumnPosition() == 19) {
            col = gameInfo.getColumnPosition();
        }

        if(col == 0) {
            return true;
        }

        if (gameInfo.getRowPosition() + shape[0].length + 1 < 9) {
            row = gameInfo.getRowPosition() + shape[0].length - 1;
        } else {
            row = 9;
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
                    if(shape[i][shape.length - 2] == 1)
                        return false;
                }
            }
        }

        return isMove;
    }

    private boolean checkMoveLeft(GameInfo gameInfo, int[][] shape) {
        boolean isMove = true;
        int col = 0;
        int row = 0;
        if ((gameInfo.getColumnPosition() > 0) && (gameInfo.getColumnPosition() < 19)) {
            col = gameInfo.getColumnPosition() + 1;
        } else if (gameInfo.getColumnPosition() == 19) {
            col = gameInfo.getColumnPosition();
        }

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

    private void moveAndCheckMoveRightOrLeft(GameInfo gameInfo, int[][] shape) {
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
}
