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
public class BlueArmy extends BaseArmy {

    public BlueArmy() {
        super();
    }
    

    
    //Si el ejército atacante es azul/verde, se sum una unidad al dice de mayor valor
    @Override
    ArrayList<Integer> attack(ArrayList<Integer> attackDice) {
        //Si se tira más de un dice
        if (attackDice.size() > 1) {
            int i = attackDice.get(maxDice(attackDice)).intValue() + 1;
            Integer sum = new Integer(i);
            attackDice.set(maxDice(attackDice), sum);
           
        }
        return attackDice;

    }

}
