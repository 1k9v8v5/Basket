package com.example.kv.basket;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class AddProduct extends Activity {
    private Date date;
    private TextView textlist;
    private TextView tdate;
    private EditText ename;
    private EditText ecount;
    private EditText eprice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        textlist = (TextView) findViewById(R.id.listNameExt);

        ename = (EditText) findViewById(R.id.nameListEditText);
        ecount = (EditText) findViewById(R.id.countListEditText);
        eprice = (EditText) findViewById(R.id.priceEditText);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.addprod_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_addprod:
                Intent productlist = new Intent(AddProduct.this,ProductList.class);
                startActivity(productlist);
                return true;
            case R.id.action_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
