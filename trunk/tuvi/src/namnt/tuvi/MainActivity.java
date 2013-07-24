package namnt.tuvi;

import java.util.Calendar;

import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;


public class MainActivity extends Activity {
	EditText mBirthday;
	DatePicker mDatepicker;
	public static final int Date_dialog_id = 1;
	 // date and time
	 private int mYear;
	 private int mMonth;
	 private int mDay;
	 Spinner mBirthtime;
	 Spinner mSex;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBirthday = (EditText) findViewById(R.id.id_txt_birthday);
		mBirthtime = (Spinner) findViewById(R.id.id_txt_birthday_time);
		mSex = (Spinner) findViewById(R.id.id_droplist_gender);
		ArrayAdapter<CharSequence> birthtime_adapter_ = ArrayAdapter.createFromResource(this,
		        R.array.giosinh_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		birthtime_adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mBirthtime.setAdapter(birthtime_adapter_);
		
		ArrayAdapter<CharSequence> gioitinh_adapter_ = ArrayAdapter.createFromResource(this,
		        R.array.gioitinh_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		gioitinh_adapter_.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		mSex.setAdapter(gioitinh_adapter_);
		
		mBirthday.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 DatePickerDialog DPD = new DatePickerDialog(
						 MainActivity.this, mDateSetListener, mYear, mMonth,
					      mDay);
					                    DPD.show();
				
			}
		});
		  final Calendar c = Calendar.getInstance();
		  mYear = c.get(Calendar.YEAR);
		  mMonth = c.get(Calendar.MONTH);
		  mDay = c.get(Calendar.DAY_OF_MONTH);
		 
		  updateDisplay();
		
	}
	@Override
	 protected void onPrepareDialog(int id, Dialog dialog) {
	  // TODO Auto-generated method stub
	  super.onPrepareDialog(id, dialog);
	 
	  ((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);
	 
	 }
	 
	 private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
	 
	  public void onDateSet(DatePicker view, int year, int monthOfYear,
	    int dayOfMonth) {
	   mYear = year;
	   mMonth = monthOfYear;
	   mDay = dayOfMonth;
	   updateDisplay();
	  }
	 };
	 
	 private void updateDisplay() {
	  // TODO Auto-generated method stub
		 mBirthday.setText(new StringBuilder()
	    // Month is 0 based so add 1
	    .append(mMonth + 1).append("-").append(mDay).append("-")
	    .append(mYear));
	 }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
