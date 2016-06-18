Keep Trash
================

Changelog
----

**Read more about Keep Trash on my [blog](http://blog.shubhangrathore.com/) [post](http://blog.shubhangrathore.com/keep-trash/index.html)**


#####v2.0.1

- Minor fix to the Xposed Module description that shows up in the Xposed installer


####v2.0

- Completely re-written Xposed implementation that is more robust and flexible
- Add support for Google Keep v3.3+
- Visibility of 6 menu icons can be customized - Delete, Archive, Share, Label, Reminder and Color picker
- New Keep Trash app icon
- New material design for the Keep Trash app
- New material drawables for the Google Keep action bar
- Removed floating action button (for now?)
- Removed customization of 'Note Editor' action bar as Google Keep doesn't have those options in action bar anymore



####v1.3

- Fix action bar icon sizes for official Google Keep
- Remove "Exit" button from Keep Trash action bar
- Added German translations (thanx to mihahn @ XDA-developers)



####v1.2

- Ability to choose which icons (Archive or Show/Hide checkboxes) should show up in action bar when the note is being edited in the official Google Keep app (any combination can be selected)
- Add android L style floating button to open Google Keep from Keep Trash (thanx to **[FAB](https://github.com/FaizMalkani/FloatingActionButton)** library)
- Change supported android version to 4.0.3+ (API 15+) (Google Keep supports 4.0.3+ only)
- Fix module description in Xposed Installer after install
- Add padding to "Share" icon to make it a bit smaller
- Set action bar color to blue



####v1.1

**NOTE** - If you have previously disabled the GUI in v1.0, uninstall and reinstall Keep Trash to enable the **new GUI** to see the customization options.

- Ability to choose which icons you want to show up in official Google Keep's action bar when note is selected (any combination from Archive, Delete and Share can be set. Any one among these, any two, all or none)
- Switch from Holo theme to Holo light with dark action bar
- Remove 'Disable GUI' checkbox
- Enable 'Open Google Keep in Play Store' permanently
- Code clean up


####v1.0

- Initial release
