
package com.example.kv.basket;
public interface Constants
{
	public static final String DB_NAME = "customer.db";

	public static final String DB_TABLE_LIST = "list";
	public static final String COLUMN_ID_LIST = "listID";
	public static final String COLUMN_DATE_LIST="date";
	public static final String COLUMN_NAME_LIST="name";

	public static final String DB_TABLE_PROD = "product";
	public static final String COLUMN_ID_PROD = "_id";
	public static final String COLUMN_NAME_PROD = "name";
	public static final String COLUMN_ID_LIST_PROD = "listID";
	public static final String COLUMN_COUNT_PROD = "count";
	public static final String COLUMN_ID_UNIT_PROD = "unitID";
	public static final String COLUMN_PRICE_PROD = "price";
	
	public static final String DB_TABLE_UNIT = "unit";
	public static final String COLUMN_ID_UNIT = "unitID";
	public static final String COLUMN_NAME_UNIT= "name";

	public static final int DB_VERSION = 1;
	
}
