/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package risk;

/**
 *
 * @author anaca
 */
public class Errors {

    String out = new String();
    
    public Errors(int number){
        switch(number){
            case 99: 
                out = "{\nerror code: " + number + ",\ndescription: \"Comando no permitido en este momento\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;
            
            case 100: 
                out = "{\nerror code: " + number + ",\ndescription: \"Color no permitido\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;
            
            case 101: 
                out = "{\nerror code: " + number + ",\ndescription: \"Comando incorrecto\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 102: 
                out = "{\nerror code: " + number + ",\ndescription: \"El continent no existe\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 103: 
                out = "{\nerror code: " + number + ",\ndescription: \"El player no existe\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 104: 
                out = "{\nerror code: " + number + ",\ndescription: \"El player ya existe\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 105: 
                out = "{\nerror code: " + number + "\ndescription: \"Los players no están creados\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 106: 
                out = "{\nerror code: " + number + ",\ndescription: \"El map no está creado\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 107: 
                out = "{\nerror code: " + number + ",\ndescription: \"El map ya ha sido creado\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 109: 
                out = "{\nerror code: " + number + ",\ndescription: \"El country no existe\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 110: 
                out = "{\nerror code: " + number + ",\ndescription: \"El país no pertenece al player\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 111: 
                out = "{\nerror code: " + number + ",\ndescription: \"El país pertenece al player\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 112: 
                out = "{\nerror code: " + number + ",\ndescription: \"Los países no son border\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 113: 
                out = "{\nerror code: " + number + ",\ndescription: \"El país ya está asignado\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 114: 
                out = "{\nerror code: " + number + ",\ndescription: \"El color ya está asignado\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 115: 
                out = "{\nerror code: " + number + ",\ndescription: \"La misión ya está asignada\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 116: 
                out = "{\nerror code: " + number + ",\ndescription: \"La misión no existe\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 117: 
                out = "{\nerror code: " + number + ",\ndescription: \"El player ya tiene asignada una misión\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 118: 
                out = "{\nerror code: " + number + ",\ndescription: \"Las missions no están asignadas\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 119: 
                out = "{\nerror code: " + number + ",\ndescription: \"Ejércitos no disponibles\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 120: 
                out = "{\nerror code: " + number + ",\ndescription: \"No hay cards suficientes\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 121: 
                out = "{\nerror code: " + number + ",\ndescription: \"No hay configuración de cambio\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 122: 
                out = "{\nerror code: " + number + ",\ndescription: \"Algunas cards no pertenecen al player\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 123: 
                out = "{\nerror code: " + number + ",\ndescription: \"Algunas cards no existen\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 124: 
                out = "{\nerror code: " + number + ",\ndescription: \"No hay ejércitos suficientes\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 125: 
                out = "{\nerror code: " + number + ",\ndescription: \"El identificador no sigue el formato correcto\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            case 126: 
                out = "{\nerror code: " + number + ",\ndescription: \"Card de equipamiento ya asignada\"\n}";
                System.out.println(out);
                Printer.print(out);
                break;

            }
    }
}