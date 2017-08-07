package es.metichi.animabeyondfantasy.CharacterSheet;

import java.io.Serializable;

/**
 * Created by Metichi on 31/07/2017.
 */

public abstract class CharacteristicRoll implements Serializable {
    protected Characteristic.PhysicalCharacteristic strength, dexterity, constitution, agility;
    protected Characteristic.IntellectualCharacteristic intelligence, power, will, perception;
    protected Characteristic appearance;
    protected int socialClass;

    public CharacteristicRoll(){
        createCharacteristics();
        roll();
    }

    protected void createCharacteristics(){
        strength = new Characteristic.PhysicalCharacteristic(1,"Strength");
        dexterity = new Characteristic.PhysicalCharacteristic(2,"Dexterity");
        constitution = new Characteristic.PhysicalCharacteristic(3,"Constitution");
        agility = new Characteristic.PhysicalCharacteristic(4,"Agility");
        intelligence = new Characteristic.IntellectualCharacteristic(5,"Intelligence");
        power = new Characteristic.IntellectualCharacteristic(6,"Power");
        will = new Characteristic.IntellectualCharacteristic(8,"Will");
        perception = new Characteristic.IntellectualCharacteristic(10,"Perception");
        appearance = new Characteristic(5,"Appearance");
        socialClass = 50;
    }

    public abstract String getName();

    public Characteristic.PhysicalCharacteristic getStrength() {
        return strength;
    }

    public Characteristic.PhysicalCharacteristic getDexterity() {
        return dexterity;
    }

    public Characteristic.PhysicalCharacteristic getConstitution() {
        return constitution;
    }

    public Characteristic.PhysicalCharacteristic getAgility() {
        return agility;
    }

    public Characteristic.IntellectualCharacteristic getIntelligence() {
        return intelligence;
    }

    public Characteristic.IntellectualCharacteristic getPower() {
        return power;
    }

    public Characteristic.IntellectualCharacteristic getWill() {
        return will;
    }

    public Characteristic.IntellectualCharacteristic getPerception() {
        return perception;
    }

    public int getSocialClass() {
        return socialClass;
    }

    public Characteristic getAppearance() {
        return appearance;
    }

    public abstract void roll();
    protected void switchBases(Characteristic a, Characteristic b){
        int baseA = a.getBase();
        a.setBase(b.getBase());
        b.setBase(baseA);
    }
}
