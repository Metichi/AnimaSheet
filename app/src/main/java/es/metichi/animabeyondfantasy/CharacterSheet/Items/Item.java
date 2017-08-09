package es.metichi.animabeyondfantasy.CharacterSheet.Items;

/**
 * Created by Metichi on 01/08/2017.
 */

public class Item {
    protected String name;
    protected String description;
    protected float weight;
    protected float value;
    protected int quality;
    protected int presence;
    public enum Aviability{
        COMMON,
        UNCOMMON,
        RARE
    }
    protected Aviability aviability;

    public Item(String name, String description, float weight, float value, int quality){
        this.name = name;
        this.description = description;
        this.weight = weight;
        this.value = value;
        this.quality = quality;
        this.aviability = Aviability.COMMON;
    }
    public Item(String name, String description, float weight, float value, int quality, Aviability aviability){
        this(name,description,weight,value,quality);
        this.aviability = aviability;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(float value) {
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

    public float getValue() {
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
        public Weapon(String name, String description, float weight, float value, int quality){
            super(name,description, weight, value, quality);
        }
        //TODO: Complete weapon class.
    }
}
