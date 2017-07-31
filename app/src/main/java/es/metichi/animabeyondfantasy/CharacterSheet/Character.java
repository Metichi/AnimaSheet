package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Character {
    private ArrayList<Modifier> modifiers;

    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }



    public enum Inhumanity{HUMAN, INHUMAN, ZEN}


    public Character(CharacteristicRoll roll){
        //GENERACIÓN DE CARACTERÍSTICAS
        this.strength = roll.getStrength();
        this.dexterity = roll.getDexterity();
        this.agility = roll.getAgility();
        this.constitution = roll.getConstitution();
        this.intelligence = roll.getIntelligence();
        this.power = roll.getPower();
        this.will = roll.getWill();
        this.perception = roll.getPerception();
        this.characteristicRoll = roll;

        //Características secundarias
        calculateSecondaryCharacteristics();
    }

    //region Characteristics
    private Characteristic.PhysicalCharacteristic strength, dexterity, agility, constitution;
    private Characteristic.IntellectualCharacteristic intelligence, power, will, perception;
    private CharacteristicRoll characteristicRoll;

    public Characteristic.PhysicalCharacteristic getStrength() {
        return strength;
    }

    public Characteristic.PhysicalCharacteristic getDexterity() {
        return dexterity;
    }

    public Characteristic.PhysicalCharacteristic getAgility() {
        return agility;
    }

    public Characteristic.PhysicalCharacteristic getConstitution() {
        return constitution;
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

    public CharacteristicRoll getCharacteristicRoll() {
        return characteristicRoll;
    }
    //endregion

    //region Secondary Characteristics

    public enum Gender{MALE, FEMALE}
    private Gender gender;
    private Characteristic appearance;
    private Characteristic size;
    private void calculateSecondaryCharacteristics(){
        this.appearance  = new Characteristic(characteristicRoll.getAppearance());
        this.size = new Characteristic(getStrength().getFinalValue()+getConstitution().getFinalValue()) {
            @Override
            public int getFinalValue(){
                this.base = getStrength().getFinalValue() + getConstitution().getFinalValue();
                return super.getFinalValue();
            }
        };
    }

    public Characteristic getSize() {
        return size;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        if (gender == Gender.FEMALE){
            ModifierDefinitions.genderSizeModifier.giveTo(this);
        } else {
            ModifierDefinitions.genderSizeModifier.removeFrom(this);
        }
    }

    public Gender getGender() {
        return gender;
    }

    public void setAppearance(Characteristic appearance) {
        this.appearance = appearance;
    }

    public Characteristic getAppearance() {
        return appearance;
    }
    //endregion

    //region Race
    //endregion

    //region Experience
    public int getTotalLevel(){
        return 0;
    }
    //endregion

    //region Categories
    //endregion

    //region Combat habilities
    //endregion

    //region Psychic habilities
    //endregion

    //region Mystic habilities
    //endregion

    // region Getter and Setter

    //endregion


}