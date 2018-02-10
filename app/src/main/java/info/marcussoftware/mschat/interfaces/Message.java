package info.marcussoftware.mschat.interfaces;

import java.util.Calendar;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface Message {
    /**
     * @return A calendar with datetime that message has sended
     */
    Calendar getDateTime();

    /**
     * @return Name of user that sended the message
     */
    String getUserName();

    /**
     * @return Id of user that sended the message
     */
    String getUserId();

    /**
     * @return Body of message
     */
    String getMessage();

    /**
     * @return Id of message
     */
    String getMessageId();
}
