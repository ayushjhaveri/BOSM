package bitspilani.bosm.notification;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by saksham on 19/9/17.
 */

public class NotificationID {
    private final static AtomicInteger c = new AtomicInteger(0);
    public static int getID() {
        return c.incrementAndGet();
    }
}
