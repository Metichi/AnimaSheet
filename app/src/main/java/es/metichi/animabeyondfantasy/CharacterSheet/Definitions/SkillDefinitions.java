package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Character;
import es.metichi.animabeyondfantasy.CharacterSheet.Skill;

/**
 * Created by Metichi on 03/08/2017.
 */

public class SkillDefinitions {
    public static HashMap<String,Skill> generateCombatSkillsFor(Character character){
        Skill.CombatSkill.AttackDefense attack = new Skill.CombatSkill.AttackDefense("Attack",character.getDexterity(),character.getCategories());
        Skill.CombatSkill.AttackDefense defense = new Skill.CombatSkill.AttackDefense("Defense",character.getDexterity(),character.getCategories());
        Skill.CombatSkill.AttackDefense dodge = new Skill.CombatSkill.AttackDefense("Dodge",character.getAgility(),character.getCategories());
        Skill.CombatSkill wearArmor = new Skill.CombatSkill("WearArmor",character.getStrength(),character.getCategories());

        Skill.CombatSkill.KiPoint agilityKi = new Skill.CombatSkill.KiPoint(character.getAgility(),character.getCategories());
        Skill.CombatSkill.KiPoint constitutionKi = new Skill.CombatSkill.KiPoint(character.getConstitution(),character.getCategories());
        Skill.CombatSkill.KiPoint dexterityKi = new Skill.CombatSkill.KiPoint(character.getDexterity(),character.getCategories());
        Skill.CombatSkill.KiPoint strengthKi = new Skill.CombatSkill.KiPoint(character.getStrength(),character.getCategories());
        Skill.CombatSkill.KiPoint powerKi = new Skill.CombatSkill.KiPoint(character.getPower(),character.getCategories());
        Skill.CombatSkill.KiPoint willKi = new Skill.CombatSkill.KiPoint(character.getWill(),character.getCategories());

        Skill.CombatSkill.KiAcumulation agilityAcu = new Skill.CombatSkill.KiAcumulation(character.getAgility(),character.getCategories());
        Skill.CombatSkill.KiAcumulation constitutionAcu = new Skill.CombatSkill.KiAcumulation(character.getConstitution(),character.getCategories());
        Skill.CombatSkill.KiAcumulation dexterityAcu = new Skill.CombatSkill.KiAcumulation(character.getDexterity(),character.getCategories());
        Skill.CombatSkill.KiAcumulation strengthAcu = new Skill.CombatSkill.KiAcumulation(character.getStrength(),character.getCategories());
        Skill.CombatSkill.KiAcumulation powerAcu = new Skill.CombatSkill.KiAcumulation(character.getPower(),character.getCategories());
        Skill.CombatSkill.KiAcumulation willAcu = new Skill.CombatSkill.KiAcumulation(character.getWill(),character.getCategories());

        Skill.CombatSkill cm = new Skill.CombatSkill("CM", null,character.getCategories()){
            @Override
            public int getSkillModifier(){
                return 0;
            }
            @Override
            public int getBaseValue(){
                return super.getBaseValue()*5;
            }
        };

        HashMap<String,Skill> combatSkills = new HashMap<>(28);
        combatSkills.put(attack.getName(),attack);
        combatSkills.put(defense.getName(),defense);
        combatSkills.put(dodge.getName(),dodge);
        combatSkills.put(wearArmor.getName(),wearArmor);

        combatSkills.put(agilityKi.getName(),agilityKi);
        combatSkills.put(constitutionKi.getName(),constitutionKi);
        combatSkills.put(dexterityKi.getName(),dexterityKi);
        combatSkills.put(strengthKi.getName(),strengthKi);
        combatSkills.put(powerKi.getName(),powerKi);
        combatSkills.put(willKi.getName(),willKi);

        combatSkills.put(agilityAcu.getName(),agilityAcu);
        combatSkills.put(constitutionAcu.getName(),constitutionAcu);
        combatSkills.put(dexterityAcu.getName(),dexterityAcu);
        combatSkills.put(strengthAcu.getName(),strengthAcu);
        combatSkills.put(powerAcu.getName(),powerAcu);
        combatSkills.put(willAcu.getName(),willAcu);

        combatSkills.put(cm.getName(),cm);

        return combatSkills;
    }
    public static HashMap<String,Skill> generateMysticSkillsFor(Character character){
        Skill.MysticSkill zeon = new Skill.MysticSkill("Zeon", character.getPower(),character.getCategories()){
            @Override
            public int getSkillModifier(){
                switch (getCharacteristic().getFinalValue()){
                    case 1: return 5;
                    case 2: return 20;
                    case 3: return 40;
                    case 4: return 55;
                    case 5: return 70;
                    case 6: return 85;
                    case 7: return 95;
                    case 8 : return 110;
                    case 9: return 120;
                    case 10: return 135;
                    case 11: return 150;
                    case 12: return 160;
                    case 13: return 175;
                    case 14: return 185;
                    case 15: return 200;
                    case 16: return 215;
                    case 17: return 225;
                    case 18: return 240;
                    case 19: return 250;
                    default: return 265;
                }
            }

            @Override
            public int getBaseValue(){
                return super.getBaseValue()*5;
            }
        };
        final Skill.MysticSkill act = new Skill.MysticSkill("ACT",character.getPower(),character.getCategories()){
            @Override
            public int getSkillModifier(){
                int value = getCharacteristic().getFinalValue();
                if (value <= 4){
                    return 0;
                } else if (value <= 7){
                    return 5;
                } else if (value <= 11){
                    return 10;
                } else if (value <= 14){
                    return 15;
                } else if (value <= 15){
                    return 20;
                } else if (value <= 17){
                    return 25;
                } else if (value <= 19){
                    return 30;
                } else {
                    return 35;
                }
            }

            @Override
            public int getBaseValue(){
                return super.getBaseValue()*getSkillModifier();
            }

        };
        Skill.MysticSkill zeonRegen = new Skill.MysticSkill("ZeonRegen",null,character.getCategories()){
            @Override
            public int getSkillModifier(){
                return act.getFinalValue();
            }
            @Override
            public int getBaseValue(){
                return super.getBaseValue()*getSkillModifier();
            }
        };
        Skill.MysticSkill magicProjection = new Skill.MysticSkill("MagicProjection",character.getDexterity(),character.getCategories());
        Skill.MysticSkill magicLevel = new Skill.MysticSkill("MagicLevel", character.getIntelligence(),character.getCategories()){
            @Override
            public int getSkillModifier(){
                int value = getCharacteristic().getFinalValue();
                if (value <= 5) {return 0;}
                else if(value <= 10) {return (value-5)*10;}
                else if(value == 11) {return 75;}
                else if(value == 12) {return 100;}
                else if(value == 13) {return 150;}
                else {return (value - 12)*100;}
            }
            @Override
            public int getBaseValue(){
                return super.getBaseValue()*5;
            }
        };

        Skill.MysticSkill summon = new Skill.MysticSkill("Summon", character.getPower(),character.getCategories());
        Skill.MysticSkill control = new Skill.MysticSkill("Control", character.getWill(),character.getCategories());
        Skill.MysticSkill bind = new Skill.MysticSkill("Bind", character.getPower(),character.getCategories());
        Skill.MysticSkill unsummon = new Skill.MysticSkill("Unsummon", character.getPower(), character.getCategories());

        HashMap<String,Skill> mysticSkills = new HashMap<>(9);
        mysticSkills.put(zeon.getName(),zeon);
        mysticSkills.put(act.getName(),act);
        mysticSkills.put(zeonRegen.getName(),zeonRegen);
        mysticSkills.put(magicLevel.getName(),magicLevel);
        mysticSkills.put(magicProjection.getName(),magicProjection);

        mysticSkills.put(summon.getName(),summon);
        mysticSkills.put(control.getName(),control);
        mysticSkills.put(bind.getName(),bind);
        mysticSkills.put(unsummon.getName(),unsummon);

        return mysticSkills;
    }
}
