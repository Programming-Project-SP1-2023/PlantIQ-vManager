package com.plantiq.vsmarthomehub.services;

import java.time.Instant;
import java.util.Date;

public class TimeService {

    public static String StringFromTimeStamp(int timestamp){
        Instant instant = Instant.ofEpochSecond(timestamp);

        return Date.from(instant).toString();
    }
}
