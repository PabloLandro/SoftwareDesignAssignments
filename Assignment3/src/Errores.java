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
public class Errores {

    String out = new String();
    
    public Errores(int numero){
        switch(numero){
            case 99: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Comando no permitido en este momento\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;
            
            case 100: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Color no permitido\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;
            
            case 101: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Comando incorrecto\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 102: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El continente no existe\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 103: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El jugador no existe\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 104: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El jugador ya existe\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 105: 
                out = "{\ncódigo de error: " + numero + "\ndescripción: \"Los jugadores no están creados\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 106: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El mapa no está creado\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 107: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El mapa ya ha sido creado\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 109: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El pais no existe\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 110: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El país no pertenece al jugador\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 111: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El país pertenece al jugador\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 112: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Los países no son frontera\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 113: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El país ya está asignado\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 114: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El color ya está asignado\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 115: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"La misión ya está asignada\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 116: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"La misión no existe\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 117: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El jugador ya tiene asignada una misión\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 118: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Las misiones no están asignadas\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 119: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Ejércitos no disponibles\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 120: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"No hay cartas suficientes\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 121: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"No hay configuración de cambio\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 122: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Algunas cartas no pertenecen al jugador\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 123: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Algunas cartas no existen\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 124: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"No hay ejércitos suficientes\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 125: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"El identificador no sigue el formato correcto\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            case 126: 
                out = "{\ncódigo de error: " + numero + ",\ndescripción: \"Carta de equipamiento ya asignada\"\n}";
                System.out.println(out);
                Impresora.imprimir(out);
                break;

            }
    }
}