package com.example.parstagram2;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.ParseException;
import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CREATED_KEY = "createdAt";
    public static final String KEY_PROFILE_PIC = "profilePicture";

    public String getDescription(){
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description){
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage(){
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public ParseFile getProfileImage(){ return getParseUser(KEY_USER).getParseFile(KEY_PROFILE_PIC);}

    public void setProfilePic(ParseFile parseImage){ put(KEY_PROFILE_PIC, parseImage);}

    public String getDate() throws ParseException {

        return TimeFormatter.getTimeDifference(getCreatedAt());
    }

    public void setDate(Date createdDate) { put(KEY_CREATED_KEY, createdDate); }
}
