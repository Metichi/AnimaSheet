package es.metichi.animabeyondfantasy;

import android.support.annotation.StringRes;

import es.metichi.animabeyondfantasy.CharacterSheet.Character;

/**
 * Created by Metichi on 07/08/2017.
 */

public interface CharacterEditor {
    Character getCharacter();
    void setTitle(@StringRes int res);
}