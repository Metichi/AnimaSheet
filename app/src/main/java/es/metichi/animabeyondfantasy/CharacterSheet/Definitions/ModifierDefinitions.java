package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import es.metichi.animabeyondfantasy.CharacterSheet.Category;
import es.metichi.animabeyondfantasy.CharacterSheet.Character;
import es.metichi.animabeyondfantasy.CharacterSheet.Characteristic;
import es.metichi.animabeyondfantasy.CharacterSheet.Modifier;

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



    public static class CostModifier extends Modifier{
        public CostModifier(int value, String source, String description){super (value, source, description);}

        @Override
        public void giveTo(Character character) {
            character.getModifiers().add(this);
            for (Category category : character.getCategories()){
                category.getCostModifiers().add(this);
            }

        }

        @Override
        public void removeFrom(Character character) {
            character.getModifiers().remove(this);
            for (Category category : character.getCategories()){
                category.getCostModifiers().remove(this);
            }
        }
    }
}
