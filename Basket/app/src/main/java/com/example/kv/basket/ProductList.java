package com.example.kv.basket;

import android.os.Bundle;
import android.app.*;
import android.view.*;
import android.content.Intent;
import android.widget.*;
import android.database.Cursor;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
//import android.widget.AdapterView.OnCreateContextMenuListener;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import java.util.concurrent.TimeUnit;
import android.content.Context;
import android.widget.AdapterView.*;
import 	android.graphics.Color;
public class ProductList extends Activity  implements LoaderCallbacks<Cursor>
{
    private dbmanag dbcreate;
    private ListView list;
    private SimpleCursorAdapter sca;
    private Cursor cursor;
    private TextView textlist;
    private static String id_list="";
    private String name_list="";
    private String _id = "";
	private long delpos;
	private String ename="";
    private String ecount="";
    private String eprice="";


    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
		ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


		Intent intent = getIntent();
		id_list = intent.getStringExtra("id");

		ename = intent.getStringExtra("ename");
		ecount = intent.getStringExtra("ecount");
		eprice = intent.getStringExtra("eprice");
		Log.d("LOG_TAG1", id_list);

        list = (ListView) findViewById(R.id.list_product);
		list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id)
				{
					delpos = id;
					for (int a = 0; a < parent.getChildCount(); a++)
					{
						parent.getChildAt(a).setBackgroundColor(Color.TRANSPARENT);
						//parent.getChildAt(a).setBackgroundColor(Color.parseColor("0x7f020020"));

					}
					view.setBackgroundColor(Color.parseColor("#E3EAEE"));
				}
			});
        registerForContextMenu(list);
        dbcreate = new dbmanag(this);
        dbcreate.open();
        Cursor cursorlistid = dbcreate.getDataListName(id_list);
        if (cursorlistid.moveToFirst())
        {
            name_list = cursorlistid.getString(2);
            //Log.d("LOG_TAG",name_list);
        }
        else
        {
            cursorlistid.close();
        }
        textlist = (TextView) findViewById(R.id.name_list_product);
        textlist.setText(name_list);
        String[] from = new String[] {dbmanag.COLUMN_NAME_PROD};
        int[] to = new int[] {R.id.textv_list_product};
        cursor = dbcreate.getDataProductList(id_list);
        sca = new SimpleCursorAdapter(this, R.layout.item_list_product, cursor, from, to, 0);

        list.setAdapter(sca);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.product_list_menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_add_product:
                Intent addprodoflist = new Intent(ProductList.this, AddProduct.class);
                addprodoflist.putExtra("id", id_list);
                addprodoflist.putExtra("name", name_list);
				startActivity(addprodoflist);			
				getLoaderManager().getLoader(0).forceLoad();
                return true;
            case R.id.action_del_product:	
				dbcreate.deleteProductItem(Long.toString(delpos));
                getLoaderManager().getLoader(0).forceLoad();
                return true;
			case android.R.id.home:
				Intent addprodlist12 = new Intent(ProductList.this, ListAll.class);
				startActivity(addprodlist12);
				return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_list_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId())
        {
            case R.id.action_edit_product:
                Intent editproduct = new Intent(ProductList.this, editProduct.class);
                _id = Long.toString(info.id);
                //Log.d("LOG_TAG",info.id+"");

                editproduct.putExtra("_id", _id);
                editproduct.putExtra("id", id_list);
                editproduct.putExtra("text", name_list);
                startActivity(editproduct);

                return true;
            case R.id.action_del_product:
                dbcreate.deleteProductItem(Long.toString(info.id));
                getLoaderManager().getLoader(0).forceLoad();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> p1, Cursor p2)
    {
// TODO: Implement this method
        sca.swapCursor(p2);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> p1)
    {
// TODO: Implement this method
    }

    @Override
    public Loader<Cursor> onCreateLoader(int p1, Bundle p2)
    {
// TODO: Implement this method
        return new MyCursorLoader(this, dbcreate);
    }
    static class MyCursorLoader extends CursorLoader
    {

        dbmanag db;

        public MyCursorLoader(Context context, dbmanag db)
        {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground()
        {

            Cursor cursor = db.getDataProductList(id_list);
            try
            {
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return cursor;
        }
    }

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();

		getLoaderManager().getLoader(0).forceLoad();
	}

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        // закрываем подключение при выходе
        dbcreate.close();
    }
}
