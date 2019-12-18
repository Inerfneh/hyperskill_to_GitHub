
import java.util.Scanner;
public class CoffeeMachine {
    private int waterInStorage;
    private int milkInStorage;
    private int beansInStorage;
    private int numOfCupsInStorage;
    private int moneyOfStorage;
    private boolean statusOn;
    Scanner sc = new Scanner(System.in);
    public CoffeeMachine(int aWaterInStorage,int aMilkInStorage,int aBeansInStorage,int aNumOfCupsInStorage,int aMoneyOfStorage, boolean aStatusOn){
        waterInStorage = aWaterInStorage;
        milkInStorage = aMilkInStorage;
        beansInStorage = aBeansInStorage;
        numOfCupsInStorage = aNumOfCupsInStorage;
        moneyOfStorage = aMoneyOfStorage;
        statusOn = aStatusOn;
    }

    public void choose(){
        System.out.println("Write action (buy, fill, take, remaining, exit)");
        String choose = sc.next();
        switch (choose) {
            case "buy" :
                buy();
                break;
            case "fill" :
                fill();
                break;
            case "take" :
                take();
                break;
            case "remaining" :
                remaining();
                break;
            case "exit" :
                exit();
                break;
            default:
                System.out.println("Please try again");
                break;
        }

    }
    public void buy(){
        System.out.println("");
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappucino, back - to main menu:");
        String drink = sc.next();
        switch (drink) {
            case "1" :
                makeEspresso(1);
                break;
            case "2" :
                makeLatte(1);
                break;
            case "3" :
                makeCappucino(1);
                break;
            case "back" :
                choose();
                break;
            default:
                System.out.println("Please try again");
                choose();
                break;
        }

    }
    public void makeEspresso(int numOfCups){
        int water = 250;
        int beans = 16;
        int cost = 4;
        int dispCups = 1;

        if (water <= waterInStorage && beans <= beansInStorage && dispCups <= numOfCupsInStorage) {
            System.out.println("I have enough resources, making you a coffee!");
            waterInStorage -= water;
            beansInStorage -= beans;
            numOfCupsInStorage -= dispCups;
            moneyOfStorage += cost;
        } else if (water > waterInStorage){
            System.out.println("Sorry, not enough water!");
        } else if (beans > beansInStorage){
            System.out.println("Sorry, not enough beans!");
        } else if (dispCups > numOfCupsInStorage){
            System.out.println("Sorry, not enough cups!");
        }
        System.out.println("");
    }
    public void makeLatte(int numOfCups){
        int water = 350;
        int milk = 75;
        int beans = 20;
        int cost = 7;
        int dispCups = 1;

        if (water <= waterInStorage && milk <= milkInStorage && beans <= beansInStorage && dispCups <= numOfCupsInStorage) {
            System.out.println("I have enough resources, making you a coffee!");
            waterInStorage -= water;
            milkInStorage -= milk;
            beansInStorage -= beans;
            numOfCupsInStorage -= dispCups;
            moneyOfStorage += cost;
        } else if (water > waterInStorage){
            System.out.println("Sorry, not enough water!");
        } else if (milk > milkInStorage){
            System.out.println("Sorry, not enough milk!");
        } else if (beans > beansInStorage){
            System.out.println("Sorry, not enough beans!");
        } else if (dispCups > numOfCupsInStorage){
            System.out.println("Sorry, not enough cups!");
        }
        System.out.println("");
    }
    public void makeCappucino(int numOfCups){
        int water = 200;
        int milk = 100;
        int beans = 12;
        int cost = 6;
        int dispCups = 1;
        if (water <= waterInStorage && milk <= milkInStorage && beans <= beansInStorage && dispCups <= numOfCupsInStorage) {
            System.out.println("I have enough resources, making you a coffee!");
            waterInStorage -= water;
            milkInStorage -= milk;
            beansInStorage -= beans;
            numOfCupsInStorage -= dispCups;
            moneyOfStorage += cost;
        } else if (water > waterInStorage){
            System.out.println("Sorry, not enough water!");
        } else if (milk > milkInStorage){
            System.out.println("Sorry, not enough milk!");
        } else if (beans > beansInStorage){
            System.out.println("Sorry, not enough beans!");
        } else if (dispCups > numOfCupsInStorage){
            System.out.println("Sorry, not enough cups!");
        }
        System.out.println("");
    }
    public void fill(){
        System.out.println("Write how many ml of water do you want to add:");
        int water = sc.nextInt();
        System.out.println("Write how many ml of milk do you want to add:");
        int milk = sc.nextInt();
        System.out.println("Write how many grams of coffee beans do you want to add:");
        int coffeeBeans = sc.nextInt();
        System.out.println("Write how many disposable cups of coffee do you want to add:");
        int dispCups = sc.nextInt();
        waterInStorage += water;
        milkInStorage += milk;
        beansInStorage += coffeeBeans;
        numOfCupsInStorage += dispCups;
        System.out.println("");
    }
    public void take(){
        System.out.println("I gave you $" + moneyOfStorage);
        moneyOfStorage -= moneyOfStorage;
        System.out.println("");
    }
    public void remaining(){
        System.out.println("");
        System.out.println("The coffee machine has:");
        System.out.println(waterInStorage + " of water");
        System.out.println(milkInStorage + " of milk");
        System.out.println(beansInStorage + " of coffee beans");
        System.out.println(numOfCupsInStorage + " of disposable cups");
        System.out.println(moneyOfStorage + " of money");
        System.out.println("");
    }
    public void exit(){
        statusOn = false;
    }
    public boolean getStatus(){
        return statusOn;
    }

    public static void main(String[] args) {
        CoffeeMachine myCM = new CoffeeMachine(400,540, 120, 9, 550, true);
        do {
            myCM.choose();
        } while (myCM.getStatus());

    }
}
