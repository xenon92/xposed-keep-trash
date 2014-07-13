Keep Trash
===

####My blog [post](http://blog.shubhangrathore.com/keep-trash/index.html) for Keep Trash.

An open source Xposed module for android to move the 'Delete' button from overflow menu to action bar in **[Google Keep](https://play.google.com/store/apps/details?id=com.google.android.keep)** app.

I use Google Keep **a lot** to jot down thoughts that come to my mind, thoughts that may come in handy in future. This leads to many many notes scattered all over the app.

To delete a note, I had to - 

1. Long tap the note
2. Tap on the 3-dot overflow menu
3. Tap on 'Delete'.

It was kind of frustrating for me to have an extra tap to open the 3-dot overflow menu just so that I could reach the 'Delete' button.

So I moved the button to the action bar. Now, to delete a note - 

1. Long tap the note
2. Tap on 'Delete'

In v1.1, I implemented a customizable icon listing in the action bar of the official Google Keep app. The users can select which icons they want to show in their Google Keep app action bar. Users can choose any combination, such as select only one among the menu icons listed below, choose any two, choose all three or choose none. Users can choose among the following icons -

- Archive
- Delete
- Share



Screenshots
---

####GUI

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2014/Jul/Screenshot_2014-07-13-13-12-35_HAenexus520140713_140345.png?raw=true)

####Before

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2014/Jul/Screenshot_2014-07-10-21-21-05.png?raw=true)


####After

![](https://raw.githubusercontent.com/xenon92/blog/gh-pages/content/images/2014/Jul/Screenshot_2014-07-10-21-15-55.png?raw=true)


How to use?
---

- Install **[Xposed](http://forum.xda-developers.com/xposed/xposed-installer-versions-changelog-t2714053)** framework for android by the developer **[rovo89](https://github.com/rovo89)**
- Install **[Keep Trash](http://repo.xposed.info/module/com.shubhangrathore.xposed.keeptrash)**
- Enable Keep Trash in Xposed

Support
---

Bugs reports, suggestions and support can be found on XDA-developers **[thread](http://forum.xda-developers.com/xposed/modules/mod-trash-t2812589)** for Keep Trash.

Requirements
---

- Xposed framework
- Android 4.0+

Downloads
---

Check the '**[Releases](https://github.com/xenon92/xposed-keep-trash/releases)**' section in my Github source.
Check the '**[Xposed module repository](http://repo.xposed.info/module/com.shubhangrathore.xposed.keeptrash)**'

Changelog
---

Check the changelog **[here](https://github.com/xenon92/xposed-keep-trash/blob/master/CHANGELOG.md)** or in my **[blog](http://blog.shubhangrathore.com/)** **[post](http://blog.shubhangrathore.com/keep-trash/index.html)**

License
---

The source code is licensed under GNU General Public License v3 (**[GPL v3](https://github.com/xenon92/xposed-keep-trash/blob/master/LICENSE)**)


Copyright
---

Copyright (C) 2014 Shubhang Rathore
