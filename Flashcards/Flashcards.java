package Flashcards;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Flashcards {

    boolean isGoOn = true;
    int numOfCards;
    Map<String, String> flashcardsMap = new LinkedHashMap<>();
    Map<String, Integer> wrongAnswersMap = new LinkedHashMap<>();
    String importPath = "empty";
    String exportPath = "empty";


    public void exportCards(Scanner sc, String path) {
        String filePath;
        if ("empty".equals(path)) {
            System.out.println("File name:");
            filePath = sc.nextLine();
        } else {
            filePath = path;
        }
        File fileIm = new File(filePath);
        try (FileWriter fW = new FileWriter(fileIm)) {
            if (fileIm.canWrite()) {
                for(Map.Entry<String, String> entry : flashcardsMap.entrySet()) {
                    fW.write(entry.getKey() + " : " + entry.getValue() + " : " + wrongAnswersMap.get(entry.getKey()) + "\n");
                }
            }
            //System.out.println(flashcardsMap.size() + " cards have been saved");
            System.out.printf("%d cards have been saved.\n", flashcardsMap.size());
        } catch (IOException e) {
            System.out.println("Error. Output file doesn't exist.0 cards have been saved.");
        }

    }

    public void importCards(Scanner sc, String path) {
        String filePath;
        if ("empty".equals(path)) {
            System.out.println("File name:");
            filePath = sc.nextLine();
        } else {
            filePath = path;
        }
        File fileEx = new File(filePath);
        String[] cards;
        int numOfImport = 0;
        try (Scanner fileSc = new Scanner(fileEx)) {
            while (fileSc.hasNext()) {
                cards = fileSc.nextLine().split("[:]");
                String card = cards[0].trim();
                String definition = cards[1].trim();
                int mistakes = Integer.parseInt(cards[2].trim());
                if (flashcardsMap.containsKey(card)) {
                    flashcardsMap.replace(card, definition);
                    wrongAnswersMap.replace(card, mistakes);
                    numOfImport++;
                } else {
                    flashcardsMap.put(card, definition);
                    wrongAnswersMap.put(card, mistakes);
                    numOfImport++;
                }
            }
            //System.out.println(numOfImport + " cards have been loaded");
            System.out.printf("%d cards have been loaded.\n", numOfImport);
        } catch (FileNotFoundException e) {
            System.out.println("Error. Input file not found.");
        }
        /*Set<String> keys = flashcardsMap.keySet();
        for (String s : keys) {
            wrongAnswersMap.put(s, 0);
        }*/
    }


    public void addCard(Scanner sc) {
        String card = "";
        String definition = "";

        System.out.println("The card:");

        card = sc.nextLine();
        if (flashcardsMap.containsKey(card)) {
            System.out.println("The card \"" + card + "\" already exists.");
            return;
        } else {
            flashcardsMap.put(card, null);
        }

        System.out.println("The definition of the card:");

        definition = sc.nextLine();
        if (flashcardsMap.containsValue(definition)) {
            System.out.println("The definition \"" + definition + "\" already exists.");
            flashcardsMap.remove(card);
            return;
        } else {
            flashcardsMap.put(card, definition);
            wrongAnswersMap.put(card, 0);
        }
        //System.out.println("The pair (\""+ card +"\":\""+ definition +"\") has been added.");
        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n", card, definition);


    }

    public void askQuetions(Scanner sc) {
        System.out.println("How many times to ask?");
        int askTimes = Integer.parseInt(sc.nextLine());
        do {
            for (Map.Entry<String, String> entry : flashcardsMap.entrySet()) {
                System.out.println("Print the definition of \"" + entry.getKey() + "\".");
                String answer = sc.nextLine();
                if (entry.getValue().equals(answer)) {
                    System.out.println("Correct answer.");
                } else {
                    if (flashcardsMap.containsValue(answer)) {
                        for (Map.Entry<String, String> entry2 : flashcardsMap.entrySet()) {
                            if (entry2.getValue().equals(answer)) {
                                System.out.println("Wrong answer. The correct one is \"" + entry.getValue() + "\", you've just written the definition of \"" + entry2.getKey() + "\".");
                            }
                        }
                    } else {
                        System.out.println("Wrong answer. The correct one is \"" + entry.getValue() + "\".");
                    }
                    int numOfWrongAnswers = wrongAnswersMap.get(entry.getKey());
                    numOfWrongAnswers++;
                    wrongAnswersMap.replace(entry.getKey(),numOfWrongAnswers);
                }
                askTimes--;
                if (askTimes < 1) {
                    break;
                }
            }
        } while (askTimes > 0);
    }

    public void exit() {
        this.isGoOn = false;
        System.out.println("Bye bye!");
    }

    public void removeCard(Scanner sc) {
        String card = "";
        System.out.println("The card:");
        card = sc.nextLine();
        if (flashcardsMap.containsKey(card)) {
            //System.out.println("The pair (\""+ card +"\":\""+ flashcardsMap.get(card) +"\") has been removed.");
            System.out.printf("The pair (\"%s\":\"%s\") has been removed.\n", card, flashcardsMap.get(card));
            flashcardsMap.remove(card);
            wrongAnswersMap.remove(card);
        } else {
            //System.out.println("Can't remove \"" + card + "\": there is no such card.");
            System.out.printf("Can't remove \"%s\": there is no such card.\n",card);
        }
    }

    public void hardestCard() {
        int maxWrongAnswers = 0;
        List<String> nameOfWrongs = new ArrayList<>();
        for (Map.Entry<String,Integer> entry: wrongAnswersMap.entrySet()) {
            if (entry.getValue() > maxWrongAnswers) {
                maxWrongAnswers = entry.getValue();
            }
        }
        for (Map.Entry<String,Integer> entry: wrongAnswersMap.entrySet()) {
            if (entry.getValue() == maxWrongAnswers) {
                nameOfWrongs.add(entry.getKey());
            }
        }
        if (maxWrongAnswers == 0) {
            System.out.println("There are no cards with errors.");
        } else if (nameOfWrongs.size() == 1) {
            System.out.print("The hardest card is ");
            for (int i = 0; i < nameOfWrongs.size(); i++) {
                System.out.printf("\"%s\"", nameOfWrongs.get(i));
                if (i != nameOfWrongs.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.printf(". You have %d errors answering it.\n", maxWrongAnswers);
        } else {
            System.out.print("The hardest cards is ");
            for (int i = 0; i < nameOfWrongs.size(); i++) {
                System.out.printf("\"%s\"", nameOfWrongs.get(i));
                if (i != nameOfWrongs.size() - 1) {
                    System.out.print(",");
                }
            }
            System.out.printf(". You have %d errors answering them.\n", maxWrongAnswers);
        }

    }

    public void resetStats() {
        for (Map.Entry<String,Integer> entry: wrongAnswersMap.entrySet()) {
            wrongAnswersMap.replace(entry.getKey(), 0);
        }
        System.out.println("Card statistics has been reset.");
    }

    public void log(Scanner sc) {
        String filePath;
        System.out.println("File name:");
        filePath = sc.nextLine();
        File fileIm = new File(filePath);
        try (FileWriter fW = new FileWriter(fileIm)) {
            if (fileIm.canWrite()) {
                fW.write("Some Log Info");
            }

            System.out.println("The log has been saved.");
        } catch (IOException e) {
            System.out.println("Error. Output file doesn't exist.0 cards have been saved.");
        }
        Set<String> keys = flashcardsMap.keySet();
        for (String s : keys) {
            wrongAnswersMap.put(s, 0);
        }

    }

    /* public void logStart(Scanner sc) {
         List<String> logArray = new ArrayList<>();
         BufferedReader buffReader = new BufferedReader(new InputStreamReader(System.in));
         try {
             OutputStream allOut = new FileOutputStream(buffReader.readLine());

             while (true) {
                 String line;
                 line = buffReader.readLine();
                 if (line.equals("todayLog.txt")) {
                     allOut.write(line.getBytes());
                     break;
                 } else {
                     allOut.write((line + "\r\n").getBytes());}
             }
         } catch (IOException e) {
             System.out.println("Something went wrong");
         }

         List<String> inLog = new ArrayList<>();
         String filePath;
         System.out.println("File name:");
         filePath = sc.nextLine();
         File fileIm = new File(filePath);

         Set<String> keys = flashcardsMap.keySet();
         for (String s : keys) {
             wrongAnswersMap.put(s, 0);
         }

     }*/
    private void choosingMode(String[] args){
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("import")){
                this.importPath = args[i + 1];
                break;
            }
        }
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("export")){
                this.exportPath = args[i + 1];
                break;
            }
        }
    }

    public void askingForAction(Scanner sc) {
        String action;
        System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card, reset stats):");
        action = sc.nextLine();
        switch (action) {
            case "add":
                addCard(sc);
                break;
            case "remove":
                removeCard(sc);
                break;
            case "import":
                importCards(sc,this.importPath);
                break;
            case "export":
                exportCards(sc,this.exportPath);
                break;
            case "ask":
                askQuetions(sc);
                break;
            case "exit":
                exit();
                if (!"empty".equals(this.exportPath)) {
                    exportCards(sc,this.exportPath);
                }
                break;
            case "log":
                log(sc);
                break;
            case "hardest card":
                hardestCard();
                break;
            case "reset stats":
                resetStats();
                break;
            default:
                System.out.println("You enter wrong action!");
                System.out.println("Choose again");
        }

    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Flashcards fsh = new Flashcards();
        fsh.choosingMode(args);
        if (!"empty".equals(fsh.importPath)) {
            fsh.importCards(sc,fsh.importPath);
        }
        while (fsh.isGoOn) {
            fsh.askingForAction(sc);
        }
    }
}
