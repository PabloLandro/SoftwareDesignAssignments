/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

/**
 *
 * @author landr
 */
public class OfHorse extends Cavalry{
    
    public OfHorse(Country country, Player player){
        super(country, "OfHorse", player);
    }

    @Override
    int getRearm() {
        return 3;
    }
    
}
