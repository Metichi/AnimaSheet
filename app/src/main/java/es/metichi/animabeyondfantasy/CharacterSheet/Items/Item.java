package es.metichi.animabeyondfantasy.CharacterSheet.Items;

/**
 * Created by Metichi on 01/08/2017.
 */

public class Item {
    protected String name;
    protected String description;
    protected float weight;
    protected Currency value;
    protected int quality;
    protected int presence;
    public enum Aviability{
        COMMON,
        UNCOMMON,
        RARE
    }
    protected Aviability aviability;

    public Item(String name, String description, float weight, Currency value, int quality){
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
        this.quality = quality;
        this.aviability = Aviability.COMMON;
    }
    public Item(String name, String description, float weight, Currency value, int quality, Aviability aviability){
        this(name,description,weight,value,quality);
        this.aviability = aviability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Currency value) {
        this.value = value;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public Currency getValue() {
        return value;
    }

    public float getWeight() {
        return weight;
    }

    public int getQuality() {
        return quality;
    }

    public int getPresence() {
        return presence;
    }

    public static class Weapon extends Item{
        public Weapon(String name, String description, float weight, Currency value, int quality){
            super(name,description, weight, value, quality);
        }
        //TODO: Complete weapon class.
    }

    /**
     * Class to represent an ammount of money
     *
     * This class holds an ammount of money and contains methods to get the stored ammount, compare
     * it to another and change the coins into bigger coins.
      */
    public static class Currency{
        private int copperPieces;
        private int silverPieces;
        private int goldPieces;

        public Currency(int gold,int silver, int copper){
            this.goldPieces = gold;
            this.silverPieces = silver;
            this.copperPieces = copper;
        }
        public Currency(int totalCopper){
            int copper = totalCopper%10;
            int remaining = totalCopper/10;
            int silver = remaining%100;
            int gold = remaining/100;

            this.copperPieces=copper;
            this.silverPieces=silver;
            this.goldPieces=gold;
        }

        public void addCopperPieces(int c){
            this.copperPieces += c;
        }
        public void addSilverPieces(int c){
            this.silverPieces += c;
        }
        public void addGoldPieces(int c){
            this.goldPieces += c;
        }

        public int getCopperPieces() {
            return copperPieces;
        }

        public int getGoldPieces() {
            return goldPieces;
        }

        public int getSilverPieces() {
            return silverPieces;
        }

        public void setCopperPieces(int copperPieces) {
            this.copperPieces = copperPieces;
        }

        public void setGoldPieces(int goldPieces) {
            this.goldPieces = goldPieces;
        }

        public void setSilverPieces(int silverPieces) {
            this.silverPieces = silverPieces;
        }

        public int getTotatlCopperPieces(){
            int totalCopperPieces = getCopperPieces();
            totalCopperPieces += getSilverPieces()*10;
            totalCopperPieces += getGoldPieces()*1000;
            return totalCopperPieces;
        }
        public void changeCurrency(){
            int remainder = getCopperPieces()%10;
            addSilverPieces(getCopperPieces()/10);
            setCopperPieces(remainder);

            remainder = getSilverPieces()%100;
            addGoldPieces(getSilverPieces()/100);
            setSilverPieces(remainder);
        }

        public void add(Currency c){
            addCopperPieces(c.getCopperPieces());
            addSilverPieces(c.getSilverPieces());
            addGoldPieces(c.getGoldPieces());
        }
        public void remove(Currency c){
            addCopperPieces(-c.getCopperPieces());
            addSilverPieces(-c.getSilverPieces());
            addGoldPieces(-c.getGoldPieces());
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Currency){
                return getTotatlCopperPieces() == ((Currency)obj).getTotatlCopperPieces();
            }else{
                return false;
            }
        }

        public boolean greaterThan(Currency c){
            return getTotatlCopperPieces() > c.getTotatlCopperPieces();
        }
        public boolean lesserThan(Currency c){
            return getTotatlCopperPieces() < c.getTotatlCopperPieces();
        }
    }
}
