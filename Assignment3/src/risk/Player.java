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
public class Player {
    
    String name;
    String color;
    ArrayList<Country> countries;
    ArrayList<Card> cards = new ArrayList<>();
    Mision mission;
    int armyNumber = 0;
    Army army;
    
    public Player(String name, String color)
    {
        this.name = name;
        this.color = color;
        this.countries = new ArrayList<>();
        this.mission = new Mision("", "0");
    }
    
    public String toString(boolean a)
    {
        String out = new String();
        String countryList = new String();
        String continentList = new String();
        String cardsList = new String();
        for(int i = 0; i < countries.size(); i++){
            countryList += "\"" + countries.get(i).getName() + "\"";
            if(i != countries.size()-1)
                countryList += ", ";
            else
                countryList += " ],\n";
        }
        for(int i = 0; i < getContinents().size(); i++){
            continentList += " \"" + getContinents().get(i).getName() + "\"";
            if(i != getContinents().size()-1)
                continentList += ",";
        }
        for(int i = 0; i < cards.size(); i++){
            cardsList += " \"" + cards.get(i).getName() + "\"";
            if(i != cards.size()-1)
                cardsList += ",";
        }
        out = "{\n";
        out += "name: \"" + name + "\",\n";
        out += "color: \"" + color + "\",\n";
        if(a)
            out += "mission: \"" + mission.getDescripcion() + "\",\n";
        out += "armyNumber: " + armyNumber + ",\n";
        out += "countries: [ " + countryList;
        out += "continents: [" + continentList + " ],\n";
        out += "cards: [" + cardsList + " ],\n";
        out += "numberArmiesRearmar: " + armyNumber + "\n";
        out += "}";
        return out;
    }
    
    @Override
    public boolean equals(Object o){
        Player player = (Player) o;
        return this.getName().equals(player.getName());
    }
    
    public void setName(String Name)
    {
        name = Name;
    }    
    public void setColor(String Color)
    {
        color = Color;
    }    
    public void setMision(Mision mission)
    {
        this.mission = mission;
    }    
    public void setArmyNumbers(int number)
    {
        this.armyNumber = number;
    }
    
    public void addArmyNumbers(int number)
    {
        this.armyNumber += number;
    }
    
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
    public int getArmyNumbers()
    {
        return armyNumber;
    }
    public Mision getMision(){
        return mission;
    }
    public ArrayList<Card> getCards()
    {
        return cards;
    }

    public Army getArmy() {
        return army;
    }

    public void setArmy(Army army) {
        this.army = army;
    }
    
    //Método que devuelve todos los continents completamente ocupados por el player
    public ArrayList<Continent> getContinents()
    {
        ArrayList<Continent> continents = new ArrayList<>();
        ArrayList<Continent> continentsLeidos = new ArrayList<>();
        for(Country country: countries)
            if(!continentsLeidos.contains(country.getContinent())){
                continentsLeidos.add(country.getContinent());
                continents.add(country.getContinent());
                for(Country countrycontinent: country.getContinent().getCountries())
                    if(!countries.contains(countrycontinent))
                        continents.remove(country.getContinent());
                    
            }
        
        return continents;
    }
}