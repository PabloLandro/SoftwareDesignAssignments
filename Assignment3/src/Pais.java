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
public class Pais {

    String nombre;
    String abreviatura;
    Continente continente;
    Jugador jugador;
    int numeroEjercito;
    Casilla casilla;
    ArrayList<Pais> fronteras;
    int vecesOcupado;
    

    public Pais(String nombre, String abreviatura, Continente continente) {
        continente.getPaises().add(this);
        this.nombre = nombre;
        this.abreviatura = abreviatura;
        this.continente = continente;
        this.vecesOcupado = 0;
        this.numeroEjercito = 1;
    }

    @Override
    public boolean equals(Object comparando) {
        Pais aux = (Pais) comparando;
        return (this.getNombre().equals(aux.getNombre()));
    }

    @Override
    public String toString() {
        String out = new String();
        out = "{\n";
        out += "nombre: \"" + nombre + "\",\n";
        out += "abreviatura: \"" + abreviatura + "\",\n";
        out += "continente: \"" + continente.getNombre() + "\",\n";
        out += "frontera: [";
        Iterator<Pais> itFrontera = fronteras.iterator();
        while (itFrontera.hasNext()) {
            out = out + " \"" + itFrontera.next().getNombre() + "\"";
            if (itFrontera.hasNext())
                out += ",";
        }
        out += " ],\n";
        out += "jugador: \"" + jugador.getNombre() + "\",\n";
        out += "numeroEjercitos: " + numeroEjercito + ",\n";
        out += "numeroVecesOcupado: " + vecesOcupado + "\n";
        out += "}";
        return out;
    }

    public Continente getContinente() {
        return continente;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public String getNombre() {
        return nombre;
    }
   
    public int getNumeroEjercito() {
        return numeroEjercito;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public ArrayList<Pais> getFronteras() {
        return fronteras;
    }

    public int getVecesOcupado() {
        return vecesOcupado;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setFronteras(ArrayList<Pais> frontera) {
        this.fronteras = frontera;
    }

    public void setContinente(Continente continente) {
        this.continente = continente;
    }
    
    public void setNumeroEjercito(int numeroEjercito) {
        this.numeroEjercito = numeroEjercito;
    }
    
    public void addNumeroEjercito(int numeroEjercito) {
        this.numeroEjercito += numeroEjercito;
    }
    
    public void aumentarVecesOcupado(){
        this.vecesOcupado++;
    }
    
}
