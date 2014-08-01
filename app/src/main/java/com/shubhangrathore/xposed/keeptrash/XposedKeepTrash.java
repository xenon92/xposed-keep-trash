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

import android.content.res.XModuleResources;
import android.util.Log;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

/**
 * Created by Shubhang on 10/7/2014.
 */
public class XposedKeepTrash implements IXposedHookInitPackageResources, IXposedHookZygoteInit {

    private static String GOOGLE_KEEP = "com.google.android.keep";
    private static String MODULE_PATH = null;
    private static String PACKAGE_NAME = XposedKeepTrash.class.getPackage().getName();
    private static String TAG = "XposedKeepTrash";

    private boolean mShowArchive;
    private boolean mShowArchiveEditor;
    private boolean mShowDelete;
    private boolean mShowShowCheckboxesEditor;
    private boolean mShowShare;


    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resParam) throws Throwable {

        // If package is not com.google.android.keep, return to not execute further
        if (!resParam.packageName.equals(GOOGLE_KEEP)) {
            return;
        }

        XSharedPreferences mXSharedPreferences = new XSharedPreferences(PACKAGE_NAME);
        XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resParam.res);


        // In Google Keep app, "menu_delete" menu item is initialized in a different xml (ids.xml)
        // than where "menu_delete" is given its properties (selection_context_menu.xml).
        // So replacing the individual menu item through Xposed, based on its <id>
        // would replace the menu item as soon as the package is loaded.
        //
        // But when the menu is inflated through "selection_context_menu" in Google Keep app,
        // it will override the initial parameters to <id> given by Xposed when the package was loaded,
        // causing the menu_delete menu item to have Google Keep default properties,
        // and it will be shown in the overflow menu again.
        // Hence I replaced the whole "selection_context_menu" menu with my custom menu,
        // "custom_selection_context_menu" with the required properties for menu_delete.
        // I'll need to keep the "custom_selection_context_menu.xml" to the latest menu items
        // if the default "selection_context_menu.xml" in Google Keep changes its menu items.
        // Same holds true for "editor_menu.xml".


        mShowArchive = mXSharedPreferences.getBoolean("show_archive_checkbox_preference", true);
        mShowArchiveEditor = mXSharedPreferences.getBoolean("show_archive_editor_checkbox_preference", true);
        mShowDelete = mXSharedPreferences.getBoolean("show_delete_checkbox_preference", true);
        mShowShowCheckboxesEditor = mXSharedPreferences.getBoolean("show_show_checkboxes_editor_checkbox_preference", true);
        mShowShare = mXSharedPreferences.getBoolean("show_share_checkbox_preference", true);


        // Replacing resources for action bar when note is selected
        // Replacing original selection_context_menu.xml with custom menu

        if (mShowArchive && !mShowDelete && !mShowShare) {

            // Only show Archive in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.archive_selection_context_menu));

            Log.i(TAG, "Replaced resources to show only Archive in action bar");

        } else if (!mShowArchive && mShowDelete && !mShowShare) {

            // Only show Delete in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.delete_selection_context_menu));

            Log.i(TAG, "Replaced resources to show only Delete in action bar");

        } else if (!mShowArchive && !mShowDelete && mShowShare) {

            // Only show Share in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.share_selection_context_menu));

            Log.i(TAG, "Replaced resources to show only Share in action bar");

        } else if (mShowArchive && mShowDelete && !mShowShare) {

            // Show Archive and Delete, but not Share in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.archive_delete_selection_context_menu));

            Log.i(TAG, "Replaced resources to show both Archive and Delete in action bar");

        } else if (!mShowArchive && mShowDelete && mShowShare) {

            // Show Delete and Share, but not Archive in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.delete_share_selection_context_menu));

            Log.i(TAG, "Replaced resources to show both Delete and Share in action bar");

        } else if (mShowArchive && !mShowDelete && mShowShare) {

            // Show Archive and Share, but not Delete in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.archive_share_selection_context_menu));

            Log.i(TAG, "Replaced resources to show both Archive and Share in action bar");

        } else if (mShowArchive && mShowDelete && mShowShare) {

            // Show Archive, Delete and Share in action bar (all)
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.archive_delete_share_selection_context_menu));

            Log.i(TAG, "Replaced resources to show all icons in action bar");

        } else if (!mShowArchive && !mShowDelete && !mShowShare) {

            // Show none among Archive, Delete or Share in action bar (none)
            resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                    modRes.fwd(R.menu.none_selection_context_menu));

            Log.i(TAG, "Replaced resources to show none of the icons in action bar");

        }


        // Replacing resources for action bar when note is being edited
        // Replacing original editor_menu.xml with custom menu

        if (!mShowArchiveEditor && !mShowShowCheckboxesEditor) {

            // Show none of Archive or Show/Hide checkboxes in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "editor_menu",
                    modRes.fwd(R.menu.none_editor_menu));

            Log.i(TAG, "Replaced resources to show none of Archive or Show/Hide checkbox in action bar in note editor");

        } else if (!mShowArchiveEditor && mShowShowCheckboxesEditor) {

            // Only show Show/Hide checkboxes menu item in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "editor_menu",
                    modRes.fwd(R.menu.show_hide_checkboxes_editor_menu));

            Log.i(TAG, "Replaced resources to show only Show/Hide checkboxes in action bar in note editor");

        } else if (mShowArchiveEditor && mShowShowCheckboxesEditor) {

            // Show both Archive and Show/Hide Checkboxes in action bar
            resParam.res.setReplacement("com.google.android.keep", "menu", "editor_menu",
                    modRes.fwd(R.menu.archive_show_hide_checkboxes_editor_menu));

            Log.i(TAG, "Replaced resources to show both Delete and Show/Hide checkboxes in action bar in note editor");

        } else if (mShowArchiveEditor && !mShowShowCheckboxesEditor) {

            // Only show Archive in action bar
            // This is the default editor menu of Google Keep.
            // This condition needs to be overwritten by xposed or else in a common use case,
            // the previously selected menu replacement will stay in effect.
            resParam.res.setReplacement("com.google.android.keep", "menu", "editor_menu",
                    modRes.fwd(R.menu.official_editor_menu));

            Log.i(TAG, "Replaced resources with official editor_menu to show only Archive in action bar in note editor");

        }
    }
}
