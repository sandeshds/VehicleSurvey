package com.sandesh.helpers;

import com.sandesh.constants.TimeConstants;

public class TimeParser {

    public static int parseMilliSecondFromInput(String input){
        try {
            int milliSeconds = Integer.parseInt(input.substring(1));
            if (milliSeconds < 0 )
                return -1;
            return milliSeconds;
        }catch (NumberFormatException e){
            System.out.println("Error parsing time from : " + input);
        }
        return -1;
    }

    public static double convertToHour(int milliseconds){
        if (milliseconds <0 )
            return -1;
        double minutesInHour = TimeConstants.MINUTES_IN_A_HOUR;
        double secondsInMinute = TimeConstants.SECONDS_IN_A_MINUTE;
        double milliSecondsInSecond = TimeConstants.MILLISECONDS_IN_A_SECOND;
        return ((double)milliseconds)/(milliSecondsInSecond * secondsInMinute * minutesInHour);
    }

}
