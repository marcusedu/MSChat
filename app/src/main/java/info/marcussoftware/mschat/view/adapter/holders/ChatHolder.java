package info.marcussoftware.mschat.view.adapter.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import info.marcussoftware.mschat.view.adapter.util.ChatWrapper;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 05/02/2018.
 */

public abstract class ChatHolder extends RecyclerView.ViewHolder {

    public ChatHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindData(ChatWrapper chatWrapper);
}
