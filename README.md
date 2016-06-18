Keep Trash
===

####My blog [post](http://blog.shubhangrathore.com/keep-trash/index.html) for Keep Trash.

An open source Xposed module for android, to move 'Delete', 'Archive', 'Show/Hide Checkboxes' or 'Share' menu options from 3-dot overflow menu to the action bar in official **[Google Keep](https://play.google.com/store/apps/details?id=com.google.android.keep)** app.

I use Google Keep **a lot** to jot down thoughts that come to my mind, thoughts that may come in handy in future. This leads to many many notes scattered all over the app.

To delete a note, I had to - 

1. Long tap the note
2. Tap on the 3-dot overflow menu
3. Tap on 'Delete'.

It was kind of frustrating for me to have an extra tap to open the 3-dot overflow menu just so that I could reach the 'Delete' button. Such was the case with a few more menu options that were hidden inside the overflow menu and were an extra tap away.

So I moved the buttons to the action bar. Now, to delete a note - 

1. Long tap the note
2. Tap on 'Delete'

In v1.1, I implemented a **customized icon listing** in the action bar when the **note is selected** in the official Google Keep app. Users can choose any combination, such as select only one among the menu icons listed below, choose any two, choose all three or choose none. Users can choose among the following icons -

- Archive
- Delete
- Share

In v1.2, I implemented a **customized icon listing** in the action bar when the **note is being edited** in the official Google Keep app. Just like v1.1, users can select any combination among the menu items listed below - 

- Archive
- Show/Hide Checkboxes

With v2.0 comes a completely re-written implementation of the Xposed module. The code is more flexible and robust. Keep Trash now uses the new material design and new material drawables that are consistent with the official Google Keep app. Now, visibility of 6 menu items can be customized - Delete, Archive, Share, Reminder, Label and Color Picker. Keep Trash is now available on Google Play store for early and automatic updates. For more details, check the changelog below.

You can select the icons you want to show in your Google Keep app action bar and customize your Google Keep experience.


Screenshots
---

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2016/06/play_store_1.jpg?raw=true)

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2016/06/play_store_2.jpg?raw=true)

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2016/06/play_store_3.jpg?raw=true)

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2016/06/play_store_4.jpg?raw=true)

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2016/06/play_store_5.jpg?raw=true)


How to use?
---

- Install **[Xposed](http://repo.xposed.info/)** framework for android by the developer **[rovo89](https://github.com/rovo89)**
- Install **[Keep Trash](http://repo.xposed.info/module/com.shubhangrathore.xposed.keeptrash)**
- Enable Keep Trash in Xposed

Support
---

Bugs reports, suggestions and support can be found on XDA-developers **[thread](http://forum.xda-developers.com/xposed/modules/mod-trash-t2812589)** for Keep Trash.

Requirements
---

- **[Xposed](http://repo.xposed.info/)** framework
- Android 4.0.3+

Downloads
---

- '**[Releases](https://github.com/xenon92/xposed-keep-trash/releases)**' section in my Github source.
- **[Xposed module repository](http://repo.xposed.info/module/com.shubhangrathore.xposed.keeptrash)**

Changelog
---

Check the changelog **[here](https://github.com/xenon92/xposed-keep-trash/blob/master/CHANGELOG.md)** or in my **[blog](http://blog.shubhangrathore.com/)** **[post](http://blog.shubhangrathore.com/keep-trash/index.html)**

License
---

The source code is licensed under GNU General Public License v3 (**[GPL v3](https://github.com/xenon92/xposed-keep-trash/blob/master/LICENSE)**)


Copyright
---

Copyright (C) 2014 - 2016 Shubhang Rathore
