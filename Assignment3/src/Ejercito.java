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
public abstract class Ejercito {
    
    
    public Ejercito(){
    }
    
    //Método hallar mayor dado
    public int maximoDado(ArrayList<Integer> dado) {
        int aux = 0;
        for (int i = 0; i < dado.size(); i++) {
            if (dado.get(i).intValue() > dado.get(aux).intValue()) {
                aux = i;
            }
        }
        return aux;
    }
    
    //Método hallar menor dado
    public int minimoDado(ArrayList<Integer> dado) {
        int aux = 0;
        for (int i = 0; i < dado.size(); i++) {
            if (dado.get(i).intValue() < dado.get(aux).intValue()) {
                aux = i;
            }
        }
        return aux;
    }
    abstract ArrayList<Integer> ataque(ArrayList<Integer> dadoAtaque);
    
}
