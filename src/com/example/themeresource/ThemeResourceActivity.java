package com.example.themeresource;

import android.app.Activity;
import android.content.Context;

public class ThemeResourceActivity extends Activity {
	
	@Override
	protected void attachBaseContext(Context newBase) {
		super.attachBaseContext(newBase);
		MainApp.changeIntoOwnResource(newBase);
	}
}
