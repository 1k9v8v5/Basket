package com.example.kv.basket;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.database.Cursor;
import android.util.Log;
public class Plist extends Activity {
	private dbmanag dbcreate;
	private EditText edit;
	private String id_list="";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plist);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		dbcreate = new dbmanag(this);
		edit = (EditText) findViewById(R.id.listedit);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.plist_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_listprod:
				dbcreate.open();
				dbcreate.insertList(edit.getText().toString());
				Cursor cursorlistid = dbcreate.getAllDataListID();
				if (cursorlistid.moveToFirst()) {
					id_list = cursorlistid.getString(0);
					Log.d("LOG_TAG",id_list);
				}
				else{
					cursorlistid.close();
				}
				dbcreate.close();
				Intent addproduct = new Intent(Plist.this,AddProduct.class);
				addproduct.putExtra("text",edit.getText().toString());
				addproduct.putExtra("id",id_list);
				startActivity(addproduct);
                return true;
            case R.id.action_settings:
			
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
