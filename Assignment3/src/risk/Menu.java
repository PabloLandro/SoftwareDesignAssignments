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
 * COSAS A MODIFICAR: //Creación de las cards correspondientes al país en
 * readCountries //Cambiar cards all y distribute armies //Ver si 6 cards
 * //Error 121 tiene sentido?
 */
public class Menu {
    // En esta clase se deberían de definir los atributos a los que será 
    // necesario acceder durante la ejecución del programa como, por ejemplo,
    // el map o los players

    HashMap<String, Player> players = new HashMap<>();
    HashMap<String, Country> countries = new HashMap<>();
    HashMap<String, Continent> continents = new HashMap<>();
    HashMap<String, Mision> missions = new HashMap<>();
    HashMap<String, String> abbreviations = new HashMap<>();
    HashMap<String, String> names = new HashMap<>();
    HashMap<String, Card> cards = new HashMap<>();
    ArrayList<String> colors = new ArrayList<>();
    ArrayList<String> usedColors = new ArrayList<>();
    ArrayList<String> assignedMissions = new ArrayList<>();
    ArrayList<String> assignedCountries = new ArrayList<>();
    ArrayList<String> turns = new ArrayList<>();                               //Guarda los names de los players en el orden de los turns
    Player turnPlayer;                                                       //Player que está jugando el turno actual

    String out = new String();

    //Contador commands
    boolean mapCreated = false;
    //boolean assignedMissionsBool = false;

    boolean battleWon = false;

    Mapa map = new Mapa();

    /**
     *
     */
    public Menu() {
        // Inicialización de algunos atributos
        readContinents();
        readCountries();
        readColors();
        readMissions();

        // Iniciar juego
        String orden = null;

        do {
            Risk.console.Print("\nOrden:");
            orden = Risk.console.read();
            String[] parts = orden.split(" ");
            String command = parts[0];
            // COMANDOS INICIALES PARA EMPEZAR A JUGAR
            //    crear map
            //    crear players <nombre_fichero>
            //    crear <nombre_jugador> <nombre_color>
            //    asignar missions
            //    asignar countries <nombre_fichero>
            //    asignar <nombre_country> <nombre_jugador>

            // COMANDOS DISPONIBLES DURANTE EL JUEGO
            //    acabar
            //    attack <nombre_country> <nombre_country>
            //    describir continent <nombre_continent>
            //    describir border <nombre_country>
            //    describir border <nombre_continent>
            //    describir player <nombre_jugador>
            //    describir country <nombre_country>
            //    player
            //    distribute armies
            //    ver map
            //    ver country <nombre_country>
            try {
                switch (command) {

                    case "crear":

                        if (parts.length == 2) {
                            //COMANDO 1
                            if (parts[1].equals("map")) {
                                if (!mapCreated) {
                                    createMap();
                                    mapCreated = true;
                                } else {
                                    GeoException error = new GeoException("107");
                                    throw (error);
                                }

                            } else {
                                //new Errors(101);
                                CommandException error = new CommandException("101");
                                throw (error);
                            }
                        } //COMANDO 3
                        else if (parts.length == 3) {
                            if ((assignedMissions() == false) && (players.size() < 6)) {
                                if (parts[1].equals("players")) {
                                    crearPlayer(new File(parts[2]));
                                } //COMANDO 2
                                else {
                                    crearPlayer(parts[1], parts[2]);
                                }
                            } else {
                                //new Errors(99);
                                CommandException error = new CommandException("99");
                                throw (error);
                            }
                        } else {
                            //new Errors(101);
                            CommandException error = new CommandException("101");
                            throw (error);
                        }

                        break;

                    case "asignar":

                        switch (parts[1]) {
                            case "countries":
                                //COMANDO 7
                                if (!assignedMissions()) {
                                    //new Errors(118);
                                    MissionException error = new MissionException("118");
                                    throw (error);
                                } else {
                                    distributeArmiesIniciales();
                                    assignCountries(new File(parts[2]));
                                }
                                break;

                            case "missions":
                                //COMANDO 5
                                if (parts.length != 3) {
                                    CommandException error = new CommandException("101");
                                    throw (error);
                                }
                                assignMissions(new File(parts[2]));
                                break;

                            case "mission":
                                //COMANDO 4
                                if (players.size() > 2) {
                                    assignMissions(parts[2], parts[3]);
                                } else {
                                    //new Errors(105);
                                    PlayerException error = new PlayerException("105");
                                    throw (error);
                                }
                                break;

                            case "country":
                                //COMANDO 6
                                if (!assignedMissions()) {
                                    //new Errors(118);
                                    MissionException error = new MissionException("118");
                                    throw (error);
                                } else {
                                    assignCountries(parts[2], parts[3]);
                                }
                                break;

                            case "carta":
                                if (parts.length != 3) {
                                    CommandException error = new CommandException("101");
                                    throw (error);
                                }
                                //COMANDO 21
                                assignCard(turnPlayer, parts[2]);
                                break;

                            default:
                                //new Errors(101);
                                CommandException error = new CommandException("101");
                                throw (error);
                        }

                        break;

                    case "get":

                        switch (parts[1]) {
                            case "border":
                                if (!countries.containsKey(parts[2])) {
                                    //new Errors(109);
                                    GeoException error = new GeoException("109");
                                    throw (error);
                                } else {
                                    ArrayList<Country> borders = map.getBorder(countries.get(parts[2]));
                                    out = "{\n  border: [";
                                    for (int i = 0; i < borders.size(); i++) {
                                        out = out + " \"" + borders.get(i).getName() + "\"";
                                        if (i != borders.size() - 1) {
                                            out = out + ",";
                                        }
                                    }
                                    out = out + " ]\n}\n";
                                    Risk.console.Print(out);
                                }
                                break;

                            case "color":
                                if (!countries.containsKey(parts[2])) {
                                    //new Errors(109);
                                    GeoException error = new GeoException("109");
                                    throw (error);
                                } else if (!colors.contains(countries.get(parts[2]).getContinent().getColor())) {
                                    //new Errors(100);
                                    GeoException error = new GeoException("100");
                                    throw (error);
                                } else if (colors.contains(countries.get(parts[2]).getContinent().getColor()) && countries.containsKey(parts[2])) {
                                    out = "{ color: \"" + countries.get(parts[2]).getContinent().getColor() + "\" }\n";
                                    Risk.console.Print(out);
                                }
                                break;

                            case "continent":
                                if (!countries.containsKey(parts[2])) {
                                    //new Errors(109);
                                    GeoException error = new GeoException("109");
                                    throw (error);
                                } else if (!continents.containsKey(abbreviations.get(countries.get(parts[2]).getContinent().getName()))) {
                                    //new Errors(102);
                                    GeoException error = new GeoException("102");
                                    throw (error);
                                } else if (colors.contains(countries.get(parts[2]).getContinent().getColor()) && countries.containsKey(parts[2])) {
                                    out = "{ continent: \"" + countries.get(parts[2]).getContinent().getName() + "\" }\n";
                                    Risk.console.Print(out);
                                }
                                break;

                            case "countries":
                                if (!continents.containsKey(parts[2])) {
                                    //new Errors(102);
                                    GeoException error = new GeoException("102");
                                    throw (error);
                                } else {
                                    ArrayList<Country> Print = continents.get(parts[2]).getCountries();
                                    out = "{\n  countries: [ ";
                                    for (int i = 0; i < Print.size(); i++) {
                                        out = out + "\"" + Print.get(i).getName() + "\"";
                                        if (i != Print.size() - 1) {
                                            out = out + ",";
                                            out = out + "\n            ";
                                        } else {
                                            out = out + "\n          ]\n";
                                        }
                                    }
                                    out = out + "}";
                                    Risk.console.Print(out);
                                }
                                break;

                            default:
                                //new Errors(101);
                                CommandException error = new CommandException("101");
                                throw (error);
                        }

                        break;

                    case "describir":
                        switch (parts[1]) {
                            case "player":
                                //COMANDO 14
                                if (!players.containsKey(parts[2])) {
                                    //new Errors(103);
                                    PlayerException error = new PlayerException("103");
                                    throw (error);
                                } else {
                                    out = players.get(parts[2]).toString(turnPlayer.equals(players.get(parts[2])));
                                    Risk.console.Print(out);
                                }
                                break;

                            case "country":
                                //COMANDO 15
                                if (!countries.containsKey(parts[2])) {
                                    //new Errors(103);
                                    PlayerException error = new PlayerException("103");
                                    throw (error);
                                } else {
                                    out = countries.get(parts[2]).toString();
                                    Risk.console.Print(out);
                                }
                                break;

                            case "continent":
                                //COMANDO 16
                                if (!continents.containsKey(parts[2])) {
                                    //new Errors(102);
                                    GeoException error = new GeoException("102");
                                    throw (error);
                                } else {
                                    out = continents.get(parts[2]).toString();
                                    Risk.console.Print(out);
                                }
                                break;
                        }

                        break;

                    case "ver":
                        //COMANDO 17
                        if (parts[1].equals("map")) {
                            map.verMapa();
                        } else {
                            //new Errors(101);
                            CommandException error = new CommandException("101");
                            throw (error);
                        }
                        break;

                    case "player":
                        //COMANDO 13
                        //Imprimimos la description del player
                        out = turnPlayer.toString(true);
                        Risk.console.Print(out);
                        break;

                    case "acabar":
                        if (parts[1].equals("turno")) {
                            //COMANDO 12
                            //Igualamos turnPlayer al siguiente, obteniéndolo
                            //del map con el name de la lista turns
                            acabarTurno();
                        } else {
                            //new Errors(101);
                            CommandException error = new CommandException("101");
                            throw (error);
                        }
                        break;

                    case "change":
                        if (parts[1].equals("cards")) {
                            if (parts[2].equals("all")) {
                                //COMANDO 11
                                changeAllCard();
                            } else {
                                //COMANDO 10
                                changeCards(turnPlayer, parts[2], parts[3], parts[4]);
                            }
                        }
                        break;

                    case "distribute":
                        if (parts[1].equals("armies")) {
                            if (parts.length == 2) {
                                //COMANDO 9
                                distributeArmies();
                            } else if (parts.length == 4) {
                                //COMANDO 8
                                distributeArmies(Integer.valueOf(parts[2]), parts[3]);
                            } else {
                                //new Errors(101);
                                CommandException error = new CommandException("101");
                                throw (error);
                            }
                        } else {
                            //new Errors(101);
                            CommandException error = new CommandException("101");
                            throw (error);
                        }
                        break;

                    case "attack":
                        if (parts.length == 5) {
                            //COMANDO 19
                            attack(parts[1], parts[2], parts[3], parts[4]);
                        } else if (parts.length == 3) {
                            //COMANDO 18
                            attack(parts[1], parts[2]);
                        } else {
                            //new Errors(101);
                            CommandException error = new CommandException("101");
                            throw (error);
                        }
                        break;

                    case "rearm":
                        if (parts.length == 4) {
                            //COMANDO 20
                            rearm(parts[1], Integer.valueOf(parts[2]), parts[3]);
                        } else {
                            //new Errors(101);
                            CommandException error = new CommandException("101");
                            throw (error);
                        }
                        break;

                    default:
                        //new Errors(101);
                        CommandException error = new CommandException("101");
                        throw (error);
                }
            } catch (CardException error) {
                error.printError();
            } catch (PlayerException error) {
                error.printError();
            } catch (GeoException error) {
                error.printError();
            } catch (CommandException error) {
                error.printError();
            } catch (MissionException error) {
                error.printError();
            }
        } while (!orden.equals("quit"));
    }

    private void readContinents() {
        String line, parts[];
        BufferedReader readBuffer = null;
        try {
            File fichero = new File("leccontinents.csv");
            FileReader lector = new FileReader(fichero);
            readBuffer = new BufferedReader(lector);
            while ((line = readBuffer.readLine()) != null) {
                parts = line.split(";");
                continents.put(parts[2], new Continent(parts[0], parts[1], parts[2]));
                abbreviations.put(parts[0], parts[2]);
                names.put(parts[2], parts[0]);
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    private void readCountries() {
        String line, parts[];
        BufferedReader readBuffer = null;
        try {
            File fichero = new File("lecCountries.csv");
            FileReader lector = new FileReader(fichero);
            readBuffer = new BufferedReader(lector);
            while ((line = readBuffer.readLine()) != null) {
                parts = line.split(";");
                countries.put(parts[1], new Country(parts[0], parts[1], continents.get(parts[2])));
                //Creación de las cards correspondientes al país
                //cards.put(new String("Infantería&" + parts[1]), new Card(new String("Infantería"), countries.get(parts[1])));
                //cards.put(new String("Caballería&" + parts[1]), new Card(new String("Caballería"), countries.get(parts[1])));
                //cards.put(new String("Artillería&" + parts[1]), new Card(new String("Artillería"), countries.get(parts[1])));
                abbreviations.put(parts[0], parts[1]);
                names.put(parts[1], parts[0]);
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    public void readColors() {
        colors.add("ROJO");
        colors.add("AMARILLO");
        colors.add("AZUL");
        colors.add("VERDE");
        colors.add("CYAN");
        colors.add("VIOLETA");
    }

    private void readMissions() {
        String line, parts[];
        BufferedReader readBuffer = null;
        try {
            File fichero = new File("lecMisiones.csv");
            FileReader lector = new FileReader(fichero);
            readBuffer = new BufferedReader(lector);
            while ((line = readBuffer.readLine()) != null) {
                parts = line.split(";");
                missions.put(parts[1], new Mision(parts[0], parts[1]));
            }
        } catch (Exception excepcion) {
            excepcion.printStackTrace();
        }
    }

    /**
     *
     * @param file
     */
    public void assignCountries(File file) throws MissionException {

        if (!assignedMissions()) {
            MissionException error = new MissionException("118");
            throw (error);
        } else {
            // Código necesario para asignar países       
            String line, parts[];
            BufferedReader readBuffer = null;
            try {
                FileReader lector = new FileReader(file);
                readBuffer = new BufferedReader(lector);
                while ((line = readBuffer.readLine()) != null) {
                    parts = line.split(";");
                    try {
                        assignCountries(parts[0], parts[1]);
                    } catch (PlayerException error) {
                        error.printError();
                    } catch (GeoException error) {
                        error.printError();
                    }
                }
            } catch (IOException excepcion) {
                excepcion.printStackTrace();
            }
        }
    }

    public void assignCountries(String nombrePlayer, String abbreviationCountry) throws PlayerException, GeoException {
        // Código necesario para asignar un país a un player
        if (!players.containsKey(nombrePlayer)) {
            //new Errors(103);
            PlayerException error = new PlayerException("103");
            throw (error);
        } else if (!countries.containsKey(abbreviationCountry)) {
            //Comprobamos el country existe
            //new Errors(109);
            GeoException error = new GeoException("109");
            throw (error);
        } /*Comprobamos que el country no ha sido asignado previamente*/ else if (assignedCountries.contains(abbreviationCountry)) {
            //new Errors(113);
            PlayerException error = new PlayerException("113");
            throw (error);
        } else if (players.containsKey(nombrePlayer) && countries.containsKey(abbreviationCountry)) {
            players.get(nombrePlayer).addArmyNumbers(-1);
            assignedCountries.add(abbreviationCountry);
            players.get(nombrePlayer).getCountries().add(countries.get(abbreviationCountry));
            if (!players.get(nombrePlayer).getContinents().contains(countries.get(abbreviationCountry).getContinent())) {
                players.get(nombrePlayer).getContinents().add(countries.get(abbreviationCountry).getContinent());
            }
            countries.get(abbreviationCountry).setPlayer(players.get(nombrePlayer));
            out = "{\n";
            out = out + "  name: \"" + nombrePlayer + "\",\n";
            out = out + "  country: \"" + countries.get(abbreviationCountry).getName() + "\",\n";
            out = out + "  continent: \"" + countries.get(abbreviationCountry).getContinent().getName() + "\",\n";
            //Print borders
            ArrayList<Country> borders = countries.get(abbreviationCountry).getBorders();
            out = out + "  border: [";
            for (int i = 0; i < borders.size(); i++) {
                out = out + " \"" + borders.get(i).getName() + "\"";
                if (i != borders.size() - 1) {
                    out = out + ",";
                }
            }
            out = out + " ]\n";
            out = out + "}\n";
            Risk.console.Print(out);
        }

    }

    public void assignMissions(File file) throws PlayerException, MissionException {
        String line, parts[];
        BufferedReader readBuffer = null;
        try {
            FileReader lector = new FileReader(file);
            readBuffer = new BufferedReader(lector);
            while ((line = readBuffer.readLine()) != null) {
                parts = line.split(";");
                try {
                    assignMissions(parts[0], parts[1]);
                } catch (PlayerException error) {
                    error.printError();
                } catch (MissionException error) {
                    error.printError();
                }
            }
        } catch (IOException excepcion) {
            excepcion.printStackTrace();
        }
        //catch(FileNotFoundException excepcion){excepcion.printStackTrace();}
    }

    public boolean assignedMissions() {
        boolean salida = true;
        for (String player : players.keySet()) {
            if (players.get(player).getMision().getId().equals("0")) {
                salida = false;
            }
        }
        if (players.size() == 0) {
            salida = false;
        }
        return salida;
    }

    public void assignMissions(String name, String id) throws PlayerException, MissionException {
        if (!players.containsKey(name)) {
            //new Errors(103);
            PlayerException error = new PlayerException("103");
            throw (error);
        } /*Comprobamos la mission no ha sido asignada*/ else if (assignedMissions.contains(id)) {
            //new Errors(115);
            MissionException error = new MissionException("115");
            throw (error);
        } else if ((!missions.containsKey(id))) {
            //new Errors(116);
            MissionException error = new MissionException("116");
            throw (error);
        } else if (!players.get(name).getMision().getId().equals("0")) {
            //new Errors(117);
            MissionException error = new MissionException("117");
            throw (error);
        } else {
            assignedMissions.add(id);
            players.get(name).setMision(missions.get(id));
            out = "{\n";
            out = out + "  name: \"" + name + "\",\n";
            out = out + "  mission: \"" + missions.get(id).getDescripcion() + "\"\n";
            out = out + "}\n";
            Risk.console.Print(out);
        }
    }

    /**
     *
     */
    public void createMap() throws GeoException {
        // Código necesario para crear el map
        String line, parts[];
        if (mapCreated) {
            //new Errors(107);
            GeoException error = new GeoException("107");
            throw (error);
        } else {
            try {
                File file = new File("lecCountries.csv");
                FileReader lector = new FileReader(file);
                BufferedReader readBuffer = new BufferedReader(lector);
                while ((line = readBuffer.readLine()) != null) {
                    parts = line.split(";");
                    map.getTiles().add(new Tile(countries.get(parts[1]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4])));
                }
            } catch (Exception excepcion) {
                excepcion.printStackTrace();
            }
            for (Country country : countries.values()) {
                country.setBorders(map.getBorder(country));
            }

            map.verMapa();
        }
    }

    /**
     *
     * @param file
     */
    private void crearPlayer(File file) {
        // Código necesario para crear a los players del RISK
        String line, parts[];
        try {
            FileReader lector = new FileReader(file);
            BufferedReader readBuffer = new BufferedReader(lector);
            while ((line = readBuffer.readLine()) != null) {
                parts = line.split(";");
                try {
                    crearPlayer(parts[0], parts[1]);
                } catch (GeoException error) {
                    error.printError();
                } catch (PlayerException error) {
                    error.printError();
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
    private void crearPlayer(String name, String color) throws GeoException, PlayerException {
        // Código necesario para crear a un player a partir de su name y color
        if (!mapCreated) {
            //new Errors(106);
            GeoException error = new GeoException("106");
            throw (error);
        } else if (!colors.contains(color)) {
            //new Errors(100);
            GeoException error = new GeoException("100");
            throw (error);
        } else if (players.containsKey(name)) {
            //new Errors(104);
            PlayerException error = new PlayerException("104");
            throw (error);
        } /*Comprobamos el color no ha sido ya asignado*/ else if (usedColors.contains(color)) {
            //new Errors(114);
            PlayerException error = new PlayerException("114");
            throw (error);
        } else if (mapCreated && colors.contains(color) && !players.containsKey(name) && !usedColors.contains(color)) {
            players.put(name, new Player(name, color));
            out = "{\n  name: \"" + name + "\",\n  color: \"" + color + "\"\n}\n";
            Risk.console.Print(out);
            usedColors.add(color);
            if (turns.size() == 0) {
                turnPlayer = players.get(name);
            }
            turns.add(name);
            switch (color) {
                case "AZUL":
                    BlueArmy armyAzul = new BlueArmy();
                    players.get(name).setArmy(armyAzul);
                    break;
                case "VERDE":
                    GreenArmy armyVerde = new GreenArmy();
                    players.get(name).setArmy(armyVerde);
                    break;
                case "AMARILLO":
                    YellowArmy armyAmarillo = new YellowArmy();
                    players.get(name).setArmy(armyAmarillo);
                    break;
                case "ROJO":
                    RedArmy armyRojo = new RedArmy();
                    players.get(name).setArmy(armyRojo);
                    break;
                case "VIOLETA":
                    PurpleArmy armyVioleta = new PurpleArmy();
                    players.get(name).setArmy(armyVioleta);
                    break;
                case "CYAN":
                    CyanArmy armyCyan = new CyanArmy();
                    players.get(name).setArmy(armyCyan);
                    break;
            }
        }
    }

    void distributeArmies(int number, String abrevCountry) throws GeoException, PlayerException {
        String out = new String();
        if (!countries.containsKey(abrevCountry)) {
            //new Errors(109);
            GeoException error = new GeoException("109");
            throw (error);
        } else if (!turnPlayer.getCountries().contains(countries.get(abrevCountry))) {
            //new Errors(110);
            PlayerException error = new PlayerException("110");
            throw (error);
        } else if (turnPlayer.getArmyNumbers() == 0) {
            //new Errors(119);
            PlayerException error = new PlayerException("119");
            throw (error);
        } else {
            int numberDisponibles = turnPlayer.getArmyNumbers();
            if (number != 0) {
                if (number >= numberDisponibles) {
                    countries.get(abrevCountry).addArmyNumber(numberDisponibles);
                    turnPlayer.setArmyNumbers(0);
                } else {
                    countries.get(abrevCountry).addArmyNumber(number);
                    turnPlayer.setArmyNumbers(numberDisponibles - number);
                }
                out = "{\n";
                out += "  country: \"" + countries.get(abrevCountry).getName() + "\",\n";
                out += "  player: \"" + turnPlayer.getName() + "\",\n";
                out += "  numberArmiesAsignados: " + number + ",\n";
                out += "  numberArmiesTotales: " + countries.get(abrevCountry).getArmyNumber() + ",\n";
                out += "  countriesOcupadoscontinent: [";
                Iterator<Country> itCountry = countries.get(abrevCountry).getContinent().getCountries().iterator();
                while (itCountry.hasNext()) {
                    Country country = itCountry.next();
                    out += " { \"" + country.getName() + "\", " + country.getArmyNumber() + " }";
                    if (itCountry.hasNext()) {
                        out = out + ", ";
                    } else {
                        out += "]\n";
                    }
                }
                out += "}";
                Risk.console.Print(out);
            }
        }
    }

    void assignCard(Player player, String idCard) throws CardException, CommandException {

        String[] parts = idCard.split("&");
        //Comprobamos idCorrecto
        if (parts.length != 2) {
            //new Errors(125);
            CardException error = new CardException("125");
            throw (error);
        } else if ((!countries.containsKey(parts[1])) || !(parts[0].equals("Rifleman")
                || parts[0].equals("Grenadier") || parts[0].equals("OfHorse")
                || parts[0].equals("OfCamel") || parts[0].equals("OfCampaign")
                || parts[0].equals("Antiair"))) {
            //new Errors(125);
            CardException error = new CardException("125");
            throw (error);
        }
        /*if(!battleWon){
            //new Errors(99);
            CommandException error = new CommandException("99");
            throw(error);
        }*/
        //Comprobamos que la carta no ha sido asignada
        if (cards.containsKey(idCard)) {
            //new Errors(126);
            CardException error = new CardException("126");
            throw (error);
        } else {
            switch (parts[0]) {
                case "Rifleman":
                    Rifleman fusilero = new Rifleman(countries.get(parts[1]), player);
                    cards.put(idCard, fusilero);
                    break;
                case "Grenadier":
                    Grenadier granadero = new Grenadier(countries.get(parts[1]), player);
                    cards.put(idCard, granadero);
                    break;
                case "OfHorse":
                    OfHorse deCaballo = new OfHorse(countries.get(parts[1]), player);
                    cards.put(idCard, deCaballo);
                    break;
                case "OfCamel":
                    OfCamel deCamello = new OfCamel(countries.get(parts[1]), player);
                    cards.put(idCard, deCamello);
                    break;
                case "OfCampaign":
                    OfCampaign deCampanha = new OfCampaign(countries.get(parts[1]), player);
                    cards.put(idCard, deCampanha);
                    break;
                case "Antiair":
                    Antiair artilleria = new Antiair(countries.get(parts[1]), player);
                    cards.put(idCard, artilleria);
                    break;

            }

            //cards.put(idCard, carta);
            player.getCards().add(cards.get(idCard));
            out = cards.get(idCard).toString();
            Risk.console.Print(out);
        }
        if (turnPlayer.getCards().size() >= 6) {
            changeAllCard();
        }
    }

    //SIN ACABAR
    void changeAllCard() {

        ArrayList<String> combinacion = maximoCambio(turnPlayer);
        try {
            changeCards(turnPlayer, combinacion.get(0), combinacion.get(1), combinacion.get(2));
            combinacion = maximoCambio(turnPlayer);
            changeCards(turnPlayer, combinacion.get(0), combinacion.get(1), combinacion.get(2));
        } catch (CardException excepcion) {
            excepcion.printError();
        };

    }

    void changeCards(Player player, String carta1, String carta2, String carta3) throws CardException {
        //Indica si se realiza el cambio
        boolean cambio = false;
        int armies = 0;
        ArrayList<Card> cardsCambiar = new ArrayList<>();
        //Comprobamos carta existe, es decir, si ha sido asignada anteriormente
        if (!(cards.keySet().contains(carta1) && cards.keySet().contains(carta2) && cards.keySet().contains(carta3))) {
            //new Errors(123);
            CardException error = new CardException("123");
            throw (error);
        } //Comprobamos que la carta ha sido asignada al player        
        else if (!(player.getCards().contains(cards.get(carta1)) && player.getCards().contains(cards.get(carta2)) && player.getCards().contains(cards.get(carta3)))) {
            //new Errors(122);
            CardException error = new CardException("122");
            throw (error);
        } else {
            int infF = 0, infG = 0, cabCam = 0, cabCab = 0, artCam = 0, artA = 0;
            //Añadimos las cards a una lista para acceder a ellas con facilidad
            cardsCambiar.add(cards.get(carta1));
            cardsCambiar.add(cards.get(carta2));
            cardsCambiar.add(cards.get(carta3));
            for (Card carta : cardsCambiar) {
                if (carta.getType().equals("Rifleman")) {
                    infF++;
                } else if (carta.getType().equals("Grenadier")) {
                    infG++;
                } else if (carta.getType().equals("OfHorse")) {
                    cabCab++;
                } else if (carta.getType().equals("OfCamel")) {
                    cabCam++;
                } else if (carta.getType().equals("OfCampaign")) {
                    artCam++;
                } else {
                    artA++;
                }
            }
            //Realizamos el cambio acorde a las cards
            if (infF + infG == 3) {
                armies = 6;
                cambio = true;
            } else if (cabCab + cabCam == 3) {
                armies = 8;
                cambio = true;
            } else if (artCam + artA == 3) {
                armies = 10;
                cambio = true;
            } else if ((infF + infG == 1) && (cabCab + cabCam == 1) && (artCam + artA == 1)) {
                armies = 12;
                cambio = true;
            } else {
                //new Errors(121);
                CardException error = new CardException("121");
                throw (error);
            }
            //Si se ha realizado el cambio, se hace lo siguiente
            if (cambio) {
                armies += infG + infF * 2 + cabCam * 2 + cabCab * 3 + artA * 3 + artCam * 4;
                player.setArmyNumbers(player.getArmyNumbers() + armies);
                for (Card carta : cardsCambiar) {
                    //Quitamos las cards al player
                    cards.remove(carta.getName());
                    player.getCards().remove(carta);
                    //Si la carta es de un país del player añadimos un ejército
                    //adicional
                    if (carta.getCountry().getPlayer().equals(player)) {
                        carta.getCountry().addArmyNumber(1);
                        armies++;
                    }
                }
                //Mensaje de salida
                out = "{\n";
                out = out + "  cardsCambio: [ \"" + carta1 + "\", \"" + carta2 + "\", \"" + carta3 + "\" ],\n";
                out = out + "  cardsQuedan: [ ";
                for (int i = 0; i < player.getCards().size(); i++) {
                    out = out + player.getCards().get(i).getName();
                    if (i != player.getCards().size() - 1) {
                        out = out + ", ";
                    }
                }
                out = out + " ],\n  numberArmiesCambiados: " + armies + ",\n";
                out = out + "  numArmiesRearme: " + turnPlayer.getArmyNumbers() + "\n}";
                Risk.console.Print(out);
            }
        }
    }

    void attack(String abrevCountry1, String abrevCountry2) throws GeoException, PlayerException {
        if ((!countries.keySet().contains(abrevCountry1)) || (!countries.keySet().contains(abrevCountry2))) {
            //new Errors(109);
            GeoException error = new GeoException("109");
            throw (error);
        } else {
            Country country1 = countries.get(abrevCountry1);
            Country country2 = countries.get(abrevCountry2);
            if (!country1.getPlayer().equals(turnPlayer)) {
                //new Errors(110);
                PlayerException error = new PlayerException("110");
                throw (error);
            } else if (country2.getPlayer().equals(turnPlayer)) {
                //new Errors(111);
                PlayerException error = new PlayerException("111");
                throw (error);
            } else if (!country1.getBorders().contains(country2)) {
                //new Errors(112);
                GeoException error = new GeoException("112");
                throw (error);
            } else if (country1.getArmyNumber() == 1) {
                //new Errors(124);
                PlayerException error = new PlayerException("124");
                throw (error);
            } else {
                ArrayList<Integer> dicesAtaque = Dice.attackDice(country1.getArmyNumber());
                ArrayList<Integer> dicesDefensa = Dice.defenseDice(country2.getArmyNumber());
                String dices1 = new String();
                dices1 = "";
                String dices2 = new String();
                dices2 = "";
                for (int i = dicesAtaque.size() - 1; i >= 0; i--) {
                    dices1 = dices1 + dicesAtaque.get(i).intValue();
                    if (i != 0) {
                        dices1 = dices1 + "x";
                    }
                }
                for (int i = dicesDefensa.size() - 1; i >= 0; i--) {
                    dices2 = dices2 + dicesDefensa.get(i).intValue();
                    if (i != 0) {
                        dices2 = dices2 + "x";
                    }
                }
                attack(abrevCountry1, dices1, abrevCountry2, dices2);
            }
        }
    }

    void attack(String abrevCountry1, String dicesAtaque, String abrevCountry2, String dicesDefensa) throws GeoException, PlayerException {
        int dice = 0, conquista = 0, armysAtaque1 = 0, armysAtaque2 = 0, armysDefensa1 = 0, armysDefensa2 = 0;
        ArrayList<Integer> attackDice = new ArrayList<>();
        ArrayList<Integer> defenseDice = new ArrayList<>();

        //Convertimos las Strings de dices a ArrayList de Integer
        //No se pueden hacer ArrayList de type primitivo int, por eso usamos
        //Integer
        String parts[] = parts = dicesAtaque.split("x");
        for (String num : parts) {
            attackDice.add(Integer.valueOf(num));
        }
        parts = dicesDefensa.split("x");
        for (String num : parts) {
            defenseDice.add(Integer.valueOf(num));
        }

        //Hacemos comprobaciones de erroes
        if (!countries.keySet().contains(abrevCountry1) || !countries.keySet().contains(abrevCountry2)) {
            //new Errors(109);
            GeoException error = new GeoException("109");
            throw (error);
        } else if (!countries.get(abrevCountry1).getPlayer().equals(turnPlayer)) {
            //new Errors(110);
            PlayerException error = new PlayerException("110");
            throw (error);
        } else if (countries.get(abrevCountry2).getPlayer().equals(turnPlayer)) {
            //new Errors(111);
            PlayerException error = new PlayerException("111");
            throw (error);
        } else if (!countries.get(abrevCountry1).getBorders().contains(countries.get(abrevCountry2))) {
            //new Errors(112);
            GeoException error = new GeoException("112");
            throw (error);
        } else if (countries.get(abrevCountry1).getArmyNumber() < 2) {
            //new Errors(124);
            PlayerException error = new PlayerException("124");
            throw (error);
        } else {

            armysAtaque1 = countries.get(abrevCountry1).getArmyNumber();
            armysDefensa1 = countries.get(abrevCountry2).getArmyNumber();

            //Actualizamos los valores del dice de attack para la jerarquía ejércitos        
            attackDice = countries.get(abrevCountry1).getPlayer().getArmy().attack(attackDice);

            //Actualizamos el dice en función de los armies del atacante
            dice = Integer.min(attackDice.size(), defenseDice.size());

            //Comparamos los dices
            for (int i = 0; i < dice; i++) {
                //Si el dice de attack es mayor, quitamos un ejército a los
                //defensores
                if (attackDice.get(i).intValue() > defenseDice.get(i).intValue()) {
                    countries.get(abrevCountry2).addArmyNumber(-1);
                } //Si el dice de defensa es mayor o igual, quitamos un ejército a los
                //atacantes
                else {
                    countries.get(abrevCountry1).addArmyNumber(-1);
                }
            }

            //Si no le quedan ejércitos al defensor, se hace la conquista
            if (countries.get(abrevCountry2).getArmyNumber() == 0) {
                //Comprobamos cuantos ejércitos puede mover el país atacante, si
                //tiene menos de 4, tendremos que sacar el mayor número posible de
                //ejércitos para conquistar el nuevo país
                conquista = Integer.min(attackDice.size(), countries.get(abrevCountry1).getArmyNumber() - 1);
                //Se actualiza el número de ejércitos en los países
                countries.get(abrevCountry1).addArmyNumber(-conquista);
                countries.get(abrevCountry2).addArmyNumber(conquista);
                countries.get(abrevCountry2).getPlayer().getCountries().remove(countries.get(abrevCountry2));
                countries.get(abrevCountry2).setPlayer(turnPlayer);
                turnPlayer.getCountries().add(countries.get(abrevCountry2));

                battleWon = true;
                countries.get(abrevCountry2).incrementTimesOccupied();
            }

            armysAtaque2 = countries.get(abrevCountry1).getArmyNumber();
            armysDefensa2 = countries.get(abrevCountry2).getArmyNumber();

            //Creamos el mensaje de salida
            String out = new String();
            out += "{\n";
            out += "dicesAtaque: [ ";
            for (int i = 0; i < attackDice.size(); i++) {
                out += attackDice.get(i);
                if (i < attackDice.size() - 1) {
                    out += ", ";
                } else {
                    out += " ],\n";
                }
            }
            out += "dicesDefensa: [ ";
            for (int i = 0; i < defenseDice.size(); i++) {
                out += defenseDice.get(i);
                if (i < defenseDice.size() - 1) {
                    out += ", ";
                } else {
                    out += " ],\n";
                }
            }
            out += "armyscountryAtaque: [ " + armysAtaque1 + ", " + armysAtaque2 + " ],\n";
            out += "armyscountryDefensa: [ " + armysDefensa1 + ", " + armysDefensa2 + " ],\n";
            out += "countryAtaquePerteneceA: \"" + turnPlayer.getName() + "\",\n";
            out += "countryDefensaPerteneceA: \"" + countries.get(abrevCountry2).getPlayer().getName() + "\",\n";
            out += "continentConquistado: \"";
            if (turnPlayer.getContinents().contains(countries.get(abrevCountry2).getContinent())) {
                out += countries.get(abrevCountry2).getContinent().getName() + "\"\n";
            } else {
                out += "null\"\n";
            }
            out += "}";
            Risk.console.Print(out);
        }
    }

    public void rearm(String abrevCountry1, int numArmies, String abrevCountry2) throws GeoException, PlayerException {
        String out = new String();
        if ((!countries.keySet().contains(abrevCountry1)) || (!countries.keySet().contains(abrevCountry2))) {
            //new Errors(109);
            GeoException error = new GeoException("109");
            throw (error);
        } else {
            Country country1 = countries.get(abrevCountry1);
            Country country2 = countries.get(abrevCountry2);
            if ((!country1.getPlayer().equals(turnPlayer)) || (!country2.getPlayer().equals(turnPlayer))) {
                //new Errors(110);
                PlayerException error = new PlayerException("110");
                throw (error);
            } else if (!country1.getBorders().contains(country2)) {
                //new Errors(112);
                GeoException error = new GeoException("112");
                throw (error);
            } else if (country1.getArmyNumber() < 1) {
                //new Errors(124);
                PlayerException error = new PlayerException("124");
                throw (error);
            } else {
                int init1 = country1.getArmyNumber();
                int init2 = country2.getArmyNumber();
                if (numArmies >= country1.getArmyNumber()) {
                    country2.addArmyNumber(country1.getArmyNumber() - 1);
                    country1.setArmyNumber(1);
                } else {
                    country2.addArmyNumber(numArmies);
                    country1.addArmyNumber(-numArmies);
                }
                out = "{\n";
                out = out + "  numberArmiesInicialesOrigen: " + init1 + ",\n";
                out = out + "  numberArmiesInicialesDestino: " + init2 + ",\n";
                out = out + "  numberArmiesFinalesOrigen: " + country1.getArmyNumber() + ",\n";
                out = out + "  numberArmiesFinalesDestino: " + country2.getArmyNumber() + ",\n";
                out = out + "}";
                Risk.console.Print(out);
            }
        }
    }

    void acabarTurno() {
        turnPlayer = players.get(turns.get((turns.indexOf(turnPlayer.getName()) + 1) % turns.size()));
        battleWon = false;
        //Imprimimos por pantalla y al archivo un mensaje
        out = "{\n";
        out = out + "  name: \"" + turnPlayer.getName() + "\"\n";
        out = out + "  numberArmiesRearmar: " + turnPlayer.getArmyNumbers() + "\n";
        out = out + "}\n";
        Risk.console.Print(out);
        //Escogemos el maximo entre 3 y el number de countries entre 3 para rearm al principio del turno
        turnPlayer.addArmyNumbers(Integer.max((int) turnPlayer.getCountries().size() / 3, 3));

        if (!turnPlayer.getContinents().isEmpty()) {
            for (Continent continent : turnPlayer.getContinents()) {
                if (continent.getAbreviatura().equals("Asia")) {
                    turnPlayer.addArmyNumbers(7);
                } else if (continent.getAbreviatura().equals("AméricaNorte") || continent.getAbreviatura().equals("Oceanía")) {
                    turnPlayer.addArmyNumbers(5);
                } else if (continent.getAbreviatura().equals("África")) {
                    turnPlayer.addArmyNumbers(3);
                } else if (continent.getAbreviatura().equals("AméricaSur") || continent.getAbreviatura().equals("Oceanía")) {
                    turnPlayer.addArmyNumbers(2);
                }
            }
        }

    }

    //Método para asignar ejércitos iniciales para poder ocupar con un ejército en
    //asignar countries
    void distributeArmiesIniciales() {
        int armies;
        switch (players.keySet().size()) {
            case 3:
                armies = 35;
                break;
            case 4:
                armies = 30;
                break;
            case 5:
                armies = 25;
                break;
            case 6:
                armies = 20;
                break;
            default:
                armies = 0;
                break;
        }

        for (String name : players.keySet()) {
            players.get(name).setArmyNumbers(armies);
        }

    }

    ArrayList<String> maximoCambio(Player player) {

        ArrayList<String> combinacion = new ArrayList<>();
        int sum = 0;
        int infF = 0, infG = 0, cabCam = 0, cabCab = 0, artCam = 0, artA = 0;
        ArrayList<String> types = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            types.add("");
        }
        //Contamos el number de cards
        for (Card carta : turnPlayer.getCards()) {
            if (carta.getType().equals("Rifleman")) {
                infF++;
            } else if (carta.getType().equals("Grenadier")) {
                infG++;
            } else if (carta.getType().equals("OfHorse")) {
                cabCab++;
            } else if (carta.getType().equals("OfCamel")) {
                cabCam++;
            } else if (carta.getType().equals("OfCampaign")) {
                artCam++;
            } else {
                artA++;
            }
        }

        //Buscamos solo 3 cards, paramos cuando contador = 3 
        if ((infF + infG > 0) && (cabCam + cabCab > 0) && (artCam + artA > 0)) {
            sum = 18 + Integer.min(1, infF) + Integer.min(1, cabCab) + Integer.min(1, artCam);
            if (infF > 0) {
                types.set(0, "Rifleman");
            } else {
                types.set(0, "Grenadier");
            }
            if (cabCab > 0) {
                types.set(1, "OfHorse");
            } else {
                types.set(1, "OfCamel");
            }
            if (artCam > 0) {
                types.set(2, "OfCampaign");
            } else {
                types.set(2, "Antiair");
            }

        }
        if ((artCam + artA >= 3)) {
            sum = Integer.max(sum, 19 + Integer.min(3, artCam));
            for (int i = 1; i <= 3; i++) {
                if (i <= artCam) {
                    types.set(i - 1, "OfCampaign");
                } else {
                    types.set(i - 1, "Antiair");
                }
            }
        }
        if ((cabCab + cabCam >= 3)) {
            sum = Integer.max(sum, 14 + Integer.min(3, cabCab));
            for (int i = 1; i <= 3; i++) {
                if (i <= cabCab) {
                    types.set(i - 1, "OfHorse");
                } else {
                    types.set(i - 1, "OfCamel");
                }
            }
        }
        if ((infF + infG >= 3)) {
            sum = Integer.max(sum, 19 + Integer.min(3, infF));
            for (int i = 1; i <= 3; i++) {
                if (i <= infF) {
                    types.set(i - 1, "Rifleman");
                } else {
                    types.set(i - 1, "Grenadier");
                }
            }
        }

        for (Card carta : player.getCards()) {
            for (int i = 0; i < 3; i++) {
                if (carta.getType().equals(types.get(i)) && (!combinacion.contains(carta.getName()))) {
                    combinacion.add(carta.getName());
                    types.set(i, "");
                }
            }
        }
        return combinacion;

    }

    void distributeArmies() {
        int indicadorR1, indicadorR4, indicadorR7;

        

        /*Creamos un HashMap para relacionar los countries ocupados en un continent*/
        //HashMap<String, int> countriesOcupados = new HashMap<>();

        /*Recorremos los players*/
        Iterator<String> itPlayeres = players.keySet().iterator();
        while (itPlayeres.hasNext()) {
            String nombrePlayer = itPlayeres.next();
            
            ArrayList<Continent> continentsR1 = new ArrayList<>();
            ArrayList<Continent> continentsR4 = new ArrayList<>();
            ArrayList<Continent> continentsR7 = new ArrayList<>();
            
            indicadorR1 = 0;
            indicadorR4 = 0;
            indicadorR7 = 0;

            /*Comprobamos qué regla se cumple para cada continent.*/
            HashMap<Continent, Float> porcentajes = new HashMap<>();

            for (Country country : players.get(nombrePlayer).getCountries()) {
                if (!porcentajes.containsKey(country.getContinent())) {
                    porcentajes.put(country.getContinent(), new Float(0.0f));
                }
                porcentajes.replace(country.getContinent(), new Float(porcentajes.get(country.getContinent()) + 1.f/(float)country.getContinent().getCountries().size()));
                
            }

            System.out.println("\n-------------");
            System.out.println("\nPlayer: " + nombrePlayer);

            for (Continent continent : porcentajes.keySet()) {
                if ((porcentajes.get(continent))>= 0.5) {
                    indicadorR1++;
                    continentsR1.add(continent);
                }
                if ((porcentajes.get(continent)) < 0.5 &&(porcentajes.get(continent)) >= 0.25) {
                    indicadorR4++;
                    continentsR4.add(continent);
                }
                if ((porcentajes.get(continent)) <= 0.25) {
                    indicadorR7++;
                    continentsR7.add(continent);
                }
            }

            /*---REGLA 1, 2, 3---*/
            if (indicadorR1 == 1) {
                distributeR123(nombrePlayer, 0, porcentajes, continentsR1);
                System.out.println("\nSe cumple regla 1 (" + indicadorR1 + ")");
            }
             
            else if (indicadorR1 > 1) {
                System.out.println("\nSe cumple regla 1 (" + indicadorR1+ ") para: \n");
                for(int i = 0; i < continentsR1.size(); i++)
                    System.out.println(continentsR1.get(i).getName() + " ");
                /*En este caso, habrá más de un continent sobre el que aplicar R1
                 *En primer lugar, comprobamos que podemos escoger aquel comntinente tal que para este player,
                 *su porcentaje de ocupación sea el máximo de entre el resto de continents que cumplen R1 */

                if (aplicarMenorNBorder(maximo(porcentajes, continentsR1), porcentajes, continentsR1) == 0) {
                    /*En el caso de que podamos escoger el máximo porcventaje de ocupación (único),
                    actualizamos la asignación de ejércitos en el continent de porcentaje de 
                    ocupación máxima para este player*/ 
                    System.out.println("\nMaximo sobre el que aplicamos: " + continentsR1.get(maximo(porcentajes, continentsR1)).getName());
                    distributeR123(nombrePlayer, maximo(porcentajes, continentsR1), porcentajes, continentsR1);
                }
                
                else{ /*En otro caso, aplicamos R3: R1 para el continent con menor number de countries border*/ 
                    System.out.println("\nMenorNBorder: " + continentsR1.get(menorNBorder(continentsR1)).getName());
                    distributeR123(nombrePlayer, menorNBorder(continentsR1), porcentajes, continentsR1);
                    
                }
                

            } 
            /*---REGLA 4, 5, 6---*/ 
            else if (indicadorR4 == 1) {
                distributeR456(nombrePlayer, 0, porcentajes, continentsR4);
                System.out.println("\nSe cumple regla 4 (1)");
            }
            
            else if (indicadorR4 > 1) {
                System.out.println("\nSe cumple regla 4 (" + indicadorR4 + ") para: \n");
                for(int i = 0; i < continentsR4.size(); i++)
                    System.out.println(continentsR4.get(i).getName());
                /*En primer lugar, comprobamos que podemos escoger aquel comntinente tal que para este player,
                su porcentaje de ocupación sea el máximo de entre el resto de continents que cumplen R4 */
                if (aplicarMenorNBorder(maximo(porcentajes, continentsR4), porcentajes, continentsR4) == 0) /*En este caso, actualizamos la asignación de ejércitos en el continent de porcentaje de 
                    ocupación máxima para este player*/ 
                    distributeR456(nombrePlayer, maximo(porcentajes, continentsR4), porcentajes, continentsR4);
                
                else /*En otro caso, aplicamos R6: R4 para el continent con menor number de countries border*/ 
                    distributeR456(nombrePlayer, menorNBorder(continentsR4), porcentajes, continentsR4);
                

            }

            else if (indicadorR7 == 1) {
                distributeR78(nombrePlayer, 0, porcentajes, continentsR7);
                System.out.println("\nSe cumple regla 7");

            }
            else
                System.out.println("\nNo se cumple ninguna regla.");

       
    }
}

void distributeR123(String nombrePlayer, int i, HashMap<Continent, Float> porcentajesR, ArrayList<Continent> continentsR) {
        int countriesOcupados = 0, num = 0, armysDisponibles = 0;
        double factordivision = 0.0;
        armysDisponibles = (players.get(nombrePlayer)).getArmyNumbers();
        countriesOcupados = (int)(porcentajesR.get(continentsR.get(i))*continentsR.get(i).getCountries().size());

        /*Asignamos según el criterio de la regla 1*/
        if (continentsR.get(i).getName().equals("Oceanía") || continentsR.get(i).getName().equals("AméricaSur")) {
            factordivision = 1.5;
        } else {
            factordivision = 1;
        }
        /*Realizamos la asignación de ejércitos para todos los countries del continent ocupados por ese player*/
        for (Country country : continentsR.get(i).getCountries()) {
            /*En el caso de que busquemos el number de countries ocupados total: 
            countriesOcupados = players.get(nombrePlayer).getCountries().size()*/
            if ((players.get(nombrePlayer)).getCountries().contains(country)) {
                num = (int) ((armysDisponibles) / (factordivision * countriesOcupados));
                country.setArmyNumber(num);
                armysDisponibles = armysDisponibles - num;
                (players.get(nombrePlayer)).setArmyNumbers(armysDisponibles);
            }
        }
        /*Si hay armies disponibles, asignamos un army a cada country con 1 army del player, priorizando los del continent
        de menor number de countries border*/       
        distributeR36(nombrePlayer, porcentajesR, continentsR);
    }

    /*Función para R3R6: añadir un ejército a países del player en continent de la regla que tengan un ejército mientras 
      queden armies disponibles*/
    void distributeR36(String nombrePlayer, HashMap<Continent, Float> porcentajesR, ArrayList<Continent> continentsR) {
        int j = 0, armysDisponibles = 0, num = 0;
        
        armysDisponibles = (players.get(nombrePlayer)).getArmyNumbers();
        for(Country country : continentsR.get(menorNBorder(continentsR)).getCountries()){
            while(armysDisponibles > 0 && country.getArmyNumber() == 1){
                //Asignamos los armies a cada country del player
                
                country.addArmyNumber(1);
                //Actualizamos los armies disponibles
                armysDisponibles = armysDisponibles - 1;
                (players.get(nombrePlayer)).setArmyNumbers(armysDisponibles);
            }            
        }
        continentsR.remove(menorNBorder(continentsR));        
        if(!continentsR.isEmpty())
            distributeR36(nombrePlayer, porcentajesR, continentsR);
    }

    void distributeR456(String nombrePlayer, int i, HashMap<Continent, Float> porcentajesR, ArrayList<Continent> continentsR) {
        int countriesOcupados = 0, num = 0, armysDisponibles = 0;
        armysDisponibles = (players.get(nombrePlayer)).getArmyNumbers();
        countriesOcupados = (int)(porcentajesR.get(continentsR.get(i))*continentsR.get(i).getCountries().size());

        for (Country country : continentsR.get(i).getCountries()) {
            /*En el caso de que busquemos el number de countries ocupados total: 
            countriesOcupados = players.get(nombrePlayer).getCountries().size()*/
            if ((players.get(nombrePlayer)).getCountries().contains(country)) {
                num = (int) ((players.get(nombrePlayer).getArmyNumbers()) / (2 * countriesOcupados));
                country.setArmyNumber(num);
                armysDisponibles -= num;
                (players.get(nombrePlayer)).setArmyNumbers(armysDisponibles);
            }
        }
        /*Si hay armies disponibles, asignamos un army a cada country con 1 army del player, priorizando los del continent
        de menor number de countries border*/
        distributeR36(nombrePlayer, porcentajesR, continentsR);

    }

    void distributeR78(String nombrePlayer, int i, HashMap<Continent, Float> porcentajesR, ArrayList<Continent> continentsR) {
        int countriesOcupados = 0, num = 0, armysDisponibles = 0;
        double factorocupacion = 0.0;
        armysDisponibles = (players.get(nombrePlayer)).getArmyNumbers();
        countriesOcupados = (int)(porcentajesR.get(continentsR.get(i))*continentsR.get(i).getCountries().size());

        /*Asignamos según el criterio de la regla 1*/
        if (players.size() == 3 || players.size() == 4) {
            factorocupacion = 2;
        } else {
            factorocupacion = 3;
        }
        /*Realizamos la asignación de ejércitos para todos los countries del continent ocupados por ese player*/

        for (Country country : continentsR.get(i).getCountries()) {
            /*En el caso de que busquemos el number de countries ocupados total: 
            countriesOcupados = players.get(nombrePlayer).getCountries().size()*/
            if ((players.get(nombrePlayer)).getCountries().contains(country)) {
                num = (int) (factorocupacion * countriesOcupados);
                country.setArmyNumber(num);
                armysDisponibles -= num;
                (players.get(nombrePlayer)).setArmyNumbers(armysDisponibles);
            }
        }
        /*Si hay armies disponibles, asignamos un army a cada country con 1 army del player, priorizando los del continent
        de menor number de countries border*/
        distributeR36(nombrePlayer, porcentajesR, continentsR);
    }


    /*Función que dice un índice comprueba si se repite el elemento en una lista*/
    int aplicarMenorNBorder(int i, HashMap<Continent, Float> porcentajes, ArrayList<Continent> continentsR) {
        float aux;
        int contador = -1, j = 0;

        aux = porcentajes.get(continentsR.get(i));

        while ((contador < 1) && (j < continentsR.size())) {
            if (aux == porcentajes.get(continentsR.get(j))) {
                contador++;
            }
            j++;
        }
        return contador;
    }


    /*Función que devuelve el índice donde se encuentra el mayor porcentaje
      de la lista de porcentajes de continents que cumplen R1, 
      este indice coincide con el de su continent correspondiente en 
      continentsR1*/
 /*Se aplica también para R4 y R7*/
    int maximo(HashMap<Continent, Float> porcentajesR, ArrayList<Continent> continentsR) {
        float max = 0;
        int indice = 0, i = 0;

        while(i < continentsR.size() && i < porcentajesR.keySet().size()) {
            //Comparamos porcentajes para escoger el máximo
            if (porcentajesR.get(continentsR.get(i)).intValue() > max) {
                max = porcentajesR.get(continentsR.get(i)).intValue() ;
                indice = i;
            }
            i++;
        }

        return indice;
    }

    /*Función que devuelve el indice en el que se encuentra el continent con menor number de countries border*/
    int menorNBorder(ArrayList<Continent> continentsR) {
        int contador = 0, aux = 42, indice = 0;

        for (int i = 0; i < continentsR.size(); i++) {
            for (Country country : continentsR.get(i).borders()) {
                /*Buscamos el number de countries border dentro del continent i*/
                if (continentsR.get(i).getCountries().contains(country.getBorders())) {
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
