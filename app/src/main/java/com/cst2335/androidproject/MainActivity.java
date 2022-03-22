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

//        switch (item.getItemId()) {
//            case R.id.item_1:
//                toastText = "You clicked on Item 1";
//                break;
//            case R.id.item_2:
//                toastText = "You clicked on Item 2";
//                break;
//            case R.id.item_3:
//                toastText = "You clicked on Item 3";
//                break;
//            case R.id.overflowItem_1:
//                toastText = "You clicked on the overflow menu";
//                break;
//        }
        Toast toast = Toast.makeText(getApplicationContext(),
                toastText,
                Toast.LENGTH_LONG);
        toast.show();

        return true;
    }

}