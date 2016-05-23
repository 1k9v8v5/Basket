package com.example.kv.basket;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.support.v7.app.AppCompatActivity;
import android.os.*;
import android.util.Log;
import android.database.sqlite.*;
import android.content.*;
import android.app.*;
import android.database.Cursor;
import java.sql.Date;
import android.widget.*;
import java.text.SimpleDateFormat;
import android.widget.AdapterView.OnItemClickListener;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.*;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import java.util.concurrent.TimeUnit;
import 	android.graphics.Color;
public class ListAll extends Activity implements LoaderCallbacks<Cursor>{
	private dbmanag dbcreate;
	private ListView list;
	SimpleCursorAdapter	adapter;
	private String id = "";
	private long delpos;
	//private String activ = "1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
		list = (ListView) findViewById(R.id.list_view);
		list.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View view,
										int position, long id) {
					delpos = id;
					for(int a = 0; a < parent.getChildCount(); a++)
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
		String[] from = new String[] {dbmanag.COLUMN_DATE_LIST,dbmanag.COLUMN_NAME_LIST};
		int[] to = new int[] {R.id.datelistid,R.id.listid};
		Cursor cursorlist = dbcreate.getAllDataList();
		adapter = new SimpleCursorAdapter(this, R.layout.basket_list, cursorlist, from, to)
		{	
			@Override
			public void setViewText(TextView v, String text)
			{
				super.setViewText(v, convText(v, text));
			}    
		};
		list.setAdapter(adapter);
		getLoaderManager().initLoader(0, null, this);

	}

	private String convText(TextView v, String text)
	{

		switch (v.getId())
		{
			case R.id.datelistid:
				String formatedText = text;
				Date date = new Date(Long.parseLong(formatedText));
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String strTime = simpleDateFormat.format(date);
				return strTime;
        }
		return text;
	}
	  @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.list_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_create_list:
                Intent addprodoflist = new Intent(ListAll.this, Plist.class);
                startActivity(addprodoflist);
                return true;
            case R.id.action_del_list:
            
				dbcreate.deleteList(Long.toString(delpos));
                getLoaderManager().getLoader(0).forceLoad();
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
		inflater.inflate(R.menu.list_menu_cont, menu);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		switch (item.getItemId())
		{
			case R.id.action_open_list_cont:
				Intent editproduct = new Intent(ListAll.this, ProductList.class);
				id = Long.toString(info.id);
				//Log.d("LOG_TAG",info.id+"");
				editproduct.putExtra("id", id);
				//editproduct.putExtra("activ",activ);
				startActivity(editproduct);
				return true;
			case R.id.action_del_list_cont:
				dbcreate.deleteList(Long.toString(info.id));
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
		adapter.swapCursor(p2);
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

			Cursor cursor = db.getAllDataList();
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
	
    


