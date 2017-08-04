package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import android.graphics.SweepGradient;

import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Category;
import es.metichi.animabeyondfantasy.CharacterSheet.Skill;

/**
 * Created by Metichi on 03/08/2017.
 */

public class Warrior extends Category {
    @Override
    public CategoryModifier getHpByLevel() {
        CategoryModifier hpModifier = new CategoryModifier("Clase Guerrero","Guerreros ganan 15HP por nivel", new String[]{"HP"}) {
            @Override
            public int getValue() {
                return 15*getLevel();
            }
        };
        return hpModifier;
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
    public CategoryModifier getInnitiativeByLevel() {
        return null;
    }
}
