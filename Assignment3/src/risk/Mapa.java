/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package risk;
import java.util.ArrayList;

/**
 *
 * @author landr
 */
public class Mapa {
    
    //Guardamos all las casillas en un ArrayList
    ArrayList <Tile> casillas;
    
    public ArrayList getTiles()
    {
        return casillas;
    }
    
    public Mapa()
    {
        casillas = new ArrayList<Tile>();
    }

    //Imprimimos el map por pantalla
    public void verMapa()
    {
        System.out.println("\nMapa");
        //Imprimimos una línea de guiones al principio
        printLinea(0);
        
        //Usamos un for para recorrer las 8 filas
        for(int i = 1; i <= 8; i++)
        {
            //Imprimimos la línea con los name de países, el índice nos sirve
            //para get los países adecuados
            printLineaCountry(i);
            
            //Imprimimos la línea con el número de ejércitos de cada país
            printLineaArmies(i);
            
            //Imprimimos una línea de guiones
            printLinea(i);
        }
        
    }
    
    
    //FUNCIONES AUXILIARES DE VER MAPA
    
    //Línea de guiones
    void printLinea(int line)
    {
        System.out.print("\n|");
        for(int i = 0; i < 11; i++){
            if((line == 5) && (i == 3))
                System.out.print("===========" + "\033[0;31m" + "|" + "\033[0m");
            else
                System.out.print("===========|");
        }
    }
    
    String colorCountry(Country country)
    {
        
        switch(country.getContinent().getColor())
        {
            case "ROJO":
                return "\033[41m";
                
            case "VERDE":
                return "\033[42m";
                
            case "AZUL":
                return "\033[44m";
                
            case "VIOLETA":
                return "\033[45m";
                
            case "CYAN":
                return "\033[46m";
                
            case "AMARILLO":
                return "\033[43m";
                
            default:
                return "";
        }
        
    }
    
    //Línea con names de países
    void printLineaCountry(int line)
    {
        System.out.print("\n|");
        
        //Usamos un for para recorrer cada columna, la fila ya la tenemos, se
        //pasa por argumento
        for(int i = 1; i <= 11; i++)
        {
            //Buscamos el país en la posición en la que nos encontramos
            Country country = buscarCountry(i, line);
            //En caso de no existir un país (la casilla es agua), imprimimos una
            //casilla vacía
            if(country != null)
            {
                //Substring para asegurarnos todos los countries tienen longitud 9 caracteres
                if(country.getAbreviatura().length() > 9)
                    System.out.print(" " + String.format(colorCountry(country) + "%-9s" + "\033[0m" , country.getAbreviatura().substring(0, 9)));
                else
                    System.out.print(" " + String.format(colorCountry(country) + "%-9s" + "\033[0m" , country.getAbreviatura()));
                System.out.print(" |");
            }
            else 
            {
                //Seleccionamos las casillas de border mar 
                //3, 1. 9, 1. 10, 1. 5, 3. 6, 3. 4, 4. 3, 5. 9, 5.
                if((i==4) && (line == 6))
                    System.out.print("\033[0;31m" + "-----------|" + "\033[0m");
                else if(((i == 10 || i == 11) && line == 1) || (i == 5 && line == 5) || (i == 4 && line == 6))
                    System.out.print("\033[0;31m" + "-----------" + "\033[0m" + "|");
                
                else if (((i == 6 || i == 7) && line == 4) || ((i == 10) && line == 6))
                    System.out.print("\033[0;31m" + "     |     " + "\033[0m" + "|");                        
                
                else if(i == 4 && line == 5)
                    System.out.print("           " + "\033[0;31m" + "|" + "\033[0m");
                else
                    System.out.print("           |");
                                
            }
                
            
        }
    }
    
    
    //Imprimimos número de ejércitos
    void printLineaArmies(int line)
    {
        System.out.print("\n|");
        //Usamos un for para recorrer cada columna, la fila ya la tenemos, se
        //pasa por argumento
        for(int i = 1; i <= 11; i++)
        {
            //En caso de no existir un país (la casilla es agua), imprimimos una
            //casilla vacía
            Country country = buscarCountry(i, line);
            if(country != null)            
                System.out.print(" " + country.getArmyNumber() + "         |");
            
            else
                
                if(line == 5 && i == 4)
                    System.out.print("           " + "\033[0;31m" + "|" + "\033[0m");
                else if (((i == 6 || i == 7) && line == 4) || ((i == 10) && line == 6))
                    System.out.print("\033[0;31m" + "     |     " + "\033[0m" + "|");   
                else
                    System.out.print("           |");
        }
    }
    
    //FIN DE FUNCIONES AUXILIARES DE VER MAPA
    
    //Devuelve un país a partir de las coordenadas de su casilla
    Country buscarCountry(int x, int y)
    {
        //Recorremos la Collection con un for-each
        for(Tile casilla: casillas)
            //En caso de que las coordenadas coincidan, devolvemos el país
            //asociado
            if(casilla.getx() == x && casilla.gety() == y) return casilla.getCountry();
        //En caso contrario, devolvemos valor nulo
        return null;
    }
   
    public ArrayList <Country> getBorder(Country country)
    {
        //Creamos el ArrayList que devolveremos al final
        ArrayList <Country> borders = new ArrayList<>();
        
        //Creamos dos ints para guardar las coordenadas del país del que
        //queremos saber la border
        int x = 0, y = 0;
        
        //Usamos un for-each para encontrar la casilla asociada al país dentro
        //de nuestro ArrayList
        for(Tile casilla: casillas)
        {
            if(casilla.getCountry().equals(country))
            {
                x = casilla.getx();
                y = casilla.gety();
            }
        }
        
        //Comprobamos si tiene países adyacentes y los añadimos al ArrayList
        if(buscarCountry(x + 1, y) != null)
            borders.add(buscarCountry(x + 1, y));

        if(buscarCountry(x - 1, y) != null)
            borders.add(buscarCountry(x - 1, y));

        if(buscarCountry(x, y + 1) != null)
            borders.add(buscarCountry(x, y + 1));

        if(buscarCountry(x, y - 1) != null)
            borders.add(buscarCountry(x, y - 1));

        //Borders mar:
            
        //Comprobamos qué país tenemos y buscamos su border en la lista de
        //casillas y la añadimos a la lista de salida
        if(country.getAbreviatura().equals("Groenlan"))
            for(Tile casilla : casillas)
                if(casilla.getCountry().getAbreviatura().equals("Islandia"))
                    borders.add(casilla.getCountry());

        if(country.getAbreviatura().equals("Islandia"))
            for(Tile casilla : casillas)
                if(casilla.getCountry().getAbreviatura().equals("Groenlan"))
                    borders.add(casilla.getCountry());

        if(country.getAbreviatura().equals("Brasil"))
            for(Tile casilla : casillas)
                if(casilla.getCountry().getAbreviatura().equals("ANorte"))
                    borders.add(casilla.getCountry());

        if(country.getAbreviatura().equals("ANorte"))
            for(Tile casilla : casillas)
            {
                if(casilla.getCountry().getAbreviatura().equals("Brasil"))
                    borders.add(casilla.getCountry());
                if(casilla.getCountry().getAbreviatura().equals("EurOcc"))
                    borders.add(casilla.getCountry());
            }

        if(country.getAbreviatura().equals("EurOcc"))
            for(Tile casilla : casillas)
                if(casilla.getCountry().getAbreviatura().equals("ANorte"))
                    borders.add(casilla.getCountry());

        if(country.getAbreviatura().equals("EurSur"))
            for(Tile casilla : casillas)
                if(casilla.getCountry().getAbreviatura().equals("Egipto"))
                    borders.add(casilla.getCountry());

            if(country.getAbreviatura().equals("Egipto"))
                for(Tile casilla : casillas)
                    if(casilla.getCountry().getAbreviatura().equals("EurSur"))
                        borders.add(casilla.getCountry());

            if(country.getAbreviatura().equals("SAsiático"))
                for(Tile casilla : casillas)
                    if(casilla.getCountry().getAbreviatura().equals("Indonesia"))
                        borders.add(casilla.getCountry());

            if(country.getAbreviatura().equals("Indonesia"))
                for(Tile casilla : casillas)
                    if(casilla.getCountry().getAbreviatura().equals("SAsiático"))
                        borders.add(casilla.getCountry());

            if(country.getAbreviatura().equals("Alaska"))
                for(Tile casilla : casillas)
                    if(casilla.getCountry().getAbreviatura().equals("Kamchatka"))
                        borders.add(casilla.getCountry());

            if(country.getAbreviatura().equals("Kamchatka"))
                for(Tile casilla : casillas)
                    if(casilla.getCountry().getAbreviatura().equals("Alaska"))
                        borders.add(casilla.getCountry());
            
        //Devolvemos el ArrayList resultante        
        return borders;
    }
}
