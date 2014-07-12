/*
 * Keep Trash
 *
 * Xposed module to move 'Delete' button from overflow menu to action bar
 * in Google Keep
 *
 * Copyright (c) 2014 Shubhang Rathore
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.shubhangrathore.xposed.keeptrash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends PreferenceActivity {

    public static String mGoogleKeepVersion;

    private static final String CHANGELOG_LINK = "https://github.com/xenon92/xposed-keep-trash/blob/master/CHANGELOG.md";
    private static final String DEVELOPER_WEBSITE_LINK = "http://shubhangrathore.com";
    private static final String GOOGLE_KEEP_PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.google.android.keep";
    private static final String READ_MORE_LINK = "http://blog.shubhangrathore.com/keep-trash/index.html";
    private static final String SOURCE_CODE_LINK = "https://www.github.com/xenon92/xposed-keep-trash";

    public static final String TAG = "xposed_keep_trash";

    private Preference mChangelog;
    private Preference mDeveloper;
    Preference mGoogleKeepVersionPreference;
    private Preference mReadMore;
    private Preference mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        addPreferencesFromResource(R.xml.preferences);

        mChangelog = findPreference("changelog_preference");
        mDeveloper = findPreference("developer_preference");
        mGoogleKeepVersionPreference = findPreference("installed_keep_version_preference");
        mReadMore = findPreference("read_more_preference");
        mSource = findPreference("app_source_preference");

        setAppVersionNameInPreference();
        setGoogleKeepVersion();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_exit) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Retrieve Keep Trash app version
     *
     * @return mVersionName String that returns the app version
     */
    private String getAppVersionName() {
        String mVersionName = null;

        try {
            mVersionName = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            Log.i(TAG, "Keep Trash version = " + mVersionName);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Couldn't get Keep Trash app version.");
        }
        return mVersionName;
    }

    private void setAppVersionNameInPreference() {
        Preference mVersionNamePreference = findPreference("app_version_name_preference");
        mVersionNamePreference.setSummary(getAppVersionName());
    }

    /**
     * Handles tap on preferences in the app
     *
     * @param preferenceScreen
     * @param preference preference that has been tapped
     * @return
     */
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if (preference == mChangelog) {

            openLink(CHANGELOG_LINK);
            return true;

        } else if (preference == mDeveloper) {

            openLink(DEVELOPER_WEBSITE_LINK);
            return true;

        } else if (preference == mGoogleKeepVersionPreference) {

            openLink(GOOGLE_KEEP_PLAY_STORE_LINK);

        } else if (preference == mReadMore) {

            openLink(READ_MORE_LINK);
            return true;

        } else if (preference == mSource) {

            openLink(SOURCE_CODE_LINK);
            return true;

        }

        return false;
    }

    /**
     * Open web links by parsing the URI of the parameter link
     *
     * @param link the link to be parsed to open
     */
    private void openLink(String link) {

        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(link));
        startActivity(browserIntent);

        Log.i(TAG, "Opening link = " + link);

    }

    /**
     * Gets and stores the Google Keep app version installed on the
     * system in the static variable mGoogleKeepVersion.
     */
    private void setGoogleKeepVersion() {

        try {

            PackageInfo mPackageInfo = getPackageManager().getPackageInfo("com.google.android.keep", 0);
            mGoogleKeepVersion = mPackageInfo.versionName;

            mGoogleKeepVersionPreference.setSummary(mGoogleKeepVersion);
            mGoogleKeepVersionPreference.setSelectable(false);
            Log.i(TAG, "Google Keep version installed = " + mGoogleKeepVersion);

        } catch (PackageManager.NameNotFoundException e) {

            Log.e(TAG, "Google Keep not installed.");
            mGoogleKeepVersion = null;

            mGoogleKeepVersionPreference.setSummary(getString(R.string.keep_not_installed) + getString(R.string.tap_to_open_keep_in_play_store));
            mGoogleKeepVersionPreference.setSelectable(true);

        }
    }

    private String getGoogleKeepVersion() {

        return mGoogleKeepVersion;
    }

}
