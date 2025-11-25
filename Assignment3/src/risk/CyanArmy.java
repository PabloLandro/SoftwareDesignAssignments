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
public class CyanArmy extends BaseArmy {

    public CyanArmy() {
        super();
    }

    //@Override
    //Si el ejército atacante es amarillo/cyan, se suman 2 unidades si se tira un único dice
    ArrayList<Integer> attack(ArrayList<Integer> attackDice) {
        if (attackDice.size() == 1) {
            int i = attackDice.get(0).intValue() + 2;
            Integer sum = new Integer(i);
            attackDice.set(0, sum);
           
        }
        return attackDice;

    }

}
