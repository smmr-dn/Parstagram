package com.example.parstagram2;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Register your parse models
        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("psz8u0fQtiIZzPDaxUxqWfu1PySZY1IIOZqroFVR")
                .clientKey("jix6nyM41hkRsqMileoOHptjqYj6dX9WOJrvfz6x")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

