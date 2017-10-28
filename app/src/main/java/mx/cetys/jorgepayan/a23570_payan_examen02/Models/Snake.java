package mx.cetys.jorgepayan.a23570_payan_examen02.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by jorge.payan on 10/27/17.
 */

public class Snake {
    private String id;
    private int begin;
    private int destination;

    public Snake(String id, int begin, int destination) {
        this.id = id;
        this.begin = begin;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }
}
