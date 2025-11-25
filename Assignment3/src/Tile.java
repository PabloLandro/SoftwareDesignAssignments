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
public class Tile {
    
    int x, y, type;
    Country country;
    
    public Tile(Country country, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.country = country;
    }
    
    
    public int getx()
    {
        return x;
    }
    public int gety()
    {
        return y;
    }
    public Country getCountry()
    {
        return country;
    }
    
    
}
