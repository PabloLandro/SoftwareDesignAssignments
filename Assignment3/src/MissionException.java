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
public class MissionException extends Exception{
    
    MissionException(String code){
        super(code);
    }
    
    public void printError(){
        
        String out = new String();
        
        switch(super.getMessage()){
            case "115":
                out = "{\nerror code: 115,\ndescription: \"La misión ya está asignada\"\n}";
                break;
            case "116":
                out = "{\nerror code: 116,\ndescription: \"La misión no existe\"\n}";
                break;
            case "117":
                out = "{\nerror code: 117,\ndescription: \"El player ya tiene asignada una misión\"\n}";
                break;
            case "118":
                out = "{\nerror code: 118,\ndescription: \"Las missions no están asignadas\"\n}";
                break;
            default:
                out = super.getMessage();
                break;
        }
        
        Risk.console.Print(out);
        
    }
    
}
