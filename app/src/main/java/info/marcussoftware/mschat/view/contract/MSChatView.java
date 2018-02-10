package info.marcussoftware.mschat.view.contract;

import android.support.annotation.Nullable;

import java.util.List;

import info.marcussoftware.mschat.BaseView;
import info.marcussoftware.mschat.interfaces.Message;
import info.marcussoftware.mschat.interfaces.OnLoadMoreItemsListener;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public interface MSChatView extends BaseView<MSChatPresenter> {
    /**
     * Show messages in the order has inserted
     *
     * @param message Message to show in feed
     */
    void showMessage(Message message);

    /**
     * Show list of messages and merge with messages in the feed
     * The messages will be sorted before showed in feed
     *
     * @param messages List os messages to merge with messages in feed
     */
    void showListMessageMerge(List<Message> messages);

    /**
     * Show list of messages and clear feed before
     *
     * @param messages List of messages to show in feed
     */
    void showListMessageReplace(List<Message> messages);

    /**
     * Show a message error to user
     *
     * @param messageError Message of error
     */
    void showError(@Nullable String messageError);

    /**
     * Show a message error to user
     *
     * @param messageError Refer to message error in resources
     */
    void showError(@Nullable int messageError);

    /**
     * Set interface callback that be call when need load more items
     *
     * @param onLoadMoreItemsListener Implementation of interface
     */
    void setOnLoadMoreItemsListener(OnLoadMoreItemsListener onLoadMoreItemsListener);

    /**
     * Set user id to classify items sender message in chat feed
     *
     * @param userId The user that send message
     */
    void setSenderUserID(String userId);
}
