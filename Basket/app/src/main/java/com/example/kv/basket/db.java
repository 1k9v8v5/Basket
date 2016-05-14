package com.example.kv.basket;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class db extends SQLiteOpenHelper implements Constants

{
	public db(Context context, String name, CursorFactory factory,
			  int version) {
		super(context, name, factory, version);
    }
/*	public db (Context context){
		super(context,DB_NAME,null,DB_VERSION);
	}

*/
@Override
	public void onCreate(SQLiteDatabase p1)
	{
		if (!p1.isReadOnly()) {
			// Enable foreign key constraints
			p1.execSQL("PRAGMA foreign_keys = ON;");
			Log.i("TAG", "FOREIGN KEY constraint enabled!");
		}
	//	p1.execSQL("PRAGMA foreign_keys = ON;"); // вкл. внеш. ключ

		p1.execSQL("create table "+ DB_TABLE_LIST + " ("
				+ COLUMN_ID_LIST + " integer primary key autoincrement, "
				+ COLUMN_DATE_LIST + " integer, "
				+ COLUMN_NAME_LIST + " text" + ");");

		p1.execSQL("insert into list values(1," +
				(new java.util.Date().getTime()) + " ,'list');");

		p1.execSQL("create table "+ DB_TABLE_UNIT + " ("
				   + COLUMN_ID_UNIT + " integer primary key autoincrement, " 
				   + COLUMN_NAME_UNIT + " text " + ");");

		p1.execSQL("insert into unit values(1,'шт.'),"+
				 "(2,'кг.')," +
				 "(3,'грамм.')," +
				 "(4,'л.')," +
				 "(5,'мл.');");

		p1.execSQL("create table "+ DB_TABLE_PROD + " ("
				   + COLUMN_ID_PROD + " integer primary key autoincrement, "
				   + COLUMN_ID_LIST_PROD + " integer, "
				   + COLUMN_ID_UNIT_PROD + " integer, "
				   + COLUMN_NAME_PROD + " text, "
				   + COLUMN_COUNT_PROD + " integer, "
				   + COLUMN_PRICE_PROD  + " real, "
				   + "foreign key( " + COLUMN_ID_LIST_PROD + " ) references "
				   + DB_TABLE_LIST + "(" + COLUMN_ID_LIST + ") on delete cascade,"
				   + "foreign key( " + COLUMN_ID_UNIT_PROD + " ) references "
				   + DB_TABLE_UNIT + "(" + COLUMN_ID_UNIT + ") on update cascade" + ");");

		p1.execSQL("insert into product values(1,1,1,'а',3,500.12);");

	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int p2, int p3)
	{
		// TODO: Implement this method
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_LIST);
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_UNIT);
		db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_PROD);
		onCreate(db);
	}

}

