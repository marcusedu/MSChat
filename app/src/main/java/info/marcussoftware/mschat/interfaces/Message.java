package info.marcussoftware.mschat.interfaces;

import java.util.Calendar;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public abstract class Message {
    protected Calendar dateTime;
    protected String userName, userId, message, messageId;

    /**
     * Setter dateTime
     *
     * @param dateTime
     * @return
     */
    public Message setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    /**
     * Setter userName
     *
     * @param userName
     * @return
     */
    public Message setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    /**
     * Setter userId
     *
     * @param userId
     * @return
     */
    public Message setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    /**
     * Setter message
     *
     * @param message
     * @return
     */
    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    /**
     * Setter messageId
     *
     * @param messageId
     * @return
     */
    public Message setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    /**
     * @return A calendar with datetime that message has sended
     */
    public Calendar getDateTime() {
        return dateTime;
    }

    /**
     * @return Name of user that sended the message
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return Id of user that sended the message
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @return Body of message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @return Id of message
     */
    public String getMessageId() {
        return messageId;
    }
}
