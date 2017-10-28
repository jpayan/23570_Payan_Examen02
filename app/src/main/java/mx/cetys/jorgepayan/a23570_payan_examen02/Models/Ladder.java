package mx.cetys.jorgepayan.a23570_payan_examen02.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by jorge.payan on 10/27/17.
 */

public class Ladder {
    private String id;
    private int begin;
    private int destination;

    public Ladder(String id, int begin, int destination) {
        this.id = id;
        this.begin = begin;
        this.destination = destination;
    }

    public Ladder(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getString("id");
            this.begin = jsonObject.getInt("begin");
            this.destination = jsonObject.getInt("destination");
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
