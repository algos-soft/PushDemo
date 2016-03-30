/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.abea.pushclient;

import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class HCInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG="GCMREG";


    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */

    @Override
    public void onTokenRefresh() {

//        // remove old token form preferences
//        Prefs.getEditor().putString(Prefs.GCM_REGISTRATION_TOKEN, null).apply();
//
//        // obtain a new token and send it to the server
//        GCMRegisterTask task = new GCMRegisterTask();
//        task.execute();

        Log.d(TAG, "GCM Token refresh received");


    }

}
