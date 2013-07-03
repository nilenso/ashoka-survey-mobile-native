# Ashoka Survey Mobile Native [![Build Status](https://travis-ci.org/nilenso/ashoka-survey-mobile-native.png?branch=master)](https://travis-ci.org/nilenso/ashoka-survey-mobile-native)

A native rewrite of [ashoka-survey-mobile](http://github.com/nilenso/ashoka-survey-mobile), which was written using Titanium.


# Setup Notes

## Get the code

```bash
$ git clone git@github.com:nilenso/ashoka-survey-mobile-native.git
$ cd ashoka-survey-mobile-native
```

## Setting Up IntelliJ

- Add `ANDROID_HOME` to your `$PATH`

```bash
$ launchctl setenv ANDROID_HOME /path/to/android/sdk
```

- Start IntelliJ
- Go to `File > Import Project` and navigate to the cloned repository. Hit okay on all the subsequent prompts.
- Go to `File > Project Structure > Modules`, hit the Dependencies tab on the right, and move the Android dependency to the bottom of the list.
- Right click the `target` and `bin` folders and `Mark As > Excluded`
