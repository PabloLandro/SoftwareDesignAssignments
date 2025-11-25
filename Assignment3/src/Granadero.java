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
public class Granadero extends Infanteria{
    
    public Granadero(Pais pais, Jugador jugador){
        super(pais, "Granadero", jugador);
    }
    
    @Override
    int obtenerRearme(){
        return 1;
    }
    
}
