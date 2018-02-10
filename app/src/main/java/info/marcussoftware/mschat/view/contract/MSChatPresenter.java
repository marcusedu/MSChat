package info.marcussoftware.mschat.view.contract;

import android.support.annotation.Nullable;

import java.util.Calendar;

import info.marcussoftware.mschat.BasePresenter;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface MSChatPresenter extends BasePresenter {
    /**
     * Called every time user start or stop typing
     *
     * @param typing Indicate if exits some text in editor
     * @param text   Text is typing
     */
    void userTyping(boolean typing, @Nullable String text);

    /**
     * Called when user decide send a message
     *
     * @param dateMessage date time of message has send
     * @param message     message body
     */
    void userSendMessage(Calendar dateMessage, String message);
}
