package Numeric_Matrix_Processor;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.performer();
    }
}

class Menu {

    MatrixReader matrixReader = new MatrixReader();

    Scanner scanner = new Scanner(System.in);

    private boolean isOn = false;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    private void printMenu() {
        System.out.println("1. Add matrices");
        System.out.println("2. Multiply matrix by a constant");
        System.out.println("3. Multiply matrices");
        System.out.println("4. Transpose matrix");
        System.out.println("5. Calculate a determinant");
        System.out.println("6. Inverse matrix");
        System.out.println("0. Exit");
        System.out.println("Your choice");
    }

    private void printTransposeMenu() {
        System.out.println("1. Main diagonal");
        System.out.println("2. Side diagonal");
        System.out.println("3. Vertical line");
        System.out.println("4. Horizontal line");
        System.out.println("Your choice");
    }

    private int chooseScanner() {
        String[] chooseString = scanner.nextLine().trim().split("\\s++");
        int choose = Integer.parseInt(chooseString[0]);
        return choose;
    }

    private void transposeBehaviorChooser(int choose, Matrix matrix) {
        double[][] transposeMode = new double[1][1];
        transposeMode[0][0] = choose;
        matrix.setSecondMatrix(transposeMode);
    }

    private void behaviorChooser(int choose, Matrix matrix) {
        switch (choose) {
            case 1:
                matrix.setMathBehavior(new AddMatrix());
                break;
            case 2:
                matrix.setMathBehavior(new ScalarMultiplication());
                break;
            case 3:
                matrix.setMathBehavior(new MatrixMultiplication());
                break;
            case 4:
                matrix.setMathBehavior(new TransposeMatrix());
                printTransposeMenu();
                transposeBehaviorChooser(chooseScanner(), matrix);
                break;
            case 5:
                matrix.setMathBehavior(new MatrixDeterminant());
                break;
            case 6:
                matrix.setMathBehavior(new InverseMatrix());
                break;
            case 0:
                setOn(false);
                break;
            default:
                System.out.println("Incorrect value");
                break;
        }
    }

    private void matrixReaderPrinter(int choose, Matrix matrix) {
        switch (choose) {
            case 1:
            case 3:
                System.out.println("Enter size of first matrix:");
                matrixReader.volumeReader(scanner);
                System.out.println("Enter first matrix:");
                matrix.setFirstMatrix(matrixReader.readMatrix(scanner));
                System.out.println("Enter size of second matrix:");
                matrixReader.volumeReader(scanner);
                System.out.println("Enter second matrix:");
                matrix.setSecondMatrix(matrixReader.readMatrix(scanner));
                break;
            case 2:
                System.out.println("Enter size of matrix:");
                matrixReader.volumeReader(scanner);
                System.out.println("Enter matrix:");
                matrix.setFirstMatrix(matrixReader.readMatrix(scanner));
                System.out.println("Enter constant");
                matrixReader.volumeReader(scanner);
                matrix.setSecondMatrix(matrixReader.readNumber(scanner));
//                matrix.setSecondMatrix(matrixReader.readMatrix(scanner));

                break;
            case 4:
                System.out.println("Enter size of matrix:");
                matrixReader.volumeReader(scanner);
                System.out.println("Enter matrix:");
                matrix.setFirstMatrix(matrixReader.readMatrix(scanner));
                break;
            case 5:
            case 6:
                System.out.println("Enter size of matrix:");
                matrixReader.volumeReader(scanner);
                System.out.println("Enter matrix:");
                matrix.setFirstMatrix(matrixReader.readMatrix(scanner));
                matrix.setSecondMatrix(matrix.getFirstMatrix().clone());
                break;
//            case 6:
//                System.out.println("Enter size of matrix:");
//                matrixReader.volumeReader(scanner);
//                System.out.println("Enter matrix:");
//                matrix.setFirstMatrix(matrixReader.readMatrix(scanner));
//                matrix.setSecondMatrix(matrix.getFirstMatrix().clone());
//                break;
            case 0:
                setOn(false);
                break;
            default:
                System.out.println("Incorrect value");
                break;
        }
    }

    public void performer() {
        Matrix matrix = new Matrix();
        setOn(true);
        while (isOn) {
            printMenu();
            int choose = chooseScanner();
            behaviorChooser(choose, matrix);
            if (choose != 0) {
                matrixReaderPrinter(choose, matrix);
                matrix.mathOperation();
            }
        }
    }
}

class MatrixReader {

//    Scanner scanner = new Scanner(System.in);

    public double[][] getMatrix() {
        return matrix;
    }

    private double[][] matrix;
    private String[] matrixVolume;

    public String[] volumeReader(Scanner scanner) {
        matrixVolume = scanner.nextLine().trim().split("\\s++");
        return matrixVolume;
    }

    public double[][] readMatrix(Scanner scanner) {
        if (matrixVolume[0].equals("1")) {
            matrix = new double[1][1];
            String[] row = scanner.nextLine().trim().split("\\s++");
            matrix[0][0] = Double.parseDouble(row[0]);
            return matrix;
        }
        int numMatrixRows = Integer.parseInt(matrixVolume[0]);
        int numMatrixColumns = Integer.parseInt(matrixVolume[1]);
        matrix = new double[numMatrixRows][numMatrixColumns];
        for (int i = 0; i < matrix.length; i++) {
            String[] row = scanner.nextLine().trim().split("\\s++");
            for (int j = 0; j < row.length; j++) {
//                matrix[i][j] = Float.parseFloat(row[j]);
                matrix[i][j] = Double.parseDouble(row[j]);
            }
        }
        return matrix;
    }

    public double[][] readNumber(Scanner scanner) {
        matrix = new double[1][1];
        matrix[0][0] = Double.parseDouble(matrixVolume[0]);
        return matrix;
    }
}

class Matrix {

    MathBehavior mathBehavior;
    MatrixReader matrixReader = new MatrixReader();
    double[][] firstMatrix;
    double[][] secondMatrix;
    double[][] finalMatrix;

    public double[][] getFirstMatrix() {
        return firstMatrix;
    }

    public double[][] getSecondMatrix() {
        return secondMatrix;
    }

    public double[][] getFinalMatrix() {
        return finalMatrix;
    }

    public void setFinalMatrix(double[][] finalMatrix) {
        this.finalMatrix = finalMatrix;
    }

    public void setFirstMatrix(double[][] firstMatrix) {
        this.firstMatrix = firstMatrix;
    }

    public void setSecondMatrix(double[][] secondMatrix) {
        this.secondMatrix = secondMatrix;
    }

    public void setMathBehavior(MathBehavior mathBehavior) {
        this.mathBehavior = mathBehavior;
    }

    Matrix() {
        setMathBehavior(new MatrixMultiplication());
    }

    public void mathOperation() {
        if (mathBehavior.performAbility(firstMatrix, secondMatrix)) {
            finalMatrix = mathBehavior.performMathOperation(firstMatrix, secondMatrix);
            printFinalMatrix();
        } else {
            System.out.println("The operation cannot be performed.");
        }
    }

    public void printFinalMatrix() {
//        System.out.println(Arrays.deepToString(finalMatrix));

        for (int i = 0; i < finalMatrix.length; i++) {
            for (int j = 0; j < finalMatrix[i].length; j++) {
//                System.out.print(finalMatrix[i][j] + " ");
                System.out.printf("%8.2f ", finalMatrix[i][j]);
            }
            System.out.println();
        }
    }
}

class MatrixSlicer {
    public static double[][] matrixSlicer(int numOfColumn, int numOfRow, double[][] matrix) {

        double[][] slicedMatrix = new double[matrix.length - 1][matrix[0].length - 1];

        for (int i = 0; i < matrix.length - 1; i++) {
            if (numOfRow == 0) {
                slicedMatrix[i] = rowCopier(i + 1, numOfColumn, matrix);
            } else if (numOfRow == matrix.length) {
                slicedMatrix[i] = rowCopier(i, numOfColumn, matrix);
            } else if (i < numOfRow) {
                slicedMatrix[i] = rowCopier(i, numOfColumn, matrix);
            } else if (i >= numOfRow) {
                slicedMatrix[i] = rowCopier(i + 1, numOfColumn, matrix);
            }
        }
//        System.out.println(Arrays.deepToString(slicedMatrix));
        return slicedMatrix;
    }

    private static double[] rowCopier(int numOfRow, int numOfColumn, double[][] matrix) {

        double[] slicedRow = new double[matrix.length - 1];

        if (numOfColumn == 0) {
            if (matrix[0].length - 1 >= 0)
                System.arraycopy(matrix[numOfRow], 1, slicedRow, 0, matrix[0].length - 1);

        } else if (numOfColumn == matrix.length) {
            if (matrix[0].length - 1 >= 0)
                System.arraycopy(matrix[numOfRow], 0, slicedRow, 0, matrix[0].length - 2);
        } else {
            if (matrix[0].length - 1 >= 0) {
                System.arraycopy(matrix[numOfRow], 0, slicedRow, 0, numOfColumn);
                System.arraycopy(matrix[numOfRow], numOfColumn + 1, slicedRow, numOfColumn, matrix[0].length - numOfColumn - 1);
            }

        }

        return slicedRow;
    }
}

interface MathBehavior {

    boolean performAbility(double[][] firstMatrix, double[][] secondMatrix);

    double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix);
}

class AddMatrix implements MathBehavior {

    double[][] resultMatrix;

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix.length != secondMatrix.length || firstMatrix[0].length != secondMatrix[0].length) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix) {
        resultMatrix = firstMatrix.clone();
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = firstMatrix[i][j] + secondMatrix[i][j];
            }
        }
        return resultMatrix;
    }
}

class ScalarMultiplication implements MathBehavior {

    double[][] resultMatrix;

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        return true;
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix) {
        resultMatrix = firstMatrix.clone();
        for (int i = 0; i < firstMatrix.length; i++) {
            for (int j = 0; j < firstMatrix[i].length; j++) {
                resultMatrix[i][j] = firstMatrix[i][j] * secondMatrix[0][0];
            }
        }
        return resultMatrix;
    }

}

class MatrixMultiplication implements MathBehavior {

    double[][] resultMatrix;

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        if (firstMatrix[0].length == secondMatrix.length) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix) {
        resultMatrix = new double[firstMatrix.length][secondMatrix[0].length];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[i].length; j++) {
                for (int k = 0; k < firstMatrix[0].length; k++) {
                    resultMatrix[i][j] += firstMatrix[i][k] * secondMatrix[k][j];
                }
            }
        }
        return resultMatrix;
    }
}

class TransposeMatrix implements MathBehavior {

    double[][] resultMatrix;
    TransposeBehavior transposeBehavior;


    public void setTransposeBehavior(TransposeBehavior transposeBehavior) {
        this.transposeBehavior = transposeBehavior;
    }

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        return true;
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] transposeBehaviorNumber) {
        transposeBehaviorChooser(transposeBehaviorNumber);
        resultMatrix = transposeBehavior.performTransposeOperation(firstMatrix);
        return resultMatrix;
    }

    private void transposeBehaviorChooser(double[][] transposeBehaviorNumber) {
        switch ((int) transposeBehaviorNumber[0][0]) {
            case 1:
                setTransposeBehavior(new mainDiagonalTranspose());
                break;
            case 2:
                setTransposeBehavior(new sideDiagonalTranspose());
                break;
            case 3:
                setTransposeBehavior(new verticalLineTranspose());
                break;
            case 4:
                setTransposeBehavior(new horizontalLineTranspose());
                break;
            default:
                System.out.println("Incorrect value");
                break;
        }
    }
}

class MatrixDeterminant implements MathBehavior {

    double[][] resultMatrix;

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        return firstMatrix.length == firstMatrix[0].length;
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix) {

//        return MatrixSlicer.matrixSlicer(2, 1, firstMatrix);

        return determinantWrapper(firstMatrix);
    }

    private double[][] determinantWrapper(double[][] matrix) {

        double[][] determinant = new double[1][1];

        determinant[0][0] = determinantCalculator(matrix);

        return determinant;

    }

//    private double[][] matrixSlicer(int numOfColumn, double[][] matrix) {
//
//        double[][] slicedMatrix = new double[matrix.length - 1][matrix[0].length - 1];
//
//        for (int i = 0; i < matrix.length - 1; i++) {
//            if (matrix[0].length - 1 >= 0) {
//                if (numOfColumn == 0) {
//                    System.arraycopy(matrix[i + 1], 1, slicedMatrix[i], 0, matrix[0].length - 1);
//                } else if (numOfColumn == matrix.length) {
//                    System.arraycopy(matrix[i + 1], 0, slicedMatrix[i], 0, matrix[0].length - 2);
//                } else {
//                    System.arraycopy(matrix[i + 1], 0, slicedMatrix[i], 0, numOfColumn);
//                    System.arraycopy(matrix[i + 1], numOfColumn + 1, slicedMatrix[i], numOfColumn, matrix[0].length - numOfColumn - 1);
//                }
//
//            }
//
//        }
//
//
//        return slicedMatrix;
//    }


    public double determinantCalculator(double[][] matrix) {

        double determinant = 0;

        if (matrix.length == 1) {
            return matrix[0][0];
        }

        if (matrix.length == 2) {
            return matrix[1][1] * matrix[0][0] - matrix[0][1] * matrix[1][0];
        }

        for (int i = 0; i < matrix[0].length; i++) {
            determinant += Math.pow(-1, 2 + i) * matrix[0][i] * determinantCalculator(MatrixSlicer.matrixSlicer(i, 0, matrix));
        }
        return determinant;
    }
}

class CofactorMatrix implements MathBehavior {

    MathBehavior determinantBehavior = new MatrixDeterminant();

    double[][] resultMatrix;

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        return firstMatrix.length == firstMatrix[0].length;
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix) {
        return resultMatrix = cofactorMatrixCalculator(firstMatrix);
    }


    private double[][] cofactorMatrixCalculator(double[][] matrix) {

        double[][] cofactorMatrix = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                cofactorMatrix[i][j] =  Math.pow(-1, i + j) * determinantBehavior.performMathOperation(MatrixSlicer.matrixSlicer(j, i, matrix), matrix)[0][0];
            }
        }
//        System.out.println(Arrays.deepToString(cofactorMatrix));
        return cofactorMatrix;
    }
}

class InverseMatrix implements MathBehavior {

    double[][] resultMatrix;

    MathBehavior cofactorMatrix = new CofactorMatrix();
    MathBehavior determinantBehavior = new MatrixDeterminant();
    MathBehavior transposeMatrix = new TransposeMatrix();
    MathBehavior scalarMultiplication = new ScalarMultiplication();
    MathBehavior matrixMultiplication = new MatrixMultiplication();
    double[][] det;

    @Override
    public boolean performAbility(double[][] firstMatrix, double[][] secondMatrix) {
        det = determinantBehavior.performMathOperation(firstMatrix, secondMatrix);
        return firstMatrix.length == firstMatrix[0].length || det[0][0] != 0;
    }

    @Override
    public double[][] performMathOperation(double[][] firstMatrix, double[][] secondMatrix) {

        det[0][0] = 1 / det[0][0];
        resultMatrix = cofactorMatrix.performMathOperation(firstMatrix, secondMatrix);
        resultMatrix = transposeMatrix.performMathOperation(resultMatrix, new double[][] {new double[] {1}});
        resultMatrix = scalarMultiplication.performMathOperation(resultMatrix, det);
//        System.out.println(Arrays.deepToString(resultMatrix));
//        resultMatrix = matrixMultiplication.performMathOperation(firstMatrix, resultMatrix);
        return resultMatrix;
    }
}

interface TransposeBehavior {
    double[][] performTransposeOperation(double[][] matrix);
}

class mainDiagonalTranspose implements TransposeBehavior {

    double[][] resultMatrix;

    @Override
    public double[][] performTransposeOperation(double[][] matrix) {
        resultMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[i].length; j++) {
                resultMatrix[i][j] = matrix[j][i];
            }
        }
        return resultMatrix;
    }
}

class sideDiagonalTranspose implements TransposeBehavior {

    double[][] resultMatrix;

    @Override
    public double[][] performTransposeOperation(double[][] matrix) {
        resultMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[i].length; j++) {
                resultMatrix[i][j] = matrix[matrix.length - 1 - j][matrix[j].length - 1 - i];
            }
        }
        return resultMatrix;
    }
}

class verticalLineTranspose implements TransposeBehavior {

    double[][] resultMatrix;

    @Override
    public double[][] performTransposeOperation(double[][] matrix) {
        resultMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[i].length; j++) {
                resultMatrix[i][j] = matrix[i][matrix[j].length - 1 - j];
            }
        }
        return resultMatrix;
    }
}

class horizontalLineTranspose implements TransposeBehavior {

    double[][] resultMatrix;

    @Override
    public double[][] performTransposeOperation(double[][] matrix) {
        resultMatrix = new double[matrix[0].length][matrix.length];
        for (int i = 0; i < resultMatrix.length; i++) {
            for (int j = 0; j < resultMatrix[i].length; j++) {
                resultMatrix[i][j] = matrix[matrix.length - 1 - i][j];
            }
        }
        return resultMatrix;
    }
}
