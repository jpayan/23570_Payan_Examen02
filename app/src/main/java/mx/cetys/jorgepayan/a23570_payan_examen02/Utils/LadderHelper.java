package mx.cetys.jorgepayan.a23570_payan_examen02.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.a23570_payan_examen02.Models.Ladder;

/**
 * Created by jorge.payan on 10/27/17.
 */

public class LadderHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] LADDER_TABLE_COLUMNS = {
            DBUtils.LADDER_BOARD_ID,
            DBUtils.LADDER_BEGIN,
            DBUtils.LADDER_DESTINATION
    };

    public LadderHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Ladder> getBoardLadders(int boardId) {
        ArrayList<Ladder> ladders = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.LADDER_TABLE_NAME, LADDER_TABLE_COLUMNS,
                DBUtils.LADDER_BOARD_ID + " = " + boardId, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            ladders.add(parseLadder(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return ladders;
    }

    public void addLadders(String boardId, int begin, int destination) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.LADDER_BOARD_ID, boardId);
        values.put(DBUtils.LADDER_BEGIN, begin);
        values.put(DBUtils.LADDER_DESTINATION, destination);

        database.insert(DBUtils.LADDER_TABLE_NAME, null, values);
    }

    public void deleteLadders(int boardId) {
        database.delete(DBUtils.LADDER_TABLE_NAME, DBUtils.LADDER_BOARD_ID + " = " + boardId, null);
    }

    public void clearTable() {
        database.execSQL("DELETE FROM " + DBUtils.LADDER_TABLE_NAME);
    }

    private Ladder parseLadder(Cursor cursor) {

        String boardId = cursor.getString(cursor.getColumnIndex(DBUtils.LADDER_BOARD_ID));
        int begin = cursor.getInt(cursor.getColumnIndex(DBUtils.LADDER_BEGIN));
        int destination = cursor.getInt(cursor.getColumnIndex(DBUtils.LADDER_DESTINATION));

        Ladder ladder = new Ladder(boardId, begin, destination);

        return ladder;
    }
}
