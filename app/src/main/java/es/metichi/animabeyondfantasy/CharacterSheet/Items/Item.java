package es.metichi.animabeyondfantasy.CharacterSheet.Items;

/**
 * Created by Metichi on 01/08/2017.
 */

public class Item {
    protected String name;
    protected float weight;
    protected float value;
    protected int quality;
    protected int presence;

    public Item(String name, float weight, float value, int quality){
        this.name = name;
        this.weight = weight;
        this.value = value;
        this.quality = quality;
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

    public static class Weapon extends Item{
        public Weapon(String name, float weight, float value, int quality){
            super(name, weight, value, quality);
        }
        //TODO: Complete weapon class.
    }
}
