package info.marcussoftware.mschat.view.adapter.holders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import info.marcussoftware.mschat.R;
import info.marcussoftware.mschat.util.DateUtil;
import info.marcussoftware.mschat.view.adapter.util.ChatWrapper;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 22/01/2018.
 */

public class DateHeaderHolder extends ChatHolder {
    private TextView mTextView;

    public DateHeaderHolder(View itemView) {
        super(itemView);
        this.mTextView = itemView.findViewById(R.id.dateTextView);
    }

    @Override
    public void bindData(ChatWrapper chatWrapper) {
        if (chatWrapper.getType() == ChatWrapper.WrapperType.DATE) {
            mTextView.setText(DateUtil.formatDate(chatWrapper.getDate()));
        } else {
            Log.d(getClass().getSimpleName(), "bindData: Invalide Wrapper type");
        }
    }
}
