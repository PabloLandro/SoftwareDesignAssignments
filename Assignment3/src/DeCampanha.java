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
public class DeCampanha extends Artilleria{
    
    public DeCampanha(Pais pais, Jugador jugador){
        super(pais, "DeCampanha", jugador);
    }

    @Override
    int obtenerRearme() {
        return 4;
    }
    
}
