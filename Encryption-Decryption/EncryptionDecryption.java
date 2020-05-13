package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        ChoosingAlgorythmAndMode choose = new ChoosingAlgorythmAndMode();
        CryptFactory cF = new CryptFactory();
        choose.choosingModeAndKey(args);
        CryptBehavior cB = cF.chooseMethod(choose.getMode(),choose.getAlgorithm());
        choose.outMethod(cB.decryptionAlgorithm(choose.readIn(args), choose.getKey()));
    }
}


class CryptFactory {
    protected CryptBehavior chooseMethod(String mode, String alg) {
     if ("dec".equals(mode) && "shift".equals(alg)) {
         return new DecryptionShift();
     } else if ("dec".equals(mode) && "unicode".equals(alg)) {
         return new DecryptionUnicode();
     } else if ("enc".equals(mode) && "shift".equals(alg)) {
         return new EncryptionShift();
     } else if ("enc".equals(mode) && "unicode".equals(alg)) {
         return new EncryptionUnicode();
     }
        return null;
    }
}

class ChoosingAlgorythmAndMode {
    private String text = "";
    private int Key = 0;
    private String mode = "enc";
    private boolean hasOut = false;
    private String filePathIn = "";
    private String filePathOut = "";
    private String algorithm = "shift";
    private boolean hasData = false;
    private boolean hasIn = false;
    private int dataNum = 0;
    private int inNum = 0;

    public int getKey() {
        return Key;
    }

    public String getMode() {
        return mode;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    protected void choosingModeAndKey(String[] args){
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("mode")){
                this.mode = args[i + 1];
                break;
            }
        }
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("key")){
                this.Key = Integer.parseInt(args[i + 1]);
                break;
            }
        }
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("data")) {
                hasData = true;
                dataNum = i+1;
            }
            if (args[i].contains("in")) {
                hasIn = true;
                inNum = i+1;
            }
        }
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("out")){
                this.hasOut = true;
                this.filePathOut = args[i + 1];
            }
        }
        for (int i = 0; i < args.length; i += 2) {
            if (args[i].contains("alg")){
                this.algorithm = args[i + 1];
                break;
            }
        }

    }
    protected String readIn(String[] args) {
        if (hasData) {
            return args[dataNum];
        } else if (hasIn) {
            this.filePathIn = args[inNum];
            return this.fileReader();
        }
        return "";
    }

    private String fileReader() {
        File inFile = new File (this.filePathIn);
        try (Scanner sc = new Scanner(inFile)){
                text = sc.nextLine();
        } catch (FileNotFoundException e) {
            System.out.println("Error. Input file doesn't exist");
        }
        return text;

    }
    protected void outMethod(String text){
        if (this.hasOut) {
            this.fileWriter(text);
        } else {
            System.out.println(text);
        }
    }

    private void fileWriter(String aText) {
        File outFile = new File (this.filePathOut);
        try (FileWriter fileWriter = new FileWriter(outFile)) {
            fileWriter.write(aText);
        } catch (IOException e){
            System.out.println("Error. Output file doesn't exist");
        }
    }
}


abstract class CryptBehavior {

    protected String decryptionAlgorithm(String text, int origKey){
        return String.valueOf(decryption(text,origKey));
    }

    protected abstract char[] decryption(String text, int origKey);
}

class DecryptionShift extends CryptBehavior {
    protected char[] decryption(String text, int origKey){
        char[] ch = text.toCharArray();
        for (int j = 0; j < ch.length; j++) {
            if (ch[j] > 64 && ch[j] < 91) {
                ch[j] -= origKey;
                if (ch[j] < 65) {
                    ch[j] = (char) (91 - (65 - ch[j]));
                }
            }
            if (ch[j] > 96 && ch[j] < 123) {
                ch[j] -= origKey;
                if (ch[j] < 97) {
                    ch[j] = (char) (123 - (97 - ch[j]));
                }
            }
        }
        return ch;
    }
}
class DecryptionUnicode extends CryptBehavior {
    protected char[] decryption(String text, int origKey){
        char[] ch = text.toCharArray();
        for (int j = 0; j < ch.length; j++) {
            ch[j] -= origKey;
        }
        return ch;
    }
}
class EncryptionShift extends CryptBehavior {
    protected char[] decryption(String text, int origKey) {
        char[] ch = text.toCharArray();
        for (int j = 0; j < ch.length; j++) {
            if (ch[j] > 64 && ch[j] < 91) {
                ch[j] += origKey;
                if (ch[j] > 90) {
                    ch[j] = (char) (64 + (ch[j] - 91));
                }
            }
            if (ch[j] > 96 && ch[j] < 123) {
                ch[j] += origKey;
                if (ch[j] > 122) {
                    ch[j] = (char) (96 + (ch[j] - 122));
                }
            }
        }
        return ch;
    }
}

class EncryptionUnicode extends CryptBehavior {
    protected char[] decryption(String text, int origKey){
        char[] ch = text.toCharArray();
        for (int j = 0; j < ch.length; j++) {
            ch[j] += origKey;
        }
        return ch;
    }
}
