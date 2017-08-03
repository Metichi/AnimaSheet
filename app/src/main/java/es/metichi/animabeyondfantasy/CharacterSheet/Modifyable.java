package es.metichi.animabeyondfantasy.CharacterSheet;

import java.util.ArrayList;

/**
 * Created by Metichi on 02/08/2017.
 */

public interface Modifyable {
    ArrayList<Modifier> getModifiers();
    void add(Modifier modifier);
    void remove(Modifier modifier);
}
