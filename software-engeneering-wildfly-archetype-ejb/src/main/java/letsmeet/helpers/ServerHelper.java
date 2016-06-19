package letsmeet.helpers;

import java.util.Date;

/**
 * A helper class to store helpers used on the server
 * @author Christian
 */
public class ServerHelper {
    /**
     * Returns a unix timestamp from the date object
     * @param date
     * @return
     * @deprecated No longer in use
     */
    public static long getUnixTimestamp(Date date) {
        return date.getTime() / 1000L;
    }

    /**
     * Gets a date from a unix timestamp
     * @param timestamp
     * @return Date
     * @deprecated No longer in use
     */
    public static Date getDateFromUnixTimestamp(long timestamp) {

        return new Date(timestamp * 1000L);
    }
}
