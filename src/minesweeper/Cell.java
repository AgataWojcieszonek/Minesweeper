package minesweeper;

public class Cell {
    private final int row;
    private final int column;
    private String symbol;
    private Status status;

    Cell(int row, int column){
        this.row = row;
        this.column = column;
    }

    public void setSymbol(String symbol){
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public int getRow() {
        return row;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }


    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
