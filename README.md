[![George Vrynios](https://img.shields.io/badge/George-Vrynios-1d408b.svg)](https://www.linkedin.com/in/george-vrynios/) [![License Apache 2.0](https://img.shields.io/badge/license-Apache%202.0-green.svg)](https://github.com/fjoglar/android-dev-challenge/blob/master/LICENSE.txt) [![Platform Android](https://img.shields.io/badge/platform-Android-blue.svg)](https://www.android.com) [![Language Java](https://img.shields.io/badge/language-Java-orange.svg)](https://www.java.com)

# Android Studio, how to prevent your Sensitive Data (like API keys) from publishing on GitHub repos

> When you use API keys or other sensitive data like username, password,
> server ip, etc. in your applications, you should take care to keep
> them secure and ensure that your code does not contain any API keys or
> any other sensitive information before you make your code publicly
> available. If you forget to remove them from the code, the sensitive
> information can be accidentally exposed to a public GitHub repo.

In my project, I had to deal with the removing of the API key from my code every time I wanted to upload my project to the public GitHub repo. As you can figure out this was not the best practice.

After reading a bit more about gradle, I used one of its capabilities to inject **Constant Fields** and **String Resources** into my Android Project. As thus, I have decided to write down the steps I just went through in showing someone how to prevent his sensitive data to be published on GitHub repos.

Instead of embedding that information in your application’s code, you can store them in a file in the project’s root folder, and don’t forget to add the filename of this file, in the file **.gitigore**, so that this file will not be uploaded on the GitHub repo.

If you decide to add contributors to your GitHub repo, then it is very easy for each of them to add an external file to have its own sensitive data stored locally in his project folder.

In this project, you will see how to implement and include such a sensitive data file in your android application, exclude this file from being uploaded in the GitHub repo, and allow to your contributors have permanently their own files. As a bonus, your project can be reviewed easy and fast by letting the reviewer add his own sensitive data directly in your code and evaluate your app as fast as possible, without to deal with an external file.

## Step 1 - creating the sensitive_data.txt file

In the project’s root folder of your android application create a file with the filename **sensitive_data.txt** , inside of this file you can store as many properties as you need in the form of a key-value pair like the example below:

```
theMovieDbApiKey = “54215e8745_YOUR_API_KEY_56987gh7”
facebookAppId = “6_YOUR_APP_ID_8”
gmailCredentialUsername = “yourmail@gmail.com”
gmailCredentialPassword = “kozd_YOUR_PWD_vf”
```

## Step 2 - Update the .gitignore file - VERY IMPORTANT!!!

Don't forget about to update the **.gitignore** file, otherwise your **sensitive_data.txt** file will be published the next time you commit to GitHub repo. To update the .gitignore file just add two lines with the following text:
```java
#my sensitive data
sensitive_data.txt
```
[a comple .gitignore file from this project can be downloaded from here](https://raw.githubusercontent.com/kalxasath/Sensitive%20Data%20Guide/master/.gitignore)

## Step 3 - Reading the key-value pairs from your sensitive_data.txt file

In step 3 & 4, you will use the capabilities from gradle to read the key-value pairs from the file and inject those key-value pairs into your application as a Constant Field and as a String Resource.

Open the Android Studio, on your project from the section “Gradle Scripts” open the file **gradle.build (Module: app)**, after the line ```apply plugin: ‘com.android.application’``` write the code to read the key-value pair properties from the sensitive_data.txt file.

```java
apply plugin: 'com.android.application'

// Begin of reading the sensitive data
// GV 21/02/2018
def noKeyFound = '"NO_KEY_FOUND"'
def theMovieDbApiKey = noKeyFound
def facebookAppId = noKeyFound
def gmailCredentialUsername = noKeyFound
def gmailCredentialPassword = noKeyFound

def sensitiveDataFile = file('../sensitive_data.txt')
def Properties sensitiveData = new Properties()

if (sensitiveDataFile.canRead()){
    // Read the sensitive data from file sensitive_data.txt
    sensitiveData.load(new FileInputStream(sensitiveDataFile))

    if (sensitiveData != null) {
        if (sensitiveData.containsKey('theMovieDbApiKey')) {
            theMovieDbApiKey = sensitiveData['theMovieDbApiKey']
        }
        if (sensitiveData.containsKey('facebookAppId')) {
            facebookAppId = sensitiveData['facebookAppId'] 
        }
        if (sensitiveData.containsKey('gmailCredentialUsername')) {
            gmailCredentialUsername =
                    sensitiveData['gmailCredentialUsername']
        }
        if (sensitiveData.containsKey('gmailCredentialPassword')) {
            gmailCredentialPassword =
                    sensitiveData['gmailCredentialPassword']
        }
    }
}

// In this section a reviewer can insert directly his own 
// sensitive data by replacing the strings beginning with REVIEWERS_
// Just double click on the string and paste you own data
if (theMovieDbApiKey == noKeyFound) {
    theMovieDbApiKey = '"REVIEWERS_THEMOVIEDB_API_KEY_GOES_HERE"'
}
if (facebookAppId == noKeyFound) {
    facebookAppId = '"REVIEWERS_FACEBOOK_APP_ID_GOES_HERE"'
}
if (gmailCredentialUsername == noKeyFound) {
    gmailCredentialUsername =
            '"REVIEWERS_GMAIL_CREDENTIAL_USERNAME_GOES_HERE"'
}
if (gmailCredentialPassword == noKeyFound) {
    gmailCredentialPassword =
            '"REVIEWERS_GMAIL_CREDENTIAL_PASSWORD_GOES_HERE"'
}
// End of reading the sensitive data

android {
    ...
```
> Hint: The reviewer can insert his sensitive data directly
> by replacing the **strings** beginning with REVIEWERS_ in the
> reviewer's section.

## Step 4 - Inject those values into your Android project

In the same file gradle.build (Module: app), inside of defaultConfig, the best practice is to add both variants, a Constant Field and a String Resource for the same key-value pair.

```java
android {
    defaultConfig {
        ...
        // Add the commands to inject your Constant Fields 
        // and the String Resources here
    }
}        
```

Example, for the key-value pair “theMovieDbApiKey” add a Constant Field with the name “THEMOVIEDB_API_KEY” and a String Resource with the same name

```java
// add a Constant Field
buildConfigField "String", "THEMOVIEDB_API_KEY", theMovieDbApiKey

// add a Resource String for the same key-value pair
resValue "string", "THEMOVIEDB_API_KEY", theMovieDbApiKey
```

All the Constant fields are stored as fields in a gradle generated BuildConfig class, the file BuildConfig.java should not be edited directly!

All the String Resources are stored in a gradle generated file, the file generated.xml should not be edited directly!

---

## Using your sensitive data via Constant Fields or String Resources anywhere in your Java code, layout, AndroidManifest, etc.

```java
// using the Constant field
// in the Java Code
theMovieDdOrgApiKeyC.setText(BuildConfig.THEMOVIEDB_API_KEY);

// using the String Resource
// in the Java Code
theMovieDdOrgApiKeyS.setText(getString(R.string.THEMOVIEDB_API_KEY));

// in the XML layout
android:text="@string/THEMOVIEDB_API_KEY"

// in the AndroidManifest
<meta-data
    android:name="THEMOVIEDB_API_KEY"          
    android:value="@string/THEMOVIEDB_API_KEY"/>
```

---
Download the project's file: [sensitive_data.txt](https://raw.githubusercontent.com/kalxasath/Sensitive%20Data%20Guide/master/assets/sensitive_data.txt) 

---
![Lastupdate](https://img.shields.io/badge/last%20update-on%2025%20February%202018-orange.svg)

> Written with [StackEdit](https://stackedit.io/).
