/*
 * Keep Trash
 *
 * Xposed module to customize Google Keep app
 *
 * Copyright (c) 2014 - 2016 Shubhang Rathore
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
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends PreferenceActivity {

    public static final String TAG = "XposedKeepTrash";

    private static final String CHANGELOG_LINK = "https://github.com/xenon92/xposed-keep-trash/blob/master/CHANGELOG.md";
    private static final String DEVELOPER_WEBSITE_LINK = "http://shubhangrathore.com";
    private static final String GOOGLE_KEEP_PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=com.google.android.keep";
    private static final String READ_MORE_LINK = "http://blog.shubhangrathore.com/keep-trash/index.html";
    private static final String SOURCE_CODE_LINK = "https://www.github.com/xenon92/xposed-keep-trash";
    private static final String DONATE_LINK = "https://play.google.com/store/apps/details?id=com.shubhangrathore.xposed.keeptrash.donate&hl=en";

    public static String mGoogleKeepVersion;
    private Preference mChangelog;
    private Preference mDeveloper;
    private Preference mDonate;
    private Preference mGoogleKeepVersionPreference;
    private Preference mReadMore;
    private SwitchPreference mShowArchive;
    private SwitchPreference mShowDelete;
    private SwitchPreference mShowShare;
    private SwitchPreference mShowColorPicker;
    private SwitchPreference mShowLabel;
    private SwitchPreference mShowReminder;
    private Preference mSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesMode(MODE_WORLD_READABLE);
        addPreferencesFromResource(R.xml.preferences);
        setContentView(R.layout.activity_main);

        mChangelog = findPreference("changelog_preference");
        mDeveloper = findPreference("developer_preference");
        mGoogleKeepVersionPreference = findPreference("installed_keep_version_preference");
        mReadMore = findPreference("read_more_preference");
        mShowArchive = (SwitchPreference) findPreference("show_archive_switch_preference");
        mShowDelete = (SwitchPreference) findPreference("show_delete_switch_preference");
        mShowShare = (SwitchPreference) findPreference("show_share_switch_preference");
        mSource = findPreference("app_source_preference");
        mDonate = findPreference("donate_preference");
        mShowColorPicker = (SwitchPreference) findPreference("show_color_changer_switch_preference");
        mShowLabel = (SwitchPreference) findPreference("show_add_label_switch_preference");
        mShowReminder = (SwitchPreference) findPreference("show_add_reminder_switch_preference");

        // Set Keep Trash version name in the app
        setAppVersionNameInPreference();

        // Set Google Keep version in the app
        setGoogleKeepVersion();
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
     * @param preference       preference that has been tapped
     * @return
     */
    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {

        if (preference == mChangelog) {
            // Link to Changelog on Github
            openLink(CHANGELOG_LINK);
            return true;
        } else if (preference == mDeveloper) {
            // Link to developer's website
            openLink(DEVELOPER_WEBSITE_LINK);
            return true;
        } else if (preference == mGoogleKeepVersionPreference) {
            // Open Google Keep on Play Store
            openLink(GOOGLE_KEEP_PLAY_STORE_LINK);
        } else if (preference == mReadMore) {
            // Open blog post on Keep Trash
            openLink(READ_MORE_LINK);
            return true;
        } else if (preference == mSource) {
            // Open source code on Github
            openLink(SOURCE_CODE_LINK);
            return true;
        } else if (preference == mDonate) {
            // Open link to donate version of the app on Play Store
            openLink(DONATE_LINK);
            return true;
        } else if ((preference == mShowArchive) || (preference == mShowDelete)
                || (preference == mShowShare) || (preference == mShowColorPicker)
                || (preference == mShowLabel) || (preference == mShowReminder)) {
            // Make a toast notification
            Toast.makeText(getApplicationContext(),
                    getString(R.string.restart_google_keep_for_changes),
                    Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * Open web links by parsing the URI of the parameter link
     *
     * @param link the link to be parsed to open
     */
    private void openLink(String link) {
        //Create a browser Intent
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
            // Fetch Google Keep version
            PackageInfo mPackageInfo = getPackageManager().getPackageInfo("com.google.android.keep", 0);
            mGoogleKeepVersion = mPackageInfo.versionName;

            mGoogleKeepVersionPreference.setSummary(mGoogleKeepVersion + getString(R.string.tap_to_open_keep_in_play_store));
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
