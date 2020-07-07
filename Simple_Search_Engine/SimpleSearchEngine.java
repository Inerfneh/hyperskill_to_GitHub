package SimpleSearchEngineProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class SimpleSearchEngine {

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Searcher expandTheSearch = new Searcher();
            expandTheSearch.setOn(true);
            expandTheSearch.fileReader(args);
            while (expandTheSearch.isOn()) {
                expandTheSearch.mainMenuChooser(sc);
            }
        }
    }
}

class Searcher {
    private List<String> contentLines = new ArrayList<>();
    private Map<String, Set<Integer>> invertedIndex = new HashMap<>();


    public SimpleSearchEngineProject.searchBehavior getSearchBehavior() {
        return searchBehavior;
    }

    public void setSearchBehavior(SimpleSearchEngineProject.searchBehavior searchBehavior) {
        this.searchBehavior = searchBehavior;
    }

    private searchBehavior searchBehavior;
    private boolean isOn = false;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public void fileReader(String[] args) {
        File textFile = new File(args[1]);
        try (Scanner scanner = new Scanner(textFile)) {
            while (scanner.hasNext()) {
                contentLines.add(scanner.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        indexMaker();
    }

    private void indexMaker() {
        String[] temporaryStringHolder;
        for (int i = 0; i < contentLines.size(); i++) {
            temporaryStringHolder = contentLines.get(i).split("\\s+");

            for (String s : temporaryStringHolder) {
                invertedIndex.putIfAbsent(s, new HashSet<>());
                invertedIndex.get(s).add(i);
            }
        }
//        System.out.println(invertedIndex.toString());
    }

    private void peopleFindPrinter(Scanner sc) {
        Set<Integer> set = searchBehavior.search(sc.nextLine(),contentLines, invertedIndex);
        if (set != null) {
            System.out.println(set.size());
            for (Integer i : set) {
                System.out.println(contentLines.get(i));
            }
        } else {
            System.out.println("No matching people found.");
        }
    }

    public void mainMenuChooser(Scanner sc) {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
        switch (sc.nextLine()) {
            case ("1"):
                searchMenuChooser(sc);
                System.out.println("Enter a name or email to search all suitable people.");
                peopleFindPrinter(sc);
                break;
            case ("2"):
                peoplePrinter();
                break;
            case ("0"):
                this.isOn = false;
                break;
            default:
                System.out.println("Incorrect option! Try again.");
        }
    }

    private void searchMenuChooser(Scanner sc) {
        while (true) {
            System.out.println("Select a matching strategy: ALL, ANY, NONE");
            switch (sc.nextLine()) {
                case ("ALL"):
                    this.setSearchBehavior(new AllSearch());
                    return;
                case ("ANY"):
                    this.setSearchBehavior(new AnySearch());
                    return;
                case ("NONE"):
                    this.setSearchBehavior(new NoneSearch());
                    return;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    private void peoplePrinter() {
        System.out.println("=== List of people ===");
        for (String s : this.contentLines) {
            System.out.println(s);
        }
    }
}


abstract class searchBehavior {

    protected String[] searchWords;
    protected Set<Integer> searchResult = new HashSet<>();

    protected void stringSplitter(String searchLine) {
        searchWords = searchLine.split("\\s++");
    }

    abstract public Set<Integer> search(String searchData, List<String> mainList, Map<String, Set<Integer>> indexMap);
}

class AllSearch extends searchBehavior {

    @Override
    public Set<Integer> search(String searchData, List<String> mainList, Map<String, Set<Integer>> indexMap) {
        stringSplitter(searchData);

        for (int i = 0; i < mainList.size(); i++) {
            searchResult.add(i);
        }

        for (String s : searchWords) {
            for (Map.Entry<String, Set<Integer>> entryMap : indexMap.entrySet()) {
                if (entryMap.getKey().toLowerCase().equals(s.toLowerCase())) {
                    searchResult.retainAll(entryMap.getValue());
                }
            }
        }
        return null;
    }
}

class AnySearch extends searchBehavior {

    @Override
    public Set<Integer> search(String searchData, List<String> mainList, Map<String, Set<Integer>> indexMap) {
        stringSplitter(searchData);


        for (String s : searchWords) {
            for (Map.Entry<String, Set<Integer>> entryMap : indexMap.entrySet()) {
                if (entryMap.getKey().toLowerCase().equals(s.toLowerCase())) {
                    searchResult.addAll(entryMap.getValue());
                }
            }
        }
        return searchResult;
    }
}

class NoneSearch extends searchBehavior {

    @Override
    public Set<Integer> search(String searchData, List<String> mainList, Map<String, Set<Integer>> indexMap) {
        stringSplitter(searchData);

        for (int i = 0; i < mainList.size(); i++) {
            searchResult.add(i);
        }

        for (String s : searchWords) {
            for (Map.Entry<String, Set<Integer>> entryMap: indexMap.entrySet()) {
                if (entryMap.getKey().toLowerCase().equals(s.toLowerCase())) {
                    searchResult.removeAll(entryMap.getValue());
                }
            }
        }
        return searchResult;
    }
}

