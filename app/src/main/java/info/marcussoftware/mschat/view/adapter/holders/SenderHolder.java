package info.marcussoftware.mschat.view.adapter.holders;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    private ImageView mStatusIV;

    public SenderHolder(View itemView) {
        super(itemView);
        mView = itemView.findViewById(R.id.balloon);
        mName = itemView.findViewById(R.id.userNameTextView);
        mMsg = itemView.findViewById(R.id.msgTextView);
        mTime = itemView.findViewById(R.id.timeTextView);
        mStatusIV = itemView.findViewById(R.id.messageStatus);
    }

    @Override
    public void bindData(ChatWrapper chatWrapper) {
        if (chatWrapper.getType() == ChatWrapper.WrapperType.SENDED_BY_ME) {
            mMsg.setText(chatWrapper.getMessage().getMessage());
            mTime.setText(DateUtil.formatTime(chatWrapper.getMessage().getDateTime()));
            mName.setText(chatWrapper.getMessage().getUserName());
            switch (chatWrapper.getMessage().getStatus()) {
                case PENDING:
                    setIcon(R.drawable.ic_clock, R.color.gray80);
                    break;
                case ERROR:
                    setIcon(R.drawable.ic_error, R.color.error);
                    break;
                case SENDED:
                    setIcon(R.drawable.ic_check, R.color.gray80);
                    break;
                case RECEIVED:
                    setIcon(R.drawable.ic_check_all, R.color.sender_color);
                    break;
                case VISUALISED:
                    setIcon(R.drawable.ic_check_all, R.color.date_color);
                    break;
            }
        } else {
            Log.d(getClass().getSimpleName(), "bindData: Invalide Wrapper type");
        }
    }

    private void setIcon( int icRes, int color) {
        Drawable icon = mStatusIV.getContext().getResources().getDrawable(icRes);
        icon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        mStatusIV.setBackground(icon);
    }

    @Override
    public ChatHolder style(MSChatStyleHelper mStyleHelper) {
        mStyleHelper.styleSenderMessage(mView, mName, mMsg, mTime);
        return this;
    }
}
