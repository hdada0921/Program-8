import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
//Utilized Github Copilot in debugging process. 
class Main {
    public static void main(String[] args) {
        //create an arraylist of instrument objects
        ArrayList<Instrument> inventory = new ArrayList<>();

        //set input for menu choices
        Scanner scanner = new Scanner(System.in);
        String inputFile  = "instrument.csv";
        String errorFile  = "error.txt";
        String reportFile = "report.txt";

        //readFile will open & close error file
        InventoryManager.readFile(inputFile, errorFile, inventory);
        System.out.println("Inventory loaded: " + inventory.size() + " items.\n");        

        //try to open the output file for the report 
        try (PrintWriter writer = new PrintWriter(new FileWriter(reportFile))) {
            boolean running = true; 
            while (running) { 

            System.out.println("""
                Welcome to the Instrument Database
                Your choices are:
                1. List All Instruments
                2. Find Specific Instruments
                3. List All Instruments for a Manufacturer
                4. Exit
                Enter your choice: 

            """);
            
            String answer = scanner.nextLine();
            int choice;

            try{
                choice = Integer.parseInt(answer);
            } catch (NumberFormatException e){
                System.out.println("\nPlease enter numbers only!");
                continue;
            }

            if (choice == 1){
                InventoryManager.listInstruments(inventory, writer);
                writer.flush();
                System.out.println("\nList has been added to report");
            } else if (choice == 2) { 
                System.out.println("\nGreat - please provide the instrument type.");
                String type = scanner.nextLine();
                InventoryManager.findInstruments(inventory, type, writer);
                writer.flush();
            } else if (choice == 3) { 
                System.out.println("\nGreat - please provide the manufacturer type.");
                String maker = scanner.nextLine();
                InventoryManager.listManufacturers(inventory, maker, writer);
                writer.flush();
            }

        }
            

        }
        catch (FileNotFoundException e)
        { 
            
            //print the menu & stay in loop until '4' is chosen
            
            //add logic (switch?) to choose which function to do based on input
  

            scanner.close();
        
        } catch (IOException e) {
            System.err.println("Could not write report: " + e.getMessage());
        }
    }
}
