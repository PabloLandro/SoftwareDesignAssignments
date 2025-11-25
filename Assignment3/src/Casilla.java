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
public class Casilla {
    
    int x, y, tipo;
    Pais pais;
    
    public Casilla(Pais pais, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.pais = pais;
    }
    
    
    public int getx()
    {
        return x;
    }
    public int gety()
    {
        return y;
    }
    public Pais getPais()
    {
        return pais;
    }
    
    
}
