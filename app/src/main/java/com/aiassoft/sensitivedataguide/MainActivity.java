/**
 * Copyright (C) 2018 by George Vrynios
 * GV 21/02/2018
 *
 * Project: how to prevent your Sensitive Data (like API keys) from publishing on GitHub repos
 * In this project you will see a solution,
 * how to protect automatically your Sensitive Data (like API keys)
 * before you make your code publicly available on GitHub repos
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

package com.aiassoft.sensitivedataguide;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * This is a demo class to show you how to use the solution
     *
     * Main project dependencies:
     * \project root folder\sensitive_data.txt
     * \project root folder\.gitignore
     * \project root folder\app\build.gradle
     */
    private TextView theMovieDdOrgApiKeyC;
    private TextView facebookAppIdC;
    private TextView gmailCredentialUsernameC;
    private TextView gmailCredentialPasswordC;

    private TextView theMovieDdOrgApiKeyS;
    private TextView facebookAppIdS;
    private TextView gmailCredentialUsernameS;
    private TextView gmailCredentialPasswordS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind the TextViews
        theMovieDdOrgApiKeyC = (TextView) findViewById(R.id.tv_c_the_movie_db_org_api_key);
        facebookAppIdC = (TextView) findViewById(R.id.tv_c_facebook_app_id);
        gmailCredentialUsernameC = (TextView) findViewById(R.id.tv_c_gmail_credential_username);
        gmailCredentialPasswordC = (TextView) findViewById(R.id.tv_c_gmail_credential_password);

        theMovieDdOrgApiKeyS = (TextView) findViewById(R.id.tv_s_the_movie_db_org_api_key);
        facebookAppIdS = (TextView) findViewById(R.id.tv_s_facebook_app_id);
        gmailCredentialUsernameS = (TextView) findViewById(R.id.tv_s_gmail_credential_username);
        gmailCredentialPasswordS = (TextView) findViewById(R.id.tv_s_gmail_credential_password);

        // Update the View's fields with the sensitive data stored in the Constant fields
        theMovieDdOrgApiKeyC.setText(BuildConfig.THEMOVIEDB_API_KEY);
        facebookAppIdC.setText(BuildConfig.FACEBOOK_APP_ID);
        gmailCredentialUsernameC.setText(BuildConfig.GMAIL_CREDENTIAL_USERNAME);
        gmailCredentialPasswordC.setText(BuildConfig.GMAIL_CREDENTIAL_PASSWORD);

        // Update the View's fields with the sensitive data stored in the String Resources
        theMovieDdOrgApiKeyS.setText(getString(R.string.THEMOVIEDB_API_KEY));
        facebookAppIdS.setText(getString(R.string.FACEBOOK_APP_ID));
        gmailCredentialUsernameS.setText(getString(R.string.GMAIL_CREDENTIAL_USERNAME));
        gmailCredentialPasswordS.setText(getString(R.string.GMAIL_CREDENTIAL_PASSWORD));
    }
}
