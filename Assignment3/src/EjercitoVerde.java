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
public class EjercitoVerde extends EjercitoCompuesto{
    
    public EjercitoVerde(){
        super();
    }
    
    @Override
    //Si el ejército atacante es verde/azul, se suma una unidad al dado de mayor valor
    ArrayList<Integer> ataque(ArrayList<Integer> dadoAtaque) {
        //Si se tira más de un dado
        if (dadoAtaque.size() > 1) {
            int i = dadoAtaque.get(maximoDado(dadoAtaque)).intValue() + 1;
            Integer suma = new Integer(i);
            dadoAtaque.set(maximoDado(dadoAtaque), suma);
           
        }
        return dadoAtaque;

    }
    
}
