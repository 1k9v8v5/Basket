package com.example.kv.basket;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

public class ListAll extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

}
