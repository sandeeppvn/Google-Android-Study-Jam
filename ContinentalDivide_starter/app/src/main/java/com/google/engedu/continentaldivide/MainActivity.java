package com.google.engedu.continentaldivide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    ContinentMap continentMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout container = (LinearLayout) findViewById(R.id.vertical_layout);
        // Create the map and insert it into the view.
        continentMap = new ContinentMap(this);
        continentMap.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        container.addView(continentMap, 0);

    }

    public boolean onGenerateTerrain(View view) {
        continentMap.generateTerrain(3);
        return true;
    }

    public boolean onFindContinentalDivideDown(View view) {
        continentMap.buildDownContinentalDivide(false);
        return true;
    }

    public boolean onFindContinentalDivideUp(View view) {
        continentMap.buildUpContinentalDivide(false);
        return true;
    }

    public boolean onClearContinentalDivide(View view) {
        continentMap.clearContinentalDivide();
        return true;
    }
}
