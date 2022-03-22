package com.cst2335.androidproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar appToolBar = findViewById(R.id.mainToolBar);
        setSupportActionBar(appToolBar);
        appToolBar.showOverflowMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tool_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String toastText = "";

        switch (item.getItemId()) {
            case R.id.menu_search:
                toastText = "You clicked on search";
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

}