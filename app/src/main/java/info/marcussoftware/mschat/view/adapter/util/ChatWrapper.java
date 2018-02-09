package info.marcussoftware.mschat.view.adapter.util;

import java.util.Calendar;

import info.marcussoftware.mschat.interfaces.Message;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public class ChatWrapper {
    private WrapperType mWrapperType;
    private Calendar date;
    private Message message;

    public enum WrapperType {
        SENDED_BY_ME(2), SENDED_BY_OTHER(4), DATE(6);
        int myNum;

        WrapperType(int i) {
            myNum = i;
        }

        public int getValue() {
            return myNum;
        }
    }

    public WrapperType getType() {
        return this.mWrapperType;
    }

    private ChatWrapper() {
    }

    public WrapperType getmWrapperType() {
        return mWrapperType;
    }

    public Calendar getDate() {
        return date;
    }

    public Message getMessage() {
        return message;
    }

    private Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ChatWrapper mChatWrapper;

        public Builder() {
            mChatWrapper = new ChatWrapper();
        }

        public Builder setMyMessage(Message message) {
            if (mChatWrapper.mWrapperType == null) {
                mChatWrapper.mWrapperType = WrapperType.SENDED_BY_ME;
                mChatWrapper.message = message;
            } else {
                throw new IllegalStateException("");
            }
            return this;
        }

        public Builder setOtherMessage(Message message) {
            if (mChatWrapper.mWrapperType == null) {
                mChatWrapper.mWrapperType = WrapperType.SENDED_BY_OTHER;
                mChatWrapper.message = message;
            } else {
                throw new IllegalStateException("");
            }
            return this;
        }

        public Builder setOtherMessage(Calendar date) {
            if (mChatWrapper.mWrapperType == null) {
                mChatWrapper.mWrapperType = WrapperType.DATE;
                mChatWrapper.date = date;
            } else {
                throw new IllegalStateException("");
            }
            return this;
        }

        public ChatWrapper build() {
            ChatWrapper mChatWrapperTemp = mChatWrapper;
            mChatWrapper = new ChatWrapper();
            return mChatWrapperTemp;
        }
    }
}
