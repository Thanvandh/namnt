package namnt.tuvi;

import java.util.Calendar;
import java.util.TimeZone;

import namnt.tuvi.utils.VietCalendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


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
	 
	 TextView mTextName;
	 Button mBtAnsao;
	
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mBirthday = (EditText) findViewById(R.id.id_txt_birthday);
		mBirthtime = (Spinner) findViewById(R.id.id_txt_birthday_time);
		mBtAnsao = (Button) findViewById(R.id.id_bt_ansao);
		mTextName = (TextView) findViewById(R.id.id_txt_name);
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
		  mBtAnsao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name_ = mTextName.getText().toString();
				int [] amlich_ = VietCalendar.convertSolar2Lunar(mDay, mMonth, mYear, 7);
				int giosinh_ = mBirthtime.getSelectedItemPosition();
				int sex_ = mSex.getSelectedItemPosition();
				Intent intent_ = new Intent(MainActivity.this, AnsaoActivity.class);
				Bundle bundle_ = new Bundle();
				bundle_.putString(AnsaoActivity.INTENT_NAME, name_);
				bundle_.putInt(AnsaoActivity.INTENT_NGAY, amlich_[0] + 1);
				bundle_.putInt(AnsaoActivity.INTENT_THANG, amlich_[1] + 1);
				bundle_.putInt(AnsaoActivity.INTENT_NAM, ((amlich_[2] + 8) % 12) + 1);
				bundle_.putInt(AnsaoActivity.INTENT_CAN, ((amlich_[2] + 6) % 10) + 1);
				bundle_.putInt(AnsaoActivity.INTENT_GIO, giosinh_ + 1);
				bundle_.putInt(AnsaoActivity.INTENT_GIOITINH, sex_ + 1);
				intent_.putExtras(bundle_);
				startActivity(intent_);
				//String[] canchiinfo_ = VietCalendar.getCanChiInfo(amlich_[0] + 1, amlich_[1] +1, amlich_[2], mDay, mMonth, mYear);
//				Toast.makeText(MainActivity.this, " Ten la " + name_ + "\nngay sinh " + (amlich_[0] +1) + "/" + (amlich_[1] +1) + "/" + amlich_[2] +
//						"\nGio sinh " + (giosinh_ + 1) + "\nGioi Tinh " + (sex_ + 1)+ "\n Can " + ((amlich_[2] + 6) % 10) + "\n Nam " +((amlich_[2] + 8) % 12) , Toast.LENGTH_LONG).show();
				
			}
		});
		
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
