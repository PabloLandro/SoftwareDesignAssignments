/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author Manuel Lama
 */
public class Risk {

    static NormalConsole console;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        console = new NormalConsole();
        new Menu();
    }
}
