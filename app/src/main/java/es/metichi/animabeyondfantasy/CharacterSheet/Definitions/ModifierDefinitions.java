package es.metichi.animabeyondfantasy.CharacterSheet.Definitions;

import es.metichi.animabeyondfantasy.CharacterSheet.Category;
import es.metichi.animabeyondfantasy.CharacterSheet.Character;
import es.metichi.animabeyondfantasy.CharacterSheet.Characteristic;
import es.metichi.animabeyondfantasy.CharacterSheet.Modifier;

/**
 * Created by Metichi on 31/07/2017.
 */

public class ModifierDefinitions {
    public static Characteristic.CharacteristicModifier genderSizeModifier = new Characteristic.CharacteristicModifier(
            -1,
            "Genero",
            "Personajes femeninos tienen un -1 al tamaño",
            new String[] {"Size"});
}
