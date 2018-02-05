package info.marcussoftware.mschat.interfaces;

import java.util.Calendar;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface Message {
    Calendar getDateTime();

    String getUserName();

    String getUserId();

    String getMessage();

    String getMessageId();
}
