package com.cst2335.androidproject;

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
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

/**
 * <p>This class is responsible for constructing the toolbar and navigation drawer that will be
 * used across the application. All activities that need toolbar and nav drawer must extend this</p>
 *
 * <p>In classes that extend this BaseNavActivity a base template layout must be used. This
 * base layout is activity_base_nav.xml. To load activity specific layouts into the layout that
 * contains all toolbar and nav code a StubView has been supplied. </p>
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
     * Simply calls on the setupNavigation method belonging to this class
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Initializes the toolbar and set the navigation drawer
     */
    public void setupNavigation() {
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
            case R.id.menu_search:
                Intent goToSearch= new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(goToSearch);
                break;
            case R.id.menu_favourites:
                toastText = "You clicked on favourites";
                break;
            case R.id.menu_popular:
                toastText = "You clicked on popular";
                break;

        }
        Toast toast = Toast.makeText(getApplicationContext(),
                toastText,
                Toast.LENGTH_LONG);
        toast.show();
        return true;
    }

    /**
     * Nav Drawer Item selected functionality.
     * @param item the item selected in the nav drawer
     * @return true to execute the item selected instructions.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        //TODO For some reason going to search page works but the others don't???
        int id = item.getItemId();

        if (id == R.id.nav_drawer_search) {
           Intent goToSearch = new Intent(getApplicationContext(), SearchActivity.class );
           startActivity(goToSearch);
        } else if ( id == R.id.nav_drawer_popular) {
            Intent goToPopular = new Intent(getApplicationContext(), PopularActivity.class );
            startActivity(goToPopular);
        } else if ( id == R.id.nav_drawer_favourites) {
            Intent goToFavs = new Intent(getApplicationContext(), FavouritesActivity.class );
            startActivity(goToFavs);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.nav_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}