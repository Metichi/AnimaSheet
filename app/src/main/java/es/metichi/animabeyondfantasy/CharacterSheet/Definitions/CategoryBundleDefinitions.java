package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import java.util.ArrayList;
import java.util.HashMap;

import es.metichi.animabeyondfantasy.CharacterSheet.Category;
import es.metichi.animabeyondfantasy.CharacterSheet.Skill;

/**
 * Definition of category bundles
 *
 * This class provides static methods to generate the categories described in the core book.
 * Created by Metichi on 04/08/2017.
 */

public class CategoryBundleDefinitions {
    private CategoryBundleDefinitions(){}

    public static Category.CategoryBundle getWarriorCategoryBundle(){
        String name = "Warrior";
        int percentageOnCombat = 60;
        int percentageOnMystic = 50;
        int percentageOnPsychic = 50;
        int costOfKiAcumulation = 20;
        int costOfKiPoints = 2;
        int hpCost = 15;
        int intervalOfLevelBetweenCV = 3;

        HashMap<String,Integer> costOfCombatSkills = new HashMap<>(5);
        costOfCombatSkills.put("Attack",2);
        costOfCombatSkills.put("Defense",2);
        costOfCombatSkills.put("Dodge",2);
        costOfCombatSkills.put("WearArmor",2);
        costOfCombatSkills.put("CM",5);

        HashMap<String,Integer> costOfMysticSkills = new HashMap<>(9);
        costOfMysticSkills.put("Zeon",3);
        costOfMysticSkills.put("ACT",70);
        costOfMysticSkills.put("ZeonRegen",25);
        costOfMysticSkills.put("MagicProjection",3);
        costOfMysticSkills.put("MagicLevel",5);
        costOfMysticSkills.put("Summon",3);
        costOfMysticSkills.put("Control",3);
        costOfMysticSkills.put("Bind",3);
        costOfMysticSkills.put("Unsummon",3);

        HashMap<String,Integer> costOfPsychicSkills = new HashMap<>(2);
        costOfPsychicSkills.put("CV",20);
        costOfPsychicSkills.put("PsychicProjection",3);

        HashMap<String,Integer> costOfSecondarySkill = new HashMap<>();
        costOfSecondarySkill.put("FeatOfStrength",1);

        HashMap<Skill.SecondarySkill.SecondarySkillType,Integer> costOfSecondaryType = new HashMap<>(7);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.ATHLETIC,2);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.SOCIAL,2);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.PERCEPTIVE,2);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.INTELLECTUAL,3);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.VIGOR,2);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.SUBTERFUGE,2);
        costOfSecondaryType.put(Skill.SecondarySkill.SecondarySkillType.CREATIVE,2);

        ArrayList<Category.Archetype> archetypes = new ArrayList<>(1);
        archetypes.add(Category.Archetype.FIGHTER);

        HashMap<String,Integer> skillGainByLevel = new HashMap<>();
        skillGainByLevel.put("Innitiative",5);
        skillGainByLevel.put("HP",15);
        skillGainByLevel.put("CM",25);
        skillGainByLevel.put("Attack",5);
        skillGainByLevel.put("Defense",5);
        skillGainByLevel.put("WearArmor",5);
        skillGainByLevel.put("FeatOfStrength",5);

        return new Category.CategoryBundle(name,percentageOnCombat,percentageOnMystic,percentageOnPsychic,
                costOfKiAcumulation,costOfKiPoints,hpCost,intervalOfLevelBetweenCV,costOfCombatSkills,
                costOfMysticSkills,costOfPsychicSkills,costOfSecondarySkill,skillGainByLevel,costOfSecondaryType,
                archetypes);
    }
}