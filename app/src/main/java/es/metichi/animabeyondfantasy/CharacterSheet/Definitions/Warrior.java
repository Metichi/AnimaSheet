package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import android.graphics.SweepGradient;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Category;
import es.metichi.animabeyondfantasy.CharacterSheet.Skill;

/**
 * Created by Metichi on 03/08/2017.
 */

public class Warrior extends Category {
    public Warrior(int level, @Nullable Category previousCategory){
        super(level,previousCategory);
    }

    @Override
    public int getPercentageOnPsychic() {
        return 50;
    }

    @Override
    public int getPercentageOnCombat() {
        return 60;
    }

    @Override
    public int getPercentageOnMystic() {
        return 50;
    }

    @Override
    public int getCategoryCostOf(Skill skill) {
        if (skill instanceof Skill.CombatSkill){
            if(skill instanceof Skill.CombatSkill.KiPoint){
                return 2;
            } else if (skill instanceof Skill.CombatSkill.KiAcumulation){
                return 20;
            } else {
                switch (skill.getName()){
                    case "Attack": return 2;
                    case "Defense": return 2;
                    case "Dodge": return 2;
                    case "WearArmor": return 2;
                    case "CM": return 5;
                    default: return 99;
                }
            }
        } else if (skill instanceof Skill.MysticSkill){
            switch (skill.getName()){
                case "Zeon": return 3;
                case "ACT": return 70;
                case "ZeonRegen": return 25;
                case "MagicProjection": return 3;
                case "MagicLevel": return 5;
                case "Summon": return 3;
                case "Control": return 3;
                case "Bind": return 3;
                case "Unsummon": return 3;
                default: return 99;
            }
        } else if (skill instanceof Skill.PsychicSkill){
            switch (skill.getName()){
                case "CV": return 20;
                case "PsychicProjection": return 3;
                default: return 99;
            }
        } else if (skill instanceof Skill.SecondarySkill){
            int value = 99;
            HashMap<Skill.SecondarySkill.SecondarySkillType,Integer> typeMap = new HashMap<>(7);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.ATHLETIC,2);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.SOCIAL,2);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.PERCEPTIVE,2);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL,3);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.VIGOR,2);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE,2);
            typeMap.put(Skill.SecondarySkill.SecondarySkillType.CREATIVE,2);
            value = typeMap.get(((Skill.SecondarySkill) skill).getType());

            switch (skill.getName()){
                case "FeatOfStrength": return 1;
                default: return value;
            }
        } else {
            switch (skill.getName()){
                case "HP": return 15;
                default: return 99;
            }
        }
    }

    @Override
    public CategoryModifier getCategoryModifierOf(Skill skill) {
        CategoryModifier innitiativeModifier = new CategoryModifier("Guerrero","Ganancia de turno por nivel",new String[]{"Innitiative"}) {
            @Override
            public int getValue() {
                return 5*getLevel();
            }
        };
        CategoryModifier hpModifier = new CategoryModifier("Guerrero","Ganancia de HP por nivel", new String[]{"HP"}) {
            @Override
            public int getValue() {
                return 15*getLevel();
            }
        };
        CategoryModifier cmModifier = new CategoryModifier("Guerrero","Ganancia de CM por nivel", new String[]{"CM"}) {
            @Override
            public int getValue() {
                return 25*getLevel();
            }
        };
        CategoryModifier cvModifier = new CategoryModifier("Guerrero", "Ganancia de CV por nivel", new String[]{"CV"}) {
            @Override
            public int getValue() {
                return 1+ (getLevel()-1)/3;
            }
        };
        CategoryModifier attackModifier = new CategoryModifier("Guerrero", "Ganancia de Ataque por nivel", new String[]{"Attack"}) {
            @Override
            public int getValue() {
                return 5*getLevel();
            }
        };
        CategoryModifier defenseModifier = new CategoryModifier("Guerrero", "Ganancia de Defensa por nivel", new String[]{"Defense"}) {
            @Override
            public int getValue() {
                return 5*getLevel();
            }
        };
        CategoryModifier wearArmorModifier = new CategoryModifier("Guerrero", "Ganancia de Llevar Armadura por nivel", new String[]{"WearArmor"}) {
            @Override
            public int getValue() {
                return 5*getLevel();
            }
        };
        CategoryModifier featOfStrengthModifier = new CategoryModifier("Guerrero", "Ganancia a Proezas de Fuerza por nivel", new String[]{"FeatOfStrength"}) {
            @Override
            public int getValue() {
                return 5*getLevel();
            }
        };

        HashMap<String, CategoryModifier> categoryModifiers = new HashMap<>(8);
        categoryModifiers.put("Innitiative",innitiativeModifier);
        categoryModifiers.put("HP",hpModifier);
        categoryModifiers.put("CM",cmModifier);
        categoryModifiers.put("CV",cvModifier);
        categoryModifiers.put("Attack",attackModifier);
        categoryModifiers.put("Defense",defenseModifier);
        categoryModifiers.put("WearArmor",wearArmorModifier);
        categoryModifiers.put("FeatOfStrength",featOfStrengthModifier);

        if (categoryModifiers.containsKey(skill.getName())){
            return categoryModifiers.get(skill.getName());
        } else {
            return null;
        }
    }

    @Override
    public ArrayList<Archetype> getArchetype() {
        ArrayList<Archetype> archetypes = new ArrayList<>(1);
        archetypes.add(Archetype.FIGHTER);
        return archetypes;
    }
}
