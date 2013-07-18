package namnt.tuvi;

import android.os.Bundle;
import android.app.Activity;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.TextView;

public class AnsaoActivity extends Activity {
	TextView[] txt_cung = new TextView[12];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ansao);
		AbsoluteLayout mainview = (AbsoluteLayout)getWindow().getDecorView().findViewById(R.layout.activity_ansao);
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		int width = metrics.widthPixels; 
		int height = metrics.heightPixels; 
		Log.v("test", "width " + width + " height " + height);
		int[] dx = { 2, 1, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3 };
		int[] dy = { 3, 3, 3, 2, 1, 0, 0, 0, 0, 1, 2, 3 };
		for (int i = 0; i < 12; i++)
	    {
		txt_cung[i] = new TextView(this.getApplicationContext());
		Log.v("test", "item width " + width/4 + " item height " + height/4 + " item x " + width/4 * dx[i] + " item y " + height/4 * dy[i]);
		AbsoluteLayout.LayoutParams lp = new AbsoluteLayout.LayoutParams(width/4, height/4, width/4 * dx[i], height/4 * dy[i]);
		
		txt_cung[i].setLayoutParams(lp);
		mainview.addView(txt_cung[i]);

		
	
		txt_cung[i].setText(String.valueOf(i));
	    }
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ansao, menu);
		return true;
		
	}

}
