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
public class Country {

    String name;
    String abbreviation;
    Continent continent;
    Player player;
    int armyNumber;
    Tile casilla;
    ArrayList<Country> borders;
    int timesOccupied;
    

    public Country(String name, String abbreviation, Continent continent) {
        continent.getCountries().add(this);
        this.name = name;
        this.abbreviation = abbreviation;
        this.continent = continent;
        this.timesOccupied = 0;
        this.armyNumber = 1;
    }

    @Override
    public boolean equals(Object comparing) {
        Country aux = (Country) comparing;
        return (this.getName().equals(aux.getName()));
    }

    @Override
    public String toString() {
        String out = new String();
        out = "{\n";
        out += "name: \"" + name + "\",\n";
        out += "abbreviation: \"" + abbreviation + "\",\n";
        out += "continent: \"" + continent.getName() + "\",\n";
        out += "border: [";
        Iterator<Country> itBorder = borders.iterator();
        while (itBorder.hasNext()) {
            out = out + " \"" + itBorder.next().getName() + "\"";
            if (itBorder.hasNext())
                out += ",";
        }
        out += " ],\n";
        out += "player: \"" + player.getName() + "\",\n";
        out += "armyNumber: " + armyNumber + ",\n";
        out += "timesOccupied: " + timesOccupied + "\n";
        out += "}";
        return out;
    }

    public Continent getContinent() {
        return continent;
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return name;
    }
   
    public int getArmyNumber() {
        return armyNumber;
    }

    public String getAbreviatura() {
        return abbreviation;
    }

    public ArrayList<Country> getBorders() {
        return borders;
    }

    public int getTimesOccupied() {
        return timesOccupied;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setBorders(ArrayList<Country> border) {
        this.borders = border;
    }

    public void setContinent(Continent continent) {
        this.continent = continent;
    }
    
    public void setArmyNumber(int armyNumber) {
        this.armyNumber = armyNumber;
    }
    
    public void addArmyNumber(int armyNumber) {
        this.armyNumber += armyNumber;
    }
    
    public void incrementTimesOccupied(){
        this.timesOccupied++;
    }
    
}
