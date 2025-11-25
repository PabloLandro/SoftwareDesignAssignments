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
public class Grenadier extends Infantry{
    
    public Grenadier(Country country, Player player){
        super(country, "Grenadier", player);
    }
    
    @Override
    int getRearm(){
        return 1;
    }
    
}
