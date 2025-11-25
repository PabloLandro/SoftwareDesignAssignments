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
public class CommandException extends Exception{
    
    CommandException(String code){
        super(code);
    }
    
    public void printError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "99":
                out = "{\nerror code: 99,\ndescription: \"Comando no permitido en este momento\"\n}";
                break;
            case "101":
                out = "{\nerror code: 101,\ndescription: \"Comando incorrecto\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.console.Print(out);
        
    }
    
}
