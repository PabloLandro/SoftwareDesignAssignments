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
public class Mision {
    
    String descripcion;
    String id;
    
    public Mision(String descripcion, String id)
    {
        this.descripcion = descripcion;
        this.id = id;
    }
    
    public String getDescripcion()
    {
        return descripcion;
    }
    public String getId()
    {
        return id;
    }
    
}
