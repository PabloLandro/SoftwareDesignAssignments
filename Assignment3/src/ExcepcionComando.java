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
public class ExcepcionComando extends Exception{
    
    ExcepcionComando(String codigo){
        super(codigo);
    }
    
    public void imprimirError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "99":
                out = "{\ncódigo de error: 99,\ndescripción: \"Comando no permitido en este momento\"\n}";
                break;
            case "101":
                out = "{\ncódigo de error: 101,\ndescripción: \"Comando incorrecto\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.consola.Imprimir(out);
        
    }
    
}
