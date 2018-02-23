package info.marcussoftware.mschat.view.adapter;

import android.support.annotation.NonNull;
import android.support.v4.util.LongSparseArray;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.marcussoftware.mschat.R;
import info.marcussoftware.mschat.interfaces.Message;
import info.marcussoftware.mschat.util.DateUtil;
import info.marcussoftware.mschat.util.MSChatStyleHelper;
import info.marcussoftware.mschat.view.adapter.holders.ChatHolder;
import info.marcussoftware.mschat.view.adapter.holders.DateHeaderHolder;
import info.marcussoftware.mschat.view.adapter.holders.RecipientHolder;
import info.marcussoftware.mschat.view.adapter.holders.SenderHolder;
import info.marcussoftware.mschat.view.adapter.util.ChatWrapper;
import info.marcussoftware.mschat.view.adapter.util.ChatWrapper.WrapperType;

import static info.marcussoftware.mschat.view.adapter.util.ChatWrapper.WrapperType.DATE;
import static info.marcussoftware.mschat.view.adapter.util.ChatWrapper.WrapperType.SENDED_BY_ME;
import static info.marcussoftware.mschat.view.adapter.util.ChatWrapper.WrapperType.SENDED_BY_OTHER;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {
    private final MSChatStyleHelper mStyleHelper;
    private SparseIntArray indexMessageSparseArray = new SparseIntArray();
    private ArrayList<ChatWrapper> messages = new ArrayList<>();
    private LongSparseArray<ArrayList<ChatWrapper>> mSparse = new LongSparseArray<>();
    private String myUserId;

    public ChatAdapter(MSChatStyleHelper msChatStyleHelper) {
        mStyleHelper = msChatStyleHelper;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getmWrapperType().getValue();
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == DATE.getValue())
            return new DateHeaderHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.item_date_header,
                            parent, false)).style(mStyleHelper);

        if (viewType == SENDED_BY_ME.getValue())
            return new SenderHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,
                            parent,
                            false)).style(mStyleHelper);

        if (viewType == SENDED_BY_OTHER.getValue())
            return new RecipientHolder(
                    LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout,
                            parent,
                            false)).style(mStyleHelper);

        return null;
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.bindData(messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public ArrayList<ChatWrapper> getMessageList() {
        return messages;
    }

    public ChatWrapper getMessage(int position) {
        if (position < 0) position = 0;
        return messages.get(position);
    }

    public void addMessage(Message message) {
        if (getMyUserId() == null) {
            Log.w("ChatAdapter", "addMessage: User id not set", new RuntimeException("You need set user id to show message sended by user correctly"));
        }
        ChatWrapper mWrapper;
        if (messages.isEmpty()
                || !DateUtil.compareSameDay(messages.get(messages.size() - 1).getDate(), message.getDateTime())) {
            mWrapper = processMessage(message, DATE);
            messages.add(mWrapper);
            notifyItemInserted(messages.indexOf(mWrapper));
        }
        if (message.getUserId().equals(myUserId))
            mWrapper = processMessage(message, SENDED_BY_ME);
        else
            mWrapper = processMessage(message, SENDED_BY_OTHER);
        int msgIndex = indexMessageSparseArray.get(mWrapper.getMessage().getMessageId().hashCode(), 0);
        if (msgIndex == 0) {
            messages.add(mWrapper);
            msgIndex = messages.indexOf(mWrapper);
            indexMessageSparseArray.put(mWrapper.getMessage().getMessageId().hashCode(), msgIndex);
            notifyItemInserted(indexMessageSparseArray.get(mWrapper.getMessage().getMessageId().hashCode()));
        } else {
            messages.get(msgIndex)
                    .getMessage()
                    .setMessage(message.getMessage())
                    .setStatus(message.getStatus());
            notifyItemChanged(msgIndex);
        }
    }

    @NonNull
    private ChatWrapper processMessage(@NonNull Message message, WrapperType type) {
        ChatWrapper.Builder mBuilder = new ChatWrapper.Builder();
        switch (type) {
            case SENDED_BY_ME:
                mBuilder.setMyMessage(message);
                break;
            case SENDED_BY_OTHER:
                mBuilder.setOtherMessage(message);
                break;
            case DATE:
                mBuilder.setDateMessage(message.getDateTime());
                break;
        }
        return mBuilder.build();
    }

    @NonNull
    private ArrayList<ChatWrapper> getChatWrapperList(@NonNull long dateKey) {
        ArrayList<ChatWrapper> list;
        if (mSparse.get(dateKey) == null) {
            list = new ArrayList<>();
            mSparse.put(dateKey, list);
        } else {
            list = mSparse.get(dateKey);
        }
        return list;
    }

    public void addListMessage(List<Message> messages) {
        this.messages.addAll(processMessages(messages));
        notifyDataSetChanged();
    }

    public void replaceMessage(List<Message> messages) {
        this.messages.clear();
        this.mSparse.clear();
        this.messages.addAll(processMessages(messages));
        notifyDataSetChanged();
    }

    private List<ChatWrapper> processMessages(List<Message> messageList) {
        if (getMyUserId() == null) {
            Log.w("ChatAdapter", "addMessage: User id not set", new RuntimeException("You need set user id to show message sended by user correctly"));
        }
        long datekey;
        ArrayList<ChatWrapper> chatWrappers = new ArrayList<>();
        for (Message message : messageList) {
            datekey = DateUtil.parseDateKey(message.getDateTime());
            if (getChatWrapperList(datekey).isEmpty()) {
                getChatWrapperList(datekey).add(0, processMessage(message, DATE));
            } else if (message.getUserId().equals(myUserId)) {
                getChatWrapperList(datekey).add(processMessage(message, SENDED_BY_ME));
            } else {
                getChatWrapperList(datekey).add(processMessage(message, SENDED_BY_OTHER));
            }
        }
        for (int i = 0; i < mSparse.size(); i++) {
            chatWrappers.addAll(mSparse.valueAt(i));
        }
        return chatWrappers;
    }

    public String getMyUserId() {
        return myUserId;
    }

    public ChatAdapter setMyUserId(String myUserId) {
        this.myUserId = myUserId;
        return this;
    }
}