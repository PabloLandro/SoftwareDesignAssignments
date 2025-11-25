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
public class Rifleman extends Infantry{
    
    public Rifleman(Country country, Player player){
        super(country, "Rifleman", player);
    }

    @Override
    int getRearm() {
        return 2;
    }    
}
