package es.metichi.animabeyondfantasy.CharacterSheet;

/**
 * Created by Metichi on 31/07/2017.
 */

public class ModifierDefinitions {
    private ModifierDefinitions(){};

    public static final Characteristic.CharacteristicModifier genderSizeModifier =
            new Characteristic.CharacteristicModifier(-1, "Genero Femenino", "Personajes femeninos tienen un -1 al tamaño") {
                @Override
                public void giveTo(Character character) {
                    character.getModifiers().add(this);
                    character.getSize().getModifiers().add(this);
                }

                @Override
                public void removeFrom(Character character) {
                    character.getModifiers().remove(this);
                    character.getSize().getModifiers().add(this);
                }
            };
}
