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
public class CardException extends Exception{
    
    CardException(String code){
        super(code);
    }
    
    public void printError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "120":
                out = "{\nerror code: 120,\ndescription: \"No hay cards suficientes\"\n}";
                break;
            case "121":
                out = "{\nerror code: 121,\ndescription: \"No hay configuración de cambio\"\n}";
                break;
            case "122":
                out = "{\nerror code: 122,\ndescription: \"Algunas cards no pertenecen al player\"\n}";
                break;
            case "123":
                out = "{\nerror code: 123,\ndescription: \"Algunas cards no existen\"\n}";
                break;
            case "125":
                out = "{\nerror code: 125,\ndescription: \"El identificador no sigue el formato correcto\"\n}";
                break;
            case "126":
                out = "{\nerror code: 126,\ndescription: \"Card de equipamiento ya asignada\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.console.Print(out);
        
    }
    
}
