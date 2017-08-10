package es.metichi.animabeyondfantasy.CharacterSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;
import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.SkillDefinitions;
import es.metichi.animabeyondfantasy.CharacterSheet.Items.Item;

/**
 * Character sheet
 *
 * This class will represent the character sheet of a character, containing all information related
 * to its skills, spells, race, categories, etc.
 */
public class Character implements Serializable {

    private static final long serialVersionUID = 88L;

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

        //Categorias
        categories = new ArrayList<>(0);

        //Características secundarias
        calculateSecondaryCharacteristics();
        gender = Gender.MALE;

        //Powers
        characterPowers = new ArrayList<>(0);
        modifiers = new ArrayList<>(0);

        for(String key : characteristics.keySet()){
            characteristics.get(key).setName(key);
        }

        //Skills
        combatSkills = SkillDefinitions.generateCombatSkillsFor(this);
        mysticSkills = SkillDefinitions.generateMysticSkillsFor(this);
        psychicSkill = SkillDefinitions.generatePsychicSkillsFor(this);
        secondarySkills = SkillDefinitions.generateSecondarySkillsFor(this);
        abstractSkills = calculateAbstractSkills();

        //Fluff
        setName("Unnamed");
    }

    //region Character Fluff
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String homeTown = "";

    public void setHomeTown(String homeTown) {
        this.homeTown = homeTown;
    }

    public String getHomeTown() {
        return homeTown;
    }

    private double height = 1.70;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    private int weight = 60;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    private String eyeColor = "";

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    private String hairColor = "";

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    private int age = 18;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
    //endregion

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

    public Characteristic[] getPrimaryCharacteristics(){
        return new Characteristic[]{getStrength(),getDexterity(),getAgility(),getConstitution(),getIntelligence(),getPower(),getWill(),getPerception()};
    }
    public CharacteristicRoll getCharacteristicRoll() {
        return characteristicRoll;
    }
    //TODO: Include setters
    //endregion

    //region Secondary Characteristics

    public enum Gender{MALE, FEMALE}
    private Gender gender;
    private HashMap<String,Characteristic> secondaryCharacteristics;
    public enum Inhumanity{HUMAN, INHUMAN, ZEN}
    private class InhumanityChar extends Characteristic{
        public InhumanityChar() {
            super(1,"Inhumanity");
        }
        @Override
        public int getBase(){
            this.setBase(1);
            if(!getModifiers().isEmpty()) {
                for (Modifier m : getModifiers()) {
                    this.setBase(Math.max(base, m.getValue()));
                }
            }
            return this.base;
        }

        public Inhumanity getLevel(){
            switch (getBase()){
                case 1: return Inhumanity.HUMAN;
                case 2: return Inhumanity.INHUMAN;
                default: return Inhumanity.ZEN;
            }
        }
    }

    private void calculateSecondaryCharacteristics(){
        secondaryCharacteristics = new HashMap<>();
        characteristics.put("Appearance", characteristicRoll.getAppearance());
        secondaryCharacteristics.put(characteristicRoll.getAppearance().getName(),characteristicRoll.getAppearance());

        Characteristic size = new Characteristic(getStrength().getFinalValue()+getConstitution().getFinalValue(),"Size") {
            @Override
            public int getFinalValue(){
                this.base = getStrength().getFinalValue() + getConstitution().getFinalValue();
                return super.getFinalValue();
            }
        };
        characteristics.put("Size", size);
        secondaryCharacteristics.put(size.getName(),size);

        Characteristic inhumanity = new InhumanityChar();
        characteristics.put(inhumanity.getName(),inhumanity);
        secondaryCharacteristics.put(inhumanity.getName(),inhumanity);
        Characteristic stamina = new SpendableCharacteristic(1,"Stamina"){
            @Override
            public int getBase(){
                this.base = getConstitution().getFinalValue();
                return this.base;
            }
        };
        characteristics.put(stamina.getName(),stamina);
        secondaryCharacteristics.put(stamina.getName(),stamina);

        Characteristic speed = new Characteristic(1,"Speed"){
            @Override
            public int getBase(){
                this.base = getAgility().getUsable(getInhumanityLevel());
                return this.base;
            }
        };
        characteristics.put(speed.getName(),speed);
        secondaryCharacteristics.put(speed.getName(),speed);
    }

    public Inhumanity getInhumanityLevel(){return ((InhumanityChar)characteristics.get("Inhumanity")).getLevel();}
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

    public HashMap<String, Characteristic> getSecondaryCharacteristics() {
        return secondaryCharacteristics;
    }

    //endregion

    //region Race
    protected Race race;

    public Race getRace() {
        return race;
    }
    public String getRaceName(){return race == null ? "No Race" : race.getName();}

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
        if(getCurrentCategory()!= null) {
            return getCurrentCategory().getTotalLevel();
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
    /**
     * Displays the active category
     *
     * The active category of a character is the one in wich new DP are spent. It is always the last
     * category added as, during gameplay, one should not be able to modify the previous ones.
     * @return Last Category object of the getCategories() method.
     */
    public Category getCurrentCategory() {
        if (getCategories().size() != 0) {
            return getCategories().get(getCategories().size() - 1);
        } else {
            return null;
        }
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
        return abstractSkills.get("HP");
    }
    HashMap<String,Skill> abstractSkills;

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

    public Innitiative getInnitiative() {
        return (Innitiative) abstractSkills.get("Innitiative");
    }

    public HashMap<String,Skill> calculateAbstractSkills(){
        HashMap<String,Skill> abstractSkills = new HashMap<>();
        Skill innitiative = new Innitiative(getDexterity(),getAgility(),getCategories());
        abstractSkills.put(innitiative.getName(),innitiative);
        Skill hp = SkillDefinitions.generateHealthFor(this);
        abstractSkills.put(hp.getName(),hp);
        return abstractSkills;
    }
    //endregion

    //region Psychic skills
    HashMap<String,Skill> psychicSkill;

    public HashMap<String, Skill> getPsychicSkill() {
        return psychicSkill;
    }
    //endregion

    //region Mystic skills
    HashMap<String,Skill> mysticSkills;

    public HashMap<String, Skill> getMysticSkills() {
        return mysticSkills;
    }
    //endregion

    //region Secondary skills
    private HashMap<String,Skill> secondarySkills;

    public HashMap<String, Skill> getSecondarySkills() {
        return secondarySkills;
    }
    //endregion

    //region Inventory
    //endregion

    // region Getter and Setter
    public HashMap<String,Skill> getAllSkills(){
        HashMap<String,Skill> allSkills = new HashMap<>();
        allSkills.putAll(combatSkills);
        allSkills.putAll(psychicSkill);
        allSkills.putAll(mysticSkills);
        allSkills.putAll(secondarySkills);
        allSkills.putAll(abstractSkills);
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
        if(!power.getPowerModifiers().isEmpty()) {
            for (Modifier modifier : power.getPowerModifiers()) {
                this.give(modifier);
            }
        }
    }

    /**
     * Removes a power from a character
     * @see #give(Power)
     * @param power
     */
    public void remove(Power power){
        characterPowers.remove(power);
        if(!power.getPowerModifiers().isEmpty()) {
            for (Modifier modifier : power.getPowerModifiers()) {
                this.remove(modifier);
            }
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
        if(!modifiers.isEmpty()) {
            for (Modifier m : modifiers) {
                addNremove(m, false);
                addNremove(m, true);
            }
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

    //region Save and Load
    public String getDefaultFilename(){
        return String.format("%s_%s_Lvl_%s", getName(),getCurrentCategory().getName(), getTotalLevel());
    }
    public String getFileExtension(){
        return ".abf";
    }

    public boolean save(File path, String name, Boolean overwrite){
        File newFile = new File(path,name+ getFileExtension());
        if(newFile.exists()&&!overwrite){
            return false;
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(newFile);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(this);
                oos.close();
                fos.close();
                return true;
            }catch(FileNotFoundException e){
                return false;
            }catch (IOException e){
                return false;
            }
        }
    }
    public static Character load(File file){
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Object readObject = ois.readObject();
            ois.close();
            fis.close();

            if(readObject instanceof Character){
                return (Character) readObject;
            } else {
                return null;
            }
        } catch (FileNotFoundException e){return null;}
        catch (IOException e){return null;}
        catch (ClassNotFoundException e){return null;}
    }
    //endregion

    //region Subclasses
    class SpendableCharacteristic extends Characteristic implements Spendable{
        private int currentValue;
        public SpendableCharacteristic(int i, String s){super(i,s);
        currentValue = getMaxValue();}
        @Override
        public int getCurrentValue() {
            return currentValue;
        }

        @Override
        public void setCurrentValue(int value) {
            this.currentValue = value;
        }

        @Override
        public int getMaxValue() {
            return getFinalValue();
        }
    }
}