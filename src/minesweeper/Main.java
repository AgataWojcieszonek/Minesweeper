package minesweeper;

public class Main {
    public static void main(String[] args) {
        // write your code here
        UserInput userInput = new UserInput();
        int mines = userInput.howManyMines();
        int height = 9;
        int width = 9;
        GameField gamefield = new GameField(height, width, mines);
        gamefield.display(height, width);

        Cell[][] cells = gamefield.getCells();
        Cell cell;
        int countMineSet = 0;
        int countFreeRound = 0;


        while (true) {
            String[] coordinates = userInput.askForMinePosition();
            int row = Integer.parseInt(coordinates[1]) - 1;
            int column = Integer.parseInt(coordinates[0]) - 1;
            String mark = coordinates[2];
            cell = cells[row][column];

            if (cell.getStatus().equals(Status.MINE) && mark.equals("free")) {
                gamefield.loserGameField(height, width);
                gamefield.display(height, width);
                System.out.println("You stepped on a mine and failed!");
                break;
            }

            if (mark.equals("free")) {
                countFreeRound = gamefield.handleFreeSituation(countFreeRound, cell);
            } else if (mark.equals("mine")){
                countMineSet = gamefield.handleMineSituation(countMineSet, cell);
            }

            gamefield.display(height, width);

            if (countMineSet == mines || mines == gamefield.getHowManyUnexploredCells(height, width)) {
                System.out.println("Congratulations! You found all the mines!");
                break;
            }
        }
    }
}
