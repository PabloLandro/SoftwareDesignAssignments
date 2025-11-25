/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.ArrayList;

/**
 *
 * @author landr
 */
public class EjercitoCyan extends EjercitoBase {

    public EjercitoCyan() {
        super();
    }

    //@Override
    //Si el ejército atacante es amarillo/cyan, se suman 2 unidades si se tira un único dado
    ArrayList<Integer> ataque(ArrayList<Integer> dadoAtaque) {
        if (dadoAtaque.size() == 1) {
            int i = dadoAtaque.get(0).intValue() + 2;
            Integer suma = new Integer(i);
            dadoAtaque.set(0, suma);
           
        }
        return dadoAtaque;

    }

}
