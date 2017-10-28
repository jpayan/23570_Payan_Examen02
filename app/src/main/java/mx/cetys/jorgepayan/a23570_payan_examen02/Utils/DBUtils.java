package mx.cetys.jorgepayan.a23570_payan_examen02.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jorge.payan on 10/27/17.
 */

public class DBUtils extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "snakesladders.db";
    public static final int DATABASE_VERSION = 1;

    public static final String BOARD_TABLE_NAME = "BOARD";
    public static final String BOARD_ID = "id";
    public static final String BOARD_NAME = "name";
    public static final String BOARD_AUTHOR = "author";

    public static final String CREATE_BOARD_TABLE =
        "CREATE TABLE " + BOARD_TABLE_NAME + "(" +
                BOARD_ID + " text not null, " +
                BOARD_NAME + " text not null, " +
                BOARD_AUTHOR + " text not null " +
        ")";

    public static final String SNAKE_TABLE_NAME = "SNAKE";
    public static final String SNAKE_BOARD_ID = "boardId";
    public static final String SNAKE_BEGIN = "begin";
    public static final String SNAKE_DESTINATION = "destination";

    public static final String CREATE_SNAKE_TABLE =
        "CREATE TABLE " + SNAKE_TABLE_NAME + "(" +
                SNAKE_BOARD_ID + " text not null, " +
                SNAKE_BEGIN + " integer not null, " +
                SNAKE_DESTINATION + " integer not null " +
        ")";

    public static final String LADDER_TABLE_NAME = "LADDER";
    public static final String LADDER_BOARD_ID = "boardId";
    public static final String LADDER_BEGIN = "begin";
    public static final String LADDER_DESTINATION = "destination";

    public static final String CREATE_LADDER_TABLE =
        "CREATE TABLE " + LADDER_TABLE_NAME + "(" +
                LADDER_BOARD_ID + " text not null, " +
                LADDER_BEGIN + " integer not null, " +
                LADDER_DESTINATION + " integer not null " +
        ")";



    public DBUtils(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOARD_TABLE);
        sqLiteDatabase.execSQL(CREATE_SNAKE_TABLE);
        sqLiteDatabase.execSQL(CREATE_LADDER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_BOARD_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_SNAKE_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_LADDER_TABLE);
        onCreate(sqLiteDatabase);
    }
}
