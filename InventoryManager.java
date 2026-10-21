import java.io.*;
import java.util.*;

public class InventoryManager {

    //PRE:  accept the strings for the input file, error file, and empty Instrument list
    //POST: verify that the input files are good (using try/catch)
    //      read the input file & create instrement objects to add to the ArrayList

    public static void readFile(String inputPath, String errorPath, ArrayList<Instrument> inventory) {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath));
             BufferedWriter errorWriter = new BufferedWriter(new FileWriter(errorPath))) {

            String line = reader.readLine(); // skip header

            while ((line = reader.readLine()) != null) {
                
                String[] tokens = line.split(",");
                //set up the fields for the instrument
                try{
                    String[] fields = line.split(",", -1);

                    if (fields.length !=8){
                        throw new IllegalArgumentException("Expected 8 fields");
                    }

                    String type = fields[0].trim();
                    String name = fields[1].trim();
                    String manufacturer = fields[2].trim();

                    int year = Integer.parseInt(fields[3].trim());
                    int cost = Integer.parseInt(fields[4].trim());

                    boolean noDetails = fields[5].trim().isEmpty() && fields[6].trim().isEmpty() && fields[7].trim().isEmpty();

                    if (noDetails){
                        Instrument instrument = new Instrument(type, name, manufacturer, year, cost);
                        inventory.add(instrument);
                    } else{
                        int strings = Integer.parseInt(fields[5].trim());
                        int frets = Integer.parseInt(fields[6].trim());
                        int bowed = Integer.parseInt(fields[7].trim());

                        if (bowed != 0 && bowed != 1) { 
                            throw new IllegalArgumentException("Must be 0 or 1");

                        }

                        boolean Bowed;

                        if( bowed == 1){
                            Bowed = true;
                        } else {
                            Bowed = false;
                        }

                        StringInstrument instrument = new StringInstrument(type, name, manufacturer, year, cost, strings, frets, Bowed);
                        inventory.add(instrument);
                    }


                } catch (IllegalArgumentException e){
                    errorWriter.write("Error: " + e.getMessage() + " - " + line);
                    errorWriter.newLine();
                }
                
                line = reader.readLine();

                try {
                    if (tokens.length < 5) throw new IllegalArgumentException("Incomplete record");


                    //add instrument
                    if (tokens.length == 5 ) {
                        String type = tokens[0].trim(); //.trim() removes whitespaces
                        String name = tokens[1].trim(); 
                        String manufacturer = tokens[2].trim();

                        int year = Integer.parseInt(tokens[3].trim());
                        int cost = Integer.parseInt(tokens[4].trim());
                        boolean noDetails = tokens[5].trim().isEmpty() && tokens[6].trim().isEmpty() && tokens[7].trim().isEmpty();

                        if (noDetails){
                            Instrument instrument = new Instrument(type, name, manufacturer, year, cost);
                            inventory.add(instrument);
                        }

                    }
                    //add string instrument
                    else {
                        String type = tokens[0].trim();
                        String name = tokens[1].trim();
                        String manufacturer = tokens[2].trim();
                        

                        int year = Integer.parseInt(tokens[3].trim());
                        int cost = Integer.parseInt(tokens[4].trim());

                        int Strings = Integer.parseInt(tokens[5].trim());
                        int frets = Integer.parseInt(tokens[5].trim());
                        boolean isBowed = tokens[7].trim().equals("1");

                        StringInstrument instrument = new StringInstrument(type, name, manufacturer, year, cost, Strings, frets, isBowed);
                        inventory.add(instrument);
                    }
                } catch (Exception e) {
                    errorWriter.write("Error: " + e.getMessage() + " — " + line + "\n");
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    //PRE:  accept the instrument arraylist and PrintWriter type (report output file) 
    //POST: print the inventory to the report file
    public static void listInstruments(ArrayList<Instrument> inventory, PrintWriter writer){
        writer.write("\n\nFULL INVENTORY LIST\n");
        writer.write(String.format("%-20s%-20s%-20s%10s%10s%15s\n","TYPE","NAME","MANUFACTURER","YEAR","COST $","STRING INFO"));
        for (Instrument i : inventory) {
            writer.print(i); 
        }

    }

    //PRE:  accept the Array list of instruments, the string to find, and the PrintWriter report output file
    //POST: print the records that contain typeQuery
    public static void findInstruments(ArrayList<Instrument> inventory, String typeQuery, PrintWriter writer) {
        
        writer.write("\n\nINVENTORY OF TYPE: " + typeQuery + "\n");

        //complete logic to only write the items of typeQuery
        writer.write(String.format("%-20s%-20s%-20s%10s%10s%15s\n","TYPE","NAME","MANUFACTURER","YEAR","COST $","STRING INFO"));
        
        //use if statements, for loop
        boolean located = false;
        for (Instrument instrument: inventory) { 
            String type = instrument.getType().toLowerCase();
            String search = typeQuery.toLowerCase();
            
            if (type.contains(search)) { 
                writer.print(instrument);

                located = true;


            }
        }
        
        if (located == false) { 
            writer.println("No instruments of type \"" + typeQuery + "\" found.");
        }
        
    }

    //PRE:  accept the Array list of instruments, the string to find, and the PrintWriter report output file
    //POST: print the inventory that was made by the 'maker'
    public static void listManufacturers(List<Instrument> inventory, String maker, PrintWriter writer) {
        boolean found = false;
        writer.write("\n\nINVENTORY FROM MAKER: " + maker + "\n");

        //complete logic to only write the items of maker
        writer.write(String.format("%-20s%-20s%-20s%10s%10s%15s\n","TYPE","NAME","MANUFACTURER","YEAR","COST $","STRING INFO"));
        for (Instrument instrument: inventory) { 
            if (instrument.getManufacturer().equalsIgnoreCase(maker));
            writer.print(instrument);

            found = true;

        }
        
        if (found == false) { 
            writer.print("No instrument by \"" + maker + "\" found.");
        }
    
    }
}
