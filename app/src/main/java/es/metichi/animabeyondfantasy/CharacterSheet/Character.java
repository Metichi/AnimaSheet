package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;
import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.SkillDefinitions;

/**
 * Created by Metichi on 31/07/2017.
 */

public class Character {

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

        for(String key : characteristics.keySet()){
            characteristics.get(key).setName(key);
        }

        //Skills
        combatSkills = SkillDefinitions.generateCombatSkillsFor(this);
        mysticSkills = SkillDefinitions.generateMysticSkillsFor(this);
        psychicSkill = SkillDefinitions.generatePsychicSkillsFor(this);
        secondarySkills = SkillDefinitions.generateSecondarySkillsFor(this);
        hp = SkillDefinitions.generateHealthFor(this);
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
    //TODO: Include setters
    //endregion

    //region Secondary Characteristics

    public enum Gender{MALE, FEMALE}
    private Gender gender;

    private void calculateSecondaryCharacteristics(){
        characteristics.put("Appearance", new Characteristic(characteristicRoll.getAppearance()));

        Characteristic size = new Characteristic(getStrength().getFinalValue()+getConstitution().getFinalValue()) {
            @Override
            public int getFinalValue(){
                this.base = getStrength().getFinalValue() + getConstitution().getFinalValue();
                return super.getFinalValue();
            }
        };
        characteristics.put("Size", size);
    }

    public Characteristic getSize() {
        return characteristics.get("Appearance");
    }

    public Gender getGender() {
        return gender;
    }

    public void setAppearance(Characteristic appearance) {
        characteristics.put("Appearance", appearance);
    }

    public Characteristic getAppearance() {
        return characteristics.get("Size");
    }

    public void setGender(Gender gender) {
        this.gender = gender;
        if (this.gender == Gender.FEMALE){
            this.give(ModifierDefinitions.genderSizeModifier);
        } else {
            this.remove(ModifierDefinitions.genderSizeModifier);
        }
    }
    //endregion

    //region Race
    protected Race race;

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        if (this.race != null){
            for (Power power : this.race.getPowers()){
                this.remove(power);
            }
        }
        this.race = race;
        for(Power power : this.race.getPowers()){
            this.give(power);
        }
    }
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
    HashMap<String, Skill> combatSkills;
    //endregion

    //region Psychic skills
    HashMap<String,Skill> psychicSkill;
    //endregion

    //region Mystic skills
    HashMap<String,Skill> mysticSkills;
    //endregion

    //region Secondary skills
    HashMap<String,Skill> secondarySkills;
    Skill hp;
    //endregion

    // region Getter and Setter
    public HashMap<String,Skill> getAllSkills(){
        HashMap<String,Skill> allSkills = new HashMap<>();
        allSkills.putAll(combatSkills);
        allSkills.putAll(psychicSkill);
        allSkills.putAll(mysticSkills);
        allSkills.putAll(secondarySkills);
        allSkills.put(hp.getName(),hp);

        return allSkills;
    }
    //endregion

    // region Powers and creation points
    private ArrayList<Power> characterPowers;
    public ArrayList<Power> getCharacterPowers() {
        return characterPowers;
    }

    public void give(Power power){
        characterPowers.add(power);
        for (Modifier modifier : power.getPowerModifiers()){
            this.give(modifier);
        }
    }
    public void remove(Power power){
        characterPowers.remove(power);
        for (Modifier modifier : power.getPowerModifiers()){
            this.remove(modifier);
        }
    }
    //endregion

    //region Modifiers
    private ArrayList<Modifier> modifiers;
    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    public void give(Modifier modifier){
        modifiers.add(modifier);

        if (modifier instanceof Characteristic.CharacteristicModifier){
            Characteristic.CharacteristicModifier characteristicModifier = (Characteristic.CharacteristicModifier) modifier;
            for (String affectedCharacteristic : characteristicModifier.getAffectedFields()){
                for (String characteristic : characteristics.keySet()){
                    if (characteristic.equals(affectedCharacteristic)){
                        characteristics.get(characteristic).add(modifier);
                    }
                }
            }
        }
    }

    public void remove(Modifier modifier){
        modifiers.remove(modifier);

        if (modifier instanceof Characteristic.CharacteristicModifier){
            Characteristic.CharacteristicModifier characteristicModifier = (Characteristic.CharacteristicModifier) modifier;
            for (String affectedCharacteristic : characteristicModifier.getAffectedFields()){
                for (String characteristic : characteristics.keySet()){
                    if (characteristic.equals(affectedCharacteristic)){
                        characteristics.get(characteristic).remove(modifier);
                    }
                }
            }
        }
    }

    //endregion
}