package com.example.kv.basket;

/**
 * Created by kv on 24.03.16.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class dbmanag implements Constants
{


    private db db;
    private SQLiteDatabase base;
    private final Context context;
    private static String[] FROM_UNIT = {COLUMN_ID_UNIT, COLUMN_NAME_UNIT};

    public dbmanag(Context context)
	{
        this.context = context;

    }

    // открыть подключение
    public void open()
	{
        db = new db(context, DB_NAME, null, DB_VERSION);
        base = db.getWritableDatabase();
    }

    // закрыть подключение
    public void close()
	{
        if (db != null) db.close();
    }

    // получить все данные из таблицы DB_TABLE_UNIT
    public Cursor getAllDataUnit()
	{
        return base.query(DB_TABLE_UNIT, FROM_UNIT, null, null, null, null, null);
    }

	public Cursor getAllDataList()
	{
        return base.rawQuery("select listID as _id,strftime('%d-%m-%Y %H:%M',date,'unixepoch') as date1,* from list;", null);
    }
	public Cursor getAllDataProduct()
	{
        return base.rawQuery("select * from product;", null);
    }
    public Cursor getAllDataListID()
	{
        return base.rawQuery("select max(listID) from list;", null);
    }
	public Cursor getDataListName(String str)
	{
        return base.rawQuery("select * from list where listID = ?;", new String[]{str});
    }
	public Cursor getDataProductList(String str)
	{
		return base.rawQuery("select * from product where listID = ?;", new String[]{str});
	}
	public Cursor getDataProductListId(String str)
	{
		return base.rawQuery("select * from product where _id = ?;", new String[]{str});
	}
    public void insertProd(String a, String b, String c, String d, String id_list)
	{
        base.execSQL("insert into product(" + COLUMN_ID_LIST_PROD + "," + COLUMN_ID_UNIT_PROD + "," + COLUMN_NAME_PROD + ","
					 + COLUMN_COUNT_PROD + "," + COLUMN_PRICE_PROD + ") values(" + id_list + "," + a + "," + "'" + b + "'" + "," + c + "," + d + ");");
    }

    public void insertList(String i)
	{
        base.execSQL("insert into list(" + COLUMN_DATE_LIST + "," + COLUMN_NAME_LIST + ") values(" + (new java.util.Date().getTime()) + "," + "'" + i + "'" + ");");
    }
	public void deleteList(String str)
	{
		if (!base.isReadOnly())
		{
			// Enable foreign key constraints
			base.execSQL("PRAGMA foreign_keys = ON;");
			Log.i("TAG", "FOREIGN KEY constraint enabled!");
		}
        base.execSQL("delete from list where listID = ?;", new String[]{str});
		//base.execSQL("delete from product;");
    }
	public void deleteProductItem(String str)
	{
		if (!base.isReadOnly())
		{
			// Enable foreign key constraints
			base.execSQL("PRAGMA foreign_keys = ON;");
			Log.i("TAG", "FOREIGN KEY constraint enabled!");
		}
        base.execSQL("delete from product where _id = ?;", new String[]{str});
		//base.execSQL("delete from product;");
    }
	
	/*public void updateProduct(String a,String b, String e){
		base.execSQL("Update product set " + COLUMN_ID_UNIT_PROD+"="+a + "," + COLUMN_NAME_PROD+"="+"'"+b +"'"+ " where _id= "+e + ";");*/
	public void updateProduct(String a, String b, String c, String d, String e){
		base.execSQL("Update product set " + COLUMN_ID_UNIT_PROD+"="+a + "," + COLUMN_NAME_PROD+"="+"'"+b 
		+"'"+ "," + COLUMN_COUNT_PROD+"="+c + "," + COLUMN_PRICE_PROD+"="+d+" where _id= "+e + ";");
	}
}
