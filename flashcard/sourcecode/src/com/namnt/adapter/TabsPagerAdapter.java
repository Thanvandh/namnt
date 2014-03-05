package com.namnt.adapter;

import com.namnt.flashcard.ListWordFragment;
import com.namnt.utils.XMLParser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public static final String URL = "http://api.androidhive.info/music/music.xml";
	String value;
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		XMLParser parser = new XMLParser();
		value = parser.getXmlFromUrl(URL);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:{
			// Top Rated fragment activity
			ListWordFragment fragment = new ListWordFragment();
			Bundle args = new Bundle();
	//		String value = "<music><song><id>1</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>2</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>3</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>4</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>5</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>6</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>7</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>8</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>9</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>10</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>11</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>12</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>13</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>14</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>15</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>16</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>17</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>18</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>19</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>20</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>21</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>22</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>23</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>24</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>25</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>26</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>27</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>28</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>29</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>30</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song></music>";
			args.putString("xml", value);
			fragment.setArguments(args);
			return fragment;
		}
		case 1:{
			// Top Rated fragment activity
			ListWordFragment fragment = new ListWordFragment();
			Bundle args = new Bundle();
			//String value = "<music><song><id>1</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>2</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>3</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>4</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>5</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>6</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>7</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>8</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>9</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>10</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>11</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>12</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>13</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>14</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>15</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>16</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>17</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>18</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>19</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>20</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>21</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>22</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>23</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>24</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>25</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>26</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>27</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>28</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>29</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>30</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song></music>";
			args.putString("xml", value);
			fragment.setArguments(args);
			return fragment;
		}
		case 2:{
			// Top Rated fragment activity
			ListWordFragment fragment = new ListWordFragment();
			Bundle args = new Bundle();
			//String value = "<music><song><id>1</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>2</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>3</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>4</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>5</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>6</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>7</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>8</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>9</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>10</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>11</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>12</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>13</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>14</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>15</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>16</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>17</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>18</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>19</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>20</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>21</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>22</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>23</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>24</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>25</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>26</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>27</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>28</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>29</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>30</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song></music>";
			args.putString("xml", value);
			fragment.setArguments(args);
			return fragment;
		}
		case 3:{
			// Top Rated fragment activity
			ListWordFragment fragment = new ListWordFragment();
			Bundle args = new Bundle();
			//String value = "<music><song><id>1</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>2</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>3</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>4</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>5</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>6</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>7</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>8</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>9</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>10</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>11</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>12</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>13</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>14</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>15</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>16</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>17</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>18</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>19</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>20</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>21</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>22</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>23</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>24</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>25</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>26</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>27</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>28</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>29</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>30</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song></music>";
			args.putString("xml", value);
			fragment.setArguments(args);
			return fragment;
		}
		case 4:{
			// Top Rated fragment activity
			ListWordFragment fragment = new ListWordFragment();
			Bundle args = new Bundle();
			//String value = "<music><song><id>1</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>2</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>3</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>4</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>5</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>6</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>7</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>8</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>9</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>10</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>11</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>12</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>13</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>14</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>15</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>16</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>17</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>18</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>19</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>20</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song><song><id>21</id><title>Someone Like You</title><artist>Adele</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/adele.png</thumb_url></song><song><id>22</id><title>Space Bound</title><artist>Eminem</artist><duration>4:38</duration><thumb_url>http://api.androidhive.info/music/images/eminem.png</thumb_url></song><song><id>23</id><title>Stranger In Moscow</title><artist>Michael Jackson</artist><duration>5:44</duration><thumb_url>http://api.androidhive.info/music/images/mj.png</thumb_url></song><song><id>24</id><title>Love The Way You Lie</title><artist>Rihanna</artist><duration>4:23</duration><thumb_url>http://api.androidhive.info/music/images/rihanna.png</thumb_url></song><song><id>25</id><title>Khwaja Mere Khwaja</title><artist>A R Rehman</artist><duration>6:58</duration><thumb_url>http://api.androidhive.info/music/images/arrehman.png</thumb_url></song><song><id>26</id><title>All My Days</title><artist>Alexi Murdoch</artist><duration>4:47</duration><thumb_url>http://api.androidhive.info/music/images/alexi_murdoch.png</thumb_url></song><song><id>27</id><title>Life For Rent</title><artist>Dido</artist><duration>3:41</duration><thumb_url>http://api.androidhive.info/music/images/dido.png</thumb_url></song><song><id>28</id><title>Love To See You Cry</title><artist>Enrique Iglesias</artist><duration>4:07</duration><thumb_url>http://api.androidhive.info/music/images/enrique.png</thumb_url></song><song><id>29</id><title>The Good, The Bad And The Ugly</title><artist>Ennio Morricone</artist><duration>2:42</duration><thumb_url>http://api.androidhive.info/music/images/ennio.png</thumb_url></song><song><id>30</id><title>Show me the meaning</title><artist>Backstreet Boys</artist><duration>3:56</duration><thumb_url>http://api.androidhive.info/music/images/backstreet_boys.png</thumb_url></song></music>";
			args.putString("xml", value);
			fragment.setArguments(args);
			return fragment;
		}
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 5;
	}

}
