class StringInstrument extends Instrument {
    private int numStrings;
    private int numFrets;
    private boolean isBowed;

    //complete overloaded constructor 
    public StringInstrument(String type, String name, String manufacturer,
                            int year, int cost, int strings, int frets, boolean bowed) {
        super(type, name, manufacturer, year, cost);

        numStrings = strings;
        numFrets = frets;
        isBowed = bowed;

    }

    //complete getters
    public int getNumStrings() { return numStrings; }
    public int getnumFrets()   {return numFrets;}
    public boolean getisBowed() { return isBowed;}


    //complete setters
    public void setNumStrings(int strings) { this.numStrings = strings; }
    public void setNumFrets(int numFrets) {this.numFrets = numFrets;}
    public void setisBowed(boolean bowed) {this.isBowed = bowed;}

    //correct toString to print String Instrument information (adding the numString, numFrets, isBowed)
    @Override
    public String toString() {
        String bowedText;
        
        if(isBowed){
            bowedText = "Bowed";
        } else {
            bowedText = "Not Bowed";
        }

        return String.format("%-20s%-20s%-20s%10s%10s%15s%n", getType(), getName(), getManufacturer(), getYear(), getCost(), numStrings + " " + numFrets + " " + bowedText);
    }
}
