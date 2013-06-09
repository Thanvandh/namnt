package namnt.drumbeat;

import java.util.ArrayList;
import java.util.HashMap;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class FavoritesRowAdapter extends BaseAdapter {
    
	 private MainActivity activity;
	    private ArrayList<HashMap<String, String>> data;
	    private static LayoutInflater inflater=null;
	    private boolean meditable;
	    private int marginRight;
	    
		    
	    public FavoritesRowAdapter(MainActivity a, ArrayList<HashMap<String, String>> d, boolean editable, int marginR) {
	        activity = a;
	        data=d;
	        meditable = editable;
	        marginRight = marginR;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	    public int getCount() {
	        return data.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    public void removefavorite(int position) {
			activity.openRemoveDialog(position);
		}
	    
	    
	    public View getView(int position, View convertView, ViewGroup parent) {
	        View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.row_main_body_list_favorite_file, null);

	        TextView name = (TextView)vi.findViewById(R.id.row_main_body_list_favorite_file_name); // title
	        TextView folder = (TextView)vi.findViewById(R.id.row_main_body_list_favorite_file_folder);
	        ImageView bt_delete = (ImageView)vi.findViewById(R.id.row_main_body_list_favorite_file_bt_delete);
	        ImageView bt_sort = (ImageView)vi.findViewById(R.id.row_main_body_list_favorite_file_bt_sort);
	        ImageView nowplaying = (ImageView) vi.findViewById(R.id.row_main_body_list_favorite_file_nowplaying);
	      
        	
			nowplaying.setVisibility(View.GONE);
			//vi.setBackgroundResource(android.R.color.transparent);
	        
	        
	        final int fposistion = position;
	        bt_delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					removefavorite(fposistion);
				}
			});
	        
	        HashMap<String, String> item = new HashMap<String, String>();
	        item = data.get(position);
	        
	        //Setting all values in listview
	        final String sfolder = item.get(MainActivity.KEY_FOLDER);
			final String file = item.get(MainActivity.KEY_NAME);
	        name.setText(file);
	        folder.setText(sfolder);
	        
	        if (meditable){
	        	bt_delete.setVisibility(View.VISIBLE);
	        	bt_sort.setVisibility(View.VISIBLE);
	        	
	        	 RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)folder.getLayoutParams();
	        	 params.addRule(RelativeLayout.LEFT_OF, R.id.row_main_body_list_favorite_file_bt_sort);
	        	 params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
	        	 params.setMargins(0, 0, activity.getResources().getDimensionPixelSize(R.dimen.row_main_body_list_favorite_file_name_margin_left), 0);
	        	folder.setLayoutParams(params);
	        	
	        } else if (sfolder.equalsIgnoreCase(DrumbeatsMediaPlayer.mfolder) && file.equalsIgnoreCase(DrumbeatsMediaPlayer.mfilename)){
				//vi.setBackgroundResource(R.color.list_view_item_selected);
	        	RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)folder.getLayoutParams();
				if (DrumbeatsMediaPlayer.bplay && (!meditable)){
					nowplaying.setVisibility(View.VISIBLE);
					params.addRule(RelativeLayout.LEFT_OF, R.id.row_main_body_list_favorite_file_nowplaying);
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,0);
				} else {
					params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
				}
				
				params.setMargins(0, 0, activity.getResources().getDimensionPixelSize(R.dimen.row_main_body_list_favorite_file_name_margin_left), 0);
				folder.setLayoutParams(params);
				
			} else {
	        	bt_delete.setVisibility(View.GONE);
	        	bt_sort.setVisibility(View.GONE);
	        	  RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)folder.getLayoutParams();
	        	  params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,1);
	        	params.setMargins(0, 0, activity.getResources().getDimensionPixelSize(R.dimen.row_main_body_list_favorite_file_folder_margin_right), 0);
	        	folder.setLayoutParams(params);
	        }
	        
	        return vi;
	    }
}