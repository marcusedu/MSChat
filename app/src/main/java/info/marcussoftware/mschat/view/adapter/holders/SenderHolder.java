package info.marcussoftware.mschat.view.adapter.holders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import info.marcussoftware.mschat.R;
import info.marcussoftware.mschat.util.DateUtil;
import info.marcussoftware.mschat.util.MSChatStyleHelper;
import info.marcussoftware.mschat.view.adapter.util.ChatWrapper;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 22/01/2018.
 */

public class SenderHolder extends ChatHolder {
    private View mView;
    private TextView mName;
    private TextView mMsg;
    private TextView mTime;

    public SenderHolder(View itemView) {
        super(itemView);
        mView = itemView.findViewById(R.id.balloon);
        mName = itemView.findViewById(R.id.userNameTextView);
        mMsg = itemView.findViewById(R.id.msgTextView);
        mTime = itemView.findViewById(R.id.timeTextView);
    }

    @Override
    public void bindData(ChatWrapper chatWrapper) {
        if (chatWrapper.getType() == ChatWrapper.WrapperType.SENDED_BY_ME) {
            mMsg.setText(chatWrapper.getMessage().getMessage());
            mTime.setText(DateUtil.formatTime(chatWrapper.getMessage().getDateTime()));
            mName.setText(chatWrapper.getMessage().getUserName());
        } else {
            Log.d(getClass().getSimpleName(), "bindData: Invalide Wrapper type");
        }
    }

    @Override
    public ChatHolder style(MSChatStyleHelper mStyleHelper) {
        mStyleHelper.styleSenderMessage(mView, mName, mMsg, mTime);
        return this;
    }
}
