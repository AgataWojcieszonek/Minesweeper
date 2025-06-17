package minesweeper;

import java.util.Scanner;

public class UserInput {

    private final Scanner scanner = new Scanner(System.in);

    public int howManyMines(){
        System.out.println("How many mines do you want on the field?");
        return Integer.parseInt(scanner.nextLine());
    }

    public String[] askForMinePosition(){
        System.out.println("Set/unset mines marks or claim a cell as free:");
        String coordinates = scanner.nextLine();
        return coordinates.split(" ");
    }
}
