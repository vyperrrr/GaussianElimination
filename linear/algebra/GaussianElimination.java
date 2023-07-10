package linear.algebra;

import java.util.Arrays;

public class GaussianElimination 
{

    private int rows;
    private int cols;
    private double[][] matrix;

    public GaussianElimination(int rows, int cols, double[][] matrix) 
    {
        this.rows = rows;
        this.cols = cols;
        this.matrix = new double[rows][cols];
        for (int i = 0; i < rows; i++)
            this.matrix[i] = Arrays.copyOf(matrix[i], cols);
    }

    public int getRows() 
    {
        return rows;
    }

    public int getCols() 
    {
        return cols;
    }

    public double[][] getMatrix() 
    {
        return matrix;
    }

    public void setMatrix(double[][] newMatrix) 
    {
        checkMatrixDimensions(newMatrix);
        for (int i = 0; i < rows; i++)
            matrix[i] = Arrays.copyOf(newMatrix[i], cols);
    }

    private void checkMatrixDimensions(double [][] matrix) 
    {
        if (matrix.length != rows || matrix[0].length != cols)
            throw new IllegalArgumentException("Invalid matrix dimensions.");
    }

    public void rowEchelonForm() 
    {
        int h = 0;
        int k = 0;
        while (h < rows && k < cols) {
            int i_max = argMax(h, k);
            if (matrix[i_max][k] == 0)
                k++;
            else 
            {
                swapRows(h, i_max);
                for (int i = h + 1; i < rows; i++)
                    multiplyAndAddRow(i, h, k);
                multiplyRow(h, k);
                h++;
                k++;
            }
        }
    }

    private int argMax(int startRow, int col) 
    {
        int maxRow = startRow;
        for (int i = startRow + 1; i < rows; i++)
            if (Math.abs(matrix[i][col]) > Math.abs(matrix[maxRow][col]))
                maxRow = i;
        return maxRow;
    }

    private void swapRows(int row1, int row2) 
    {
        double[] temp = matrix[row1];
        matrix[row1] = matrix[row2];
        matrix[row2] = temp;
    }

    private void multiplyAndAddRow(int addRow, int mulRow, int colIndex) 
    {
        double factor = matrix[addRow][colIndex] / matrix[mulRow][colIndex];
        matrix[addRow][colIndex] = 0;
        for (int j = colIndex + 1; j < cols; j++)
            matrix[addRow][j] = matrix[addRow][j] - (factor * matrix[mulRow][j]);
    }

    private void multiplyRow(int row, int col) 
    {
        double factor = matrix[row][col];
        for (int j = 0; j < cols; j++)
            matrix[row][j] /= factor;
    }

    public void backSubstitution() 
    {
        for (int i = rows - 1; i >= 0; i--) 
        {
            int j = cols - 1 - (rows - i);

            if (matrix[i][j] == 0)
                throw new IllegalArgumentException("No solution exists.");

            divideRow(i, j);

            for (int k = i - 1; k >= 0; k--)
                multiplyAndAddRow(k, i, j);
        }
    }

    private void divideRow(int rowIndex, int colIndex) 
    {
        matrix[rowIndex][colIndex] = 1;
        matrix[rowIndex][cols - 1] /= matrix[rowIndex][colIndex];
    }

    public void print() 
    {
        String[] variables = {"x", "y", "z", "e", "f", "g", "h", "i", "j", "k"};
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                if (j == cols - 1) {
                    System.out.print("=" + prettyPrint(matrix[i][j]));
                } else 
                {
                    System.out.print(prettyPrint(matrix[i][j]) + variables[j]);
                    if (j < cols - 2 && matrix[i][j + 1] >= 0) 
                    {
                        System.out.print("+");
                    }
                }
            }
            System.out.println();
        }
    }

    public static String prettyPrint(double d) 
    {
        int i = (int) d;
        return d == i ? (String.valueOf(i)+".0") : String.valueOf(d);
    }

}
