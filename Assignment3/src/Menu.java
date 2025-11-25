package risk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * COSAS A MODIFICAR: //Creación de las cartas correspondientes al país en
 * leerPaises //Cambiar cartas todas y repartir ejercitos //Ver si 6 cartas
 * //Error 121 tiene sentido?
 */
public class Menu {
    // En esta clase se deberían de definir los atributos a los que será 
    // necesario acceder durante la ejecución del programa como, por ejemplo,
    // el mapa o los jugadores

    HashMap<String, Jugador> jugadores = new HashMap<>();
    HashMap<String, Pais> paises = new HashMap<>();
    HashMap<String, Continente> continentes = new HashMap<>();
    HashMap<String, Mision> misiones = new HashMap<>();
    HashMap<String, String> abreviaturas = new HashMap<>();
    HashMap<String, String> nombres = new HashMap<>();
    HashMap<String, Carta> cartas = new HashMap<>();
    ArrayList<String> colores = new ArrayList<>();
    ArrayList<String> coloresUsados = new ArrayList<>();
    ArrayList<String> misionesAsignadas = new ArrayList<>();
    ArrayList<String> paisesAsignados = new ArrayList<>();
    ArrayList<String> turnos = new ArrayList<>();                               //Guarda los nombres de los jugadores en el orden de los turnos
    Jugador jugadorTurno;                                                       //Jugador que está jugando el turno actual

    String out = new String();

    //Contador comandos
    boolean mapaCreado = false;
    //boolean misionesAsignadasBool = false;

    boolean batallaGanada = false;

    Mapa mapa = new Mapa();

    /**
     *
     */
    public Menu() {
        // Inicialización de algunos atributos
        leerContinentes();
        leerPaises();
        leerColores();
        leerMisiones();

        // Iniciar juego
        String orden = null;

        do {
            Risk.consola.Imprimir("\nOrden:");
            orden = Risk.consola.leer();
            String[] partes = orden.split(" ");
            String comando = partes[0];
            // COMANDOS INICIALES PARA EMPEZAR A JUGAR
            //    crear mapa
            //    crear jugadores <nombre_fichero>
            //    crear <nombre_jugador> <nombre_color>
            //    asignar misiones
            //    asignar paises <nombre_fichero>
            //    asignar <nombre_pais> <nombre_jugador>

            // COMANDOS DISPONIBLES DURANTE EL JUEGO
            //    acabar
            //    atacar <nombre_pais> <nombre_pais>
            //    describir continente <nombre_continente>
            //    describir frontera <nombre_pais>
            //    describir frontera <nombre_continente>
            //    describir jugador <nombre_jugador>
            //    describir pais <nombre_pais>
            //    jugador
            //    repartir ejercitos
            //    ver mapa
            //    ver pais <nombre_pais>
            try {
                switch (comando) {

                    case "crear":

                        if (partes.length == 2) {
                            //COMANDO 1
                            if (partes[1].equals("mapa")) {
                                if (!mapaCreado) {
                                    crearMapa();
                                    mapaCreado = true;
                                } else {
                                    ExcepcionGeo error = new ExcepcionGeo("107");
                                    throw (error);
                                }

                            } else {
                                //new Errores(101);
                                ExcepcionComando error = new ExcepcionComando("101");
                                throw (error);
                            }
                        } //COMANDO 3
                        else if (partes.length == 3) {
                            if ((misionesAsignadas() == false) && (jugadores.size() < 6)) {
                                if (partes[1].equals("jugadores")) {
                                    crearJugador(new File(partes[2]));
                                } //COMANDO 2
                                else {
                                    crearJugador(partes[1], partes[2]);
                                }
                            } else {
                                //new Errores(99);
                                ExcepcionComando error = new ExcepcionComando("99");
                                throw (error);
                            }
                        } else {
                            //new Errores(101);
                            ExcepcionComando error = new ExcepcionComando("101");
                            throw (error);
                        }

                        break;

                    case "asignar":

                        switch (partes[1]) {
                            case "paises":
                                //COMANDO 7
                                if (!misionesAsignadas()) {
                                    //new Errores(118);
                                    ExcepcionMision error = new ExcepcionMision("118");
                                    throw (error);
                                } else {
                                    repartirEjercitosIniciales();
                                    asignarPaises(new File(partes[2]));
                                }
                                break;

                            case "misiones":
                                //COMANDO 5
                                if (partes.length != 3) {
                                    ExcepcionComando error = new ExcepcionComando("101");
                                    throw (error);
                                }
                                asignarMisiones(new File(partes[2]));
                                break;

                            case "mision":
                                //COMANDO 4
                                if (jugadores.size() > 2) {
                                    asignarMisiones(partes[2], partes[3]);
                                } else {
                                    //new Errores(105);
                                    ExcepcionJugador error = new ExcepcionJugador("105");
                                    throw (error);
                                }
                                break;

                            case "pais":
                                //COMANDO 6
                                if (!misionesAsignadas()) {
                                    //new Errores(118);
                                    ExcepcionMision error = new ExcepcionMision("118");
                                    throw (error);
                                } else {
                                    asignarPaises(partes[2], partes[3]);
                                }
                                break;

                            case "carta":
                                if (partes.length != 3) {
                                    ExcepcionComando error = new ExcepcionComando("101");
                                    throw (error);
                                }
                                //COMANDO 21
                                asignarCarta(jugadorTurno, partes[2]);
                                break;

                            default:
                                //new Errores(101);
                                ExcepcionComando error = new ExcepcionComando("101");
                                throw (error);
                        }

                        break;

                    case "obtener":

                        switch (partes[1]) {
                            case "frontera":
                                if (!paises.containsKey(partes[2])) {
                                    //new Errores(109);
                                    ExcepcionGeo error = new ExcepcionGeo("109");
                                    throw (error);
                                } else {
                                    ArrayList<Pais> fronteras = mapa.obtenerFrontera(paises.get(partes[2]));
                                    out = "{\n  frontera: [";
                                    for (int i = 0; i < fronteras.size(); i++) {
                                        out = out + " \"" + fronteras.get(i).getNombre() + "\"";
                                        if (i != fronteras.size() - 1) {
                                            out = out + ",";
                                        }
                                    }
                                    out = out + " ]\n}\n";
                                    Risk.consola.Imprimir(out);
                                }
                                break;

                            case "color":
                                if (!paises.containsKey(partes[2])) {
                                    //new Errores(109);
                                    ExcepcionGeo error = new ExcepcionGeo("109");
                                    throw (error);
                                } else if (!colores.contains(paises.get(partes[2]).getContinente().getColor())) {
                                    //new Errores(100);
                                    ExcepcionGeo error = new ExcepcionGeo("100");
                                    throw (error);
                                } else if (colores.contains(paises.get(partes[2]).getContinente().getColor()) && paises.containsKey(partes[2])) {
                                    out = "{ color: \"" + paises.get(partes[2]).getContinente().getColor() + "\" }\n";
                                    Risk.consola.Imprimir(out);
                                }
                                break;

                            case "continente":
                                if (!paises.containsKey(partes[2])) {
                                    //new Errores(109);
                                    ExcepcionGeo error = new ExcepcionGeo("109");
                                    throw (error);
                                } else if (!continentes.containsKey(abreviaturas.get(paises.get(partes[2]).getContinente().getNombre()))) {
                                    //new Errores(102);
                                    ExcepcionGeo error = new ExcepcionGeo("102");
                                    throw (error);
                                } else if (colores.contains(paises.get(partes[2]).getContinente().getColor()) && paises.containsKey(partes[2])) {
                                    out = "{ continente: \"" + paises.get(partes[2]).getContinente().getNombre() + "\" }\n";
                                    Risk.consola.Imprimir(out);
                                }
                                break;

                            case "paises":
                                if (!continentes.containsKey(partes[2])) {
                                    //new Errores(102);
                                    ExcepcionGeo error = new ExcepcionGeo("102");
                                    throw (error);
                                } else {
                                    ArrayList<Pais> imprimir = continentes.get(partes[2]).getPaises();
                                    out = "{\n  paises: [ ";
                                    for (int i = 0; i < imprimir.size(); i++) {
                                        out = out + "\"" + imprimir.get(i).getNombre() + "\"";
                                        if (i != imprimir.size() - 1) {
                                            out = out + ",";
                                            out = out + "\n            ";
                                        } else {
                                            out = out + "\n          ]\n";
                                        }
                                    }
                                    out = out + "}";
                                    Risk.consola.Imprimir(out);
                                }
                                break;

                            default:
                                //new Errores(101);
                                ExcepcionComando error = new ExcepcionComando("101");
                                throw (error);
                        }

                        break;

                    case "describir":
                        switch (partes[1]) {
                            case "jugador":
                                //COMANDO 14
                                if (!jugadores.containsKey(partes[2])) {
                                    //new Errores(103);
                                    ExcepcionJugador error = new ExcepcionJugador("103");
                                    throw (error);
                                } else {
                                    out = jugadores.get(partes[2]).toString(jugadorTurno.equals(jugadores.get(partes[2])));
                                    Risk.consola.Imprimir(out);
                                }
                                break;

                            case "pais":
                                //COMANDO 15
                                if (!paises.containsKey(partes[2])) {
                                    //new Errores(103);
                                    ExcepcionJugador error = new ExcepcionJugador("103");
                                    throw (error);
                                } else {
                                    out = paises.get(partes[2]).toString();
                                    Risk.consola.Imprimir(out);
                                }
                                break;

                            case "continente":
                                //COMANDO 16
                                if (!continentes.containsKey(partes[2])) {
                                    //new Errores(102);
                                    ExcepcionGeo error = new ExcepcionGeo("102");
                                    throw (error);
                                } else {
                                    out = continentes.get(partes[2]).toString();
                                    Risk.consola.Imprimir(out);
                                }
                                break;
                        }

                        break;

                    case "ver":
                        //COMANDO 17
                        if (partes[1].equals("mapa")) {
                            mapa.verMapa();
                        } else {
                            //new Errores(101);
                            ExcepcionComando error = new ExcepcionComando("101");
                            throw (error);
                        }
                        break;

                    case "jugador":
                        //COMANDO 13
                        //Imprimimos la descripción del jugador
                        out = jugadorTurno.toString(true);
                        Risk.consola.Imprimir(out);
                        break;

                    case "acabar":
                        if (partes[1].equals("turno")) {
                            //COMANDO 12
                            //Igualamos jugadorTurno al siguiente, obteniéndolo
                            //del mapa con el nombre de la lista turnos
                            acabarTurno();
                        } else {
                            //new Errores(101);
                            ExcepcionComando error = new ExcepcionComando("101");
                            throw (error);
                        }
                        break;

                    case "cambiar":
                        if (partes[1].equals("cartas")) {
                            if (partes[2].equals("todas")) {
                                //COMANDO 11
                                cambiarCartasTodas();
                            } else {
                                //COMANDO 10
                                cambiarCartas(jugadorTurno, partes[2], partes[3], partes[4]);
                            }
                        }
                        break;

                    case "repartir":
                        if (partes[1].equals("ejercitos")) {
                            if (partes.length == 2) {
                                //COMANDO 9
                                repartirEjercitos();
                            } else if (partes.length == 4) {
                                //COMANDO 8
                                repartirEjercitos(Integer.valueOf(partes[2]), partes[3]);
                            } else {
                                //new Errores(101);
                                ExcepcionComando error = new ExcepcionComando("101");
                                throw (error);
                            }
                        } else {
                            //new Errores(101);
                            ExcepcionComando error = new ExcepcionComando("101");
                            throw (error);
                        }
                        break;

                    case "atacar":
                        if (partes.length == 5) {
                            //COMANDO 19
                            atacar(partes[1], partes[2], partes[3], partes[4]);
                        } else if (partes.length == 3) {
                            //COMANDO 18
                            atacar(partes[1], partes[2]);
                        } else {
                            //new Errores(101);
                            ExcepcionComando error = new ExcepcionComando("101");
                            throw (error);
                        }
                        break;

                    case "rearmar":
                        if (partes.length == 4) {
                            //COMANDO 20
                            rearmar(partes[1], Integer.valueOf(partes[2]), partes[3]);
                        } else {
                            //new Errores(101);
                            ExcepcionComando error = new ExcepcionComando("101");
                            throw (error);
                        }
                        break;

                    default:
                        //new Errores(101);
                        ExcepcionComando error = new ExcepcionComando("101");
                        throw (error);
                }
            } catch (ExcepcionCarta error) {
                error.imprimirError();
            } catch (ExcepcionJugador error) {
                error.imprimirError();
            } catch (ExcepcionGeo error) {
                error.imprimirError();
            } catch (ExcepcionComando error) {
                error.imprimirError();
            } catch (ExcepcionMision error) {
                error.imprimirError();
            }
        } while (!orden.equals("salir"));
    }

    private void leerContinentes() {
        String linea, partes[];
        BufferedReader bufferLector = null;
        try {
            File fichero = new File("lecContinentes.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                continentes.put(partes[2], new Continente(partes[0], partes[1], partes[2]));
                abreviaturas.put(partes[0], partes[2]);
                nombres.put(partes[2], partes[0]);
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    private void leerPaises() {
        String linea, partes[];
        BufferedReader bufferLector = null;
        try {
            File fichero = new File("lecPaises.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                paises.put(partes[1], new Pais(partes[0], partes[1], continentes.get(partes[2])));
                //Creación de las cartas correspondientes al país
                //cartas.put(new String("Infantería&" + partes[1]), new Carta(new String("Infantería"), paises.get(partes[1])));
                //cartas.put(new String("Caballería&" + partes[1]), new Carta(new String("Caballería"), paises.get(partes[1])));
                //cartas.put(new String("Artillería&" + partes[1]), new Carta(new String("Artillería"), paises.get(partes[1])));
                abreviaturas.put(partes[0], partes[1]);
                nombres.put(partes[1], partes[0]);
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    public void leerColores() {
        colores.add("ROJO");
        colores.add("AMARILLO");
        colores.add("AZUL");
        colores.add("VERDE");
        colores.add("CYAN");
        colores.add("VIOLETA");
    }

    private void leerMisiones() {
        String linea, partes[];
        BufferedReader bufferLector = null;
        try {
            File fichero = new File("lecMisiones.csv");
            FileReader lector = new FileReader(fichero);
            bufferLector = new BufferedReader(lector);
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                misiones.put(partes[1], new Mision(partes[0], partes[1]));
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    public void asignarPaises(File file) throws ExcepcionMision {

        if (!misionesAsignadas()) {
            ExcepcionMision error = new ExcepcionMision("118");
            throw (error);
        } else {
            // Código necesario para asignar países       
            String linea, partes[];
            BufferedReader bufferLector = null;
            try {
                FileReader lector = new FileReader(file);
                bufferLector = new BufferedReader(lector);
                while ((linea = bufferLector.readLine()) != null) {
                    partes = linea.split(";");
                    try {
                        asignarPaises(partes[0], partes[1]);
                    } catch (ExcepcionJugador error) {
                        error.imprimirError();
                    } catch (ExcepcionGeo error) {
                        error.imprimirError();
                    }
                }
            } catch (IOException excepcion) {
                excepcion.printStackTrace();
            }
        }
    }

    public void asignarPaises(String nombreJugador, String abreviaturaPais) throws ExcepcionJugador, ExcepcionGeo {
        // Código necesario para asignar un país a un jugador
        if (!jugadores.containsKey(nombreJugador)) {
            //new Errores(103);
            ExcepcionJugador error = new ExcepcionJugador("103");
            throw (error);
        } else if (!paises.containsKey(abreviaturaPais)) {
            //Comprobamos el pais existe
            //new Errores(109);
            ExcepcionGeo error = new ExcepcionGeo("109");
            throw (error);
        } /*Comprobamos que el pais no ha sido asignado previamente*/ else if (paisesAsignados.contains(abreviaturaPais)) {
            //new Errores(113);
            ExcepcionJugador error = new ExcepcionJugador("113");
            throw (error);
        } else if (jugadores.containsKey(nombreJugador) && paises.containsKey(abreviaturaPais)) {
            jugadores.get(nombreJugador).addNumeroEjercitos(-1);
            paisesAsignados.add(abreviaturaPais);
            jugadores.get(nombreJugador).getPaises().add(paises.get(abreviaturaPais));
            if (!jugadores.get(nombreJugador).getContinentes().contains(paises.get(abreviaturaPais).getContinente())) {
                jugadores.get(nombreJugador).getContinentes().add(paises.get(abreviaturaPais).getContinente());
            }
            paises.get(abreviaturaPais).setJugador(jugadores.get(nombreJugador));
            out = "{\n";
            out = out + "  nombre: \"" + nombreJugador + "\",\n";
            out = out + "  pais: \"" + paises.get(abreviaturaPais).getNombre() + "\",\n";
            out = out + "  continente: \"" + paises.get(abreviaturaPais).getContinente().getNombre() + "\",\n";
            //Imprimir fronteras
            ArrayList<Pais> fronteras = paises.get(abreviaturaPais).getFronteras();
            out = out + "  frontera: [";
            for (int i = 0; i < fronteras.size(); i++) {
                out = out + " \"" + fronteras.get(i).getNombre() + "\"";
                if (i != fronteras.size() - 1) {
                    out = out + ",";
                }
            }
            out = out + " ]\n";
            out = out + "}\n";
            Risk.consola.Imprimir(out);
        }

    }

    public void asignarMisiones(File file) throws ExcepcionJugador, ExcepcionMision {
        String linea, partes[];
        BufferedReader bufferLector = null;
        try {
            FileReader lector = new FileReader(file);
            bufferLector = new BufferedReader(lector);
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                try {
                    asignarMisiones(partes[0], partes[1]);
                } catch (ExcepcionJugador error) {
                    error.imprimirError();
                } catch (ExcepcionMision error) {
                    error.imprimirError();
                }
            }
        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
        //catch(FileNotFoundException excepcion){excepcion.printStackTrace();}
    }

    public boolean misionesAsignadas() {
        boolean salida = true;
        for (String jugador : jugadores.keySet()) {
            if (jugadores.get(jugador).getMision().getId().equals("0")) {
                salida = false;
            }
        }
        if (jugadores.size() == 0) {
            salida = false;
        }
        return salida;
    }

    public void asignarMisiones(String nombre, String id) throws ExcepcionJugador, ExcepcionMision {
        if (!jugadores.containsKey(nombre)) {
            //new Errores(103);
            ExcepcionJugador error = new ExcepcionJugador("103");
            throw (error);
        } /*Comprobamos la mision no ha sido asignada*/ else if (misionesAsignadas.contains(id)) {
            //new Errores(115);
            ExcepcionMision error = new ExcepcionMision("115");
            throw (error);
        } else if ((!misiones.containsKey(id))) {
            //new Errores(116);
            ExcepcionMision error = new ExcepcionMision("116");
            throw (error);
        } else if (!jugadores.get(nombre).getMision().getId().equals("0")) {
            //new Errores(117);
            ExcepcionMision error = new ExcepcionMision("117");
            throw (error);
        } else {
            misionesAsignadas.add(id);
            jugadores.get(nombre).setMision(misiones.get(id));
            out = "{\n";
            out = out + "  nombre: \"" + nombre + "\",\n";
            out = out + "  mision: \"" + misiones.get(id).getDescripcion() + "\"\n";
            out = out + "}\n";
            Risk.consola.Imprimir(out);
        }
    }

    /**
     *
     */
    public void crearMapa() throws ExcepcionGeo {
        // Código necesario para crear el mapa
        String linea, partes[];
        if (mapaCreado) {
            //new Errores(107);
            ExcepcionGeo error = new ExcepcionGeo("107");
            throw (error);
        } else {
            try {
                File file = new File("lecPaises.csv");
                FileReader lector = new FileReader(file);
                BufferedReader bufferLector = new BufferedReader(lector);
                while ((linea = bufferLector.readLine()) != null) {
                    partes = linea.split(";");
                    mapa.getCasillas().add(new Casilla(paises.get(partes[1]), Integer.parseInt(partes[3]), Integer.parseInt(partes[4])));
                }
            } catch (Exception excepcion) {
                excepcion.printStackTrace();
            }
            for (Pais pais : paises.values()) {
                pais.setFronteras(mapa.obtenerFrontera(pais));
            }

            mapa.verMapa();
        }
    }

    /**
     *
     * @param file
     */
    private void crearJugador(File file) {
        // Código necesario para crear a los jugadores del RISK
        String linea, partes[];
        try {
            FileReader lector = new FileReader(file);
            BufferedReader bufferLector = new BufferedReader(lector);
            while ((linea = bufferLector.readLine()) != null) {
                partes = linea.split(";");
                try {
                    crearJugador(partes[0], partes[1]);
                } catch (ExcepcionGeo error) {
                    error.imprimirError();
                } catch (ExcepcionJugador error) {
                    error.imprimirError();
                }
            }
        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    private void crearJugador(String nombre, String color) throws ExcepcionGeo, ExcepcionJugador {
        // Código necesario para crear a un jugador a partir de su nombre y color
        if (!mapaCreado) {
            //new Errores(106);
            ExcepcionGeo error = new ExcepcionGeo("106");
            throw (error);
        } else if (!colores.contains(color)) {
            //new Errores(100);
            ExcepcionGeo error = new ExcepcionGeo("100");
            throw (error);
        } else if (jugadores.containsKey(nombre)) {
            //new Errores(104);
            ExcepcionJugador error = new ExcepcionJugador("104");
            throw (error);
        } /*Comprobamos el color no ha sido ya asignado*/ else if (coloresUsados.contains(color)) {
            //new Errores(114);
            ExcepcionJugador error = new ExcepcionJugador("114");
            throw (error);
        } else if (mapaCreado && colores.contains(color) && !jugadores.containsKey(nombre) && !coloresUsados.contains(color)) {
            jugadores.put(nombre, new Jugador(nombre, color));
            out = "{\n  nombre: \"" + nombre + "\",\n  color: \"" + color + "\"\n}\n";
            Risk.consola.Imprimir(out);
            coloresUsados.add(color);
            if (turnos.size() == 0) {
                jugadorTurno = jugadores.get(nombre);
            }
            turnos.add(nombre);
            switch (color) {
                case "AZUL":
                    EjercitoAzul ejercitoAzul = new EjercitoAzul();
                    jugadores.get(nombre).setEjercito(ejercitoAzul);
                    break;
                case "VERDE":
                    EjercitoVerde ejercitoVerde = new EjercitoVerde();
                    jugadores.get(nombre).setEjercito(ejercitoVerde);
                    break;
                case "AMARILLO":
                    EjercitoAmarillo ejercitoAmarillo = new EjercitoAmarillo();
                    jugadores.get(nombre).setEjercito(ejercitoAmarillo);
                    break;
                case "ROJO":
                    EjercitoRojo ejercitoRojo = new EjercitoRojo();
                    jugadores.get(nombre).setEjercito(ejercitoRojo);
                    break;
                case "VIOLETA":
                    EjercitoVioleta ejercitoVioleta = new EjercitoVioleta();
                    jugadores.get(nombre).setEjercito(ejercitoVioleta);
                    break;
                case "CYAN":
                    EjercitoCyan ejercitoCyan = new EjercitoCyan();
                    jugadores.get(nombre).setEjercito(ejercitoCyan);
                    break;
            }
        }
    }

    void repartirEjercitos(int numero, String abrevPais) throws ExcepcionGeo, ExcepcionJugador {
        String out = new String();
        if (!paises.containsKey(abrevPais)) {
            //new Errores(109);
            ExcepcionGeo error = new ExcepcionGeo("109");
            throw (error);
        } else if (!jugadorTurno.getPaises().contains(paises.get(abrevPais))) {
            //new Errores(110);
            ExcepcionJugador error = new ExcepcionJugador("110");
            throw (error);
        } else if (jugadorTurno.getNumeroEjercitos() == 0) {
            //new Errores(119);
            ExcepcionJugador error = new ExcepcionJugador("119");
            throw (error);
        } else {
            int numeroDisponibles = jugadorTurno.getNumeroEjercitos();
            if (numero != 0) {
                if (numero >= numeroDisponibles) {
                    paises.get(abrevPais).addNumeroEjercito(numeroDisponibles);
                    jugadorTurno.setNumeroEjercitos(0);
                } else {
                    paises.get(abrevPais).addNumeroEjercito(numero);
                    jugadorTurno.setNumeroEjercitos(numeroDisponibles - numero);
                }
                out = "{\n";
                out += "  pais: \"" + paises.get(abrevPais).getNombre() + "\",\n";
                out += "  jugador: \"" + jugadorTurno.getNombre() + "\",\n";
                out += "  numeroEjercitosAsignados: " + numero + ",\n";
                out += "  numeroEjercitosTotales: " + paises.get(abrevPais).getNumeroEjercito() + ",\n";
                out += "  paisesOcupadosContinente: [";
                Iterator<Pais> itPais = paises.get(abrevPais).getContinente().getPaises().iterator();
                while (itPais.hasNext()) {
                    Pais pais = itPais.next();
                    out += " { \"" + pais.getNombre() + "\", " + pais.getNumeroEjercito() + " }";
                    if (itPais.hasNext()) {
                        out = out + ", ";
                    } else {
                        out += "]\n";
                    }
                }
                out += "}";
                Risk.consola.Imprimir(out);
            }
        }
    }

    void asignarCarta(Jugador jugador, String idCarta) throws ExcepcionCarta, ExcepcionComando {

        String[] partes = idCarta.split("&");
        //Comprobamos idCorrecto
        if (partes.length != 2) {
            //new Errores(125);
            ExcepcionCarta error = new ExcepcionCarta("125");
            throw (error);
        } else if ((!paises.containsKey(partes[1])) || !(partes[0].equals("Fusilero")
                || partes[0].equals("Granadero") || partes[0].equals("DeCaballo")
                || partes[0].equals("DeCamello") || partes[0].equals("DeCampanha")
                || partes[0].equals("Antiaerea"))) {
            //new Errores(125);
            ExcepcionCarta error = new ExcepcionCarta("125");
            throw (error);
        }
        /*if(!batallaGanada){
            //new Errores(99);
            ExcepcionComando error = new ExcepcionComando("99");
            throw(error);
        }*/
        //Comprobamos que la carta no ha sido asignada
        if (cartas.containsKey(idCarta)) {
            //new Errores(126);
            ExcepcionCarta error = new ExcepcionCarta("126");
            throw (error);
        } else {
            switch (partes[0]) {
                case "Fusilero":
                    Fusilero fusilero = new Fusilero(paises.get(partes[1]), jugador);
                    cartas.put(idCarta, fusilero);
                    break;
                case "Granadero":
                    Granadero granadero = new Granadero(paises.get(partes[1]), jugador);
                    cartas.put(idCarta, granadero);
                    break;
                case "DeCaballo":
                    DeCaballo deCaballo = new DeCaballo(paises.get(partes[1]), jugador);
                    cartas.put(idCarta, deCaballo);
                    break;
                case "DeCamello":
                    DeCamello deCamello = new DeCamello(paises.get(partes[1]), jugador);
                    cartas.put(idCarta, deCamello);
                    break;
                case "DeCampanha":
                    DeCampanha deCampanha = new DeCampanha(paises.get(partes[1]), jugador);
                    cartas.put(idCarta, deCampanha);
                    break;
                case "Antiaerea":
                    Antiaerea artilleria = new Antiaerea(paises.get(partes[1]), jugador);
                    cartas.put(idCarta, artilleria);
                    break;

            }

            //cartas.put(idCarta, carta);
            jugador.getCartas().add(cartas.get(idCarta));
            out = cartas.get(idCarta).toString();
            Risk.consola.Imprimir(out);
        }
        if (jugadorTurno.getCartas().size() >= 6) {
            cambiarCartasTodas();
        }
    }

    //SIN ACABAR
    void cambiarCartasTodas() {

        ArrayList<String> combinacion = maximoCambio(jugadorTurno);
        try {
            cambiarCartas(jugadorTurno, combinacion.get(0), combinacion.get(1), combinacion.get(2));
            combinacion = maximoCambio(jugadorTurno);
            cambiarCartas(jugadorTurno, combinacion.get(0), combinacion.get(1), combinacion.get(2));
        } catch (ExcepcionCarta excepcion) {
            excepcion.imprimirError();
        };

    }

    void cambiarCartas(Jugador jugador, String carta1, String carta2, String carta3) throws ExcepcionCarta {
        //Indica si se realiza el cambio
        boolean cambio = false;
        int ejercitos = 0;
        ArrayList<Carta> cartasCambiar = new ArrayList<>();
        //Comprobamos carta existe, es decir, si ha sido asignada anteriormente
        if (!(cartas.keySet().contains(carta1) && cartas.keySet().contains(carta2) && cartas.keySet().contains(carta3))) {
            //new Errores(123);
            ExcepcionCarta error = new ExcepcionCarta("123");
            throw (error);
        } //Comprobamos que la carta ha sido asignada al jugador        
        else if (!(jugador.getCartas().contains(cartas.get(carta1)) && jugador.getCartas().contains(cartas.get(carta2)) && jugador.getCartas().contains(cartas.get(carta3)))) {
            //new Errores(122);
            ExcepcionCarta error = new ExcepcionCarta("122");
            throw (error);
        } else {
            int infF = 0, infG = 0, cabCam = 0, cabCab = 0, artCam = 0, artA = 0;
            //Añadimos las cartas a una lista para acceder a ellas con facilidad
            cartasCambiar.add(cartas.get(carta1));
            cartasCambiar.add(cartas.get(carta2));
            cartasCambiar.add(cartas.get(carta3));
            for (Carta carta : cartasCambiar) {
                if (carta.getTipo().equals("Fusilero")) {
                    infF++;
                } else if (carta.getTipo().equals("Granadero")) {
                    infG++;
                } else if (carta.getTipo().equals("DeCaballo")) {
                    cabCab++;
                } else if (carta.getTipo().equals("DeCamello")) {
                    cabCam++;
                } else if (carta.getTipo().equals("DeCampanha")) {
                    artCam++;
                } else {
                    artA++;
                }
            }
            //Realizamos el cambio acorde a las cartas
            if (infF + infG == 3) {
                ejercitos = 6;
                cambio = true;
            } else if (cabCab + cabCam == 3) {
                ejercitos = 8;
                cambio = true;
            } else if (artCam + artA == 3) {
                ejercitos = 10;
                cambio = true;
            } else if ((infF + infG == 1) && (cabCab + cabCam == 1) && (artCam + artA == 1)) {
                ejercitos = 12;
                cambio = true;
            } else {
                //new Errores(121);
                ExcepcionCarta error = new ExcepcionCarta("121");
                throw (error);
            }
            //Si se ha realizado el cambio, se hace lo siguiente
            if (cambio) {
                ejercitos += infG + infF * 2 + cabCam * 2 + cabCab * 3 + artA * 3 + artCam * 4;
                jugador.setNumeroEjercitos(jugador.getNumeroEjercitos() + ejercitos);
                for (Carta carta : cartasCambiar) {
                    //Quitamos las cartas al jugador
                    cartas.remove(carta.obtenerNombre());
                    jugador.getCartas().remove(carta);
                    //Si la carta es de un país del jugador añadimos un ejército
                    //adicional
                    if (carta.getPais().getJugador().equals(jugador)) {
                        carta.getPais().addNumeroEjercito(1);
                        ejercitos++;
                    }
                }
                //Mensaje de salida
                out = "{\n";
                out = out + "  cartasCambio: [ \"" + carta1 + "\", \"" + carta2 + "\", \"" + carta3 + "\" ],\n";
                out = out + "  cartasQuedan: [ ";
                for (int i = 0; i < jugador.getCartas().size(); i++) {
                    out = out + jugador.getCartas().get(i).obtenerNombre();
                    if (i != jugador.getCartas().size() - 1) {
                        out = out + ", ";
                    }
                }
                out = out + " ],\n  numeroEjercitosCambiados: " + ejercitos + ",\n";
                out = out + "  numEjercitosRearme: " + jugadorTurno.getNumeroEjercitos() + "\n}";
                Risk.consola.Imprimir(out);
            }
        }
    }

    void atacar(String abrevPais1, String abrevPais2) throws ExcepcionGeo, ExcepcionJugador {
        if ((!paises.keySet().contains(abrevPais1)) || (!paises.keySet().contains(abrevPais2))) {
            //new Errores(109);
            ExcepcionGeo error = new ExcepcionGeo("109");
            throw (error);
        } else {
            Pais pais1 = paises.get(abrevPais1);
            Pais pais2 = paises.get(abrevPais2);
            if (!pais1.getJugador().equals(jugadorTurno)) {
                //new Errores(110);
                ExcepcionJugador error = new ExcepcionJugador("110");
                throw (error);
            } else if (pais2.getJugador().equals(jugadorTurno)) {
                //new Errores(111);
                ExcepcionJugador error = new ExcepcionJugador("111");
                throw (error);
            } else if (!pais1.getFronteras().contains(pais2)) {
                //new Errores(112);
                ExcepcionGeo error = new ExcepcionGeo("112");
                throw (error);
            } else if (pais1.getNumeroEjercito() == 1) {
                //new Errores(124);
                ExcepcionJugador error = new ExcepcionJugador("124");
                throw (error);
            } else {
                ArrayList<Integer> dadosAtaque = Dado.dadoAtaque(pais1.getNumeroEjercito());
                ArrayList<Integer> dadosDefensa = Dado.dadoDefensa(pais2.getNumeroEjercito());
                String dados1 = new String();
                dados1 = "";
                String dados2 = new String();
                dados2 = "";
                for (int i = dadosAtaque.size() - 1; i >= 0; i--) {
                    dados1 = dados1 + dadosAtaque.get(i).intValue();
                    if (i != 0) {
                        dados1 = dados1 + "x";
                    }
                }
                for (int i = dadosDefensa.size() - 1; i >= 0; i--) {
                    dados2 = dados2 + dadosDefensa.get(i).intValue();
                    if (i != 0) {
                        dados2 = dados2 + "x";
                    }
                }
                atacar(abrevPais1, dados1, abrevPais2, dados2);
            }
        }
    }

    void atacar(String abrevPais1, String dadosAtaque, String abrevPais2, String dadosDefensa) throws ExcepcionGeo, ExcepcionJugador {
        int dado = 0, conquista = 0, ejercitosAtaque1 = 0, ejercitosAtaque2 = 0, ejercitosDefensa1 = 0, ejercitosDefensa2 = 0;
        ArrayList<Integer> dadoAtaque = new ArrayList<>();
        ArrayList<Integer> dadoDefensa = new ArrayList<>();

        //Convertimos las Strings de dados a ArrayList de Integer
        //No se pueden hacer ArrayList de tipo primitivo int, por eso usamos
        //Integer
        String partes[] = partes = dadosAtaque.split("x");
        for (String num : partes) {
            dadoAtaque.add(Integer.valueOf(num));
        }
        partes = dadosDefensa.split("x");
        for (String num : partes) {
            dadoDefensa.add(Integer.valueOf(num));
        }

        //Hacemos comprobaciones de erroes
        if (!paises.keySet().contains(abrevPais1) || !paises.keySet().contains(abrevPais2)) {
            //new Errores(109);
            ExcepcionGeo error = new ExcepcionGeo("109");
            throw (error);
        } else if (!paises.get(abrevPais1).getJugador().equals(jugadorTurno)) {
            //new Errores(110);
            ExcepcionJugador error = new ExcepcionJugador("110");
            throw (error);
        } else if (paises.get(abrevPais2).getJugador().equals(jugadorTurno)) {
            //new Errores(111);
            ExcepcionJugador error = new ExcepcionJugador("111");
            throw (error);
        } else if (!paises.get(abrevPais1).getFronteras().contains(paises.get(abrevPais2))) {
            //new Errores(112);
            ExcepcionGeo error = new ExcepcionGeo("112");
            throw (error);
        } else if (paises.get(abrevPais1).getNumeroEjercito() < 2) {
            //new Errores(124);
            ExcepcionJugador error = new ExcepcionJugador("124");
            throw (error);
        } else {

            ejercitosAtaque1 = paises.get(abrevPais1).getNumeroEjercito();
            ejercitosDefensa1 = paises.get(abrevPais2).getNumeroEjercito();

            //Actualizamos los valores del dado de ataque para la jerarquía ejércitos        
            dadoAtaque = paises.get(abrevPais1).getJugador().getEjercito().ataque(dadoAtaque);

            //Actualizamos el dado en función de los ejercitos del atacante
            dado = Integer.min(dadoAtaque.size(), dadoDefensa.size());

            //Comparamos los dados
            for (int i = 0; i < dado; i++) {
                //Si el dado de ataque es mayor, quitamos un ejército a los
                //defensores
                if (dadoAtaque.get(i).intValue() > dadoDefensa.get(i).intValue()) {
                    paises.get(abrevPais2).addNumeroEjercito(-1);
                } //Si el dado de defensa es mayor o igual, quitamos un ejército a los
                //atacantes
                else {
                    paises.get(abrevPais1).addNumeroEjercito(-1);
                }
            }

            //Si no le quedan ejércitos al defensor, se hace la conquista
            if (paises.get(abrevPais2).getNumeroEjercito() == 0) {
                //Comprobamos cuantos ejércitos puede mover el país atacante, si
                //tiene menos de 4, tendremos que sacar el mayor número posible de
                //ejércitos para conquistar el nuevo país
                conquista = Integer.min(dadoAtaque.size(), paises.get(abrevPais1).getNumeroEjercito() - 1);
                //Se actualiza el número de ejércitos en los países
                paises.get(abrevPais1).addNumeroEjercito(-conquista);
                paises.get(abrevPais2).addNumeroEjercito(conquista);
                paises.get(abrevPais2).getJugador().getPaises().remove(paises.get(abrevPais2));
                paises.get(abrevPais2).setJugador(jugadorTurno);
                jugadorTurno.getPaises().add(paises.get(abrevPais2));

                batallaGanada = true;
                paises.get(abrevPais2).aumentarVecesOcupado();
            }

            ejercitosAtaque2 = paises.get(abrevPais1).getNumeroEjercito();
            ejercitosDefensa2 = paises.get(abrevPais2).getNumeroEjercito();

            //Creamos el mensaje de salida
            String out = new String();
            out += "{\n";
            out += "dadosAtaque: [ ";
            for (int i = 0; i < dadoAtaque.size(); i++) {
                out += dadoAtaque.get(i);
                if (i < dadoAtaque.size() - 1) {
                    out += ", ";
                } else {
                    out += " ],\n";
                }
            }
            out += "dadosDefensa: [ ";
            for (int i = 0; i < dadoDefensa.size(); i++) {
                out += dadoDefensa.get(i);
                if (i < dadoDefensa.size() - 1) {
                    out += ", ";
                } else {
                    out += " ],\n";
                }
            }
            out += "ejercitospaisAtaque: [ " + ejercitosAtaque1 + ", " + ejercitosAtaque2 + " ],\n";
            out += "ejercitospaisDefensa: [ " + ejercitosDefensa1 + ", " + ejercitosDefensa2 + " ],\n";
            out += "paisAtaquePerteneceA: \"" + jugadorTurno.getNombre() + "\",\n";
            out += "paisDefensaPerteneceA: \"" + paises.get(abrevPais2).getJugador().getNombre() + "\",\n";
            out += "continenteConquistado: \"";
            if (jugadorTurno.getContinentes().contains(paises.get(abrevPais2).getContinente())) {
                out += paises.get(abrevPais2).getContinente().getNombre() + "\"\n";
            } else {
                out += "null\"\n";
            }
            out += "}";
            Risk.consola.Imprimir(out);
        }
    }

    public void rearmar(String abrevPais1, int numEjercitos, String abrevPais2) throws ExcepcionGeo, ExcepcionJugador {
        String out = new String();
        if ((!paises.keySet().contains(abrevPais1)) || (!paises.keySet().contains(abrevPais2))) {
            //new Errores(109);
            ExcepcionGeo error = new ExcepcionGeo("109");
            throw (error);
        } else {
            Pais pais1 = paises.get(abrevPais1);
            Pais pais2 = paises.get(abrevPais2);
            if ((!pais1.getJugador().equals(jugadorTurno)) || (!pais2.getJugador().equals(jugadorTurno))) {
                //new Errores(110);
                ExcepcionJugador error = new ExcepcionJugador("110");
                throw (error);
            } else if (!pais1.getFronteras().contains(pais2)) {
                //new Errores(112);
                ExcepcionGeo error = new ExcepcionGeo("112");
                throw (error);
            } else if (pais1.getNumeroEjercito() < 1) {
                //new Errores(124);
                ExcepcionJugador error = new ExcepcionJugador("124");
                throw (error);
            } else {
                int init1 = pais1.getNumeroEjercito();
                int init2 = pais2.getNumeroEjercito();
                if (numEjercitos >= pais1.getNumeroEjercito()) {
                    pais2.addNumeroEjercito(pais1.getNumeroEjercito() - 1);
                    pais1.setNumeroEjercito(1);
                } else {
                    pais2.addNumeroEjercito(numEjercitos);
                    pais1.addNumeroEjercito(-numEjercitos);
                }
                out = "{\n";
                out = out + "  numeroEjercitosInicialesOrigen: " + init1 + ",\n";
                out = out + "  numeroEjercitosInicialesDestino: " + init2 + ",\n";
                out = out + "  numeroEjercitosFinalesOrigen: " + pais1.getNumeroEjercito() + ",\n";
                out = out + "  numeroEjercitosFinalesDestino: " + pais2.getNumeroEjercito() + ",\n";
                out = out + "}";
                Risk.consola.Imprimir(out);
            }
        }
    }

    void acabarTurno() {
        jugadorTurno = jugadores.get(turnos.get((turnos.indexOf(jugadorTurno.getNombre()) + 1) % turnos.size()));
        batallaGanada = false;
        //Imprimimos por pantalla y al archivo un mensaje
        out = "{\n";
        out = out + "  nombre: \"" + jugadorTurno.getNombre() + "\"\n";
        out = out + "  numeroEjercitosRearmar: " + jugadorTurno.getNumeroEjercitos() + "\n";
        out = out + "}\n";
        Risk.consola.Imprimir(out);
        //Escogemos el maximo entre 3 y el numero de paises entre 3 para rearmar al principio del turno
        jugadorTurno.addNumeroEjercitos(Integer.max((int) jugadorTurno.getPaises().size() / 3, 3));

        if (!jugadorTurno.getContinentes().isEmpty()) {
            for (Continente continente : jugadorTurno.getContinentes()) {
                if (continente.getAbreviatura().equals("Asia")) {
                    jugadorTurno.addNumeroEjercitos(7);
                } else if (continente.getAbreviatura().equals("AméricaNorte") || continente.getAbreviatura().equals("Oceanía")) {
                    jugadorTurno.addNumeroEjercitos(5);
                } else if (continente.getAbreviatura().equals("África")) {
                    jugadorTurno.addNumeroEjercitos(3);
                } else if (continente.getAbreviatura().equals("AméricaSur") || continente.getAbreviatura().equals("Oceanía")) {
                    jugadorTurno.addNumeroEjercitos(2);
                }
            }
        }

    }

    //Método para asignar ejércitos iniciales para poder ocupar con un ejército en
    //asignar paises
    void repartirEjercitosIniciales() {
        int ejercitos;
        switch (jugadores.keySet().size()) {
            case 3:
                ejercitos = 35;
                break;
            case 4:
                ejercitos = 30;
                break;
            case 5:
                ejercitos = 25;
                break;
            case 6:
                ejercitos = 20;
                break;
            default:
                ejercitos = 0;
                break;
        }

        for (String nombre : jugadores.keySet()) {
            jugadores.get(nombre).setNumeroEjercitos(ejercitos);
        }

    }

    ArrayList<String> maximoCambio(Jugador jugador) {

        ArrayList<String> combinacion = new ArrayList<>();
        int suma = 0;
        int infF = 0, infG = 0, cabCam = 0, cabCab = 0, artCam = 0, artA = 0;
        ArrayList<String> tipos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            tipos.add("");
        }
        //Contamos el numero de cartas
        for (Carta carta : jugadorTurno.getCartas()) {
            if (carta.getTipo().equals("Fusilero")) {
                infF++;
            } else if (carta.getTipo().equals("Granadero")) {
                infG++;
            } else if (carta.getTipo().equals("DeCaballo")) {
                cabCab++;
            } else if (carta.getTipo().equals("DeCamello")) {
                cabCam++;
            } else if (carta.getTipo().equals("DeCampanha")) {
                artCam++;
            } else {
                artA++;
            }
        }

        //Buscamos solo 3 cartas, paramos cuando contador = 3 
        if ((infF + infG > 0) && (cabCam + cabCab > 0) && (artCam + artA > 0)) {
            suma = 18 + Integer.min(1, infF) + Integer.min(1, cabCab) + Integer.min(1, artCam);
            if (infF > 0) {
                tipos.set(0, "Fusilero");
            } else {
                tipos.set(0, "Granadero");
            }
            if (cabCab > 0) {
                tipos.set(1, "DeCaballo");
            } else {
                tipos.set(1, "DeCamello");
            }
            if (artCam > 0) {
                tipos.set(2, "DeCampanha");
            } else {
                tipos.set(2, "Antiaerea");
            }

        }
        if ((artCam + artA >= 3)) {
            suma = Integer.max(suma, 19 + Integer.min(3, artCam));
            for (int i = 1; i <= 3; i++) {
                if (i <= artCam) {
                    tipos.set(i - 1, "DeCampanha");
                } else {
                    tipos.set(i - 1, "Antiaerea");
                }
            }
        }
        if ((cabCab + cabCam >= 3)) {
            suma = Integer.max(suma, 14 + Integer.min(3, cabCab));
            for (int i = 1; i <= 3; i++) {
                if (i <= cabCab) {
                    tipos.set(i - 1, "DeCaballo");
                } else {
                    tipos.set(i - 1, "DeCamello");
                }
            }
        }
        if ((infF + infG >= 3)) {
            suma = Integer.max(suma, 19 + Integer.min(3, infF));
            for (int i = 1; i <= 3; i++) {
                if (i <= infF) {
                    tipos.set(i - 1, "Fusilero");
                } else {
                    tipos.set(i - 1, "Granadero");
                }
            }
        }

        for (Carta carta : jugador.getCartas()) {
            for (int i = 0; i < 3; i++) {
                if (carta.getTipo().equals(tipos.get(i)) && (!combinacion.contains(carta.obtenerNombre()))) {
                    combinacion.add(carta.obtenerNombre());
                    tipos.set(i, "");
                }
            }
        }
        return combinacion;

    }

    void repartirEjercitos() {
        int indicadorR1, indicadorR4, indicadorR7;

        

        /*Creamos un HashMap para relacionar los paises ocupados en un continente*/
        //HashMap<String, int> paisesOcupados = new HashMap<>();

        /*Recorremos los jugadores*/
        Iterator<String> itJugadores = jugadores.keySet().iterator();
        while (itJugadores.hasNext()) {
            String nombreJugador = itJugadores.next();
            
            ArrayList<Continente> continentesR1 = new ArrayList<>();
            ArrayList<Continente> continentesR4 = new ArrayList<>();
            ArrayList<Continente> continentesR7 = new ArrayList<>();
            
            indicadorR1 = 0;
            indicadorR4 = 0;
            indicadorR7 = 0;

            /*Comprobamos qué regla se cumple para cada continente.*/
            HashMap<Continente, Float> porcentajes = new HashMap<>();

            for (Pais pais : jugadores.get(nombreJugador).getPaises()) {
                if (!porcentajes.containsKey(pais.getContinente())) {
                    porcentajes.put(pais.getContinente(), new Float(0.0f));
                }
                porcentajes.replace(pais.getContinente(), new Float(porcentajes.get(pais.getContinente()) + 1.f/(float)pais.getContinente().getPaises().size()));
                
            }

            System.out.println("\n-------------");
            System.out.println("\nJugador: " + nombreJugador);

            for (Continente continente : porcentajes.keySet()) {
                if ((porcentajes.get(continente))>= 0.5) {
                    indicadorR1++;
                    continentesR1.add(continente);
                }
                if ((porcentajes.get(continente)) < 0.5 &&(porcentajes.get(continente)) >= 0.25) {
                    indicadorR4++;
                    continentesR4.add(continente);
                }
                if ((porcentajes.get(continente)) <= 0.25) {
                    indicadorR7++;
                    continentesR7.add(continente);
                }
            }

            /*---REGLA 1, 2, 3---*/
            if (indicadorR1 == 1) {
                repartirR123(nombreJugador, 0, porcentajes, continentesR1);
                System.out.println("\nSe cumple regla 1 (" + indicadorR1 + ")");
            }
             
            else if (indicadorR1 > 1) {
                System.out.println("\nSe cumple regla 1 (" + indicadorR1+ ") para: \n");
                for(int i = 0; i < continentesR1.size(); i++)
                    System.out.println(continentesR1.get(i).getNombre() + " ");
                /*En este caso, habrá más de un continente sobre el que aplicar R1
                 *En primer lugar, comprobamos que podemos escoger aquel comntinente tal que para este jugador,
                 *su porcentaje de ocupación sea el máximo de entre el resto de continentes que cumplen R1 */

                if (aplicarMenorNFrontera(maximo(porcentajes, continentesR1), porcentajes, continentesR1) == 0) {
                    /*En el caso de que podamos escoger el máximo porcventaje de ocupación (único),
                    actualizamos la asignación de ejércitos en el continente de porcentaje de 
                    ocupación máxima para este jugador*/ 
                    System.out.println("\nMaximo sobre el que aplicamos: " + continentesR1.get(maximo(porcentajes, continentesR1)).getNombre());
                    repartirR123(nombreJugador, maximo(porcentajes, continentesR1), porcentajes, continentesR1);
                }
                
                else{ /*En otro caso, aplicamos R3: R1 para el continente con menor numero de paises frontera*/ 
                    System.out.println("\nMenorNFrontera: " + continentesR1.get(menorNFrontera(continentesR1)).getNombre());
                    repartirR123(nombreJugador, menorNFrontera(continentesR1), porcentajes, continentesR1);
                    
                }
                

            } 
            /*---REGLA 4, 5, 6---*/ 
            else if (indicadorR4 == 1) {
                repartirR456(nombreJugador, 0, porcentajes, continentesR4);
                System.out.println("\nSe cumple regla 4 (1)");
            }
            
            else if (indicadorR4 > 1) {
                System.out.println("\nSe cumple regla 4 (" + indicadorR4 + ") para: \n");
                for(int i = 0; i < continentesR4.size(); i++)
                    System.out.println(continentesR4.get(i).getNombre());
                /*En primer lugar, comprobamos que podemos escoger aquel comntinente tal que para este jugador,
                su porcentaje de ocupación sea el máximo de entre el resto de continentes que cumplen R4 */
                if (aplicarMenorNFrontera(maximo(porcentajes, continentesR4), porcentajes, continentesR4) == 0) /*En este caso, actualizamos la asignación de ejércitos en el continente de porcentaje de 
                    ocupación máxima para este jugador*/ 
                    repartirR456(nombreJugador, maximo(porcentajes, continentesR4), porcentajes, continentesR4);
                
                else /*En otro caso, aplicamos R6: R4 para el continente con menor numero de paises frontera*/ 
                    repartirR456(nombreJugador, menorNFrontera(continentesR4), porcentajes, continentesR4);
                

            }

            else if (indicadorR7 == 1) {
                repartirR78(nombreJugador, 0, porcentajes, continentesR7);
                System.out.println("\nSe cumple regla 7");

            }
            else
                System.out.println("\nNo se cumple ninguna regla.");

       
    }
}

void repartirR123(String nombreJugador, int i, HashMap<Continente, Float> porcentajesR, ArrayList<Continente> continentesR) {
        int paisesOcupados = 0, num = 0, ejercitosDisponibles = 0;
        double factordivision = 0.0;
        ejercitosDisponibles = (jugadores.get(nombreJugador)).getNumeroEjercitos();
        paisesOcupados = (int)(porcentajesR.get(continentesR.get(i))*continentesR.get(i).getPaises().size());

        /*Asignamos según el criterio de la regla 1*/
        if (continentesR.get(i).getNombre().equals("Oceanía") || continentesR.get(i).getNombre().equals("AméricaSur")) {
            factordivision = 1.5;
        } else {
            factordivision = 1;
        }
        /*Realizamos la asignación de ejércitos para todos los paises del continente ocupados por ese jugador*/
        for (Pais pais : continentesR.get(i).getPaises()) {
            /*En el caso de que busquemos el numero de paises ocupados total: 
            paisesOcupados = jugadores.get(nombreJugador).getPaises().size()*/
            if ((jugadores.get(nombreJugador)).getPaises().contains(pais)) {
                num = (int) ((ejercitosDisponibles) / (factordivision * paisesOcupados));
                pais.setNumeroEjercito(num);
                ejercitosDisponibles = ejercitosDisponibles - num;
                (jugadores.get(nombreJugador)).setNumeroEjercitos(ejercitosDisponibles);
            }
        }
        /*Si hay ejercitos disponibles, asignamos un ejercito a cada pais con 1 ejercito del jugador, priorizando los del continente
        de menor numero de paises frontera*/       
        repartirR36(nombreJugador, porcentajesR, continentesR);
    }

    /*Función para R3R6: añadir un ejército a países del jugador en continente de la regla que tengan un ejército mientras 
      queden ejercitos disponibles*/
    void repartirR36(String nombreJugador, HashMap<Continente, Float> porcentajesR, ArrayList<Continente> continentesR) {
        int j = 0, ejercitosDisponibles = 0, num = 0;
        
        ejercitosDisponibles = (jugadores.get(nombreJugador)).getNumeroEjercitos();
        for(Pais pais : continentesR.get(menorNFrontera(continentesR)).getPaises()){
            while(ejercitosDisponibles > 0 && pais.getNumeroEjercito() == 1){
                //Asignamos los ejercitos a cada pais del jugador
                
                pais.addNumeroEjercito(1);
                //Actualizamos los ejercitos disponibles
                ejercitosDisponibles = ejercitosDisponibles - 1;
                (jugadores.get(nombreJugador)).setNumeroEjercitos(ejercitosDisponibles);
            }            
        }
        continentesR.remove(menorNFrontera(continentesR));        
        if(!continentesR.isEmpty())
            repartirR36(nombreJugador, porcentajesR, continentesR);
    }

    void repartirR456(String nombreJugador, int i, HashMap<Continente, Float> porcentajesR, ArrayList<Continente> continentesR) {
        int paisesOcupados = 0, num = 0, ejercitosDisponibles = 0;
        ejercitosDisponibles = (jugadores.get(nombreJugador)).getNumeroEjercitos();
        paisesOcupados = (int)(porcentajesR.get(continentesR.get(i))*continentesR.get(i).getPaises().size());

        for (Pais pais : continentesR.get(i).getPaises()) {
            /*En el caso de que busquemos el numero de paises ocupados total: 
            paisesOcupados = jugadores.get(nombreJugador).getPaises().size()*/
            if ((jugadores.get(nombreJugador)).getPaises().contains(pais)) {
                num = (int) ((jugadores.get(nombreJugador).getNumeroEjercitos()) / (2 * paisesOcupados));
                pais.setNumeroEjercito(num);
                ejercitosDisponibles -= num;
                (jugadores.get(nombreJugador)).setNumeroEjercitos(ejercitosDisponibles);
            }
        }
        /*Si hay ejercitos disponibles, asignamos un ejercito a cada pais con 1 ejercito del jugador, priorizando los del continente
        de menor numero de paises frontera*/
        repartirR36(nombreJugador, porcentajesR, continentesR);

    }

    void repartirR78(String nombreJugador, int i, HashMap<Continente, Float> porcentajesR, ArrayList<Continente> continentesR) {
        int paisesOcupados = 0, num = 0, ejercitosDisponibles = 0;
        double factorocupacion = 0.0;
        ejercitosDisponibles = (jugadores.get(nombreJugador)).getNumeroEjercitos();
        paisesOcupados = (int)(porcentajesR.get(continentesR.get(i))*continentesR.get(i).getPaises().size());

        /*Asignamos según el criterio de la regla 1*/
        if (jugadores.size() == 3 || jugadores.size() == 4) {
            factorocupacion = 2;
        } else {
            factorocupacion = 3;
        }
        /*Realizamos la asignación de ejércitos para todos los paises del continente ocupados por ese jugador*/

        for (Pais pais : continentesR.get(i).getPaises()) {
            /*En el caso de que busquemos el numero de paises ocupados total: 
            paisesOcupados = jugadores.get(nombreJugador).getPaises().size()*/
            if ((jugadores.get(nombreJugador)).getPaises().contains(pais)) {
                num = (int) (factorocupacion * paisesOcupados);
                pais.setNumeroEjercito(num);
                ejercitosDisponibles -= num;
                (jugadores.get(nombreJugador)).setNumeroEjercitos(ejercitosDisponibles);
            }
        }
        /*Si hay ejercitos disponibles, asignamos un ejercito a cada pais con 1 ejercito del jugador, priorizando los del continente
        de menor numero de paises frontera*/
        repartirR36(nombreJugador, porcentajesR, continentesR);
    }


    /*Función que dado un índice comprueba si se repite el elemento en una lista*/
    int aplicarMenorNFrontera(int i, HashMap<Continente, Float> porcentajes, ArrayList<Continente> continentesR) {
        float aux;
        int contador = -1, j = 0;

        aux = porcentajes.get(continentesR.get(i));

        while ((contador < 1) && (j < continentesR.size())) {
            if (aux == porcentajes.get(continentesR.get(j))) {
                contador++;
            }
            j++;
        }
        return contador;
    }


    /*Función que devuelve el índice donde se encuentra el mayor porcentaje
      de la lista de porcentajes de continentes que cumplen R1, 
      este indice coincide con el de su continente correspondiente en 
      continentesR1*/
 /*Se aplica también para R4 y R7*/
    int maximo(HashMap<Continente, Float> porcentajesR, ArrayList<Continente> continentesR) {
        float max = 0;
        int indice = 0, i = 0;

        while(i < continentesR.size() && i < porcentajesR.keySet().size()) {
            //Comparamos porcentajes para escoger el máximo
            if (porcentajesR.get(continentesR.get(i)).intValue() > max) {
                max = porcentajesR.get(continentesR.get(i)).intValue() ;
                indice = i;
            }
            i++;
        }

        return indice;
    }

    /*Función que devuelve el indice en el que se encuentra el continente con menor numero de paises frontera*/
    int menorNFrontera(ArrayList<Continente> continentesR) {
        int contador = 0, aux = 42, indice = 0;

        for (int i = 0; i < continentesR.size(); i++) {
            for (Pais pais : continentesR.get(i).fronteras()) {
                /*Buscamos el numero de paises frontera dentro del continente i*/
                if (continentesR.get(i).getPaises().contains(pais.getFronteras())) {
                    contador++;
                }

                if (contador < aux) {
                    indice = i;
                    aux = contador;
                }
            }
        }
        return indice;
    }

}
