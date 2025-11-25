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
public class DeCaballo extends Caballeria{
    
    public DeCaballo(Pais pais, Jugador jugador){
        super(pais, "DeCaballo", jugador);
    }

    @Override
    int obtenerRearme() {
        return 3;
    }
    
}
