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

import android.content.res.XModuleResources;
import android.view.Menu;
import android.view.MenuItem;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Created by Shubhang on 10/7/2014.
 */
public class XposedKeepTrash implements IXposedHookInitPackageResources, IXposedHookZygoteInit, IXposedHookLoadPackage {

    public static XModuleResources modRes;
    private static boolean DEBUG = false;

    private static String TAG = "XposedKeepTrash";
    private static String GOOGLE_KEEP = "com.google.android.keep";
    private static String MODULE_PATH = null;
    private static String PACKAGE_NAME = XposedKeepTrash.class.getPackage().getName();
    private static String BROWSE_FRAGMENT_CLASS = "com.google.android.keep.browse.BrowseFragment";

    private boolean mShowArchive;
    private boolean mShowDelete;
    private boolean mShowShare;
    private boolean mShowColorChanger;
    private boolean mShowLabel;
    private boolean mShowReminder;

    private int mKeepMenuArchive;
    private int mKeepMenuUnarchive;
    private int mKeepMenuDelete;
    private int mKeepMenuShare;
    private int mKeepMenuColorChanger;
    private int mKeepMenuLabel;
    private int mKeepMenuReminder;

    private Menu mMenu;

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(final XC_InitPackageResources.InitPackageResourcesParam resParam) throws Throwable {

        // If package is not com.google.android.keep, return to not execute further
        if (!resParam.packageName.equals(GOOGLE_KEEP)) {
            return;
        }

        XSharedPreferences mXSharedPreferences = new XSharedPreferences(PACKAGE_NAME);
        modRes = XModuleResources.createInstance(MODULE_PATH, resParam.res);

        // "Note Selected" preferences
        mShowArchive = mXSharedPreferences.getBoolean("show_archive_switch_preference", true);
        mShowDelete = mXSharedPreferences.getBoolean("show_delete_switch_preference", true);
        mShowShare = mXSharedPreferences.getBoolean("show_share_switch_preference", true);
        mShowColorChanger = mXSharedPreferences.getBoolean("show_color_changer_switch_preference", true);
        mShowLabel = mXSharedPreferences.getBoolean("show_add_label_switch_preference", true);
        mShowReminder = mXSharedPreferences.getBoolean("show_add_reminder_switch_preference", true);

        mKeepMenuArchive = resParam.res.getIdentifier("menu_archive", "id", GOOGLE_KEEP);
        mKeepMenuUnarchive = resParam.res.getIdentifier("menu_unarchive", "id", GOOGLE_KEEP);
        mKeepMenuDelete = resParam.res.getIdentifier("menu_delete", "id", GOOGLE_KEEP);
        mKeepMenuShare = resParam.res.getIdentifier("menu_send", "id", GOOGLE_KEEP);
        mKeepMenuColorChanger = resParam.res.getIdentifier("menu_color_picker", "id", GOOGLE_KEEP);
        mKeepMenuLabel = resParam.res.getIdentifier("menu_change_labels", "id", GOOGLE_KEEP);
        mKeepMenuReminder = resParam.res.getIdentifier("menu_add_reminder", "id", GOOGLE_KEEP);
    }

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        // If package is not com.google.android.keep, return to not execute further
        if (!loadPackageParam.packageName.equals(GOOGLE_KEEP)) {
            return;
        }

        // Hook method 'setActionModeMenuItemVisibility' that updates the visibility of
        // menu items in the class 'BrowseFragment' when a note is selected in Google Keep.
        XposedHelpers.findAndHookMethod(BROWSE_FRAGMENT_CLASS, loadPackageParam.classLoader, "setActionModeMenuItemVisibility", Menu.class, long[].class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(final MethodHookParam methodHookParam) throws Throwable {

                // Get reference of the Action Bar Menu
                mMenu = (Menu) methodHookParam.args[0];

                // Updating the Share Menu Item
                if (mShowShare) {
                    mMenu.findItem(mKeepMenuShare)
                            .setIcon(modRes.getDrawable(R.drawable.ic_share_white_24dp))
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    mMenu.findItem(mKeepMenuShare)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }

                // Updating the Archive and Unarchive Menu Item
                if (mShowArchive) {

                    // Archive
                    mMenu.findItem(mKeepMenuArchive)
                            .setIcon(modRes.getDrawable(R.drawable.ic_archive_white_24dp))
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

                    // Unarchive
                    mMenu.findItem(mKeepMenuUnarchive)
                            .setIcon(modRes.getDrawable(R.drawable.ic_unarchive_white_24dp))
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {

                    // Archive
                    mMenu.findItem(mKeepMenuArchive)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);

                    // Unarchive
                    mMenu.findItem(mKeepMenuUnarchive)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }

                // Update the Delete Menu Item
                // Restore Menu Item doesn't need to be updated as it shows up in the "Trash"
                // activity in Google Keep
                if (mShowDelete) {
                    mMenu.findItem(mKeepMenuDelete)
                            .setIcon(modRes.getDrawable(R.drawable.ic_delete_white_24dp))
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    mMenu.findItem(mKeepMenuDelete)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }

                // Update Color Picker Menu Item
                if (mShowColorChanger) {
                    mMenu.findItem(mKeepMenuColorChanger)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    mMenu.findItem(mKeepMenuColorChanger)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }

                // Update Add Label Menu Item
                if (mShowLabel) {
                    mMenu.findItem(mKeepMenuLabel)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    mMenu.findItem(mKeepMenuLabel)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }

                // Update Add Reminder Menu Item
                if (mShowReminder) {
                    mMenu.findItem(mKeepMenuReminder)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                } else {
                    mMenu.findItem(mKeepMenuReminder)
                            .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
                }
            }
        });
    }
}
