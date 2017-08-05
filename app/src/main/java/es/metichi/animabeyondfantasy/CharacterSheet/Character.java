package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;
import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.SkillDefinitions;
/**
 * Character sheet
 *
 * This class will represent the character sheet of a character, containing all information related
 * to its skills, spells, race, categories, etc.
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
        return characteristics.get("Size");
    }

    public Gender getGender() {
        return gender;
    }

    public void setAppearance(Characteristic appearance) {
        characteristics.put("Appearance", appearance);
    }

    public Characteristic getAppearance() {
        return characteristics.get("Appearance");
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
        if(getCategories().size()>0) {
            return getCategories().get(getCategories().size() - 1).getTotalLevel();
        }else{
            return 0;
        }
    }
    //endregion

    //region Categories
    ArrayList<Category> categories;

    public ArrayList<Category> getCategories() {
        return categories;
    }
    //endregion

    //region Combat skills
    private HashMap<String, Skill> combatSkills;
    private Skill.CombatSkill getCombatSkill(String string) {return (Skill.CombatSkill) combatSkills.get(string);}
    public Skill.CombatSkill getAttack(){
        return getCombatSkill("Attack");
    }
    public Skill.CombatSkill getDefense() {return getCombatSkill("Defense");}
    public Skill.CombatSkill getDodge() {return getCombatSkill("Dodge");}

    //endregion

    //region Abstract Skills
    private Skill hp;
    public Skill getHp() {
        return hp;
    }
    private int currentHp;
    public int getCurrentHp() {
        return currentHp;
    }
    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
    }
    public void healToMax(){this.currentHp = hp.getFinalValue();}

    public class Innitiative extends Skill{
        Characteristic dexterity;
        Characteristic agility;
        public Innitiative(Characteristic dexterity, Characteristic agility ,ArrayList<Category> categories){
            super("Innitiative",null,categories);
            this.dexterity = dexterity;
            this.agility = agility;
        }
        @Override
        public int getBaseValue(){
            return 20;
        }

        public int getDexBonus(){
            return dexterity.getSkillBonus();
        }
        public int getAgiBonus(){
            return agility.getSkillBonus();
        }
        @Override
        public int getCharacteristicBonus(){
            return getDexBonus() + getAgiBonus();
        }
    }
    private Innitiative innitiative = new Innitiative(getDexterity(),getAgility(),getCategories());

    public Innitiative getInnitiative() {
        return innitiative;
    }
    //endregion

    //region Psychic skills
    HashMap<String,Skill> psychicSkill;
    //endregion

    //region Mystic skills
    HashMap<String,Skill> mysticSkills;
    //endregion

    //region Secondary skills
    private HashMap<String,Skill> secondarySkills;

    //endregion

    // region Getter and Setter
    public HashMap<String,Skill> getAllSkills(){
        HashMap<String,Skill> allSkills = new HashMap<>();
        allSkills.putAll(combatSkills);
        allSkills.putAll(psychicSkill);
        allSkills.putAll(mysticSkills);
        allSkills.putAll(secondarySkills);
        allSkills.put(hp.getName(),hp);
        allSkills.put(innitiative.getName(),innitiative);

        return allSkills;
    }
    //endregion

    // region Powers and creation points
    private ArrayList<Power> characterPowers;
    public ArrayList<Power> getCharacterPowers() {
        return characterPowers;
    }


    /**
     * Gives a power to a character.
     *
     * A power consists of a description and any number of modifiers. The power is stored in the
     * character sheet and the modifiers applied to the character.
     * @param power
     */
    public void give(Power power){
        characterPowers.add(power);
        for (Modifier modifier : power.getPowerModifiers()){
            this.give(modifier);
        }
    }

    /**
     * Removes a power from a character
     * @see #give(Power)
     * @param power
     */
    public void remove(Power power){
        characterPowers.remove(power);
        for (Modifier modifier : power.getPowerModifiers()){
            this.remove(modifier);
        }
    }
    //endregion

    //region Modifiers
    private ArrayList<Modifier> modifiers;

    /**
     * Shows the complete list of modifiers of a character
     *
     * When a modifier is given to the character, is stored in a complete list as well as in each
     * modifyable field.
     * @return Complete list of modifiers.
     */
    public ArrayList<Modifier> getModifiers() {
        return modifiers;
    }

    /**
     * Gives a modifier to a character
     *
     * This method checks for the different kinds of modifier and call the add(Modifier) method on each
     * modifyable.
     * @param modifier Modifier to apply
     */
    public void give(Modifier modifier){
        modifiers.add(modifier);
        addNremove(modifier,true);
    }

    /**
     * Removes a modifier from a character
     *
     * This method checks for the different kinds of modifier and call the remove(Modifier) method on each
     * modifyable.
     * @param modifier
     */
    public void remove(Modifier modifier){
        modifiers.remove(modifier);
        addNremove(modifier,false);
    }

    public void refreshModifiers(){
        for (Modifier m : modifiers){
            addNremove(m,false);
            addNremove(m,true);
        }
    }

    private void addNremove(Modifier modifier, boolean add){
        if (modifier instanceof Characteristic.CharacteristicModifier){
            Characteristic.CharacteristicModifier characteristicModifier = (Characteristic.CharacteristicModifier) modifier;
            for (String affectedCharacteristic : characteristicModifier.getAffectedFields()){
                for (String characteristic : characteristics.keySet()){
                    if (characteristic.equals(affectedCharacteristic)){
                        if(add) {characteristics.get(characteristic).add(modifier);}
                        else {characteristics.get(characteristic).remove(modifier);}
                    }
                }
            }
        } else if (modifier instanceof Skill.SkillModifier){
            HashMap<String, Skill> allSkills = getAllSkills();
            for(String skill : allSkills.keySet()){
                for(String affectedSkill : modifier.getAffectedFields()){
                    if(skill.equals(affectedSkill)){
                        if(add) {allSkills.get(skill).add(modifier);}
                        else { allSkills.get(skill).remove(modifier);}
                    }
                }
            }
        } else if (modifier instanceof Category.CostModifier){
            for (Category category : categories){
                if (add) {category.add(modifier);}
                else {category.remove(modifier);}
            }
        }
    }

    //endregion
}