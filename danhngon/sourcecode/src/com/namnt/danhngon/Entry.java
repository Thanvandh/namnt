package com.namnt.danhngon;

public class Entry {

	// private variables
	String _id;
	String _title;
	String _content;
	String _creation_time;

	// Empty constructor
	public Entry() {

	}

	// constructor
	public Entry(String id, String title, String content, String creation_time) {
		this._id = id;
		this._title = title;
		this._content = content;
		this._creation_time = creation_time;
	}

	// constructor
	public Entry(String title, String content) {
		this._title = title;
		this._content = content;
	}

	// getting ID
	public String getID() {
		return this._id;
	}

	// setting id
	public void setID(String id) {
		this._id = id;
	}

	// getting title
	public String gettitle() {
		return this._title;
	}

	// setting title
	public void settitle(String title) {
		this._title = title;
	}

	// getting content
	public String getcontent() {
		return this._content;
	}

	// setting content
	public void setcontent(String content) {
		this._content = content;
	}

	// getting creation time
	public String getcreation_time() {
		return this._creation_time;
	}

	// setting creation time
	public void setcreation_tim(String creation_time) {
		this._creation_time = creation_time;
	}
}
