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
public class Antiair extends Artillery{
    
    public Antiair(Country country, Player player){
        super(country, "Antiair", player);
    }

    @Override
    int getRearm() {
        return 3;
    }
 
}
