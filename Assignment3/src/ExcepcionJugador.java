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
public class ExcepcionJugador extends Exception{
    
    ExcepcionJugador(String codigo){
        super(codigo);
    }
    
    public void imprimirError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "103":
                out = "{\ncódigo de error: 103,\ndescripción: \"El jugador no existe\"\n}";
                break;
            case "104":
                out = "{\ncódigo de error: 104,\ndescripción: \"El jugador ya existe\"\n}";
                break;
            case "105":
                out = "{\ncódigo de error: 105,\ndescripción: \"Los jugadores no están creados\"\n}";
                break;
            case "110":
                out = "{\ncódigo de error: 110,\ndescripción: \"El país no pertenece al jugador\"\n}";
                break;
            case "111":
                out = "{\ncódigo de error: 111,\ndescripción: \"El país pertenece al jugador\"\n}";
                break;
            case "113":
                out = "{\ncódigo de error: 113,\ndescripción: \"El país ya está asignado\"\n}";
                break;
            case "114":
                out = "{\ncódigo de error: 114,\ndescripción: \"El color ya está asignado\"\n}";
                break;
            case "119":
                out = "{\ncódigo de error: 119,\ndescripción: \"Ejércitos no disponibles\"\n}";
                break;
            case "124":
                out = "{\ncódigo de error: 124,\ndescripción: \"No hay ejércitos suficientes\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.consola.Imprimir(out);
        
    }
    
}
