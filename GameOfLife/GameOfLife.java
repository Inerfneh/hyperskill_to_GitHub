package GameOfLife;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GameOfLife extends JFrame {

    GameField gameField;
    int numOfRow;
    int numOfCol;
    int boxSize;
    int playTime;
    JLabel genLabel = new JLabel();
    JLabel aliveLabel = new JLabel();
    JSlider speedSlider;
    JPanel[][] jPanels;
    JPanel gameFieldPanel;
    JToggleButton playButton;
    JButton pauseButton;
    JButton resetButton;
    JButton decreaseSpeedButton;
    JButton increaseSpeedButton;

    Timer timer;

    private int sleepTime = 1000;

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        if (sleepTime <= 100) {
            this.sleepTime = 100;
        } else {
            this.sleepTime = sleepTime;
        }
    }

    private ActionListener playButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (timer.isRunning()) {
                timer.stop();
            } else {
                timer.start();
            }
        }
    };

    private ActionListener pauseButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            timer.stop();
        }
    };

    private ActionListener resetButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setGameField(new GameField());
            playTime = 0;
        }
    };

    private ActionListener decreaseSpeedButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setSleepTime(getSleepTime() + 100);
            timer.setDelay(getSleepTime());
        }
    };

    private ActionListener increaseSpeedButtonListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setSleepTime(getSleepTime() - 100);
            timer.setDelay(getSleepTime());
        }
    };

    private ActionListener timerListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            (new GameOfLife.SwingWorkerTrying(getGameField())).execute();
            playTime++;
            if (playTime >= gameField.getNumOfGeneration()) {
                timer.stop();
            }
        }
    };


    public GameField getGameField() {
        return gameField;
    }

    public void setGameField(GameField gameField) {
        this.gameField = gameField;
    }

    public GameOfLife() {
        super("Game of Life");
        setGameField(new GameField());
        gameField.readFieldParameters();
        numOfRow = gameField.getCurrentGeneration()[0].length;
        numOfCol = gameField.getCurrentGeneration().length;
        boxSize = 25;
        JPanel[][] jPanels;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize((numOfCol * boxSize + numOfCol + 1 + 300), (numOfRow * boxSize + numOfRow + 1 + 120));
        setLocationRelativeTo(null);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        initComponents();

        setLayout(null);
        setVisible(true);
    }

    public void initComponents() {
        playTime = 0;
        timer = new Timer(sleepTime, timerListener);
        timer.setRepeats(true);

        genLabel.setText("Generation #" + (gameField.getCurrGenNum() + 1));
        aliveLabel.setText("Alive: " + gameField.getAliveCounterInCurrGeneration());
        speedSlider = new JSlider(10, 11, 10);
        playButton = new JToggleButton();
//        pauseButton = new JButton();
        resetButton = new JButton();
        decreaseSpeedButton = new JButton();
        increaseSpeedButton = new JButton();

        playButton.setText("Play/Pause");
//        pauseButton.setText("Pause");
        resetButton.setText("Reset");
        decreaseSpeedButton.setText("Slow down");
        increaseSpeedButton.setText("Go on");

        genLabel.setHorizontalAlignment(SwingConstants.LEFT);

        genLabel.setBounds(10, 130, 200, 20);
        aliveLabel.setBounds(10, 160, 200, 20);
        speedSlider.setBounds(10, 190, 200, 20);
        playButton.setBounds(10, 10, 150, 50);
//        pauseButton.setBounds(80, 10, 70, 50);
        resetButton.setBounds(150, 10, 70, 50);
        decreaseSpeedButton.setBounds(10, 70, 105, 50);
        increaseSpeedButton.setBounds(115, 70, 105, 50);

        genLabel.setName("GenerationLabel");
        aliveLabel.setName("AliveLabel");
        speedSlider.setName("SpeedSlider");
        resetButton.setName("ResetButton");
        playButton.setName("PlayToggleButton");
//        pauseButton.setName("PauseButton");
        increaseSpeedButton.setName("IncreaseSpeedButton");
        decreaseSpeedButton.setName("DecreaseSpeedButton");

        add(genLabel);
        add(aliveLabel);
        add(playButton);
//        add(pauseButton);
        add(resetButton);
        add(decreaseSpeedButton);
        add(increaseSpeedButton);

        playButton.addActionListener(playButtonListener);
//        pauseButton.addActionListener(pauseButtonListener);
        resetButton.addActionListener(resetButtonListener);
        decreaseSpeedButton.addActionListener(decreaseSpeedButtonListener);
        increaseSpeedButton.addActionListener(increaseSpeedButtonListener);

        gameFieldPanel = new JPanel();
        jPanels = new JPanel[numOfCol][numOfRow];

        gameFieldPanel.setBounds(230, 10, (numOfCol * boxSize + numOfCol + 1), (numOfRow * boxSize + numOfRow + 1));
        gameFieldPanel.setLayout(new GridLayout(numOfRow, numOfCol));
        gameFieldPanel.setBackground(Color.DARK_GRAY);

        for (int i = 0; i < numOfCol; i++) {
            for (int j = 0; j < numOfRow; j++) {
                if (gameField.getCurrentGeneration()[i][j]) {
                    jPanels[i][j] = new JPanel();
                    jPanels[i][j].setBounds(50, 50, boxSize, boxSize);
                    gameFieldPanel.add(jPanels[i][j]);
                    jPanels[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    jPanels[i][j] = new JPanel();
                    jPanels[i][j].setBounds(50, 50, boxSize, boxSize);
                    gameFieldPanel.add(jPanels[i][j]);
                    jPanels[i][j].setBackground(Color.DARK_GRAY);
                }
            }
        }
        add(gameFieldPanel);
        timer.start();
    }

    public void refreshComponents() {

        genLabel.setText("Generation #" + (gameField.getCurrGenNum() + 1));
        aliveLabel.setText("Alive: " + gameField.getAliveCounterInCurrGeneration());

        for (int i = 0; i < numOfCol; i++) {
            for (int j = 0; j < numOfRow; j++) {
                if (gameField.getCurrentGeneration()[i][j]) {
                    jPanels[i][j].setBackground(Color.LIGHT_GRAY);
                } else {
                    jPanels[i][j].setBackground(Color.DARK_GRAY);
                }
            }
        }

    }

    class SwingWorkerTrying extends SwingWorker<GameField, GameField> {

        GameField gameField;

        public SwingWorkerTrying(GameField outerGameField) {
            gameField = outerGameField;

        }

        @Override
        protected GameField doInBackground() throws Exception {
            gameField.genChanger();
            return gameField;
        }

        @Override
        protected void done() {
            refreshComponents();
        }
    }


//    public static void main(String[] args) throws InterruptedException {
//        GameOfLife liveBegin = new GameOfLife();
//        liveBegin.initComponents();
//        for (int i = 0; i < liveBegin.gameField.getNumOfGeneration(); i++) {
////            liveBegin.gameField.printFieldWithCleanConsole(liveBegin.gameField.getCurrentGeneration());
//            Thread.sleep(250);
//            liveBegin.gameField.genChanger();
//            liveBegin.refreshComponents();
//            liveBegin.initComponents();
//        }
//    }
}

class FourthStage {

    public static void main(String[] args) throws InterruptedException {
        GameOfLife liveBegin = new GameOfLife();
        liveBegin.initComponents();
        for (int i = 0; i < liveBegin.gameField.getNumOfGeneration(); i++) {
//            liveBegin.gameField.printFieldWithCleanConsole(liveBegin.gameField.getCurrentGeneration());
            Thread.sleep(250);
            liveBegin.gameField.genChanger();
            liveBegin.refreshComponents();
            liveBegin.initComponents();
        }
    }

    public void makeLive() throws InterruptedException {
        GameField gameField = new GameField();
        gameField.readFieldParameters();
        for (int i = 0; i < gameField.getNumOfGeneration(); i++) {
            gameField.genChanger();
            gameField.printFieldWithCleanConsole(gameField.getCurrentGeneration());
            Thread.sleep(250);
        }
    }
}


class GameField {

    private boolean[][] currentGeneration;
    private boolean[][] nextGeneration;
    private int sizeOfUniverse;
    private int randomSeed;
    private int numOfGeneration;
    private Random random;
    private int aliveCounterInCurrGeneration;
    private int currGenNum;

    public int getCurrGenNum() {
        return currGenNum;
    }

    public int getAliveCounterInCurrGeneration() {
        return aliveCounterInCurrGeneration;
    }

    public boolean[][] getCurrentGeneration() {
        return currentGeneration;
    }

    public int getNumOfGeneration() {
        return numOfGeneration;
    }

    GenerationNew generationNew;

    public void readFieldParameters() {
        try (Scanner sc = new Scanner(System.in)) {
//            sizeOfUniverse = sc.nextInt();
            sizeOfUniverse = 20;
//            randomSeed = sc.nextInt();
//            numOfGeneration = sc.nextInt();
            numOfGeneration = 100;
            random = new Random();
            currentGeneration = new boolean[sizeOfUniverse][sizeOfUniverse];
            nextGeneration = new boolean[sizeOfUniverse][sizeOfUniverse];
            makeStartField(currentGeneration, random);
            generationNew = new GenerationNew();
            aliveCounterInCurrGeneration = aliveCounter(currentGeneration);
            currGenNum = 0;
        }
    }


    public void genChanger() {
        nextGeneration = generationNew.genMaker(currentGeneration, sizeOfUniverse);
        currentGeneration = nextGeneration;
        currGenNum++;
        aliveCounterInCurrGeneration = aliveCounter(currentGeneration);
    }

    private void makeStartField(boolean[][] field, Random random) {
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                field[i][j] = random.nextBoolean();
            }
        }
    }

    public void printFieldWithCleanConsole(boolean[][] field) {

        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException e) {
        }

        System.out.println("Generation #" + (currGenNum));
        System.out.println("Alive: " + aliveCounter(field));


        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j]) {
                    System.out.print("O");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private int aliveCounter(boolean[][] field) {
        int aliveNumber = 0;
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                if (field[i][j]) {
                    aliveNumber++;
                }
            }
        }
        return aliveNumber;
    }
}

class GenerationNew {

    public boolean[][] genMaker(boolean[][] curGen, int sizeOfU) {

        boolean[][] nextGen = new boolean[sizeOfU][sizeOfU];

        for (int i = 0; i < curGen.length; i++) {
            for (int j = 0; j < curGen[i].length; j++) {

                int counter = 0;

                for (int m = -1; m < 2; m++) {
                    for (int n = -1; n < 2; n++) {

                        int a = i + m;
                        int b = j + n;

                        if (m == 0 && n == 0) {
                            continue;
                        }
                        if (a < 0) {
                            a = curGen.length + m;
                        } else if (a > curGen.length - 1) {
                            a = m - 1;
                        }
                        if (b < 0) {
                            b = curGen[i].length + n;
                        } else if (b > curGen[i].length - 1) {
                            b = n - 1;
                        }
                        if (curGen[a][b]) {
                            counter++;
                        }
                    }
                }

                if (counter < 2 || counter > 3) {
                    nextGen[i][j] = false;
                } else if (!curGen[i][j] && counter == 3) {
                    nextGen[i][j] = true;
                } else if (curGen[i][j]) {
                    nextGen[i][j] = true;
                }
//                System.out.print(counter + " ");
            }
//            System.out.println();
        }
        return nextGen;
    }
}
