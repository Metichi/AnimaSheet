package es.metichi.animabeyondfantasy;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.support.annotation.StringRes;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import es.metichi.animabeyondfantasy.CharacterSheet.Character;

public class Activity_Sheet extends AppCompatActivity implements Fragment_General.CharacterEditor {
    private String[] mSheetTabs;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ConstraintLayout mHeader;
    private Character character;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheet);
        mSheetTabs = getResources().getStringArray(R.array.sheetTabs);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mHeader = (ConstraintLayout) getLayoutInflater().inflate(R.layout.menu_header,null);
        mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.drawer_open,R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.menu_items,mSheetTabs));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerList.addHeaderView(mHeader);
        mToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mToolbar);

        character = (Character) getIntent().getSerializableExtra("CHARACTER");
        updateHeader();

        Fragment generalFragment = new Fragment_General();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.content_frame, generalFragment)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            selectItem(i);
        }
    }
    private void selectItem(int position){
    }

    public void updateHeader(){
        TextView characterName = (TextView) mHeader.findViewById(R.id.characterName);
        TextView characterLevel = (TextView) mHeader.findViewById(R.id.characterLevel);
        TextView characterCategory = (TextView) mHeader.findViewById(R.id.characterCategory);

        characterName.setText(character.getName());
        characterLevel.setText(String.valueOf(character.getTotalLevel()));
        characterCategory.setText(character.getCurrentCategory() == null ? getResources().getText(R.string.noCategory):character.getCurrentCategory().getName());
    }

    @Override
    public Character getCharacter() {
        return character;
    }

    @Override
    public void setTitle(@StringRes int res) {
        getSupportActionBar().setTitle(res);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){return true;}
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)){
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            TextView exitMessage = new TextView(this);
            exitMessage.setText(R.string.exitSheetWarning);
            new AlertDialog.Builder(this)
                    .setTitle(R.string.exitSheetWarningTitle)
                    .setView(exitMessage)
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    })
                    .create()
                    .show();
        }
    }
}
