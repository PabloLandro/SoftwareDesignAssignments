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
public class Printer {
    
    private static final String fileName = "resultados.txt";
    private static PrintWriter outputFile = null;
    
    public static void initPrinter()
    {
        BufferedWriter writeBuffer= null;
        try {
            writeBuffer= new BufferedWriter(new FileWriter("resultados.txt"));
            outputFile= new PrintWriter(writeBuffer);
        } catch(IOException exc) {
            System.out.println(exc.getMessage());
        }
    }
    
    public static void print(String out)
    {
        outputFile.println(out);
    }
    
    public static void endFile()
    {
        outputFile.println("EOF");
        outputFile.close();
    }
    
}
