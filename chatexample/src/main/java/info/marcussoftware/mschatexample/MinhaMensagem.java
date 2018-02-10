package info.marcussoftware.mschatexample;

import java.util.Calendar;

import info.marcussoftware.mschat.interfaces.Message;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 09/02/2018.
 */

public class MinhaMensagem implements Message {
    private Calendar dateTime;
    private String userName, userId, message, messageId;

    public MinhaMensagem setDateTime(Calendar dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public MinhaMensagem setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public MinhaMensagem setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public MinhaMensagem setMessage(String message) {
        this.message = message;
        return this;
    }

    public MinhaMensagem setMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    @Override
    public Calendar getDateTime() {
        return dateTime;
    }

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessageId() {
        return messageId;
    }
}
