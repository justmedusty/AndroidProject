package com.cst2335.utilities;
/*
File: BaseNavActivity.java
Author: Chad Rocheleau
Lab Section: 012
Date: March 24 2022

 */
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.cst2335.androidproject.FavouritesActivity;
import com.cst2335.androidproject.MainActivity;
import com.cst2335.androidproject.PopularActivity;
import com.cst2335.androidproject.R;
import com.cst2335.androidproject.SearchActivity;
import com.google.android.material.navigation.NavigationView;

/**
 * <p>This class is responsible for constructing the toolbar and navigation drawer that will be
 * used across the application. All activities that need toolbar and nav drawer must extend this</p>
 *
 * <p>In classes that extend this BaseNavActivity a base template layout must be used. This
 * base layout is activity_base_nav.xml. To load activity specific layouts into the layout that
 * contains all toolbar and nav code a StubView has been supplied. Finally it is important
 * that classes extending this make a call to setupNavigation() </p>
 *
 * <p>To load an activity specific layout into the activity_base_nav the following is necessary
 *         ViewStub stub = (ViewStub) findViewById(R.id.layout_stub);
 *         stub.setLayoutResource(R.layout.nameOfActivitySpecificLayout);
 *         stub.inflate();</p>
 *
 *  <p>Should any activity specific behavior be needed any of the methods here that control menu
 *  item functionality may be overridden in sub classes.</p>
 */
public class BaseNavActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    /**
     * Necessary override of onCreate. This class only exists to provide subclasses with
     * toolbar and nav drawer functionality. and so nothing happens in the onCreate method.
     * @param savedInstanceState Bundle passed to this activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initializes the toolbar and set the navigation drawer
     */
    public void setupNavigation(String author, String activityTitle, String version) {
        //*************** TOOLBAR AND NAV DRAWER *******
        Toolbar testToolBar = findViewById(R.id.testToolBar);
        setSupportActionBar(testToolBar);
        testToolBar.showOverflowMenu();

        // if there is action bar remove the title
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        // *********  Navigation drawer setup
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, testToolBar,
                R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ((TextView)navView.getHeaderView(0).findViewById(R.id.activity_title)).setText(activityTitle);
        ((TextView)navView.getHeaderView(0).findViewById(R.id.author)).setText(author);
        ((TextView)navView.getHeaderView(0).findViewById(R.id.version_number)).setText(version);

        navView.setNavigationItemSelectedListener(this);
        //********* END navigation drawer SETUP.
        //********** END TOOL BAR SETUP **************************************

    }
    /**
     * Inflates the layout for the tool bar
     * @param menu the menu object to inflate
     * @return true to inflate the options menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * tool bar options selected operations.
     * @param item the item selected in the toolbar
     * @return true to execute the item selected operation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String toastText = "";

        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent goToHome= new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goToHome);
                break;
            case R.id.menu_info:
                toastText = "You clicked on info";
                Toast toast = Toast.makeText(getApplicationContext(),
                        toastText,
                        Toast.LENGTH_LONG);
                toast.show();
                break;

        }

        return true;
    }

    /**
     * Nav Drawer Item selected functionality.
     * @param item the item selected in the nav drawer
     * @return true to execute the item selected instructions.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {


        int id = item.getItemId();
        //boolean isPhone = findViewById(R.id.recipe_details_fragment) == null;
        if (id == R.id.nav_drawer_search) {
           Intent goToSearch = new Intent(getApplicationContext(), SearchActivity.class );
           //goToSearch.putExtra("isPhone", isPhone);
           startActivity(goToSearch);
        } else if ( id == R.id.nav_drawer_popular) {
            Intent goToPopular = new Intent(getApplicationContext(), PopularActivity.class );
            //goToPopular.putExtra("isPhone", isPhone);
            startActivity(goToPopular);
        } else if ( id == R.id.nav_drawer_favourites) {
            Intent goToFavs = new Intent(getApplicationContext(), FavouritesActivity.class );
            //goToFavs.putExtra("isPhone", isPhone);
            startActivity(goToFavs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}