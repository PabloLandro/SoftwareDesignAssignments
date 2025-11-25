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
public class NormalConsole implements Console{

    Scanner scanner;
    
    public NormalConsole(){
        scanner = new Scanner(System.in);
    }
    
    @Override
    public void Print(String message) {
        System.out.println(message);
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }

}
