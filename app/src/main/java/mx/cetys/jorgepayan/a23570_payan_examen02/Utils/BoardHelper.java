package mx.cetys.jorgepayan.a23570_payan_examen02.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import mx.cetys.jorgepayan.a23570_payan_examen02.Models.Board;

/**
 * Created by jorge.payan on 10/27/17.
 */

public class BoardHelper {
    private DBUtils dbHelper;
    private SQLiteDatabase database;
    private String[] BOARD_TABLE_COLUMNS = {
            DBUtils.BOARD_ID,
            DBUtils.BOARD_NAME,
            DBUtils.BOARD_AUTHOR
    };

    public BoardHelper(Context context) {
        dbHelper = new DBUtils(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public ArrayList<Board> getBoards() {
        ArrayList<Board> boards = new ArrayList<>();
        Cursor cursor = database.query(DBUtils.BOARD_TABLE_NAME, BOARD_TABLE_COLUMNS,
                null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()) {
            boards.add(parseBoard(cursor));
            cursor.moveToNext();
        }
        cursor.close();

        return boards;
    }

    public Board getBoard(String boardId) {
        Cursor cursor = database.query(DBUtils.BOARD_TABLE_NAME, BOARD_TABLE_COLUMNS,
                DBUtils.BOARD_ID + " = " + boardId, null, null, null, null);

        cursor.moveToFirst();
        Board board = parseBoard(cursor);
        cursor.close();

        return board;
    }

    public void addBoard(String id, String name, String author) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.BOARD_ID, id);
        values.put(DBUtils.BOARD_NAME, name);
        values.put(DBUtils.BOARD_AUTHOR, author);

        database.insert(DBUtils.BOARD_TABLE_NAME, null, values);
    }

    public void updateBoard(Board board) {
        ContentValues values = new ContentValues();

        values.put(DBUtils.BOARD_ID, board.getId());
        values.put(DBUtils.BOARD_NAME, board.getName());
        values.put(DBUtils.BOARD_AUTHOR, board.getAuthor());

        database.update(DBUtils.BOARD_TABLE_NAME, values, DBUtils.BOARD_ID + " = " + board.getId(), null);
    }

    public void deleteBoard(int boardId) {
        database.delete(DBUtils.BOARD_TABLE_NAME, DBUtils.BOARD_ID + " = " + boardId, null);
    }

    public void clearTable() {
        database.execSQL("DELETE FROM " + DBUtils.BOARD_TABLE_NAME);
    }

    private Board parseBoard(Cursor cursor) {

        String id = cursor.getString(cursor.getColumnIndex(DBUtils.BOARD_ID));
        String name = cursor.getString(cursor.getColumnIndex(DBUtils.BOARD_NAME));
        String author = cursor.getString(cursor.getColumnIndex(DBUtils.BOARD_AUTHOR));

        Board board = new Board(id, name, author);

        return board;
    }
}