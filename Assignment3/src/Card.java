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
public abstract class Card {
    
    Country country;
    Player player;
    String type;

    Card(Country country, String type, Player player){
        
        this.country = country;
        this.type = type;
        this.player = player;
    }
    

    abstract int getPower();
    
    public String getName(){
        return new String(type + "&" + country.getAbreviatura());
    }
    public String getType(){
        return type;
    }
    public Country getCountry(){
        return country;
    }
    @Override
    public String toString()
    {
        String out = new String();
        out = "{\n";
        out = out + "typeCard: \"" + type + "\"\n";
        out = out + "countryAsociado: \"" + country.getName() + "\"\n";
        out = out + "perteneceAPlayer: \"" + player.getName() + "\"\n";
        if(player.getCountries().contains(country))
            out = out + "armysDeRearme: " + (getPower()+1) + "\n}";
        else
            out = out + "armysDeRearme: " + getPower() + "\n}";
        return out;
    }
    
}
