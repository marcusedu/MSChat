package info.marcussoftware.mschat.interfaces;

import java.util.Calendar;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface Message<ID_TYPE> {
    Calendar getDateTime();

    String getUserName();

    ID_TYPE getUserId();

    String getMessage();

    ID_TYPE getMessageId();
}
