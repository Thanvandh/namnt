/**
 * Copyright 2010-present Facebook
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package namnt.drumbeat.facebook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import namnt.drumbeat.facebook.SessionEvents.AuthListener;
import namnt.drumbeat.facebook.SessionEvents.LogoutListener;

@SuppressWarnings("deprecation")
public class FacebookAuthButton extends RelativeLayout {

    private Facebook mFb;
    private Handler mHandler;
    private SessionListener mSessionListener = new SessionListener();
    private String[] mPermissions;
    private Activity mActivity;
    private int mActivityCode;

    public FacebookAuthButton(Context context) {
        super(context);
    }

    public FacebookAuthButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FacebookAuthButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void init(final Activity activity, final int activityCode, final Facebook fb) {
        init(activity, activityCode, fb, new String[] {});
    }

    public void init(final Activity activity, final int activityCode, final Facebook fb,
            final String[] permissions) {
        mActivity = activity;
        mActivityCode = activityCode;
        mFb = fb;
        mPermissions = permissions;
        mHandler = new Handler();

        SessionEvents.addAuthListener(mSessionListener);
        SessionEvents.addLogoutListener(mSessionListener);
        setOnClickListener(new ButtonOnClickListener());
    }

    private final class ButtonOnClickListener implements OnClickListener {
        /*
         * Source Tag: login_tag
         */
        @Override
        public void onClick(View arg0) {
            if (mFb.isSessionValid()) {
                SessionEvents.onLogoutBegin();
                AsyncFacebookRunner asyncRunner = new AsyncFacebookRunner(mFb);
                asyncRunner.logout(getContext(), new LogoutRequestListener());
            }
            else {
            	mFb.authorize(mActivity, mPermissions, mActivityCode, new LoginDialogListener());
            }
        }
    }

    private final class LoginDialogListener implements DialogListener {
        @Override
        public void onComplete(Bundle values) {
            SessionEvents.onLoginSuccess();
        }

        @Override
        public void onFacebookError(FacebookError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        @Override
        public void onError(DialogError error) {
            SessionEvents.onLoginError(error.getMessage());
        }

        @Override
        public void onCancel() {
            SessionEvents.onLoginError("Action Canceled");
        }
    }

    private class LogoutRequestListener extends BaseRequestListener {
        @Override
        public void onComplete(String response, final Object state) {
            /*
             * callback should be run in the original thread, not the background
             * thread
             */
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    SessionEvents.onLogoutFinish();
                }
            });
        }

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub
			
		}
    }

    private class SessionListener implements AuthListener, LogoutListener {

        @Override
        public void onAuthSucceed() {
//            setImageResource(R.drawable.logout_button);
            SessionStore.save(mFb, getContext());
            Log.i("fb session", "success");
        }

        @Override
        public void onAuthFail(String error) {
            Log.i("fb session", "fail");
        }

        @Override
        public void onLogoutBegin() {
        }

        @Override
        public void onLogoutFinish() {
            SessionStore.clear(getContext());
//            setImageResource(R.drawable.login_button);
        }
    }

}
