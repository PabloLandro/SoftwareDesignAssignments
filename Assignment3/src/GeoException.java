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
public class GeoException extends Exception{
    
    GeoException(String code){
        super(code);
    }
    
    public void printError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "100":
                out = "{\nerror code: 100,\ndescription: \"Color no permitido\"\n}";
                break;
            case "102":
                out = "{\nerror code: 102,\ndescription: \"El continent no existe\"\n}";
                break;
            case "106":
                out = "{\nerror code: 106,\ndescription: \"El map no está creado\"\n}";
                break;
            case "107":
                out = "{\nerror code: 107,\ndescription: \"El map ya ha sido creado\"\n}";
                break;
            case "109":
                out = "{\nerror code: 109,\ndescription: \"El country no existe\"\n}";
                break;
            case "112":
                out = "{\nerror code: 112,\ndescription: \"Los países no son border\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.console.Print(out);
        
    }
    
}
