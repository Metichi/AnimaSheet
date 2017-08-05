package es.metichi.animabeyondfantasy.CharacterSheet;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;

/**
 * Created by Metichi on 01/08/2017.
 */

public class Category implements Modifyable, Serializable{
    public enum Archetype{
        FIGHTER,
        MYSTIC,
        PSYCHIC,
        STALKER,
        DOMINE,
        UNCHATEGORIZED
    }

    private int level;
    private Category previousCategory;
    private CategoryBundle categoryBundle;
    public Category(int level, @Nullable Category previousCategory, CategoryBundle categoryBundle){
        this.level = level;
        this.previousCategory = previousCategory;
        this.categoryBundle = categoryBundle;
    }

    //region Level

    /**
     * Gets the level of the category
     * @return Level of the category
     */
    public int getLevel() {
        return level;
    }

    public int getTotalLevel(){
        if (previousCategory == null){
            return getLevel();
        } else {
            return getLevel() + previousCategory.getTotalLevel();
        }
    }

    /**
     * Sets the level of the category
     * @param level Level of the category
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Ammount of DP aviable
     *
     * This method calculates the ammount of DP aviable to this category depending on the level and
     * wether or not there is a previous category.
     * @return DP aviable
     */
    public int getDPAtThisLevel(){
        if (previousCategory == null){
            switch (level){
                case 0:
                    return 400;
                case 1:
                    return 600;
                default:
                    return (500 + 100*level);
            }
        } else {
            return 100*level;
        }
    }

    /**
     * Returns the cost of changing class
     *
     * This method returns an integer value that represents the ammount of DP expended in changing
     * from the previous Category to this one.
     * @return cost of changing category.
     */
    public int getMulticlassCost(){
        if (previousCategory == null){
            return 0;
        } else {
            ArrayList<Archetype> previousArchetype = previousCategory.getArchetype();
            if (previousArchetype.contains(Archetype.UNCHATEGORIZED) || this.getArchetype().contains(Archetype.UNCHATEGORIZED)){
                return 20;
            } else {
                boolean match = false;
                for (Archetype archetype : this.getArchetype()){
                    if (previousArchetype.contains(archetype)){
                        if (previousArchetype.size() == 1 && this.getArchetype().size() == 1){
                            return 20;
                        } else {
                            return 40;
                        }
                    }
                }
                return 60;
            }
        }
    }

    public int getMaxDP(){
        return (getDPAtThisLevel() - getMulticlassCost());
    }
    public int getMaxDPOnCombat(){
        return (getMaxDP()*getPercentageOnCombat())/100;
    }
    public int getMaxDPOnMystic(){
        return (getMaxDP()*getPercentageOnMystic())/100;
    }
    public int getMaxDPOnPsychic(){
        return (getMaxDP()*getPercentageOnPsychic())/100;
    }
    //endregion

    //region DP management
    private HashMap<Skill, Integer> dpInvestedOnSkills = new HashMap<>();

    public void setDPInvestedOn(Skill skill, int dp){
        dpInvestedOnSkills.put(skill,dp);
    }
    public int getDPInvestedOn(Skill skill){
        return dpInvestedOnSkills.get(skill);
    }

    protected ArrayList<Table> knownTables = new ArrayList<>(0);
    public void buyTable(Table table){
        knownTables.add(table);
    }
    public void forgetTable(Table table){
        knownTables.remove(table);
    }
    public ArrayList<Table> getKnownTables() {
        return knownTables;
    }

    public int getDPInvestedOnCombat(){
        Iterator it = dpInvestedOnSkills.keySet().iterator();
        int dp = 0;
        while (it.hasNext()){
            Skill skill = (Skill) it.next();
            if (skill instanceof Skill.CombatSkill){
                dp += dpInvestedOnSkills.get(skill);
            }
        }
        for (Table table : knownTables){
            if (table instanceof Table.WeaponTable
                    || table instanceof Table.CombatStyleTable
                    || table instanceof Table.MartialArtsTable
                    || table instanceof Table.ArsMagnus){
                dp += getCostOf(table);
            }
        }
        return dp;
    }
    public int getDPInvestedOnMystic(){
        Iterator it = dpInvestedOnSkills.keySet().iterator();
        int dp = 0;
        while (it.hasNext()){
            Skill skill = (Skill) it.next();
            if (skill instanceof Skill.MysticSkill){
                dp += dpInvestedOnSkills.get(skill);
            }
        }
        for (Table table : knownTables){
            if (table instanceof Table.MysticTable){
                dp += getCostOf(table);
            }
        }
        return dp;
    }
    public int getDPInvestedOnPsychic(){
        Iterator it = dpInvestedOnSkills.keySet().iterator();
        int dp = 0;
        while (it.hasNext()){
            Skill skill = (Skill) it.next();
            if (skill instanceof Skill.MysticSkill){
                dp += dpInvestedOnSkills.get(skill);
            }
        }
        for (Table table : knownTables){
            if (table instanceof Table.PsychicPattern
                    || table instanceof Table.PsychicTable){
                dp += getCostOf(table);
            }
        }
        return dp;
    }
    public int getDPInvestedOnSecondary(){
        Iterator it = dpInvestedOnSkills.keySet().iterator();
        int dp = 0;
        while (it.hasNext()){
            Skill skill = (Skill) it.next();
            if (skill instanceof Skill.MysticSkill){
                dp += dpInvestedOnSkills.get(skill);
            }
        }
        return dp;
    }
    public int getTotalDPInvested(){
        return getDPInvestedOnCombat() + getPercentageOnMystic() + getPercentageOnPsychic() + getDPInvestedOnSecondary();
    }
    //endregion

    public Category getPreviousCategory() {
        return previousCategory;
    }
    public void setPreviousCategory(Category previousCategory) {
        this.previousCategory = previousCategory;
    }
    public ArrayList<Archetype> getArchetype(){
        return categoryBundle.getArchetypes();
    };

    /**
     * Category dependant bonus of a specific skill
     *
     * Categories offer bonuses to skills related to the level in that category. When inputed a skill
     * the method will compare it with the category specific definitions and return a CategoryModifier
     * object wich value depends on the level of the skill.
     *
     * Hp, Innitiative and CV are considered skills.
     * @param skill Skill to modify
     * @return Category modifier to that skill.
     */
    public CategoryModifier getCategoryModifierOf(Skill skill){
        String source = categoryBundle.getName();
        String description = String.format("Ganancia de %s por nivel", skill.getName());
        String[] affectedFields = new String[]{skill.getName()};
        if (categoryBundle.getSkillGainByLevel().containsKey(skill.getName())) {
            final int gain = categoryBundle.getSkillGainByLevel().get(skill.getName());
            CategoryModifier categoryModifier = new CategoryModifier(source, description, affectedFields) {
                @Override
                public int getValue() {
                    return getLevel() * gain;
                }
            };
            return categoryModifier;
        } else if (skill.getName().equals("CV")){
            final int interval = categoryBundle.getIntervalOfLevelBetweenCV();
            return new CategoryModifier(source,description,affectedFields) {
                @Override
                public int getValue() {
                    if (getPreviousCategory() == null){return 1 + (getTotalLevel()-1)/interval;}
                    else {return (getTotalLevel()-1)/interval;}
                }
            };
        } else {
            return null;
        }
    }

    public int getPercentageOnCombat(){
        return categoryBundle.getPercentageOnCombat();
    };
    public int getPercentageOnMystic(){
        return categoryBundle.getPercentageOnMystic();
    };
    public int getPercentageOnPsychic(){
        return categoryBundle.getPercentageOnPsychic();
    }

    public int getCategoryCostOf(Skill skill){
        if(skill instanceof Skill.CombatSkill){
            if (skill instanceof Skill.CombatSkill.KiPoint){return categoryBundle.getCostOfKiPoints();}
            else if (skill instanceof Skill.CombatSkill.KiAcumulation){return categoryBundle.getCostOfKiAcumulation();}
            else{
                if(categoryBundle.getCostOfCombatSkills().containsKey(skill.getName())){
                    return categoryBundle.getCostOfCombatSkills().get(skill.getName());
                } else {
                    return 99;
                }
            }
        } else if (skill instanceof Skill.MysticSkill){
            if (categoryBundle.getCostOfMysticSkills().containsKey(skill.getName())){
                return categoryBundle.getCostOfMysticSkills().get(skill.getName());
            } else {
                return 99;
            }
        } else if (skill instanceof Skill.PsychicSkill){
            if (categoryBundle.getCostOfPsychicSkills().containsKey(skill.getName())){
                return categoryBundle.getCostOfPsychicSkills().get(skill.getName());
            } else {
                return 99;
            }
        } else if (skill instanceof Skill.SecondarySkill){
            int typeCost;
            if (categoryBundle.getCostOfSecondaryType().containsKey(((Skill.SecondarySkill) skill).getType())){
                typeCost = categoryBundle.getCostOfSecondaryType().get(((Skill.SecondarySkill) skill).getType());
            } else {
                return 99;
            }
            if (categoryBundle.getCostOfSecondarySkill().containsKey(skill.getName())){
                return categoryBundle.getCostOfSecondarySkill().get(skill.getName());
            } else {
                return typeCost;
            }
        } else {
            switch (skill.getName()){
                case "HP": return categoryBundle.getHpCost();
                default: return 99;
            }
        }

    }
    public int getCostOf(Skill skill){
        int cost = getCategoryCostOf(skill);
        for (Modifier m : costModifiers){
            if (m instanceof CostModifier){
                if(((CostModifier) m).affects(skill)){cost += m.getValue();}
            }
        }
        return Math.max(1,cost);
    }
    public int getCostOf(Table table){
        return table.getDpCost();
    }



    ArrayList<Modifier> costModifiers = new ArrayList<>(0);
    @Override
    public ArrayList<Modifier> getModifiers() {
        return costModifiers;
    }

    @Override
    public void add(Modifier modifier) {
        if(modifier instanceof CostModifier){
            costModifiers.add(modifier);
        }
    }

    @Override
    public void remove(Modifier modifier) {
        if(modifier instanceof CostModifier){
            costModifiers.remove(modifier);
        }
    }

    public static class CostModifier extends Modifier{
        public CostModifier(int value, String source, String description, String[] affectedFields){
            super(value,source,description,affectedFields);
        }
        public boolean affects(Skill skill){
            for (String field : getAffectedFields()){
                if(field.equals(skill.getName())){return true;}
            }
            return false;
        }
    }
    public static abstract class CategoryModifier extends Skill.SkillModifier{
        public CategoryModifier(String source, String description, String[] affectedFields){
            super(0,source,description,affectedFields);
        }

        @Override
        public abstract int getValue();
    }

    public static class CategoryBundle{
        private String name;
        private int percentageOnPsychic;
        private int percentageOnMystic;
        private int percentageOnCombat;

        private int costOfKiPoints;
        private int costOfKiAcumulation;

        private HashMap<String, Integer> costOfCombatSkills;
        private HashMap<String, Integer> costOfMysticSkills;
        private HashMap<String, Integer> costOfPsychicSkills;

        private HashMap<Skill.SecondarySkill.SecondarySkillType,Integer> costOfSecondaryType;
        private HashMap<String, Integer> costOfSecondarySkill;
        private int hpCost;
        private ArrayList<Archetype> archetypes;

        private int intervalOfLevelBetweenCV;
        private HashMap<String,Integer> skillGainByLevel;

        public CategoryBundle(String name, int percentageOnCombat, int percentageOnMystic,
                              int percentageOnPsychic, int costOfKiAcumulation, int costOfKiPoints,
                              int hpCost, int intervalOfLevelBetweenCV,
                              HashMap<String,Integer> costOfCombatSkills,
                              HashMap<String,Integer> costOfMysticSkills,
                              HashMap<String,Integer> costOfPsychicSkills,
                              HashMap<String,Integer> costOfSecondarySkill,
                              HashMap<String,Integer> skillGainByLevel,
                              HashMap<Skill.SecondarySkill.SecondarySkillType,Integer> costOfSecondaryType,
                              ArrayList<Archetype> archetypes){
            this.name = name;
            this.percentageOnCombat = percentageOnCombat;
            this.percentageOnMystic = percentageOnMystic;
            this.percentageOnPsychic = percentageOnPsychic;
            this.costOfKiAcumulation = costOfKiAcumulation;
            this.costOfKiPoints = costOfKiPoints;
            this.hpCost = hpCost;
            this.intervalOfLevelBetweenCV = intervalOfLevelBetweenCV;
            this.costOfCombatSkills = costOfCombatSkills;
            this.costOfPsychicSkills = costOfPsychicSkills;
            this.costOfMysticSkills = costOfMysticSkills;
            this.costOfSecondarySkill = costOfSecondarySkill;
            this.skillGainByLevel = skillGainByLevel;
            this.costOfSecondaryType = costOfSecondaryType;
            this.archetypes = archetypes;
        }

        public String getName() {
            return name;
        }

        public HashMap<Skill.SecondarySkill.SecondarySkillType, Integer> getCostOfSecondaryType() {
            return costOfSecondaryType;
        }

        public HashMap<String, Integer> getCostOfCombatSkills() {
            return costOfCombatSkills;
        }

        public HashMap<String, Integer> getCostOfMysticSkills() {
            return costOfMysticSkills;
        }

        public HashMap<String, Integer> getCostOfPsychicSkills() {
            return costOfPsychicSkills;
        }

        public HashMap<String, Integer> getCostOfSecondarySkill() {
            return costOfSecondarySkill;
        }

        public HashMap<String, Integer> getSkillGainByLevel() {
            return skillGainByLevel;
        }

        public int getCostOfKiAcumulation() {
            return costOfKiAcumulation;
        }

        public int getCostOfKiPoints() {
            return costOfKiPoints;
        }

        public int getHpCost() {
            return hpCost;
        }

        public int getIntervalOfLevelBetweenCV() {
            return intervalOfLevelBetweenCV;
        }

        public int getPercentageOnCombat() {
            return percentageOnCombat;
        }

        public int getPercentageOnMystic() {
            return percentageOnMystic;
        }

        public int getPercentageOnPsychic() {
            return percentageOnPsychic;
        }

        public void setSkillGainByLevel(HashMap<String, Integer> skillGainByLevel) {
            this.skillGainByLevel = skillGainByLevel;
        }

        public ArrayList<Archetype> getArchetypes() {
            return archetypes;
        }
    }

}
