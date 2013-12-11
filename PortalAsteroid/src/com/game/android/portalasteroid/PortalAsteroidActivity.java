package com.game.android.portalasteroid;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.PortalAsteroidjava.PortalAsteroidjava;
import com.badlogic.gdx.backends.android.AndroidApplication;

public class PortalAsteroidActivity extends AndroidApplication {
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new PortalAsteroidjava(), false);
	}

}
