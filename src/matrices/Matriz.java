/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package matrices;

import java.awt.Dimension;
import java.util.Random;

/**
 *
 * @author galvez
 */
public class Matriz {
    private double[][]datos;
    private Random rnd = new Random();
    
    public Matriz(int filas, int columnas, boolean inicializarAleatorio){
        datos = new double[columnas][];
        for(int i=0; i<columnas; i++){
            datos[i] = new double[filas];
            if (inicializarAleatorio)
                for(int j=0; j<filas; j++)
                    datos[i][j] = rnd.nextInt(100);
        }
    }
    public Matriz(Dimension d, boolean inicializarAleatorio){
        this(d.height, d.width, inicializarAleatorio);
    }
    
    public Dimension getDimension(){
        return new Dimension(datos.length, datos[0].length);
    }
    
    public static Matriz sumarDosMatrices(Matriz a, Matriz b) throws DimensionesIncompatibles { 
        if(! a.getDimension().equals(b.getDimension())) throw new DimensionesIncompatibles("La suma de matrices requiere matrices de las mismas dimensiones");        
        int i, j, filasA, columnasA; 
        filasA = a.getDimension().height; 
        columnasA = a.getDimension().width; 
        Matriz matrizResultante = new Matriz(filasA, columnasA, false);
        for (j = 0; j < filasA; j++) { 
            for (i = 0; i < columnasA; i++) { 
                matrizResultante.datos[i][j] += a.datos[i][j] + b.datos[i][j]; 
            } 
        } 
        return matrizResultante; 
    }

    public static Matriz invertirMatriz(Matriz a) throws MatrizNoInvertible, InversionIneficiente {
        if (a.getDimension().height != a.getDimension().width) {
            throw new MatrizNoInvertible("Una matriz que no es cuadrada no es inveertible.");
        } else if (a.getDimension().height > 3 || a.getDimension().width > 3) {
            throw new InversionIneficiente("Este cálculo es ineficiente para matrices de orden superior a 3.");
        }
        double det = 1/determinante(a);
        Matriz adjunta = matrizAdjunta(a);
        multiplicarMatrizPorEscalar(det , adjunta);
        return adjunta;
    }

    private static void multiplicarMatrizPorEscalar(double n, Matriz a) {
        for (int i=0; i<a.datos.length; i++) {
            for (int j=0; j<a.datos.length; j++) {
                a.datos[i][j] *= n;
            }
        }
    }

    private static Matriz matrizAdjunta(Matriz a){
        return matrizTranspuesta(matrizCofactores(a));
    }

    private static Matriz matrizCofactores(Matriz a){
        Matriz nm = new Matriz(a.datos.length, a.datos.length, false);
        for (int i = 0; i < a.datos.length; i++) {
            for (int j = 0; j < a.datos.length; j++) {
                Matriz det = new Matriz(a.datos.length-1, a.datos.length-1, false);
                double detValor;
                for (int k = 0; k < a.datos.length; k++) {
                    if (k != i) {
                        for (int l = 0; l < a.datos.length; l++) {
                            if (l != j) {
                                int indice1 = k < i ? k : k-1;
                                int indice2 = l < j ? l : l-1;
                                det.datos[indice1][indice2] = a.datos[k][l];
                            }
                        }
                    }
                }
                detValor = determinante(det);
                nm.datos[i][j] = detValor * (double)Math.pow(-1, i+j+2);
            }
        }
        return nm;
    }

    private static Matriz matrizTranspuesta(Matriz a){
        Matriz nuevam = new Matriz(a.datos[0].length, a.datos.length, false);
        for(int i = 0; i < a.datos.length; i++)
        {
            for(int j = 0; j < a.datos.length; j++) {
                nuevam.datos[i][j] = a.datos[j][i];
            }
        }
        return nuevam;
    }

    private static double determinante(Matriz a)
    {
        double det;
        if (a.datos.length == 1) {
        	return a.datos[0][0];
        } else if(a.datos.length == 2) {
            det=(a.datos[0][0]*a.datos[1][1])-(a.datos[1][0]*a.datos[0][1]);
            return det;
        }
        double suma=0;
        for(int i=0; i<a.datos.length; i++) {
            Matriz nm=new Matriz(a.datos.length-1, a.datos.length-1, false);
            for (int j=0; j<a.datos.length; j++) {
                if (j!=i) {
                    for (int k=1; k<a.datos.length; k++) {
                        int indice=-1;
                        if(j<i)
                            indice=j;
                        else if(j>i)
                            indice=j-1;
                        nm.datos[indice][k-1]=a.datos[j][k];
                    }
                }
            }
            if(i%2==0)
                suma+=a.datos[i][0] * determinante(nm);
            else
                suma-=a.datos[i][0] * determinante(nm);
        }
        return suma;
    }



    @Override
    public String toString(){
        String ret = "";
        ret += "[\n";
        for (int i = 0; i < getDimension().width; i++) {
            ret += "(";
            for (int j = 0; j < getDimension().height; j++) {  
                ret += String.format("%3f", datos[i][j]); 
                if (j != getDimension().height - 1) ret += ", ";
            } 
            ret += ")";
            if (i != getDimension().width - 1) ret += ",";
            ret += "\n";
        } 
        ret += "]\n";
        return ret;
    }
}
