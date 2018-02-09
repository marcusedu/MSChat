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
    void showMessage(Message message);

    void showListMessageMerge(List<Message> messages);

    void showListMessageReplace(List<Message> messages);

    void showError(@Nullable String messageError);

    void showError(@Nullable int messageError);

    void setOnLoadMoreItemsListener(OnLoadMoreItemsListener onLoadMoreItemsListener);
}
