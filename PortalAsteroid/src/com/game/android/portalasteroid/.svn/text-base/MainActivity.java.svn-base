package com.game.android.portalasteroid;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.preference.*;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	
	boolean CheckboxPreference;
    String ListPreference;
    String editTextPreference;
    String ringtonePreference;
    String secondEditTextPreference;
    String customPref;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /** The button that starts the game. */
        Button start = (Button) findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent portalAsteroidActivity = new Intent(getBaseContext(), PortalAsteroidActivity.class);
				startActivity(portalAsteroidActivity);
			}
            
        });
        
        /** The button that starts the settings page. */
        Button settings = (Button) findViewById(R.id.settingsButton);
        settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				getPrefs();
				Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
				startActivity(settingsActivity);
			}
        });
        
        /** The button that starts the high scores page. */
        Button score = (Button) findViewById(R.id.highscoreButton);
        score.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent highscoreActivity = new Intent(getBaseContext(), Highscore.class);
				startActivity(highscoreActivity);
			}
        });
        
        /** The button that starts the about page. */
        Button about = (Button) findViewById(R.id.infoButton);
        about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent aboutActivity = new Intent(getBaseContext(), About.class);
				startActivity(aboutActivity);
			}
        });
    }

    /** Get the preferences of the user. */
    private void getPrefs() {
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    	
    	CheckboxPreference = prefs.getBoolean("checkboxPref", true);
    	ListPreference = prefs.getString("listPref", "nr1");
    	editTextPreference = prefs.getString("editTextPref", "Nothing has been entered");
    	ringtonePreference = prefs.getString("ringtonePref", "DEFAULT_RINGTONE_URI");
    	secondEditTextPreference = prefs.getString("SecondEditTextPref", "Nothing has been entered");
    	
    	// Get the custom preference
    	SharedPreferences mySharedPreferences = getSharedPreferences("myCustomSharedPrefs", Activity.MODE_PRIVATE);
    	customPref = mySharedPreferences.getString("myCusomPref", "");
    }
}