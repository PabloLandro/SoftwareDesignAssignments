/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author landr
 */
public class Continent {
    String abbreviation;
    String name;
    String color;
    ArrayList<Country> countries = new ArrayList();
    
    public Continent(String name, String color, String abbreviation)
    {       
        this.color = color;
        this.name = name;
        this.abbreviation = abbreviation;
    }
    
    @Override
    public String toString()
    {
        String out = new String();
        ArrayList <Player> players = new ArrayList<>();
        boolean last;
        int armies;
        out = "{\nnombre: \"" + name + "\",\n";
        out += "abbreviation: \"" + abbreviation + "\",\n";
        out += "players: [ ";
        for(int i = 0; i < countries.size(); i++){
            Player player = countries.get(i).getPlayer();
            if(!players.contains(player))
                players.add(player);
        }
        for(int i = 0; i < players.size(); i++){
            armies = 0;
            last = true;
            for(Country country: players.get(i).getCountries()){
                if(country.getContinent().equals(this)){
                    armies += country.getArmyNumber();
                }
            }
            out += "{ \"" + players.get(i).getName() + "\", " + armies + " }";
            for(int j = i+1; j < players.size(); j++){
                for(Country country: players.get(i).getCountries()){
                    if(country.getContinent().equals(this) && (country.getArmyNumber() != 0)){
                        last = false;
                    }
                }
            }
            if(!last){
                out += ", ";
            }
        }
        out += " ],\n";
        out += "armyNumber: " + this.armyNumber() + ",\n";
        out += "rearmContinent: " + this.rearmContinent() + "\n";
        out += "}";
        return out;
    }
    
    @Override
    public boolean equals(Object comparing) {
        Continent aux = (Continent) comparing;
        return (this.getName().equals(aux.getName()));
    }
    
    //Los getters de continent
    public String getName()
    {
        return name;
    }
    public String getColor()
    {
        return color;
    }
    public ArrayList<Country> getCountries()
    {
        return countries;
    }
    public String getAbreviatura()
    {
        return abbreviation;
    }
    
    
    //Los setters de continent
    public void setColor(String color)
    {
        this.color = color;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public void setCountries(ArrayList countries)
    {
        this.countries = countries;
    }
    
    
    int armyNumber()
    {
        int num = 0;
        for(Country country: countries)
            num += country.getArmyNumber();
        return num;
    }
    
    public int rearmContinent(){
        switch(abbreviation){
            case "Asia":
                return 12;
                
            case "África":
                return 6;
                
            case "AméricaNorte":
                return 9;
                
            case "AméricaSur": case "Oceanía":
                return 4;
                
            case "Europa":
                return 7;
                
            default:
                return 0;
        }
    }
    
    public boolean isCountryInContinent(Country country)
    {
        return countries.contains(country);
    }
    
    public ArrayList<Country> borders()
    {
        ArrayList<Country> borders = new ArrayList<>();
        for(Country country: countries)
            for(Country border: country.getBorders())
                if(!borders.contains(border) && !countries.contains(border))
                    borders.add(border);
        return borders;
    }
}
