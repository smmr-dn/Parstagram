package com.example.parstagram2;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeFormatter {
    //Date createdDate
    public static String getTimeDifference(Date createdDate) throws ParseException {
        String time = "";
        //String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        String dateFormat = "MM/DD/yyyy";
        SimpleDateFormat format = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        format.setLenient(true);
        try {
            long diff = (System.currentTimeMillis() - createdDate.getTime()) / 1000;
            if (diff < 5)
                time = "Just now";
            else if (diff < 60)
                time = String.format(Locale.ENGLISH, "%d seconds ago", diff);
            else if (diff < 60 * 60)
                time = String.format(Locale.ENGLISH, "%d minutes ago", diff / 60);
            else if (diff < 60 * 60 * 24)
                time = String.format(Locale.ENGLISH, "%d hours ago", diff / (60 * 60));
            else if (diff < 60 * 60 * 24 * 30)
                time = String.format(Locale.ENGLISH, "%d days ago", diff / (60 * 60 * 24));
            else {
                Calendar now = Calendar.getInstance();
                Calendar then = Calendar.getInstance();
                createdDate = format.parse(dateFormat);
                then.setTime(createdDate);
                if (now.get(Calendar.YEAR) == then.get(Calendar.YEAR)) {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US);
                } else {
                    time = String.valueOf(then.get(Calendar.DAY_OF_MONTH)) + " "
                            + then.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US)
                            + " " + String.valueOf(then.get(Calendar.YEAR) - 2000);
                }
            }
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        return time;
    }

    /**
     * Given a date String of the format given by the Twitter API, returns a display-formatted
     * String of the absolute date of the form "30 Jun 16".
     * This, as of 2016-06-30, matches the behavior of the official Twitter app.
     */
    public static String getTimeStamp(String rawJsonDate) {
        String time = "";
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat format = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        format.setLenient(true);
        try {
            Calendar then = Calendar.getInstance();
            then.setTime(format.parse(rawJsonDate));
            Date date = then.getTime();

            SimpleDateFormat format1 = new SimpleDateFormat("h:mm a \u00b7 dd MMM yy");

            time = format1.format(date);

        }  catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}