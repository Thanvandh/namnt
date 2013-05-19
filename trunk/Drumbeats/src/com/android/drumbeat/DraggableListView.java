package com.android.drumbeat;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

	public class DraggableListView extends ListView {

		private Paint mPaint;
		private PorterDuffXfermode PorterDuff;
		boolean mDragMode;

		int mStartPosition;
		int mEndPosition;
		int mDragPointOffset;		//Used to adjust drag view location
		
		ImageView mDragView;
		DropListener mDropListener;
		RemoveListener mRemoveListener;
		DragListener mDragListener;
		private static Context mcontext;
		private boolean bmove = false;
		private static MotionEvent mevent;
		private long touchTime;

		//ImageView vdrag;
		//WindowManager mWindowManager;
		
		public DraggableListView(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
			//vdrag = new ImageView(context);
			//mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			mcontext = context;
			init();
			// TODO Auto-generated constructor stub
		}
		public DraggableListView (Context context) {
			    super(context);
//			    vdrag = new ImageView(context);
//			    mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			    mcontext = context;
			    init();
			  }

		public DraggableListView (Context context, AttributeSet attrs) {
			    super(context, attrs);
//			    vdrag = new ImageView(context);
//			    mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			    mcontext = context;
			    init();
			  }
		public void setDropListener(DropListener l) {
			mDropListener = l;
		}

		public void setRemoveListener(RemoveListener l) {
			mRemoveListener = l;
		}
		
		public void setDragListener(DragListener l) {
			mDragListener = l;
		}
		public void init(){
			mPaint = new Paint();
			//mPaint.setColor(Color.BLUE);
			mPaint.setARGB(128, 36, 23, 250);
			mPaint.setStyle(Paint.Style.FILL);
			//setBackgroundColor(SmartGuideSettingsHelper.getMainviewBackgroundColor(mcontext));
			//setBackgroundDrawable(getResources().getDrawable(R.drawable.main_background_160x164));
			
			//mPaint.setXfermode(xfermode)
			//mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
		}
//		public void setoffest(float offset){
//			moffset = offset;
//		}
//		@Override
//		protected void onDraw(Canvas canvas) {
//			// TODO Auto-generated method stub
//			//RectF r;
//			//r.s
//			Rect r;
//			
//			//canvas.save();
//			if (moffset >= 0)
//			{
//			int with = getMeasuredWidth()-100;
//			int offset = (int) (moffset*with);
//			offset = offset + 100;
//			r = new Rect(offset-5, 0,offset+5 , getMeasuredHeight());
//			canvas.drawRect(r, mPaint);
//			}
//			//canvas.save();
//			//super.onDraw(canvas);
//			//canvas.restore();
//		}
		public void setmove(boolean move)
		{
			bmove = move;
		}
		@Override
//	    public boolean onTouchEvent( MotionEvent event)
//	    {
//	        long touchDuration = 0;
	//
	//
//	            if ( event.getAction() == MotionEvent.ACTION_DOWN )
//	            {
//	                //Start timer
//	                touchTime = System.currentTimeMillis();
//	                onTouchEventDrag(event);
	//
//	            }else if ( event.getAction() == MotionEvent.ACTION_UP )
//	            {
//	                //stop timer
//	                touchDuration = System.currentTimeMillis() - touchTime;
	//
//	                if ( touchDuration < 800 )
//	                {
//	                	onTouchEventDrag(event);
//	                	//return super.onTouchEvent(event);
//	                }else
//	                {
//	                	Log.v("press","Long press");
//	                	return super.onTouchEvent(event);
//	                	//onTouchEventDrag(event);
//	                	
//	                }
//	            }
//	            return true;
//	        }

		public boolean onTouchEvent(MotionEvent ev) {		
			if (!bmove) return super.onTouchEvent(ev);
			final int action = ev.getAction();
			final int x = (int) ev.getX();
			final int y = (int) ev.getY();
			final float press = ev.getPressure();
//			if (!bmove &&( action == MotionEvent.ACTION_UP)) {
//				mevent = ev;
//				mStartPosition = pointToPosition(x,y);
//			}
			int limit = this.getWidth() - getResources().getDimensionPixelSize(R.dimen.row_main_body_list_favorite_file_bt_sort_margin_right) - getResources().getDimensionPixelSize(R.dimen.row_main_body_list_favorite_file_bt_sort_width);
			Log.d("test|", "limit|" + limit);
			if (action == MotionEvent.ACTION_DOWN && (x > limit) ) {
				mDragMode = true;
			}
			//mDragMode = drag;
			
			if (!mDragMode) 
				return super.onTouchEvent(ev);

			switch (action) {
				case MotionEvent.ACTION_DOWN:
					mStartPosition = pointToPosition(x,y);
					if (mStartPosition != INVALID_POSITION) {
						int mItemPosition = mStartPosition - getFirstVisiblePosition();
	                    mDragPointOffset = y - getChildAt(mItemPosition).getTop();
	                    mDragPointOffset -= ((int)ev.getRawY()) - y;
						startDrag(mItemPosition,y);
						drag(0,y);// replace 0 with x if desired
					}	
					break;
				case MotionEvent.ACTION_MOVE:
					drag(0,y);// replace 0 with x if desired
					break;
				case MotionEvent.ACTION_CANCEL:
				case MotionEvent.ACTION_UP:
				default:
					mDragMode = false;
					//bmove	= false;
					mEndPosition = pointToPosition(x,y);
					stopDrag(mStartPosition - getFirstVisiblePosition());
					if (mDropListener != null && mStartPosition != INVALID_POSITION && mEndPosition != INVALID_POSITION) 
		        		 mDropListener.onDrop(mStartPosition, mEndPosition);
					break;
			}
			return true;
		}	
		
		// move the drag view
		private void drag(int x, int y) {
			if (mDragView != null) {
				WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) mDragView.getLayoutParams();
				layoutParams.x = x;
				layoutParams.y = y - mDragPointOffset;
				WindowManager mWindowManager = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
				mWindowManager.updateViewLayout(mDragView, layoutParams);

				if (mDragListener != null)
					mDragListener.onDrag(x, y, null);// change null to "this" when ready to use
			}
		}

		// enable the drag view for dragging
		private void startDrag(int itemIndex, int y) {
			stopDrag(itemIndex);

			View item = getChildAt(itemIndex);
			if (item == null) return;
			//item.setBackgroundResource(R.drawable.background_main);
			item.setDrawingCacheEnabled(true);
			
			if (mDragListener != null)
				mDragListener.onStartDrag(item);
			
	        // Create a copy of the drawing cache so that it does not get recycled
	        // by the framework when the list tries to clean up memory
	        Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
//			Bitmap bitmap = null;
//			try {
//				bitmap = BitmapFactory.decodeStream(mcontext.getAssets().open("1.png"));
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
	        
	        WindowManager.LayoutParams mWindowParams = new WindowManager.LayoutParams();
	        mWindowParams.gravity = Gravity.TOP;
	        mWindowParams.x = 0;
	        mWindowParams.y = y - mDragPointOffset;

	        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
	        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
	        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
	                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
	                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
	                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
	                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
	        mWindowParams.format = PixelFormat.TRANSLUCENT;
	        mWindowParams.windowAnimations = 0;
	        
	        //Context context = getContext();
	        ImageView vdrag = new ImageView(mcontext);
	        vdrag.setImageBitmap(bitmap);
	        //v.setBackgroundResource(R.drawable.ico_search);

	        WindowManager mWindowManager = (WindowManager)mcontext.getSystemService(Context.WINDOW_SERVICE);
	        mWindowManager.addView(vdrag, mWindowParams);
	        mDragView = vdrag;
		}

		// destroy drag view
		private void stopDrag(int itemIndex) {
			if (mDragView != null) {
				if (mDragListener != null)
					mDragListener.onStopDrag(getChildAt(itemIndex));
	            mDragView.setVisibility(GONE);
	            WindowManager wm = (WindowManager)mcontext.getSystemService(Context.WINDOW_SERVICE);
	            wm.removeView(mDragView);
	            mDragView.setImageDrawable(null);
	            mDragView = null;
	        }
		}
	}