package ReadabilityScore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadabilityScore {

        private String[] arrayOfSentencies;
        private String fullText;
        private int[] sentenciesVolume;
        private double averageNumOfWords;
        private int numOfWords;
        private int numOfSentencies;
        private int numOfCharacters;
        private int numOfSyllable;
        private int numOfPolySyllable;
        private double scoreAri;
        private double scoreFk;
        private double scoreSmog;
        private double scoreCl;


        public String export(String[] args) {
            String filePath = args[0];
            File textFile = new File(filePath);
            StringBuilder sB = new StringBuilder();
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile));
                try (Scanner fileScanner = new Scanner(bufferedReader)) {

                    while (fileScanner.hasNextLine()) {
                        sB.append(fileScanner.nextLine());
                    }
                    this.setFullText(sB.toString());
                }
            } catch (IOException e) {
                System.out.println("File not found");
            }
            return sB.toString();

        }

        public int syllableCounter(String fullText) {
            int syllableCounter = 0;
            String[] words = fullText.split("\\b");
            System.out.println(words.length);
            Pattern p = Pattern.compile("\\w?[AEIOUYaeiouy]\\w?");
            Pattern p2 = Pattern.compile("\\w*[AEIOUYaeiouy]\\w+[eE]\\b");
            for (String s : words) {
                System.out.println(s);
                Matcher m = p.matcher(s);
                Matcher m2 = p2.matcher(s);
                while (m.find()) {
                    syllableCounter++;
                }
                System.out.println(syllableCounter);
                if (m2.matches()) {
                    syllableCounter--;
                }
                System.out.println(syllableCounter);
            }
            return syllableCounter;
        }



        public int polySyllableCounter(String fullText) {
            int polySyllableCounter = 0;
            String[] words = fullText.split("\\b");
            System.out.println(words.length);
            Pattern p = Pattern.compile("\\w?[AEIOUYaeiouy]\\w?");
            Pattern p2 = Pattern.compile("\\w*[AEIOUYaeiouy]\\w+[eE]\\b");
            for (String s : words) {
                int wordSyllableCounter = 0;
                Matcher m = p.matcher(s);
                Matcher m2 = p2.matcher(s);
                while (m.find()) {
                    wordSyllableCounter++;
                }
                if (m2.matches()) {
                    wordSyllableCounter--;
                }
                if (wordSyllableCounter > 2) {
                    polySyllableCounter++;
                }
            }

            return polySyllableCounter;
        }

        public String[] splitterAtSentencies(String fullLine) {
            return fullLine.split("[.!?]");
        }

        public int sentenciesCounter(String[] arrayOfSentencies) {
            return arrayOfSentencies.length;
        }

        public int characterCounter(String fullText) {
            int numOfChars = 0;
            Pattern p = Pattern.compile("\\S");
            Matcher m = p.matcher(fullText);
            while (m.find()) {
                numOfChars++;
            }
            return numOfChars;
        }

        public int[] inSentecnceWordsCounter(String[] arrayOfSentencies) {
            int[] localSentenciesVolume = new int[arrayOfSentencies.length];
            Pattern p = Pattern.compile("[\\w+]\\b");
            for (int i = 0; i < arrayOfSentencies.length; i++) {
                Matcher m = p.matcher(arrayOfSentencies[i]);
                while (m.find()) {
                    localSentenciesVolume[i]++;
                }
            }
            return localSentenciesVolume;
        }

        public int allWordsCounter(String fullText) {
            int wordsCounter = 0;
            Pattern p = Pattern.compile("\\d+[.,]\\d+\\b|\\w+\\b");
            Matcher m = p.matcher(fullText);
            while (m.find()) {
                wordsCounter++;
            }
            return wordsCounter;

        }

        public double wordsInSentenceAverge(int[] sentenciesVolume) {
            averageNumOfWords = 0;
            for (int i : sentenciesVolume) {
                averageNumOfWords += i;
            }
            return averageNumOfWords /= sentenciesVolume.length;
        }

        public void scoreComputing() {

            this.scoreAri = 4.71 * ((double) getNumOfCharacters() / (double) getNumOfWords()) +
                    0.5 * ((double) getNumOfWords() / (double) getNumOfSentencies()) - 21.43;

            this.scoreFk = 0.39 * ((double) this.numOfWords / this.numOfSentencies) +
                    11.8 * ((double) numOfSyllable / numOfWords) - 15.59;

            this.scoreSmog = 1.043 * Math.sqrt((numOfPolySyllable * ((double) 30 / numOfSentencies))) + 3.1291;

            this.scoreCl = 0.0588 * ((double) numOfCharacters / numOfWords) * 100 - 0.296 * ((double) numOfSentencies / numOfWords) * 100 - 15.8;
        }

        public void analyzePrinter() {
            System.out.printf("Words: %d\n", getNumOfWords());
            System.out.printf("Sentences: %d\n", getNumOfSentencies());
            System.out.printf("Characters: %d\n", getNumOfCharacters());
            System.out.printf("Syllables: %d\n", getNumOfSyllable());
            System.out.printf("Polysyllables: %d\n", getNumOfPolySyllable());
        }
        public void scorePrinter() {
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all):");
            try (Scanner sc = new Scanner(System.in)) {
                String mode = sc.next();
                switch (mode) {
                    case "ARI" :
                        System.out.printf("Automated Readability Index: %.2f (about %d year olds).",this.scoreAri , ageAnalayzer(this.scoreAri));
                        break;
                    case "FK" :
                        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d year olds).",this.scoreFk , ageAnalayzer(this.scoreFk));
                        break;
                    case "SMOG" :
                        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).",this.scoreSmog , ageAnalayzer(this.scoreSmog));
                        break;
                    case "CL" :
                        System.out.printf("Coleman–Liau index: %.2f (about %d year olds).",this.scoreCl , ageAnalayzer(this.scoreCl));
                        break;
                    case "all" :
                        double averageAge = (double) (scoreAri + scoreFk + scoreSmog + scoreCl) / 4;
                        System.out.printf("Automated Readability Index: %.2f (about %d year olds).\n",this.scoreAri , ageAnalayzer(this.scoreAri));
                        System.out.printf("Flesch–Kincaid readability tests: %.2f (about %d year olds).\n",this.scoreFk , ageAnalayzer(this.scoreFk));
                        System.out.printf("Simple Measure of Gobbledygook: %.2f (about %d year olds).\n",this.scoreSmog , ageAnalayzer(this.scoreSmog));
                        System.out.printf("Coleman–Liau index: %.2f (about %d year olds).\n",this.scoreCl , ageAnalayzer(this.scoreCl));
                        System.out.printf("This text should be understood in average by %.2f year olds.\n", averageAge);
                        break;
                    default :
                        System.out.println("You enter wrong mode");

                }
            }


        }


        public long ageAnalayzer(double score) {
            if (Math.round(score) < 3) {
                return Math.round(score) + 5;
            } else if (Math.round(score) < 13 ) {
                return Math.round(score) + 6;
            } else if (Math.round(score) == 13 ) {
                return Math.round(score) + 11;
            } else {
                return 24;
            }
        }

        public void progammMaker(String[] args) {
            setFullText(this.export(args));
            System.out.println("The text is:");
            System.out.println(getFullText());
            setArrayOfSentencies(splitterAtSentencies(getFullText()));
            setNumOfWords(allWordsCounter(getFullText()));
            setNumOfSentencies(sentenciesCounter(getArrayOfSentencies()));
            setNumOfCharacters(characterCounter(getFullText()));
            setNumOfSyllable(syllableCounter(getFullText()));
            setNumOfPolySyllable(polySyllableCounter(getFullText()));
            scoreComputing();
            analyzePrinter();
            scorePrinter();
        }
        public int getNumOfWords() {
            return numOfWords;
        }

        public void setNumOfWords(int numOfWords) {
            this.numOfWords = numOfWords;
        }

        public void setFullText(String fullText) {
            this.fullText = fullText;
        }

        public String getFullText() {
            return fullText;
        }
        public String[] getArrayOfSentencies() {
            return arrayOfSentencies;
        }

        public void setArrayOfSentencies(String[] arrayOfSentencies) {
            this.arrayOfSentencies = arrayOfSentencies;
        }

        public int[] getSentenciesVolume() {
            return sentenciesVolume;
        }

        public void setSentenciesVolume(int[] sentenciesVolume) {
            this.sentenciesVolume = sentenciesVolume;
        }

        public double getAverageNumOfWords() {
            return averageNumOfWords;
        }

        public void setAverageNumOfWords(double averageNumOfWords) {
            this.averageNumOfWords = averageNumOfWords;
        }
        public int getNumOfSentencies() {
            return numOfSentencies;
        }

        public void setNumOfSentencies(int numOfSentencies) {
            this.numOfSentencies = numOfSentencies;
        }

        public int getNumOfCharacters() {
            return numOfCharacters;
        }

        public void setNumOfCharacters(int numOfCharacters) {
            this.numOfCharacters = numOfCharacters;
        }

        public int getNumOfSyllable() {
            return numOfSyllable;
        }

        public void setNumOfSyllable(int numOfSullable) {
            this.numOfSyllable = numOfSullable;
        }

        public int getNumOfPolySyllable() {
            return numOfPolySyllable;
        }

        public void setNumOfPolySyllable(int numOfPolySullable) {
            this.numOfPolySyllable = numOfPolySullable;
        }
        public static void main(String[] args) {
            ReadabilityScore wordsAndSentencies = new ReadabilityScore();
            wordsAndSentencies.progammMaker(args);

        }
    }
