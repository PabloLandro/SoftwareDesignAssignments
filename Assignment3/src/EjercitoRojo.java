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
public class EjercitoRojo extends EjercitoBase {

    public EjercitoRojo() {
        super();
    }
    

    @Override
    //Si el ejército atacante es rojo/violeta, se suma una unidad al dado de menor valor
    ArrayList<Integer> ataque(ArrayList<Integer> dadoAtaque) {
        if (dadoAtaque.size() > 1) {
            int i = dadoAtaque.get(minimoDado(dadoAtaque)).intValue() + 1;
            Integer suma = new Integer(i);
            dadoAtaque.set(minimoDado(dadoAtaque), suma);

        }

        return dadoAtaque;
    }

}
