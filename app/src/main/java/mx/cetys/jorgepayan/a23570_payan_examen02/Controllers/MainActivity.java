package mx.cetys.jorgepayan.a23570_payan_examen02.Controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import mx.cetys.jorgepayan.a23570_payan_examen02.Models.Board;
import mx.cetys.jorgepayan.a23570_payan_examen02.Models.Ladder;
import mx.cetys.jorgepayan.a23570_payan_examen02.Models.Snake;
import mx.cetys.jorgepayan.a23570_payan_examen02.R;
import mx.cetys.jorgepayan.a23570_payan_examen02.Utils.BoardHelper;
import mx.cetys.jorgepayan.a23570_payan_examen02.Utils.LadderHelper;
import mx.cetys.jorgepayan.a23570_payan_examen02.Utils.SnakeHelper;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_KEY = "Message";
    BoardHelper boardHelper;
    SnakeHelper snakeHelper;
    LadderHelper ladderHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_getBoards = (Button) findViewById(R.id.button_getBoards);
        Button button_makeBoard = (Button) findViewById(R.id.button_makeBoard);

        boardHelper = new BoardHelper(getApplicationContext());
        snakeHelper = new SnakeHelper(getApplicationContext());
        ladderHelper = new LadderHelper(getApplicationContext());

        final RequestQueue queue = Volley.newRequestQueue(this);

        button_getBoards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeToast("Getting boards...");
                String boardsURL = "http://107.170.247.123:2403/snakes-ladders";
                JsonArrayRequest postRequest = new JsonArrayRequest(
                    Request.Method.GET, boardsURL, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            try {
                                System.out.println(response);
                                boardHelper.open();
                                boardHelper.clearTable();
                                boardHelper.close();
                                snakeHelper.open();
                                snakeHelper.clearTable();
                                snakeHelper.close();
                                ladderHelper.open();
                                ladderHelper.clearTable();
                                ladderHelper.close();
                                for(int i = 0; i < response.length(); i++){
                                    JSONObject postJson = response.getJSONObject(i);
                                    Board board = new Board(postJson);
                                    boardHelper.open();
                                    boardHelper.addBoard(board.getId(), board.getName(),
                                            board.getAuthor());
                                    boardHelper.close();
                                }
                                makeToast("Completed.");
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }
                );
                queue.add(postRequest);
            }
        });

        Board board = new Board("123123", "board-1", "Jorge Payan");

        ArrayList<Ladder> ladders = new ArrayList<Ladder>() {{
            add(new Ladder("id1", 32, 62));
            add(new Ladder("id2", 64, 96));
            add(new Ladder("id3", 12, 15));
        }};

        ArrayList<String> plays = getBestGame(board, ladders);
    }

    private void makeToast(String msg) {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

//    private ArrayList<Snake> Snake(String id, JSONObject jsonObject) {
//        try {
//            JSONArray snakes = jsonObject.getJSONArray("snakes");
//            for (int i = 0; i < snakes.length(); i++) {
//
//            }
//            this.id = jsonObject.getString("id");
//            this.begin = jsonObject.getInt("begin");
//            this.destination = jsonObject.getInt("destination");
//        }
//        catch(JSONException e){
//
//        }
//    }

    // Disculpe el "algoritmo", no se enoje por favor
    private ArrayList<String> getBestGame(Board board, ArrayList<Ladder> iladders) {

        ArrayList<ArrayList<String>> allPlays = new ArrayList<ArrayList<String>>();
        ArrayList<Ladder> ladders = sortLadders(iladders);
        int position = 1;
        int nextLadder = 0;

        for(int i = 0; i < ladders.size(); i++) {
            ArrayList<String> plays = new ArrayList<>();
            while(position < 100) {
                if(nextLadder > ladders.size() - 1) {
                    int remaining = 100 - position;
                    if((remaining - 6) < 0) {
                        plays.add("Dice: " + String.valueOf(remaining) + ". From " + position + " to " + String.valueOf(position + remaining));
                        position += remaining;
                    }
                    else {
                        if((remaining - 6) < 0) {
                            plays.add("Dice: " + String.valueOf(remaining) + ". From " + position + " to " + String.valueOf(position + remaining));
                            position += remaining;
                        }
                        else {
                            plays.add("Dice: " + String.valueOf(6) + ". From " + String.valueOf(position) + " to " + String.valueOf(position + 6));
                            position += 6;
                        }
                    }
                }
                else {
                    if(position > ladders.get(nextLadder).getBegin()) {
                        nextLadder++;
                    }
                    else {
                        int space = ladders.get(nextLadder).getBegin() - position;
                        if((space - 6) < 0) {
                            plays.add("Dice: " + space + ". From " + position + " to " + ladders.get(nextLadder).getDestination());
                            position = ladders.get(nextLadder).getDestination();
                        }
                        else {
                            plays.add("Dice: " + 6 + ". From " + position + " to " + (position + 6));
                            position += 6;
                            while(space > 0) {
                                space -= 6;
                                if((space - 6) < 0) {
                                    plays.add("Dice: " + space + ". From " + position + " to " + ladders.get(nextLadder).getDestination());
                                    position = ladders.get(nextLadder).getDestination();
                                    space = 0;
                                }
                                else {
                                    plays.add("Dice: " + String.valueOf(6) + ". From " + String.valueOf(position) + " to " + String.valueOf(position + 6));
                                    position += 6;
                                }
                            }
                        }
                    }
                }
                nextLadder++;
            }
            allPlays.add(plays);
            position = 0;
            nextLadder = i + 1;
        }

        int leastTurns = 100;
        int index = 0;
        for(int i = 0; i < allPlays.size(); i++) {
            if(leastTurns > allPlays.get(i).size()) {
                leastTurns = allPlays.get(i).size();
                index = i;
            }
        }

        return allPlays.get(index);
    }

    // Ahi disculpe el bubble sort #NotCheckingPerformance
    private ArrayList<Ladder> sortLadders(ArrayList<Ladder> ladders) {
        for (int i = 0; i < ladders.size(); i++) {
            for(int j=1; j < (ladders.size() - i); j++){
                if(ladders.get(j - 1).getBegin() > ladders.get(j).getBegin()){
                    Ladder temp = ladders.get(j - 1);
                    ladders.set(j - 1, ladders.get(j));
                    ladders.set(j, temp);
                }
            }
        }
        return ladders;
    }
}
