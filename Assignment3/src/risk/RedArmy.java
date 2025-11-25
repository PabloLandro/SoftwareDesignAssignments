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
public class RedArmy extends BaseArmy {

    public RedArmy() {
        super();
    }
    

    @Override
    //Si el ejército atacante es rojo/violeta, se sum una unidad al dice de menor valor
    ArrayList<Integer> attack(ArrayList<Integer> attackDice) {
        if (attackDice.size() > 1) {
            int i = attackDice.get(minDice(attackDice)).intValue() + 1;
            Integer sum = new Integer(i);
            attackDice.set(minDice(attackDice), sum);

        }

        return attackDice;
    }

}
