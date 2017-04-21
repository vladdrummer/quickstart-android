# Mobile Center - Quickstart App for iOS
This sample app will help you onboard to Mobile Center Build, Analytics and Crashes services within few minutes.

# Steps:
1. Create an app in Mobile Center portal - Login to the [portal](https://mobile.azure.com/) and create an app {Add new} in Android
2. Download or clone this repo
3. Now you should fill in the APP_SECRET String from MainActivity.java in both Crash Sample and Analytics Sample projects with your actual app secret you've obtained from the Mobile Center and build the apps.
```java
public final static String APP_SECRET = "My App Secret";
```
4. Upon building the Crash Sample App you can
  - Choose an Error to Crash your App
  - Start your app over and watch through the entire Error Log from a crash report
  - Go to the [Mobile Center](https://mobile.azure.com/) and watch your crash logs there
5. Upon building the Analytics Sample App you can
  - Switch between the Screens and watch the events of opening a particular screen in [Mobile Center](https://mobile.azure.com/) 
  - On a first screen press a button  to register an event without parameters
  - On a second screen choose an item from a ListView to register an event with one parameter - you'll observe a result of your choice in an Analytics section of [Mobile Center](https://mobile.azure.com/) 
  - On a third screen you may fill the survey and see it's results in Analytics as well as some extra useful events like "User canceled filling the survey" or "User tried to complete the survey with an empty name"
