package mx.cetys.jorgepayan.a23570_payan_examen02.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jorge.payan on 10/27/17.
 */

public class Board {
    private String id;
    private String name;
    private String author;

    public Board(String id, String name, String author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public Board(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.name = jsonObject.getString("title");
            this.author = jsonObject.getString("body");
        }
        catch(JSONException e){

        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
