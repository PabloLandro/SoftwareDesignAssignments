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
public class ExcepcionMision extends Exception{
    
    ExcepcionMision(String codigo){
        super(codigo);
    }
    
    public void imprimirError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "115":
                out = "{\ncódigo de error: 115,\ndescripción: \"La misión ya está asignada\"\n}";
                break;
            case "116":
                out = "{\ncódigo de error: 116,\ndescripción: \"La misión no existe\"\n}";
                break;
            case "117":
                out = "{\ncódigo de error: 117,\ndescripción: \"El jugador ya tiene asignada una misión\"\n}";
                break;
            case "118":
                out = "{\ncódigo de error: 118,\ndescripción: \"Las misiones no están asignadas\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.consola.Imprimir(out);
        
    }
    
}
