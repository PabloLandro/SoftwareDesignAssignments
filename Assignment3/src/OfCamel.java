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
public class OfCamel extends Cavalry{
    
    public OfCamel(Country country, Player player){
        super(country, "OfCamel", player);
    }

    @Override
    int getRearm() {
        return 2;
    }
    
}
