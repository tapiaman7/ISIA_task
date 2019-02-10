package tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import matrices.DimensionesIncompatibles;
import matrices.InversionIneficiente;
import matrices.MatrizNoInvertible;
import matrices.Matriz;

public class Matrices {

    public static void main(String[] args) {
        Matriz m1 = new Matriz(3, 2, true);
        System.out.println(m1);
        Matriz m2 = new Matriz(2, 3, true);
        System.out.println(m2);
        try {
            System.out.println(Matriz.multiplicarDosMatrices(m1, m2));
        } catch (DimensionesIncompatibles ex) {
            ex.printStackTrace();
        }

        Matriz m3 = new Matriz(2, 2, true);
        System.out.println(m3);
        try {
            System.out.println(Matriz.invertirMatriz(m3));
        } catch (MatrizNoInvertible ex) {
            ex.printStackTrace();
        } catch (InversionIneficiente ex) {
            ex.printStackTrace();
        }
    }
    
}
