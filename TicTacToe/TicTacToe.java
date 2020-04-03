package tictactoe;
import java.util.*;
public class Main {
    private int counter = 0;
    private char[][] ticToe = new char[3][3];
    private boolean isOWin = false;
    private boolean isXWin = false;
    private boolean isDraw = false;
    private boolean isGameOver = false;
    private char winner = ' ';
    private int numOfX = 0;
    private int numOfO = 0;
    private boolean isMatchToUs = true;
    private int [] humanMove = new int[2];
    private Scanner sc = new Scanner(System.in);
    private char signOfMoveHolder = 'X';
    private String ticToeString;

    private void takeField (String field){
        ticToeString = field;
        for (int i = 0; i < ticToe.length; i++) {
            for (int j = 0; j < ticToe[i].length; j++) {
                ticToe[i][j] = ticToeString.charAt(counter);
                if (ticToeString.charAt(counter) == 'X') {
                    numOfX++;
                } else if (ticToeString.charAt(counter) == 'O') {
                    numOfO++;
                }
                counter++;

            }
        }

    }
    private void printState() {
        System.out.println("---------");
        for (int i = 0; i < this.ticToe.length; i++) {
            System.out.print("| ");
            for (int j = 0; j < ticToe[i].length; j++) {
                if (ticToe[i][j] == '_'){
                    System.out.print(' ');
                } else {
                System.out.print(ticToe[i][j]);
                }
                System.out.print(" ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }
    private void playerMove (){
        System.out.println("Enter the coordinates: ");
        while (true) {
            if (sc.hasNextInt()) {
                isMatchToUs = true;
                for (int i = 0; i < humanMove.length; i++) {
                    humanMove[i] = sc.nextInt();
                    if (humanMove[i] > ticToe.length || humanMove[i] < 0) {
                        isMatchToUs = false;
                    }
                }
                sc.nextLine();
                if (isMatchToUs) {
                    if (humanMove[1] == 1){
                        humanMove[1] = 3;
                    } else if (humanMove[1] == 3){
                        humanMove[1] = 1;
                    }
                    if (ticToe[humanMove[1]-1][humanMove[0]-1] == 'X' || ticToe[humanMove[1]-1][humanMove[0]-1] == 'O'){
                        System.out.println("This cell is occupied! Choose another one!");
                        System.out.println("Enter the coordinates: ");
                        continue;
                    } else {
                        ticToe[humanMove[1]-1][humanMove[0]-1] = signOfMoveHolder;
                        signOfMoveHolder = signOfMoveHolder == 'X' ? 'O' : 'X';
                        break;
                    }
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                    System.out.println("Enter the coordinates: ");
                }
            } else if (!sc.hasNextInt())
            {
                String pass = sc.nextLine();
                isMatchToUs = false;
                System.out.println("You should enter numbers!");
                System.out.println("Enter the coordinates: ");
            }
            //sc.nextLine();
        }
    }
    private void checkState (){
        for (int j = 0; j < ticToe[0].length; j++) {
            for (int i = 0; i < ticToe.length; i++) {

                if (ticToe[0][j] != ticToe[i][j]) {
                    break;
                }
                if (i == ticToe.length - 1) {
                    winner = ticToe[i][j];
                    if (ticToe[i][j] == 'X') {
                        isXWin = true;
                    } else if (ticToe[i][j] == 'O') {
                        isOWin = true;
                    }
                }
            }
        }

        for (int j = 0; j < ticToe[0].length; j++) {
            for (int i = 0; i < ticToe.length; i++) {

                if (ticToe[j][0] != ticToe[j][i]) {
                    break;
                }
                if (i == ticToe.length - 1) {
                    winner = ticToe[j][i];
                    if (ticToe[j][i] == 'X') {
                        isXWin = true;
                    } else if (ticToe[j][i] == 'O') {
                        isOWin = true;
                    }
                }
            }
        }
        for (int j = 0; j < ticToe[0].length; j++) {
            for (int i = 0; i < ticToe.length; i++) {
                if (ticToe[0][j] != ticToe[i][j]) {
                    break;
                }
                if (i == ticToe.length - 1) {
                    winner = ticToe[i][j];
                    if (ticToe[i][j] == 'X') {
                        isXWin = true;
                    } else if (ticToe[i][j] == 'O') {
                        isOWin = true;
                    }
                }
            }
            for (int i = 0; i < ticToe.length; i++) {
                if (ticToe[0][ticToe.length - 1] != ticToe[i][ticToe.length - 1 - i]) {
                    break;
                }

                if (i == ticToe.length - 1) {
                    winner = ticToe[i][ticToe.length - 1 - i];

                    if (ticToe[i][ticToe.length - 1 - i] == 'X') {
                        isXWin = true;
                    } else if (ticToe[i][ticToe.length - 1 - i] == 'O') {
                        isOWin = true;
                    }}
            }

            for (int i = 0; i < ticToe.length; i++) {
                if (ticToe[0][0] != ticToe[i][i]) {
                    break;
                }
                if (i == ticToe.length - 1) {
                    winner = ticToe[i][i];

                    if (ticToe[i][i] == 'X') {
                        isXWin = true;
                    } else if (ticToe[i][i] == 'O') {
                        isOWin = true;
                    }}

            }
        }
        String ticToeUpdate = "";
        for (int m = 0; m < ticToe.length; m++){
            for (int n = 0; n <ticToe[m].length; n++){
                ticToeUpdate = ticToeUpdate.concat(String.valueOf(ticToe[m][n]));
            }
        }
        if (!ticToeUpdate.contains(" ") && !ticToeUpdate.contains("_") && (!isXWin && !isOWin)){
            isDraw = true;
        }
        if (numOfO - numOfX >= 2 || numOfX - numOfO >= 2) {
            System.out.println("Impossible");
        } else if (isXWin && isOWin) {
           System.out.println("Impossible");
        } else /*if (!isXWin && !isOWin && (ticToeString.contains("_") || ticToeString.contains(" "))) {
            System.out.println("Game not finished");
        } else */if (isDraw){
            System.out.println("Draw");
        } else if (isOWin || isXWin) {
            System.out.println(winner + " wins");
        }
       if (isXWin || isOWin || isDraw) {
            isGameOver = true;
        }
    }
    public static void main(String[] args) {
        Main Main = new Main();
        Main.takeField("_________");
        Main.printState();

        while(!Main.isGameOver)
        {
            Main.playerMove();
            Main.printState();
            Main.checkState();
        }
    }
}
