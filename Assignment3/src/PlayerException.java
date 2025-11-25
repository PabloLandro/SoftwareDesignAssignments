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
public class PlayerException extends Exception{
    
    PlayerException(String code){
        super(code);
    }
    
    public void printError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "103":
                out = "{\nerror code: 103,\ndescription: \"El player no existe\"\n}";
                break;
            case "104":
                out = "{\nerror code: 104,\ndescription: \"El player ya existe\"\n}";
                break;
            case "105":
                out = "{\nerror code: 105,\ndescription: \"Los players no están creados\"\n}";
                break;
            case "110":
                out = "{\nerror code: 110,\ndescription: \"El país no pertenece al player\"\n}";
                break;
            case "111":
                out = "{\nerror code: 111,\ndescription: \"El país pertenece al player\"\n}";
                break;
            case "113":
                out = "{\nerror code: 113,\ndescription: \"El país ya está asignado\"\n}";
                break;
            case "114":
                out = "{\nerror code: 114,\ndescription: \"El color ya está asignado\"\n}";
                break;
            case "119":
                out = "{\nerror code: 119,\ndescription: \"Ejércitos no disponibles\"\n}";
                break;
            case "124":
                out = "{\nerror code: 124,\ndescription: \"No hay ejércitos suficientes\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.console.Print(out);
        
    }
    
}
