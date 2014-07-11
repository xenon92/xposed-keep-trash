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

import android.content.pm.PackageInfo;
import android.content.res.XModuleResources;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;

/**
 * Created by Shubhang on 10/7/2014.
 */
public class XposedKeepTrash implements IXposedHookInitPackageResources, IXposedHookZygoteInit {

    private static String MODULE_PATH = null;

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resParam) throws Throwable {

        // If package is not com.google.android.keep, return to not execute further
        if (!resParam.packageName.equals("com.google.android.keep")) {
            return;
        }

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
        resParam.res.setReplacement("com.google.android.keep", "menu", "selection_context_menu",
                modRes.fwd(R.menu.custom_selection_context_menu));

    }
}
