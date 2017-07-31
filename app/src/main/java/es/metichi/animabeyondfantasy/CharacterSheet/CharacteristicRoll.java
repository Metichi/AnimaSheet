package es.metichi.animabeyondfantasy.CharacterSheet;

/**
 * Created by Metichi on 31/07/2017.
 */

public interface CharacteristicRoll {
    Characteristic.PhysicalCharacteristic getStrength();
    Characteristic.PhysicalCharacteristic getDexterity();
    Characteristic.PhysicalCharacteristic getConstitution();
    Characteristic.PhysicalCharacteristic getAgility();
    Characteristic.IntellectualCharacteristic getIntelligence();
    Characteristic.IntellectualCharacteristic getPower();
    Characteristic.IntellectualCharacteristic getWill();
    Characteristic.IntellectualCharacteristic getPerception();
    int getSocialClass();
    int getAppearance();
}
