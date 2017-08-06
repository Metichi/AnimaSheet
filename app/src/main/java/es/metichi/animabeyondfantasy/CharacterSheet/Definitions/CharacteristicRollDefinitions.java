package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import es.metichi.animabeyondfantasy.CharacterSheet.CharacteristicRoll;

/**
 * Created by Metichi on 05/08/2017.
 */

public class CharacteristicRollDefinitions {
    private CharacteristicRollDefinitions(){};
    public static class CustomRoll extends CharacteristicRoll{
        public CustomRoll(){super();}
        public CustomRoll(int strength,int dexterity,int agility,int constitution,
                          int intelligence, int power, int will, int perception,
                          int socialClass, int appearance){
            createCharacteristics();
            getStrength().setBase(strength);
            getDexterity().setBase(dexterity);
            getAgility().setBase(agility);
            getConstitution().setBase(constitution);
            getIntelligence().setBase(intelligence);
            getPower().setBase(power);
            getWill().setBase(will);
            getPerception().setBase(perception);
            getAppearance().setBase(appearance);
            this.socialClass = socialClass;
        }
        @Override
        public void roll() {}

        @Override
        public String getName() {
            return "Custom Roll";
        }
    }
}
