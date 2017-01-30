package com.example.asce.easyreader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordMeaning extends AppCompatActivity {

    private static Bundle mBundleRecyclerViewState;
    ProgressDialog pDialog;
    public String KEY_RECYCLER_STATE="save";
    int i;
    String recognizedText;
    String wordData=" hello ";
    private final String RESULT="results";
    private final String HEADWORD="headword";
    private final String PART_OF_SPEECH="part_of_speech";
    private final String SENSES="senses";
    private final String DEFINITION="definition";
    private final String EXAMPLES="examples";
    private final String TEXT="text";
    String tag_json_obj = "json_obj_req";
    public ArrayList<String> easyList;
    public String[] list;
    ArrayList<String> finalList;

    RecyclerView recyclerView;
    private WordAdapter adapter;
    private List<Word> wordList;
    private ImageButton shortlist;
    String easyWord="a able about account acid across act addition adjustment advertisement after again against agreement air all almost among amount amusement "+
            "and angle angry animal answer ant any apparatus apple approval arch argument arm army art as atattack attempt attention attraction authority automatic awake "+
            "baby back bad bag balance ball band base basin basket bath be beautiful because bed bee before behaviour belief bell bent berry between "+
            "bird birth bit bite bitter black blade blood blow blue board boat body boiling bone book boot bottle box boy brain brake branch "+
            "brass bread breath brick bridge bright broken brother brown brush bucket building bulb burn burst business but butter "+
            "button by cake camera canvas card care carriage cart cat cause certain chain chalk chance change cheap cheese chemical chest chief chin church circle clean clear clockcloth "+
            "cloud coal coat cold collar colour comb come comfort committee common company comparison competition complete complex condition connection consciouscontrol cook copper copy cord cork cotton cough country cover cow crack "+
            "credit crime cruel crush cry cup cup current "+
            "curtain curve cushion damage danger dark daughter daydead dear death debt decision deep degree delicate dependent design desire destruction detail development different digestion direction dirty discovery discussion disease disgust "+
            "distance distribution division do dog don door doubt down drain drawer dress drink driving drop dry dust earearly earth east edge education effect egg elastic "+
            "electric end engine enough equal error even event ever every example exchange existence expansion experience expert eye face fact fall false family far farm fat father "+
            "fear feather feeble feeling female fertile fiction field fight finger fire first fish fixed flag flame flat flight floor flower fly fold food "+
            "foolish foot for force fork form forward fowl frame free frequent friend from front fruit full future garden general get girlgive glass glove go goat "+
            "goldgood government grain grass great green grey grip group growth guide gun hair hammer hand hanging happy harbour hard harmony hat hate have he head healthy hearhearing heart heat help high history hole hollow "+
            "hook hope horn horse hospital hour house how humour I ice idea if ill important impulse in increase industry inkinsect instrument insurance interest invention iron island "+
            "jelly jewel join journey judge jump keep kettle key kick kind kiss knee knife knot knowledge land language last late laugh law lead leaf learning leather left leg let letter level library lift light like limit line linen lip liquid list little living locklong lookloose loss loud "+
            "love low machinemake male man manager map mark market married mass match material may meal measure meat medical meeting memory metal middle military milk mind mine minute mist mixed money monkey month moon morning mother "+
            "motion mountain mouth move much muscle music nail name narrow nation natural near necessary neck need needle nerve net new news night no noise normal "+
            "north nose not note now number nut observation of off offer office oil old on only open"+ "operation opinion opposite or orange order organization ornament other out oven over owner page pain paint paper parallel parcel part past paste payment peace pen "+
            "pencil person physical picture pig pin pipe place plane plant plate play please pleasure plough pocket point poison polish political poor porter position possible pot potato powder power present price printprison "+
            "private probable process produce profit property prose protestpublic pull pump punishment "+
            "purpose push put quality question quick quiet quite rail rain range rat rate ray reaction reading ready reason receipt record red regret regular relation religion representative request respect responsible rest reward rhythm rice right "+
            "ringriver road rod roll roof room root rough round rub rule run sad safe sail salt same sand say scale school science scissors screw sea seat second secret secretary see seed seem selection self send sense separate serious servant sex shade shake shame sharp sheep shelf ship shirt shock shoe short shutside sign silk silver simple sister size skin skirtsky sleep "+
            "slip slope slow small smash smell smile smoke smooth snake sneeze snow "+
            "so soap society sock soft solid some son song sort sound soup south space spade special sponge spoon spring square stage stamp star start "+
            "statement station steam steel stem step stick sticky stiff still stitch stocking"+ "stomach stone stop store story straight strange street stretch strong structure substance such sudden sugar suggestion summer sun support surprise sweet swim system table tail "+
            "take talk tall taste tax teaching tendency test than that the then theory there thick thin thing this thought thread throat through"+ "through thumb thunder ticket tight till time tin tired to toe together tomorrow tongue tooth top touch town trade train transport tray tree trick trouble trousers true turn twist "+
            "umbrella under unit up us use value verse very vessel view violent voice waiting walk wall war warm wash waste watch water wave wax way weather week weight well west wet wheel when where while whip whistle white who why wide will wind "+
            "window wine wing winter wire wise withwoman wood wool word work worm wound writing wrong year yellow yes yesterday you your yours young";


            String [] easyWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_meaning);

        shortlist=(ImageButton)findViewById(R.id.floating_button);

        shortlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MyActivity.class);
                startActivity(i);
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        wordList = new ArrayList<Word>();
        adapter = new WordAdapter(this,wordList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);


        Bundle extras=getIntent().getExtras();
        recognizedText=extras.getString("EXTRADATA");
        Log.v(recognizedText,"OCERD TEXT");
        list=recognizedText.split(" ");
        Log.i("hello",list.length+"----------------------------- ");
        for(int i=0;i<list.length;i++)
        {
            Log.v(list[i],"LIST______LIST");
        }
        finalList=new ArrayList<String>();
        for(int i=0;i<list.length;i++)
        {
            if(list[i].length()==1)
            {
                continue;
            }
            if(isNumeric(list[i]))
            {
                continue;
            }
            if(!isAlpha(list[i]))
            {
                continue;
            }
            finalList.add(list[i].toLowerCase());
        }

        Collections.sort(finalList);
        Set<String> hs = new HashSet<>();
        hs.addAll(finalList);
        finalList.clear();
        finalList.addAll(hs);

        easyWords=easyWord.split(" ");

        easyList=new ArrayList<String >();
        for(int i=0;i<easyWords.length;i++)
        {
            easyList.add(easyWords[i]);
            Log.v(easyList.get(i),"LIST______LIST___________");
        }
        finalList.removeAll(easyList);
        VolleyParsing(finalList);

    }


    public boolean isAlpha(String name) {
        return name.matches("[a-zA-Z]+");
    }
    public static boolean isNumeric(String str)
    {
        for (char c : str.toCharArray())
        {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }
    public void VolleyParsing(ArrayList<String> find)
    {

        //final Word word;
        pDialog= new ProgressDialog(this);
        // pDialog.setMessage("Loading...");
        //pDialog.show();
        ;

        for( i=0;i<find.size();i++) {
            String url = "http://api.pearson.com/v2/dictionaries/laad3/entries?headword="+find.get(i);

            final int finalI = i;
            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                    url, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            wordData = response.toString();


                            try {

                                JSONObject jObject = new JSONObject(wordData);

                                JSONArray jArray = jObject.optJSONArray(RESULT);
                                String headword="",pos="",defination="",example="";

                                if(jArray.length()>=1)

                                {
                                    JSONObject jObjectResult = jArray.getJSONObject(0);

                                    Log.v("Error-3", "Message");

                                    headword = jObjectResult.optString(HEADWORD).toString();
                                    pos = jObjectResult.optString(PART_OF_SPEECH).toString();

                                    JSONArray jArraySenses = jObjectResult.optJSONArray(SENSES);

                                    if(jArraySenses.length()>=1) {

                                        JSONObject jObjectSenes = jArraySenses.getJSONObject(0);
                                        defination = jObjectSenes.optString(DEFINITION).toString();
                                        //JSONArray jArrayExample = jObjectSenes.optJSONArray(EXAMPLES);
                                       // if(jArrayExample.length()>=1) {
                                         //   JSONObject jObjectExample = jArrayExample.getJSONObject(0);
                                           //  example = jObjectExample.optString(TEXT).toString();
                                      //  }

                                    }


                                }

                                Word word = new Word();
                                word.setTitle(headword);
                                word.setId(i+1);
                                word.setExample(example);
                                word.setMeaning(defination);
                                word.setPart_of_speech(pos);

                                if(word.getTitle()!="") {
                                    wordList.add(word);
                                    for(int j=0;j<wordList.size();j++)
                                    {
                                        Log.i(wordList.get(j).getTitle(),"-----");
                                    }
                                    adapter.notifyDataSetChanged();
                                }
                                //Log.v(word.getTitle() + " ", "Message())))))------");



                            } catch (JSONException e) {
                                Log.v("Error", "Message");
                            }

                            pDialog.hide();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Main", "Error: " + error.getMessage());
                    // hide the progress dialog
                    //pDialog.hide();
                }
            });


            // Adding request to request queue


            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

    }
    protected void onPause()
    {
        super.onPause();

        // save RecyclerView state
        mBundleRecyclerViewState = new Bundle();
        Parcelable listState = recyclerView.getLayoutManager().onSaveInstanceState();
        mBundleRecyclerViewState.putParcelable(KEY_RECYCLER_STATE, listState);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // restore RecyclerView state
        if (mBundleRecyclerViewState != null) {
            Parcelable listState = mBundleRecyclerViewState.getParcelable(KEY_RECYCLER_STATE);
            recyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
        adapter.notifyDataSetChanged();
    }

}
