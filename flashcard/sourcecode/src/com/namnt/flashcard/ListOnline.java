package com.namnt.flashcard;

import java.util.ArrayList;
import java.util.HashMap;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.namnt.utils.LazyAdapter;
import com.namnt.utils.XMLParser;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ListOnline extends Fragment {
	
	// XML node keys
	public static final String URL = "http://api.androidhive.info/music/music.xml";
	public static final String KEY_SONG = "song"; // parent node
	public static final String KEY_ID = "id";
	public static final String KEY_TITLE = "title";
	public static final String KEY_ARTIST = "artist";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_THUMB_URL = "thumb_url";
	ListView list;
    LazyAdapter adapter;
    ProgressDialog barProgressDialog;
    Handler updateBarHandler;
    XMLParser parser = new XMLParser();
    String xml;
    
    ProgressDialog ringProgressDialog;
    View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		rootView = inflater.inflate(R.layout.fragment_list_word, container, false);
//		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();

//		XMLParser parser = new XMLParser();
//		Bundle bundle = getArguments();
//		xml = bundle.getString("xml");
//		Log.e("test", "xml start");
//		String xml = parser.getXmlFromUrl(URL); // getting XML from URL
//		Log.e("test", "xml " + xml);
		

		//updateBarHandler = new Handler();
		//launchRingDialog();
//		String xml = "<music><song><id>1</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>2</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>3</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>4</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>5</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>6</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>7</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>8</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>9</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>10</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>11</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>12</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>13</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>14</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>15</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>16</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>17</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>18</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>19</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>20</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>21</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>22</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>23</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>24</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>25</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>26</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>27</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>28</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>29</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>30</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song></music>";
//		Document doc = parser.getDomElement(xml); // getting DOM element
//		
//		NodeList nl = doc.getElementsByTagName(KEY_SONG);
//		// looping through all song nodes <song>
//		for (int i = 0; i < nl.getLength(); i++) {
//			// creating new HashMap
//			HashMap<String, String> map = new HashMap<String, String>();
//			Element e = (Element) nl.item(i);
//			// adding each child node to HashMap key => value
//			map.put(KEY_ID, parser.getValue(e, KEY_ID));
//			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
//			map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
//			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
//			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));
//
//			// adding HashList to ArrayList
//			songsList.add(map);
//		}
		
		
		
		
		// Getting adapter by passing xml data ArrayList
        //adapter= null;        
        //list.setAdapter(adapter);
		
		return rootView;
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
	    super.onViewCreated(view, savedInstanceState);
	    new ProgressAsyncTask().execute();


	}


	 public void populate_listview()
	 {
		 //Log.v("test","sao ko vao day");


		 try {
             // Here you should write your time consuming task...
             // Let the progress ring for 10 seconds...
     		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
         	
     		//Log.e("test", "xml " + xml);
     		
         	Document doc = parser.getDomElement(xml); // getting DOM element
     		
     		NodeList nl = doc.getElementsByTagName(KEY_SONG);
     		// looping through all song nodes <song>
     		for (int i = 0; i < nl.getLength(); i++) {
     			// creating new HashMap
     			HashMap<String, String> map = new HashMap<String, String>();
     			Element e = (Element) nl.item(i);
     			// adding each child node to HashMap key => value
     			map.put(KEY_ID, parser.getValue(e, KEY_ID));
     			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
     			map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
     			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
     			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

     			// adding HashList to ArrayList
     			songsList.add(map);
     		}
     		list=(ListView)rootView.findViewById(R.id.list);
             adapter=new LazyAdapter(getActivity(), songsList);      
             //list.invalidate();
             //adapter.notifyDataSetChanged();
             
             list.setAdapter(adapter);
             //list.refreshDrawableState();
             //Thread.sleep(10000);
         } catch (Exception e) {

         }





	}






	 public void StartProgress(){
	        new ProgressAsyncTask().execute();
	    }

	    public class ProgressAsyncTask extends 
	        AsyncTask<Void, Integer, Void> {

	        int myProgress;

	        @Override
	        protected void onPreExecute() {
	            // TODO Auto-generated method stub
	        	//ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Loading ...", true);
	            super.onPreExecute();

	            myProgress = 0;


	        }

	        @Override
	        protected void onPostExecute(Void result) {
	            // TODO Auto-generated method stub
	        	
	        	
	            
	            //ringProgressDialog.dismiss();
	            //Log.v("test", "vao day");
	            populate_listview();
	            super.onPostExecute(result);
	            


	        }

	        @Override
	        protected Void doInBackground(Void... arg0) {
	            // TODO Auto-generated method stub


//	                keralaparser.parse();//here I am calling both functions
	            
	        	xml = parser.getXmlFromUrl(URL);







	            return null;
	        }

	        @Override
	        protected void onProgressUpdate(Integer... values) {
	            // TODO Auto-generated method stub





	        }

	        }
	
	public void launchRingDialog() {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(getActivity(), "Please wait ...", "Downloading Image ...", true);
        ringProgressDialog.setCancelable(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Here you should write your time consuming task...
                    // Let the progress ring for 10 seconds...
            		ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
                	xml = parser.getXmlFromUrl(URL);
            		//Log.e("test", "xml " + xml);
            		
                	Document doc = parser.getDomElement(xml); // getting DOM element
            		
            		NodeList nl = doc.getElementsByTagName(KEY_SONG);
            		// looping through all song nodes <song>
            		for (int i = 0; i < nl.getLength(); i++) {
            			// creating new HashMap
            			HashMap<String, String> map = new HashMap<String, String>();
            			Element e = (Element) nl.item(i);
            			// adding each child node to HashMap key => value
            			map.put(KEY_ID, parser.getValue(e, KEY_ID));
            			map.put(KEY_TITLE, parser.getValue(e, KEY_TITLE));
            			map.put(KEY_ARTIST, parser.getValue(e, KEY_ARTIST));
            			map.put(KEY_DURATION, parser.getValue(e, KEY_DURATION));
            			map.put(KEY_THUMB_URL, parser.getValue(e, KEY_THUMB_URL));

            			// adding HashList to ArrayList
            			songsList.add(map);
            		}
                    adapter=new LazyAdapter(getActivity(), songsList);      
                    //list.invalidate();
                    //adapter.notifyDataSetChanged();
                    list.setAdapter(adapter);
                    //list.refreshDrawableState();
                    //Thread.sleep(10000);
                } catch (Exception e) {

                }
                ringProgressDialog.dismiss();
            }
        }).start();
    }
}
