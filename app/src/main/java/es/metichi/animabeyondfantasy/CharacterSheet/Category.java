package es.metichi.animabeyondfantasy.CharacterSheet;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import es.metichi.animabeyondfantasy.CharacterSheet.Definitions.ModifierDefinitions;

/**
 * Created by Metichi on 01/08/2017.
 */

public abstract class Category {
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
    private ArrayList<ModifierDefinitions.CostModifier> costModifiers;

    public ArrayList<ModifierDefinitions.CostModifier> getCostModifiers() {
        return costModifiers;
    }

    public Category(int level, @Nullable Category previousCategory){
        this.level = level;
        this.previousCategory = previousCategory;
    }

    //region Level and DP management
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    private int getDPAtThisLevel(){
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

    private int getMulticlassCost(){
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

    private HashMap<Skill, Integer> dpInvestedOnSkills = new HashMap<>();
    public void setDPInvestedOn(Skill skill, int dp){
        dpInvestedOnSkills.put(skill,dp);
    }
    public int getDPInvestedOn(Skill skill){
        return dpInvestedOnSkills.get(skill);
    }
    public int getBaseValueOf(Skill skill){ return getDPInvestedOn(skill)/getCostOf(skill);}

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
    public abstract int getHpCost();
    public abstract int getHpByLevel();
    public abstract int getInnitiativeByLevel();
    public abstract int getCVByLevel();
    public abstract int getCategoryBonusOf(Skill skill);

    public abstract int getPercentageOnCombat();
    public abstract int getPercentageOnMystic();
    public abstract int getPercentageOnPsychic();

    public abstract int getCostOf(Skill skill);
    public int getCostOf(Table table){
        return table.getDpCost();
    }


}
