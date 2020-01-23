package bot;

import java.util.Scanner;

public class SimpleBot {
    final static Scanner scanner = new Scanner(System.in); // Do not change this line

    public static void main(String[] args) {
        greet("Aid", "2018"); // change it as you need
        remindName();
        guessAge();
        count();
        test();
        end();
    }

    static void greet(String assistantName, String birthYear) {
        System.out.println("Hello! My name is " + assistantName + ".");
        System.out.println("I was created in " + birthYear + ".");
        System.out.println("Please, remind me your name.");
    }

    static void remindName() {
        String name = scanner.nextLine();
        System.out.println("What a great name you have, " + name + "!");
    }

    static void guessAge() {
        System.out.println("Let me guess your age.");
        System.out.println("Say me remainders of dividing your age by 3, 5 and 7.");
        int rem3 = scanner.nextInt();
        int rem5 = scanner.nextInt();
        int rem7 = scanner.nextInt();
        int age = (rem3 * 70 + rem5 * 21 + rem7 * 15) % 105;
        System.out.println("Your age is " + age + "; that's a good time to start programming!");
    }

    static void count() {
        System.out.println("Now I will prove to you that I can count to any number you want.");
        int num = scanner.nextInt();
        for (int i = 0; i <= num; i++) {
            System.out.printf("%d!\n", i);
        }
    }

    static void test() {
        System.out.println("Let's test your programming knowledge.");
        System.out.println("На каких глубинах пустоты в выработках под зданиями, сооружениями, коммуникациями ликвидируется путем закладки"); // write your code here
        System.out.println("1. На глубинах до 25H (H - высота выработки вчерне), а при наличии в массиве прорывоопасных пород - на глубинах до 80H, но не менее 100м"); // write your code here
        System.out.println("2. На глубинах до 20H (H - высота выработки вчерне), а при наличии в массиве прорывоопасных пород - на глубинах до 50H, но не менее 90м"); // write your code here
        System.out.println("3. На глубинах до 15H (H - высота выработки вчерне), а при наличии в массиве прорывоопасных пород - на глубинах до 30H, но не менее 80м"); // write your code here
        System.out.println("4. Все выработки ликвидируются путем закладки незавимимо от глубины"); // write your code here
        String choosing = scanner.next();
        while (!choosing.equals("3")){
            System.out.println("Please try again.");
            choosing = scanner.next();
        }
        }

    static void end() {
        System.out.println("Congratulations, have a nice day!"); // Do not change this text
    }
}
