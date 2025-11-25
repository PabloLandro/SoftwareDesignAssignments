/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.Scanner;

/**
 *
 * @author landr
 */
public class ConsolaNormal implements Consola{

    Scanner scanner;
    
    public ConsolaNormal(){
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void Imprimir(String mensaje) {
        System.out.println(mensaje);
    }

    @Override
    public String leer() {
        return scanner.nextLine();
    }

}
