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
    public static HashMap<String,Skill> generatePsychicSkillsFor(Character character){
        Skill.PsychicSkill cv = new Skill.PsychicSkill("CV", null,character.getCategories());
        Skill.PsychicSkill psychicProjection = new Skill.PsychicSkill("PsychicProjection",character.getDexterity(),character.getCategories());

        HashMap<String,Skill> psychicSkills = new HashMap<>(2);
        psychicSkills.put(cv.getName(),cv);
        psychicSkills.put(psychicProjection.getName(),psychicProjection);

        return psychicSkills;
    }
    public static HashMap<String,Skill> generateSecondarySkillsFor(Character character){
        Skill.SecondarySkill acrobatics = new Skill.SecondarySkill("Acrobatics",character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.ATHLETIC);
        Skill.SecondarySkill athletism = new Skill.SecondarySkill("Athletism", character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.ATHLETIC);
        Skill.SecondarySkill ride = new Skill.SecondarySkill("Ride", character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.ATHLETIC);
        Skill.SecondarySkill swim = new Skill.SecondarySkill("Swim", character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.ATHLETIC);
        Skill.SecondarySkill climb = new Skill.SecondarySkill("Climb",character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.ATHLETIC);
        Skill.SecondarySkill jump = new Skill.SecondarySkill("Jump",character.getStrength(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.ATHLETIC);

        Skill.SecondarySkill composture = new Skill.SecondarySkill("Composture",character.getWill(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.VIGOR);
        Skill.SecondarySkill featOfStrength = new Skill.SecondarySkill("FeatOfStrength",character.getStrength(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.VIGOR);
        Skill.SecondarySkill painEndurance = new Skill.SecondarySkill("PainEndurance",character.getWill(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.VIGOR);

        Skill.SecondarySkill notice = new Skill.SecondarySkill("Notice",character.getPerception(),character.getCategories(),Skill.SecondarySkill.SecondarySkillType.PERCEPTIVE);
        Skill.SecondarySkill search = new Skill.SecondarySkill("Search",character.getPerception(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.PERCEPTIVE);
        Skill.SecondarySkill track = new Skill.SecondarySkill("Track",character.getPerception(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.PERCEPTIVE);

        Skill.SecondarySkill animals = new Skill.SecondarySkill("Animals",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill science = new Skill.SecondarySkill("Science",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill herbalism = new Skill.SecondarySkill("Herbalism",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill history = new Skill.SecondarySkill("History",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill medicine = new Skill.SecondarySkill("Medicine",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill memorice = new Skill.SecondarySkill("Memorice",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill navigation = new Skill.SecondarySkill("Navigation",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill occult = new Skill.SecondarySkill("Occult",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill appraisal = new Skill.SecondarySkill("Appraisal",character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);
        Skill.SecondarySkill magicPerception = new Skill.SecondarySkill("MagicPerception",character.getPower(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL);

        Skill.SecondarySkill style = new Skill.SecondarySkill("Style", character.getPower(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SOCIAL);
        Skill.SecondarySkill intimidation = new Skill.SecondarySkill("Intimidation", character.getWill(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SOCIAL);
        Skill.SecondarySkill leadership = new Skill.SecondarySkill("Leadership", character.getPower(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SOCIAL);
        Skill.SecondarySkill persuasion = new Skill.SecondarySkill("Persuasion", character.getIntelligence(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SOCIAL);

        Skill.SecondarySkill lockpicking = new Skill.SecondarySkill("Lockpicking", character.getDexterity(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);
        Skill.SecondarySkill disguise = new Skill.SecondarySkill("Disguise", character.getDexterity(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);
        Skill.SecondarySkill hiding = new Skill.SecondarySkill("Hiding", character.getPerception(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);
        Skill.SecondarySkill stealing = new Skill.SecondarySkill("Stealing",character.getDexterity(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);
        Skill.SecondarySkill stealth = new Skill.SecondarySkill("Stealth", character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);
        Skill.SecondarySkill traps = new Skill.SecondarySkill("Traps",character.getPerception(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);
        Skill.SecondarySkill poison = new Skill.SecondarySkill("Poison", character.getPerception(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE);

        Skill.SecondarySkill art = new Skill.SecondarySkill("Art",character.getPower(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.CREATIVE);
        Skill.SecondarySkill dance = new Skill.SecondarySkill("Dance", character.getAgility(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.CREATIVE);
        Skill.SecondarySkill forge = new Skill.SecondarySkill("Forge", character.getDexterity(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.CREATIVE);
        Skill.SecondarySkill music = new Skill.SecondarySkill("Music", character.getPower(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.CREATIVE);
        Skill.SecondarySkill sleightOfHand = new Skill.SecondarySkill("SleightOfHand",character.getDexterity(),character.getCategories(), Skill.SecondarySkill.SecondarySkillType.CREATIVE);

        HashMap<String,Skill> secondarySkills = new HashMap<>(38);
        placeAtMap(secondarySkills,acrobatics);
        placeAtMap(secondarySkills,athletism);
        placeAtMap(secondarySkills,swim);
        placeAtMap(secondarySkills,ride);
        placeAtMap(secondarySkills,climb);
        placeAtMap(secondarySkills,jump);

        placeAtMap(secondarySkills,style);
        placeAtMap(secondarySkills,intimidation);
        placeAtMap(secondarySkills,leadership);
        placeAtMap(secondarySkills,persuasion);

        placeAtMap(secondarySkills,notice);
        placeAtMap(secondarySkills,search);
        placeAtMap(secondarySkills,track);

        placeAtMap(secondarySkills,animals);
        placeAtMap(secondarySkills,science);
        placeAtMap(secondarySkills,herbalism);
        placeAtMap(secondarySkills,history);
        placeAtMap(secondarySkills,medicine);
        placeAtMap(secondarySkills,memorice);
        placeAtMap(secondarySkills,navigation);
        placeAtMap(secondarySkills,occult);
        placeAtMap(secondarySkills,appraisal);
        placeAtMap(secondarySkills,magicPerception);

        placeAtMap(secondarySkills,composture);
        placeAtMap(secondarySkills,featOfStrength);
        placeAtMap(secondarySkills,painEndurance);

        placeAtMap(secondarySkills,lockpicking);
        placeAtMap(secondarySkills,disguise);
        placeAtMap(secondarySkills,hiding);
        placeAtMap(secondarySkills,stealing);
        placeAtMap(secondarySkills,stealth);
        placeAtMap(secondarySkills,traps);
        placeAtMap(secondarySkills,poison);

        placeAtMap(secondarySkills,art);
        placeAtMap(secondarySkills,dance);
        placeAtMap(secondarySkills,forge);
        placeAtMap(secondarySkills,music);
        placeAtMap(secondarySkills,sleightOfHand);

        return secondarySkills;
    }
    public static Skill generateHealthFor(Character character){
        Skill health = new Skill("HP",character.getConstitution(),character.getCategories()){
            @Override
            public int getBaseValue(){
                return super.getBaseValue()*characteristic.getFinalValue()+20;
            }
        };
        return health;
    }
    private static void placeAtMap(HashMap<String,Skill> map, Skill skill){
        map.put(skill.getName(),skill);
    }
}
