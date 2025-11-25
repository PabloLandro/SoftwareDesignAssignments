/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
/**
 *
 * @author landr
 */
public class Impresora {
    
    private static final String nombreArchivo = "resultados.txt";
    private static PrintWriter ficheroSalida = null;
    
    public static void inicializarImpresora()
    {
        BufferedWriter bufferEscritura= null;
        try {
            bufferEscritura= new BufferedWriter(new FileWriter("resultados.txt"));
            ficheroSalida= new PrintWriter(bufferEscritura);
        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
    
    public static void imprimir(String out)
    {
        ficheroSalida.println(out);
    }
    
    public static void finArchivo()
    {
        ficheroSalida.println("EOF");
        ficheroSalida.close();
    }
    
}
