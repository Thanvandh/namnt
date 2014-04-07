package com.namnt.danhngon;

public class Quote {

	// private variables
	String mId;
	String mDescription;
	String mDanhngon;
	String mTacgia;

	// Empty constructor
	public Quote() {

	}

	// constructor
	public Quote(String id, String description, String danhngon, String tacgia) {
		this.mId = id;
		this.mDescription = description;
		this.mDanhngon = danhngon;
		this.mTacgia = tacgia;
	}

	// constructor
//	public Quote(String title, String content) {
//		this._title = title;
//		this._content = content;
//	}

	// getting ID
	public String getId() {
		return this.mId;
	}

	// setting id
	public void setId(String id) {
		this.mId = id;
	}

	// getting title
	public String getDescription() {
		return this.mDescription;
	}

	// setting title
	public void setDescription(String description) {
		this.mDescription = description;
	}

	// getting content
	public String getDanhngon() {
		return this.mDanhngon;
	}

	// setting content
	public void setDanhngon(String danhngon) {
		this.mDanhngon = danhngon;
	}

	// getting creation time
	public String getTacgia() {
		return this.mTacgia;
	}

	// setting creation time
	public void setTacgia(String tacgia) {
		this.mTacgia = tacgia;
	}
}
