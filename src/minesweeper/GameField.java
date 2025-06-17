package minesweeper;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameField {

    private final int rows;
    private final int columns;
    private final int mines;
    private final Cell[][] cell;

    public GameField(int rows, int columns, int mines) {
        this.rows = rows;
        this.columns = columns;
        this.mines = mines;
        this.cell = createGameField(rows, columns);
    }

    public Cell[][] getCells() {
        return cell;
    }

    public void display(int rows, int columns) {
        System.out.print(" |");
        for (int i = 0; i < columns; i++) {
            System.out.print(i + 1);
        }
        System.out.println("|");
        System.out.print("-|");
        for (int i = 0; i < columns; i++) {
            System.out.print("-");
        }
        System.out.println("|");

        for (int i = 0; i < rows; i++) {
            System.out.print((i + 1) + "|");
            for (int j = 0; j < columns; j++) {
                System.out.print(cell[i][j]);
            }
            System.out.println("|");
        }

        System.out.print("-|");
        for (int i = 0; i < columns; i++) {
            System.out.print("-");
        }
        System.out.println("|");
    }

    public int handleMineSituation(int countMineSet, Cell cell){
        if (cell.getStatus().equals(Status.MINE) && cell.getSymbol().equals(".")) {
            cell.setSymbol("*");
            countMineSet++;
        } else if (cell.getStatus().equals(Status.MINE) && cell.getSymbol().equals("*")) {
            cell.setSymbol(".");
            countMineSet--;
        } else if ((cell.getStatus().equals(Status.UNEXPLORED)||cell.getStatus().equals(Status.NUMBER)) && cell.getSymbol().equals(".")) {
            cell.setSymbol("*");
            countMineSet--;
        } else if ((cell.getStatus().equals(Status.UNEXPLORED)||cell.getStatus().equals(Status.NUMBER))  && cell.getSymbol().equals("*")) {
            cell.setSymbol(".");
            countMineSet++;
        }
        return countMineSet;
    }

    public int handleFreeSituation(int countFreeRound, Cell cell){
        if (countFreeRound == 0) {
            putMines(cell);
            setNumberStatus();
        }

        checkNeighbours(cell);
        countFreeRound++;
        Queue<Cell> queueUnexploredCells = new LinkedList<>();
        Queue<Cell> queueNumberCells = new LinkedList<>();

        if(cell.getSymbol().equals("/")){
            queueUnexploredCells.add(cell);
        }

        while(!queueUnexploredCells.isEmpty()){
            checkNeighbours(queueUnexploredCells.peek());
            ArrayList<Cell> neighbours = findNeighbours(queueUnexploredCells.peek());
            for (Cell neighbour : neighbours) {
                if (neighbour == null) {
                    continue;
                }
                if(queueUnexploredCells.contains(neighbour)){
                    continue;
                }
                if (neighbour.getStatus().equals(Status.UNEXPLORED)) {
                    queueUnexploredCells.add(neighbour);
                } else if (neighbour.getStatus().equals(Status.NUMBER)) {
                    queueNumberCells.add(neighbour);
                }
            }
            queueUnexploredCells.poll();
        }

        while(!queueNumberCells.isEmpty()){
            checkNeighbours(queueNumberCells.poll());
        }
        return countFreeRound;
    }

    public void loserGameField(int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cell[i][j].getStatus().equals(Status.MINE)) {
                    cell[i][j].setSymbol("X");
                }
            }
        }
    }

    public int getHowManyUnexploredCells(int rows, int columns) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cell[i][j].getSymbol().equals(".")) {
                    count++;
                }
            }
        }
        return count;
    }

    private void checkNeighbours(Cell cell) {
        int count = 0;
        if (cell.getSymbol().equals(".") || cell.getSymbol().equals("*")) {
            ArrayList<Cell> neighbours = findNeighbours(cell);
            for (Cell neighbour : neighbours) {
                if (neighbour == null) {
                    continue;
                }
                if (neighbour.getStatus().equals(Status.MINE)) {
                    count++;
                }
            }
        }
        if (!cell.getStatus().equals(Status.MINE)) {
            if (count == 0 && cell.getStatus().equals(Status.UNEXPLORED)) {
                cell.setSymbol("/");
                cell.setStatus(Status.EXPLORED_FREE);
            } else if (count > 0) {
                cell.setSymbol(count + "");
                cell.setStatus(Status.NUMBER);
            }
        }
    }

    private ArrayList<Cell> findNeighbours(Cell cell) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        neighbours.add(oneNeighbour(cell.getRow() - 1, cell.getColumn() - 1)); //up left
        neighbours.add(oneNeighbour(cell.getRow() - 1, cell.getColumn())); //up
        neighbours.add(oneNeighbour(cell.getRow() - 1, cell.getColumn() + 1)); //up right
        neighbours.add(oneNeighbour(cell.getRow(), cell.getColumn() - 1)); //left
        neighbours.add(oneNeighbour(cell.getRow(), cell.getColumn() + 1));  //right
        neighbours.add(oneNeighbour(cell.getRow() + 1, cell.getColumn() - 1));  //down left
        neighbours.add(oneNeighbour(cell.getRow() + 1, cell.getColumn()));  //down
        neighbours.add(oneNeighbour(cell.getRow() + 1, cell.getColumn() + 1));  //down right
        return neighbours;
    }

    private Cell oneNeighbour(int x, int y) {
        try {
            return cell[x][y];
        } catch (Exception e) {
            return null;
        }
    }

    private void putMines(Cell firstFreeCell) {
        Random random = new Random();
        int count = 0;
        do {
            int a = random.nextInt(rows);
            int b = random.nextInt(columns);

            if (a == firstFreeCell.getRow() && b == firstFreeCell.getColumn()) {
                continue;
            }
            if (cell[a][b].getStatus().equals(Status.UNEXPLORED)) {
                cell[a][b].setStatus(Status.MINE);
                count++;
            }
        } while (count != mines);
    }

    private void setNumberStatus() {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cell[i][j].getStatus().equals(Status.UNEXPLORED)) {
                    ArrayList<Cell> neighbours = findNeighbours(cell[i][j]);
                    for (Cell neighbour : neighbours) {
                        if (neighbour == null) {
                            continue;
                        }
                        if (neighbour.getStatus().equals(Status.MINE)) {
                            count++;
                        }
                    }
                }
                if (!cell[i][j].getStatus().equals(Status.MINE)) {
                    if (count == 0) {
                        cell[i][j].setStatus(Status.UNEXPLORED);
                    } else if (count > 0) {
                        cell[i][j].setStatus(Status.NUMBER);
                    }
                }
                count = 0;
            }
        }
    }

    private Cell[][] createGameField(int rows, int columns) {
        Cell[][] gameField = new Cell[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                gameField[i][j] = new Cell(i, j);
                gameField[i][j].setSymbol(".");
                gameField[i][j].setStatus(Status.UNEXPLORED);
            }
        }
        return gameField;
    }
}


