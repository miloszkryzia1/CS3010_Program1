import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args) throws IOException{
        double[][] coeffMatrix;
        double[] bValues;
        double[] s;
        double[] sRatios;
        int[] l;
        ArrayList<Integer> lList = new ArrayList<>();
        double[] multipliers;
        int numEqns;
        ArrayList<Integer> usedPivots = new ArrayList<>();
        double[] solutions;

        //get input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of equations to solve: ");
        numEqns = scanner.nextInt();
        coeffMatrix = new double[numEqns][numEqns];
        bValues = new double[numEqns];
        s = new double[numEqns];
        solutions = new double[numEqns];
        l = new int[numEqns];
        System.out.println("Choose input method:\n1) From console\n2) From file");
        int option = scanner.nextInt();
        scanner.nextLine();
        switch (option){
            case 1:
                System.out.println("Enter the coefficients and b value for each row: ");
                for (int i = 0; i < numEqns; i++){
                    String row = scanner.nextLine();
                    Scanner rowScanner = new Scanner(row);
                    for (int j = 0; j < numEqns; j++){
                        coeffMatrix[i][j] = rowScanner.nextDouble();
                    }
                    bValues[i] = rowScanner.nextDouble();
                }
                System.out.println();
                break;
            case 2:
                System.out.println("Enter file name or path: ");
                File inputFile = new File(scanner.next());
                Scanner fileScanner = new Scanner(inputFile);
                for (int i = 0; i < numEqns; i++){
                    String row = fileScanner.nextLine();
                    Scanner rowScanner = new Scanner(row);
                    for (int j = 0; j < numEqns; j++){
                        coeffMatrix[i][j] = rowScanner.nextDouble();
                    }
                    bValues[i] = rowScanner.nextDouble();
                }
                break;
            default:
                System.out.println("Invalid option!");
                break;
        }
        System.out.println("Initial coefficient matrix and b values:");
        outputMatrix(coeffMatrix, bValues);

        //get scale factor
        for (int i = 0; i < coeffMatrix.length; i++){
            double max = Math.abs(coeffMatrix[i][0]);
            for (int j = 0; j < coeffMatrix[i].length; j++){
                if (Math.abs(coeffMatrix[i][j]) > max){
                    max = Math.abs(coeffMatrix[i][j]);
                }
            }
            s[i] = max;
        }
        //output scale factor
            System.out.println("Scale factor: ");
            for (double fact : s){
                System.out.print(fact + " ");   
            }
            System.out.println();
            System.out.println();

        //perform Gaussian Elimination with Scaled partial pivoting
        for (int k = 0; k < numEqns-1; k++){
            System.out.println("=========================");
            //output step number
            System.out.println("Step " + (k+1) + "\n");

            //get scale ratios
            sRatios = new double[numEqns - k];

            for (int i = 0, j = 0; i < s.length; i++){
                if (!usedPivots.contains(i)){
                    sRatios[j] = Math.abs(coeffMatrix[i][k]) / s[i];
                    j++; 
                }
            }
            //for tests - output scale ratios
            System.out.print("Scale ratios: ");
            for (double r : sRatios){
             System.out.print(r + " ");   
            }
            System.out.println();

            //Choose pivot row
            int[] tempIndexes = new int[numEqns - k];
            int n = 0;
            for (int i = 0; i < numEqns; i++){
                if (!usedPivots.contains(i)){
                    tempIndexes[n] = i;
                    n++;
                }
            }
            int pivotIndex = 0;
            for (int i = 0; i < sRatios.length; i++){
                if (sRatios[i] > sRatios[pivotIndex])
                {
                    pivotIndex = i;
                }
            }
            int pivot = tempIndexes[pivotIndex];
            usedPivots.add(pivot);

            //output pivot row
            System.out.println("Pivot row: " + (pivot + 1));

            //Compute multipliers
            multipliers = new double[numEqns - usedPivots.size()];
            for (int i = 0, j = 0; i < numEqns; i++){
                if ((i != pivot) && (!usedPivots.contains(i))){
                    multipliers[j] = coeffMatrix[i][k] / coeffMatrix[pivot][k];
                    j++;
                }
            }
            //output multipliers
            System.out.print("Multipliers: ");
            for (double m : multipliers){
                System.out.print(m + " ");   
            }
            System.out.println();
            System.out.println();

            //add to l vector
            lList.add(pivot);

            //compute new matrix
            for (int i = 0, m = 0; i < coeffMatrix.length; i++){
                if (!usedPivots.contains(i)){
                    for (int j = k; j < numEqns; j++){
                        coeffMatrix[i][j] -= coeffMatrix[pivot][j] * multipliers[m];
                    }
                    bValues[i] -= bValues[pivot] * multipliers[m];
                    m++;
                }
            }

            //output matrix
            System.out.println("Resulting coefficient matrix and b values:");
            outputMatrix(coeffMatrix, bValues);
            System.out.println("=========================\n");
        }

        //Fill L array
        for (int i = 0; i < numEqns; i++){
            if (!lList.contains(i)){
                lList.add(i);
            }
        }
        for (int i = 0; i < lList.size(); i++){
            l[i] = lList.get(i);
        }
        System.out.print("Final L vector: ");
        for (int index : l){
            System.out.print(index + 1 + " ");
        }
        System.out.println();
        System.out.println();

        //Solve - back substitution
        int i = numEqns - 1;
        while (i != -1){
            int rowIndex = l[i];
            double[] currentRow = coeffMatrix[rowIndex];
            double sumCoeffs = 0;
            for (int j = 0; j < numEqns; j++){
                if (j != i){
                    sumCoeffs += currentRow[j];
                }
            }
            double b = bValues[rowIndex];
            b -= sumCoeffs;
            b /= currentRow[i];
            solutions[i] = b;
            if (i > 0){
                for (int k = 0; k < coeffMatrix.length; k++){
                    if (k != rowIndex){
                        coeffMatrix[k][i] *= b;
                    }
                }
            }
            i--;
        }

        //Output solution
        System.out.println("Solution: ");
        for (int j = 0; j < solutions.length; j++){
            System.out.println("x(" + (j+1) + ")" + String.format(" = %.2f", solutions[j]));
        }
    }

    //Ouput current coefficient matrix
    private static void outputMatrix(double[][] matrix, double[] b){
        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.print("B = [ ");
        for (int i = 0; i < b.length; i++){
            System.out.print(b[i] + " ");
        }
        System.out.println("]");
        System.out.println();
    }
}