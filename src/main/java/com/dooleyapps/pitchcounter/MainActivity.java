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

import com.firebase.client.Firebase;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.w3c.dom.Text;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Firebase.setAndroidContext(this);

        if (savedInstanceState != null) {
            bCnt = savedInstanceState.getDouble("bCnt");
            gCnt = savedInstanceState.getDouble("gCnt");
            sCnt = savedInstanceState.getDouble("sCnt");
            tCnt = savedInstanceState.getDouble("tCnt");
            sPercent = savedInstanceState.getDouble("sPercent");
            gPercent = savedInstanceState.getDouble("gPercent");
            sgPercent = savedInstanceState.getDouble("sgPercent");
            bPercent = savedInstanceState.getDouble("bPercent");

            setContentView(R.layout.activity_main);
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDouble("bCnt", bCnt);
        savedInstanceState.putDouble("gCnt", gCnt);
        savedInstanceState.putDouble("sCnt", sCnt);
        savedInstanceState.putDouble("tCnt", tCnt);
        savedInstanceState.putDouble("sPercent", sPercent);
        savedInstanceState.putDouble("gPercent", gPercent);
        savedInstanceState.putDouble("sgPercent", sgPercent);
        savedInstanceState.putDouble("bPercent", bPercent);
    }

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
        mBadText = (TextView) findViewById(R.id.bad_ball_cnt);
        mBadText.setText(String.format("%1$,.0f", bCnt));
        totalCount();
    }

    public void subBad(View view) {
        if (bCnt > 0) {
            bCnt = bCnt - 1;
            mBadText = (TextView) findViewById(R.id.bad_ball_cnt);
            mBadText.setText(String.format("%1$,.0f", bCnt));
            totalCount();
        }
    }

    public void addGood(View view) {
        gCnt = gCnt + 1;
        mGoodText = (TextView) findViewById(R.id.good_ball_cnt);
        mGoodText.setText(String.format("%1$,.0f", gCnt));
        totalCount();
    }

    public void subGood(View view) {
        if (gCnt > 0) {
            gCnt = gCnt - 1;
            mGoodText = (TextView) findViewById(R.id.good_ball_cnt);
            mGoodText.setText(String.format("%1$,.0f", gCnt));
            totalCount();
        }
    }

    public void addStrike(View view) {
        sCnt = sCnt + 1;
        mStrikeText = (TextView) findViewById(R.id.strike_cnt);
        mStrikeText.setText(String.format("%1$,.0f", sCnt));
        totalCount();
    }

    public void subStrike(View view) {
        if (sCnt > 0) {
            sCnt = sCnt - 1;
            mStrikeText = (TextView) findViewById(R.id.strike_cnt);
            mStrikeText.setText(String.format("%1$,.0f", sCnt));
            totalCount();
        }
    }

    private void totalCount() {
        tCnt = sCnt + bCnt + gCnt;
        mTotalText = (TextView) findViewById(R.id.total_cnt);
        mTotalText.setText(String.format("%1$,.0f", tCnt));

        sPercent = (sCnt / tCnt) * 100;
        mStrikePercent = (TextView) findViewById(R.id.strike_percent_value);
        mStrikePercent.setText(String.format("%1$,.0f", sPercent));

        sgPercent = ((sCnt + gCnt) / tCnt) * 100;
        mStrikeGoodPercent = (TextView) findViewById(R.id.good_strike_percent_value);
        mStrikeGoodPercent.setText(String.format("%1$,.0f", sgPercent));

        bPercent = (bCnt / tCnt) * 100;
        mBallPercent = (TextView) findViewById(R.id.ball_percent_value);
        mBallPercent.setText(String.format("%1$,.0f", bPercent));

        gPercent = (gCnt / tCnt) * 100;
        mGoodPercent = (TextView) findViewById(R.id.good_percent_value);
        mGoodPercent.setText(String.format("%1$,.0f", gPercent));
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
