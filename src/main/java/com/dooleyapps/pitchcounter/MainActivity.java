package com.dooleyapps.pitchcounter;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.dooleyapps.pitchcounter.MESSAGE";
    private Double bCnt = 0.0;
    private Double gCnt = 0.0;
    private Double sCnt = 0.0;
    private Double tCnt = 0.0;
    private Double sPercent = 0.0;
    private Double gPercent = 0.0;
    private Double sgPercent = 0.0;
    private Double bPercent = 0.0;

    private TextView mBadText;
    private TextView mGoodText;
    private TextView mStrikeText;
    private TextView mTotalText;
    private TextView mStrikePercent;
    private TextView mStrikeGoodPercent;
    private TextView mBallPercent;
    private TextView mGoodPercent;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");;
    private DateFormat dateFormatLong = new SimpleDateFormat("yyyyMMddHHmmssSS");
    private Date date;
    private Date sessionStartDate;

    private Firebase fbData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sessionStartDate = new Date();

        Firebase.setAndroidContext(this);
        fbData = new Firebase("https://pitchtracker.firebaseio.com/");

       // DataSnapshot fbChild = fbData.child(dateFormat.format(sessionStartDate);

       // fbChild.g

        //Query queryRef = fbData.orderByChild("height").equalTo(25);

     //   /*
        if (savedInstanceState != null) {
            bCnt = savedInstanceState.getDouble("bCnt");
            gCnt = savedInstanceState.getDouble("gCnt");
            sCnt = savedInstanceState.getDouble("sCnt");
        }
     //   */

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        mStrikeText = (TextView) findViewById(R.id.strike_cnt);
        mBadText = (TextView) findViewById(R.id.bad_ball_cnt);
        mGoodText = (TextView) findViewById(R.id.good_ball_cnt);
        mTotalText = (TextView) findViewById(R.id.total_cnt);
        mStrikePercent = (TextView) findViewById(R.id.strike_percent_value);
        mGoodPercent = (TextView) findViewById(R.id.good_percent_value);
        mBallPercent = (TextView) findViewById(R.id.ball_percent_value);
        mStrikeGoodPercent = (TextView) findViewById(R.id.good_strike_percent_value);
        paintScreen();
    }
//  /*
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("bCnt", bCnt);
        savedInstanceState.putDouble("gCnt", gCnt);
        savedInstanceState.putDouble("sCnt", sCnt);
    }
//  */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addBad(View view) {
        bCnt = bCnt + 1;
        date = new Date();
        fbData.child(dateFormat.format(sessionStartDate)).child("z").child(dateFormatLong.format(date)).child("pitch").setValue("B");
        paintScreen();
    }

    public void subBad(View view) {
        if (bCnt > 0) {
            bCnt = bCnt - 1;
            paintScreen();
        }
    }

    public void addGood(View view) {
        gCnt = gCnt + 1;
        date = new Date();
        fbData.child(dateFormat.format(sessionStartDate)).child("z").child(dateFormatLong.format(date)).child("pitch").setValue("G");
        paintScreen();
    }

    public void subGood(View view) {
        if (gCnt > 0) {
            gCnt = gCnt - 1;
            paintScreen();
        }
    }

    public void addStrike(View view) {
        sCnt = sCnt + 1;
        date = new Date();

        fbData.child(dateFormat.format(sessionStartDate)).child("z").child(dateFormatLong.format(date)).child("pitch").setValue("S");
        paintScreen();
    }

    public void subStrike(View view) {
        if (sCnt > 0) {
            sCnt = sCnt - 1;
            paintScreen();
        }
    }

    private void paintScreen(){
        totalCount();
        mStrikeText.setText(String.format("%1$,.0f", sCnt));
        mBadText.setText(String.format("%1$,.0f", bCnt));
        mGoodText.setText(String.format("%1$,.0f", gCnt));
        mTotalText.setText(String.format("%1$,.0f", tCnt));
        mStrikePercent.setText(String.format("%1$,.0f", sPercent));
        mStrikeGoodPercent.setText(String.format("%1$,.0f", sgPercent));
        mBallPercent.setText(String.format("%1$,.0f", bPercent));
        mGoodPercent.setText(String.format("%1$,.0f", gPercent));

        //Add in the total session counts to Firebase
        //fbData.child("session").setValue(dateFormat.format(sessionStartDate));
        //fbData.child("session").child("strikeCount").setValue(sCnt);

        fbData.child(dateFormat.format(sessionStartDate)).child("strikeCount").setValue(sCnt);
        fbData.child(dateFormat.format(sessionStartDate)).child("ballCount").setValue(bCnt);
        fbData.child(dateFormat.format(sessionStartDate)).child("goodCount").setValue(gCnt);

    }
    private void totalCount() {
        tCnt = sCnt + bCnt + gCnt;
        sPercent = (sCnt / tCnt) * 100;
        sgPercent = ((sCnt + gCnt) / tCnt) * 100;
        bPercent = (bCnt / tCnt) * 100;
        gPercent = (gCnt / tCnt) * 100;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.dooleyapps.pitchcounter/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.dooleyapps.pitchcounter/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
