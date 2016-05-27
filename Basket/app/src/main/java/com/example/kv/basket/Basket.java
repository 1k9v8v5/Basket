package com.example.kv.basket;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Basket extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        //MenuItem menuItem = menu.findItem(R.id.action_list);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_list:
                Intent plist = new Intent(Basket.this,Plist.class);
                startActivity(plist);
                return true;
            case R.id.action_list:
                Intent listall = new Intent(Basket.this,ListAll.class);
                startActivity(listall);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
