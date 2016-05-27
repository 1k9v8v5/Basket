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
import java.text.SimpleDateFormat;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.Toast;
import android.database.Cursor;
import java.util.HashMap;
import java.util.ArrayList;
import android.view.View;
import android.widget.SimpleAdapter;
import android.view.View.OnClickListener;
import android.util.Log;
public class AddProduct extends Activity
{
	private dbmanag dbcreate;
	String LOG_TAG = "Log";

    private TextView textlist;

    private EditText ename;
    private EditText ecount;
    private EditText eprice;
	private String posunit="";
    private String id_list="";
	private String name_list="";

    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		dbcreate = new dbmanag(this);
		Intent intent = getIntent();	
		textlist = (TextView) findViewById(R.id.listNameExt);
		name_list = intent.getStringExtra("name");
		textlist.setText(name_list);
		id_list = intent.getStringExtra("id");

		ename = (EditText) findViewById(R.id.nameListEditText);
		ecount = (EditText) findViewById(R.id.countListEditText);
		eprice = (EditText) findViewById(R.id.priceEditText);

		ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map;
		dbcreate.open();
		Cursor cursorunit=dbcreate.getAllDataUnit();
		while (cursorunit.moveToNext())
		{
			int idColIndex = cursorunit.getColumnIndex("unitID");;
			int nameColIndex = cursorunit.getColumnIndex("name");
			map = new HashMap<String, String>();
			map.put("unitID", cursorunit.getString(idColIndex));
			map.put("name", cursorunit.getString(nameColIndex));
			myArrList.add(map);
		}
		cursorunit.close();
		dbcreate.close();
		// адаптер
		SimpleAdapter adapter = new SimpleAdapter(this, myArrList,  android.R.layout.simple_spinner_item, 
												  new String[] {"name"},
												  new int[] {android.R.id.text1});

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
		spinner.setSelection(0);
		// устанавливаем обработчик нажатия
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
										   int position, long id)
				{

					posunit = Integer.toString(position);
					HashMap<String, Object> map = (HashMap<String, Object>) parent.getItemAtPosition(position);
					Toast.makeText(AddProduct.this, map.get("unitID").toString(), Toast.LENGTH_SHORT).show();
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0)
				{
				}
			});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
	{
        getMenuInflater().inflate(R.menu.addprod_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
	{
        switch (item.getItemId())
		{
            case R.id.action_addprod:
				Log.d(LOG_TAG, ename.getText().toString() + " " + ecount.getText().toString() + " "
					  + posunit + " " + eprice.getText().toString());
				dbcreate.open();
				dbcreate.insertProd(posunit, ename.getText().toString(), ecount.getText().toString(), eprice.getText().toString(), id_list);
				dbcreate.close();
				Intent productlist = new Intent(AddProduct.this, ProductList.class);
				productlist.putExtra("id", id_list);
				startActivity(productlist);			
                return true;
			case android.R.id.home:
				Intent addprodlist1 = new Intent(AddProduct.this, ProductList.class);
				addprodlist1.putExtra("id", id_list);
				startActivity(addprodlist1);
				return true;
            case R.id.action_settings:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
