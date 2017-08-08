package es.metichi.animabeyondfantasy;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by Metichi on 06/08/2017.
 */

public class CharacterDisplayBundle {
    private String title;
    private View layout;
    private FloatingActionButton.OnClickListener onClickListener;
    private boolean hideButton;

    public CharacterDisplayBundle(String title, View layout, FloatingActionButton.OnClickListener onClickListener){
        this.layout = layout;
        this.title = title;
        this.onClickListener = onClickListener;
        this.hideButton = false;
    }
    public CharacterDisplayBundle(String title, View layout, FloatingActionButton.OnClickListener onClickListener, boolean hideButton){
        this(title,layout,onClickListener);
        this.hideButton = hideButton;
    }

    public View getLayout() {
        return layout;
    }

    public FloatingActionButton.OnClickListener getOnClickListener() {
        return onClickListener;
    }

    public String getTitle() {
        return title;
    }

    public boolean getHideButton(){
        return hideButton;
    }
}
