package es.metichi.animabeyondfantasy.CharacterSheet;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;

/**
 * Created by Metichi on 01/08/2017.
 */

public abstract class Category implements Modifyable{
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
    public Category(int level, @Nullable Category previousCategory){
        this.level = level;
        this.previousCategory = previousCategory;
    }

    //region Level

    /**
     * Gets the level of the category
     * @return Level of the category
     */
    public int getLevel() {
        return level;
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



    public abstract ArrayList<Archetype> getArchetype();

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
    public abstract CategoryModifier getCategoryModifierOf(Skill skill);

    public abstract int getPercentageOnCombat();
    public abstract int getPercentageOnMystic();
    public abstract int getPercentageOnPsychic();

    public abstract int getCategoryCostOf(Skill skill);
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

}
