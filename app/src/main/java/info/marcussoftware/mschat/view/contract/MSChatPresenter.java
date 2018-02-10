package info.marcussoftware.mschat.view.contract;

import android.support.annotation.Nullable;

import java.util.Calendar;

import info.marcussoftware.mschat.BasePresenter;
import info.marcussoftware.mschat.interfaces.Message;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface MSChatPresenter extends BasePresenter {
    void userTyping(boolean typing, @Nullable String text);

    void userSendMessage(Calendar instance, String s);
}
