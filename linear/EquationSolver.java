package linear;

import java.util.Arrays;
import linear.algebra.GaussianElimination;

public class EquationSolver 
{
    public static void main(String[] args) 
    {
        
        double[][] numbers = stringsToDoubles(args);
        GaussianElimination solver = new GaussianElimination(numbers.length, numbers[0].length, numbers);

        System.out.println("Original equation:");
        solver.print();

        System.out.println("Row echelon form:");
        solver.rowEchelonForm();
        solver.print();

        System.out.println("Solution:");
        solver.backSubstitution();
        solver.print();

    }

    public static double[][] stringsToDoubles(String[] args) 
    {
        double[][] result = new double[args.length][];
        for (int i = 0; i < args.length; i++) 
        {
            String[] split = args[i].split(",");
            result[i] = new double[split.length];

            for (int j = 0; j < split.length; j++)
                result[i][j] = Double.parseDouble(split[j]);
        }
        return result;
    }

}

