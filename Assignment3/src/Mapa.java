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
    
    //Guardamos todas las casillas en un ArrayList
    ArrayList <Casilla> casillas;
    
    public ArrayList getCasillas()
    {
        return casillas;
    }
    
    public Mapa()
    {
        casillas = new ArrayList<Casilla>();
    }

    //Imprimimos el mapa por pantalla
    public void verMapa()
    {
        System.out.println("\nMapa");
        //Imprimimos una línea de guiones al principio
        imprimirLinea(0);
        
        //Usamos un for para recorrer las 8 filas
        for(int i = 1; i <= 8; i++)
        {
            //Imprimimos la línea con los nombre de países, el índice nos sirve
            //para obtener los países adecuados
            imprimirLineaPais(i);
            
            //Imprimimos la línea con el número de ejércitos de cada país
            imprimirLineaEjercitos(i);
            
            //Imprimimos una línea de guiones
            imprimirLinea(i);
        }
        
    }
    
    
    //FUNCIONES AUXILIARES DE VER MAPA
    
    //Línea de guiones
    void imprimirLinea(int linea)
    {
        System.out.print("\n|");
        for(int i = 0; i < 11; i++){
            if((linea == 5) && (i == 3))
                System.out.print("===========" + "\033[0;31m" + "|" + "\033[0m");
            else
                System.out.print("===========|");
        }
    }
    
    String colorPais(Pais pais)
    {
        
        switch(pais.getContinente().getColor())
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
    
    //Línea con nombres de países
    void imprimirLineaPais(int linea)
    {
        System.out.print("\n|");
        
        //Usamos un for para recorrer cada columna, la fila ya la tenemos, se
        //pasa por argumento
        for(int i = 1; i <= 11; i++)
        {
            //Buscamos el país en la posición en la que nos encontramos
            Pais pais = buscarPais(i, linea);
            //En caso de no existir un país (la casilla es agua), imprimimos una
            //casilla vacía
            if(pais != null)
            {
                //Substring para asegurarnos todos los paises tienen longitud 9 caracteres
                if(pais.getAbreviatura().length() > 9)
                    System.out.print(" " + String.format(colorPais(pais) + "%-9s" + "\033[0m" , pais.getAbreviatura().substring(0, 9)));
                else
                    System.out.print(" " + String.format(colorPais(pais) + "%-9s" + "\033[0m" , pais.getAbreviatura()));
                System.out.print(" |");
            }
            else 
            {
                //Seleccionamos las casillas de frontera mar 
                //3, 1. 9, 1. 10, 1. 5, 3. 6, 3. 4, 4. 3, 5. 9, 5.
                if((i==4) && (linea == 6))
                    System.out.print("\033[0;31m" + "-----------|" + "\033[0m");
                else if(((i == 10 || i == 11) && linea == 1) || (i == 5 && linea == 5) || (i == 4 && linea == 6))
                    System.out.print("\033[0;31m" + "-----------" + "\033[0m" + "|");
                
                else if (((i == 6 || i == 7) && linea == 4) || ((i == 10) && linea == 6))
                    System.out.print("\033[0;31m" + "     |     " + "\033[0m" + "|");                        
                
                else if(i == 4 && linea == 5)
                    System.out.print("           " + "\033[0;31m" + "|" + "\033[0m");
                else
                    System.out.print("           |");
                                
            }
                
            
        }
    }
    
    
    //Imprimimos número de ejércitos
    void imprimirLineaEjercitos(int linea)
    {
        System.out.print("\n|");
        //Usamos un for para recorrer cada columna, la fila ya la tenemos, se
        //pasa por argumento
        for(int i = 1; i <= 11; i++)
        {
            //En caso de no existir un país (la casilla es agua), imprimimos una
            //casilla vacía
            Pais pais = buscarPais(i, linea);
            if(pais != null)            
                System.out.print(" " + pais.getNumeroEjercito() + "         |");
            
            else
                
                if(linea == 5 && i == 4)
                    System.out.print("           " + "\033[0;31m" + "|" + "\033[0m");
                else if (((i == 6 || i == 7) && linea == 4) || ((i == 10) && linea == 6))
                    System.out.print("\033[0;31m" + "     |     " + "\033[0m" + "|");   
                else
                    System.out.print("           |");
        }
    }
    
    //FIN DE FUNCIONES AUXILIARES DE VER MAPA
    
    //Devuelve un país a partir de las coordenadas de su casilla
    Pais buscarPais(int x, int y)
    {
        //Recorremos la Collection con un for-each
        for(Casilla casilla: casillas)
            //En caso de que las coordenadas coincidan, devolvemos el país
            //asociado
            if(casilla.getx() == x && casilla.gety() == y) return casilla.getPais();
        //En caso contrario, devolvemos valor nulo
        return null;
    }
   
    public ArrayList <Pais> obtenerFrontera(Pais pais)
    {
        //Creamos el ArrayList que devolveremos al final
        ArrayList <Pais> fronteras = new ArrayList<>();
        
        //Creamos dos ints para guardar las coordenadas del país del que
        //queremos saber la frontera
        int x = 0, y = 0;
        
        //Usamos un for-each para encontrar la casilla asociada al país dentro
        //de nuestro ArrayList
        for(Casilla casilla: casillas)
        {
            if(casilla.getPais().equals(pais))
            {
                x = casilla.getx();
                y = casilla.gety();
            }
        }
        
        //Comprobamos si tiene países adyacentes y los añadimos al ArrayList
        if(buscarPais(x + 1, y) != null)
            fronteras.add(buscarPais(x + 1, y));

        if(buscarPais(x - 1, y) != null)
            fronteras.add(buscarPais(x - 1, y));

        if(buscarPais(x, y + 1) != null)
            fronteras.add(buscarPais(x, y + 1));

        if(buscarPais(x, y - 1) != null)
            fronteras.add(buscarPais(x, y - 1));

        //Fronteras mar:
            
        //Comprobamos qué país tenemos y buscamos su frontera en la lista de
        //casillas y la añadimos a la lista de salida
        if(pais.getAbreviatura().equals("Groenlan"))
            for(Casilla casilla : casillas)
                if(casilla.getPais().getAbreviatura().equals("Islandia"))
                    fronteras.add(casilla.getPais());

        if(pais.getAbreviatura().equals("Islandia"))
            for(Casilla casilla : casillas)
                if(casilla.getPais().getAbreviatura().equals("Groenlan"))
                    fronteras.add(casilla.getPais());

        if(pais.getAbreviatura().equals("Brasil"))
            for(Casilla casilla : casillas)
                if(casilla.getPais().getAbreviatura().equals("ANorte"))
                    fronteras.add(casilla.getPais());

        if(pais.getAbreviatura().equals("ANorte"))
            for(Casilla casilla : casillas)
            {
                if(casilla.getPais().getAbreviatura().equals("Brasil"))
                    fronteras.add(casilla.getPais());
                if(casilla.getPais().getAbreviatura().equals("EurOcc"))
                    fronteras.add(casilla.getPais());
            }

        if(pais.getAbreviatura().equals("EurOcc"))
            for(Casilla casilla : casillas)
                if(casilla.getPais().getAbreviatura().equals("ANorte"))
                    fronteras.add(casilla.getPais());

        if(pais.getAbreviatura().equals("EurSur"))
            for(Casilla casilla : casillas)
                if(casilla.getPais().getAbreviatura().equals("Egipto"))
                    fronteras.add(casilla.getPais());

            if(pais.getAbreviatura().equals("Egipto"))
                for(Casilla casilla : casillas)
                    if(casilla.getPais().getAbreviatura().equals("EurSur"))
                        fronteras.add(casilla.getPais());

            if(pais.getAbreviatura().equals("SAsiático"))
                for(Casilla casilla : casillas)
                    if(casilla.getPais().getAbreviatura().equals("Indonesia"))
                        fronteras.add(casilla.getPais());

            if(pais.getAbreviatura().equals("Indonesia"))
                for(Casilla casilla : casillas)
                    if(casilla.getPais().getAbreviatura().equals("SAsiático"))
                        fronteras.add(casilla.getPais());

            if(pais.getAbreviatura().equals("Alaska"))
                for(Casilla casilla : casillas)
                    if(casilla.getPais().getAbreviatura().equals("Kamchatka"))
                        fronteras.add(casilla.getPais());

            if(pais.getAbreviatura().equals("Kamchatka"))
                for(Casilla casilla : casillas)
                    if(casilla.getPais().getAbreviatura().equals("Alaska"))
                        fronteras.add(casilla.getPais());
            
        //Devolvemos el ArrayList resultante        
        return fronteras;
    }
}
