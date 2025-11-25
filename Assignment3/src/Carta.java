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
public abstract class Carta {
    
    Pais pais;
    Jugador jugador;
    String tipo;

    Carta(Pais pais, String tipo, Jugador jugador){
        
        this.pais = pais;
        this.tipo = tipo;
        this.jugador = jugador;
    }
    

    abstract int obtenerRearme();
    
    public String obtenerNombre(){
        return new String(tipo + "&" + pais.getAbreviatura());
    }
    public String getTipo(){
        return tipo;
    }
    public Pais getPais(){
        return pais;
    }
    @Override
    public String toString()
    {
        String out = new String();
        out = "{\n";
        out = out + "tipoCarta: \"" + tipo + "\"\n";
        out = out + "paisAsociado: \"" + pais.getNombre() + "\"\n";
        out = out + "perteneceAJugador: \"" + jugador.getNombre() + "\"\n";
        if(jugador.getPaises().contains(pais))
            out = out + "ejercitosDeRearme: " + (obtenerRearme()+1) + "\n}";
        else
            out = out + "ejercitosDeRearme: " + obtenerRearme() + "\n}";
        return out;
    }
    
}
