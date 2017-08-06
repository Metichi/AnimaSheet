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
        strength = new Characteristic.PhysicalCharacteristic(5);
        dexterity = new Characteristic.PhysicalCharacteristic(5);
        constitution = new Characteristic.PhysicalCharacteristic(5);
        agility = new Characteristic.PhysicalCharacteristic(5);
        intelligence = new Characteristic.IntellectualCharacteristic(5);
        power = new Characteristic.IntellectualCharacteristic(5);
        will = new Characteristic.IntellectualCharacteristic(5);
        perception = new Characteristic.IntellectualCharacteristic(5);
        appearance = new Characteristic(5);
        socialClass = 50;
        strength.setName("Strength");
        dexterity.setName("Dexterity");
        constitution.setName("Constitution");
        agility.setName("Agility");
        intelligence.setName("Intelligence");
        power.setName("Power");
        will.setName("Will");
        perception.setName("Perception");
        appearance.setName("Appearance");
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
