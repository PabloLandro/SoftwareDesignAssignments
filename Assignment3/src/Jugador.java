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
public class Jugador {
    
    String nombre;
    String color;
    ArrayList<Pais> paises;
    ArrayList<Carta> cartas = new ArrayList<>();
    Mision mision;
    int numeroEjercitos = 0;
    Ejercito ejercito;
    
    public Jugador(String nombre, String color)
    {
        this.nombre = nombre;
        this.color = color;
        this.paises = new ArrayList<>();
        this.mision = new Mision("", "0");
    }
    
    public String toString(boolean a)
    {
        String out = new String();
        String paisLista = new String();
        String continenteLista = new String();
        String cartasLista = new String();
        for(int i = 0; i < paises.size(); i++){
            paisLista += "\"" + paises.get(i).getNombre() + "\"";
            if(i != paises.size()-1)
                paisLista += ", ";
            else
                paisLista += " ],\n";
        }
        for(int i = 0; i < getContinentes().size(); i++){
            continenteLista += " \"" + getContinentes().get(i).getNombre() + "\"";
            if(i != getContinentes().size()-1)
                continenteLista += ",";
        }
        for(int i = 0; i < cartas.size(); i++){
            cartasLista += " \"" + cartas.get(i).obtenerNombre() + "\"";
            if(i != cartas.size()-1)
                cartasLista += ",";
        }
        out = "{\n";
        out += "nombre: \"" + nombre + "\",\n";
        out += "color: \"" + color + "\",\n";
        if(a)
            out += "mision: \"" + mision.getDescripcion() + "\",\n";
        out += "numeroEjercitos: " + numeroEjercitos + ",\n";
        out += "paises: [ " + paisLista;
        out += "continentes: [" + continenteLista + " ],\n";
        out += "cartas: [" + cartasLista + " ],\n";
        out += "numeroEjercitosRearmar: " + numeroEjercitos + "\n";
        out += "}";
        return out;
    }
    
    @Override
    public boolean equals(Object o){
        Jugador jugador = (Jugador) o;
        return this.getNombre().equals(jugador.getNombre());
    }
    
    public void setNombre(String Nombre)
    {
        nombre = Nombre;
    }    
    public void setColor(String Color)
    {
        color = Color;
    }    
    public void setMision(Mision mision)
    {
        this.mision = mision;
    }    
    public void setNumeroEjercitos(int numero)
    {
        this.numeroEjercitos = numero;
    }
    
    public void addNumeroEjercitos(int numero)
    {
        this.numeroEjercitos += numero;
    }
    
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
    public int getNumeroEjercitos()
    {
        return numeroEjercitos;
    }
    public Mision getMision(){
        return mision;
    }
    public ArrayList<Carta> getCartas()
    {
        return cartas;
    }

    public Ejercito getEjercito() {
        return ejercito;
    }

    public void setEjercito(Ejercito ejercito) {
        this.ejercito = ejercito;
    }
    
    //Método que devuelve todos los continentes completamente ocupados por el jugador
    public ArrayList<Continente> getContinentes()
    {
        ArrayList<Continente> continentes = new ArrayList<>();
        ArrayList<Continente> continentesLeidos = new ArrayList<>();
        for(Pais pais: paises)
            if(!continentesLeidos.contains(pais.getContinente())){
                continentesLeidos.add(pais.getContinente());
                continentes.add(pais.getContinente());
                for(Pais paisContinente: pais.getContinente().getPaises())
                    if(!paises.contains(paisContinente))
                        continentes.remove(pais.getContinente());
                    
            }
        
        return continentes;
    }
}