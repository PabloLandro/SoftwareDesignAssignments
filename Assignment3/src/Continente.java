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
public class Continente {
    String abreviatura;
    String nombre;
    String color;
    ArrayList<Pais> paises = new ArrayList();
    
    public Continente(String nombre, String color, String abreviatura)
    {       
        this.color = color;
        this.nombre = nombre;
        this.abreviatura = abreviatura;
    }
    
    @Override
    public String toString()
    {
        String out = new String();
        ArrayList <Jugador> jugadores = new ArrayList<>();
        boolean ultimo;
        int ejercitos;
        out = "{\nnombre: \"" + nombre + "\",\n";
        out += "abreviatura: \"" + abreviatura + "\",\n";
        out += "jugadores: [ ";
        for(int i = 0; i < paises.size(); i++){
            Jugador jugador = paises.get(i).getJugador();
            if(!jugadores.contains(jugador))
                jugadores.add(jugador);
        }
        for(int i = 0; i < jugadores.size(); i++){
            ejercitos = 0;
            ultimo = true;
            for(Pais pais: jugadores.get(i).getPaises()){
                if(pais.getContinente().equals(this)){
                    ejercitos += pais.getNumeroEjercito();
                }
            }
            out += "{ \"" + jugadores.get(i).getNombre() + "\", " + ejercitos + " }";
            for(int j = i+1; j < jugadores.size(); j++){
                for(Pais pais: jugadores.get(i).getPaises()){
                    if(pais.getContinente().equals(this) && (pais.getNumeroEjercito() != 0)){
                        ultimo = false;
                    }
                }
            }
            if(!ultimo){
                out += ", ";
            }
        }
        out += " ],\n";
        out += "numeroEjercitos: " + this.numeroEjercitos() + ",\n";
        out += "rearmeContinente: " + this.rearmeContinente() + "\n";
        out += "}";
        return out;
    }
    
    @Override
    public boolean equals(Object comparando) {
        Continente aux = (Continente) comparando;
        return (this.getNombre().equals(aux.getNombre()));
    }
    
    //Los getters de continente
    public String getNombre()
    {
        return nombre;
    }
    public String getColor()
    {
        return color;
    }
    public ArrayList<Pais> getPaises()
    {
        return paises;
    }
    public String getAbreviatura()
    {
        return abreviatura;
    }
    
    
    //Los setters de continente
    public void setColor(String color)
    {
        this.color = color;
    }
    public void seNombre(String nombre)
    {
        this.nombre = nombre;
    }
    public void setPaises(ArrayList paises)
    {
        this.paises = paises;
    }
    
    
    int numeroEjercitos()
    {
        int num = 0;
        for(Pais pais: paises)
            num += pais.getNumeroEjercito();
        return num;
    }
    
    public int rearmeContinente(){
        switch(abreviatura){
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
    
    public boolean esPaisDelContinente(Pais pais)
    {
        return paises.contains(pais);
    }
    
    public ArrayList<Pais> fronteras()
    {
        ArrayList<Pais> fronteras = new ArrayList<>();
        for(Pais pais: paises)
            for(Pais frontera: pais.getFronteras())
                if(!fronteras.contains(frontera) && !paises.contains(frontera))
                    fronteras.add(frontera);
        return fronteras;
    }
}
