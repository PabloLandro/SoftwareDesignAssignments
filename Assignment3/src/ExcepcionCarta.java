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
public class ExcepcionCarta extends Exception{
    
    ExcepcionCarta(String codigo){
        super(codigo);
    }
    
    public void imprimirError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "120":
                out = "{\ncódigo de error: 120,\ndescripción: \"No hay cartas suficientes\"\n}";
                break;
            case "121":
                out = "{\ncódigo de error: 121,\ndescripción: \"No hay configuración de cambio\"\n}";
                break;
            case "122":
                out = "{\ncódigo de error: 122,\ndescripción: \"Algunas cartas no pertenecen al jugador\"\n}";
                break;
            case "123":
                out = "{\ncódigo de error: 123,\ndescripción: \"Algunas cartas no existen\"\n}";
                break;
            case "125":
                out = "{\ncódigo de error: 125,\ndescripción: \"El identificador no sigue el formato correcto\"\n}";
                break;
            case "126":
                out = "{\ncódigo de error: 126,\ndescripción: \"Carta de equipamiento ya asignada\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.consola.Imprimir(out);
        
    }
    
}
