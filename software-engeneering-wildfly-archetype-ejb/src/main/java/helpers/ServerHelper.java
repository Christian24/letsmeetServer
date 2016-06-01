package helpers;

import java.util.Date;

/**
 * Created by Christian on 01.06.2016.
 * A helper class to store helpers used on the server
 */
public class ServerHelper {
    /**
     * Returns a unix timestamp from the date object
     * @param date
     * @return
     */
    public static long getUnixTimestamp(Date date) {
        return date.getTime() / 1000L;
    }

    /**
     * Gets a date from a unix timestamp
     * @param timestamp
     * @return
     */
    public static Date getDateFromUnixTimestamp(long timestamp) {

        return new Date(timestamp * 1000L);
    }
}
