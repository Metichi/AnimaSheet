package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;

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
        characteristics = new HashMap<>(8);
        characteristics.put("Strength", roll.getStrength());
        characteristics.put("Dexterity", roll.getDexterity());
        characteristics.put("Agility", roll.getAgility());
        characteristics.put("Constitution", roll.getConstitution());
        characteristics.put("Power", roll.getPower());
        characteristics.put("Intelligence", roll.getIntelligence());
        characteristics.put("Will", roll.getWill());
        characteristics.put("Perception",roll.getPerception());
        this.characteristicRoll = roll;

        //Características secundarias
        calculateSecondaryCharacteristics();

        //Powers
        characterPowers = new ArrayList<>(0);
    }

    //region Characteristics
    private HashMap<String,Characteristic> characteristics;
    private CharacteristicRoll characteristicRoll;

    public Characteristic getStrength() {
        return characteristics.get("Strength");
    }

    public Characteristic getDexterity() {
        return characteristics.get("Dexterity");
    }

    public Characteristic getAgility() {
        return characteristics.get("Agility");
    }

    public Characteristic getConstitution() {
        return characteristics.get("Constitution");
    }

    public Characteristic getIntelligence() {
        return characteristics.get("Intelligence");
    }

    public Characteristic getPower() {
        return characteristics.get("Power");
    }

    public Characteristic getWill() {
        return characteristics.get("Will");
    }

    public Characteristic getPerception() {
        return characteristics.get("Perception");
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
    ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }
    //endregion

    //region Combat skills
    //endregion

    //region Psychic skills
    //endregion

    //region Mystic skills
    //endregion

    //region Secondary skills
    //endregion

    // region Getter and Setter

    //endregion

    // region Powers and creation points
    private ArrayList<Power> characterPowers;

    public ArrayList<Power> getCharacterPowers() {
        return characterPowers;
    }
}