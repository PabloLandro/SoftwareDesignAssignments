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
public class ExcepcionGeo extends Exception{
    
    ExcepcionGeo(String codigo){
        super(codigo);
    }
    
    public void imprimirError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "100":
                out = "{\ncódigo de error: 100,\ndescripción: \"Color no permitido\"\n}";
                break;
            case "102":
                out = "{\ncódigo de error: 102,\ndescripción: \"El continente no existe\"\n}";
                break;
            case "106":
                out = "{\ncódigo de error: 106,\ndescripción: \"El mapa no está creado\"\n}";
                break;
            case "107":
                out = "{\ncódigo de error: 107,\ndescripción: \"El mapa ya ha sido creado\"\n}";
                break;
            case "109":
                out = "{\ncódigo de error: 109,\ndescripción: \"El pais no existe\"\n}";
                break;
            case "112":
                out = "{\ncódigo de error: 112,\ndescripción: \"Los países no son frontera\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.consola.Imprimir(out);
        
    }
    
}
