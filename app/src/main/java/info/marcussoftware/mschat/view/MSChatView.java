package info.marcussoftware.mschat.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import info.marcussoftware.mschat.R;
import info.marcussoftware.mschat.interfaces.Message;
import info.marcussoftware.mschat.util.AnimateUtil;
import info.marcussoftware.mschat.util.DateUtil;
import info.marcussoftware.mschat.view.adapter.ChatAdapter;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public class MSChatView extends View {
    private RecyclerView mRecyclerView;
    private TextView mDateTopIndicator;
    private EditText mMessageEditor;
    private ImageButton mSenderButton;
    private ChatAdapter mAdapter;
    private RecyclerView.OnScrollListener mScrollListener;
    private OnLoadMoreItemsListener mOnLoadMoreItemsListener;
    private Handler mHandler = new Handler(Looper.myLooper());

    public MSChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttributes(attrs);
        initViews(inflate(context, R.layout.ms_chat_layout, null));
        initRecyclerView();
    }

    private void initAttributes(AttributeSet attrs) {

    }

    private void initViews(View v) {
        mRecyclerView = v.findViewById(R.id.chatRecyclerView);
        mDateTopIndicator = v.findViewById(R.id.chatDateTopIndicator);
        mMessageEditor = v.findViewById(R.id.chatEditText);
        mSenderButton = v.findViewById(R.id.chatSendButton);

        registerUserTextInputListener(mMessageEditor);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(getAdapter());
        mRecyclerView.addOnScrollListener(getScrollListener());
    }

    private RecyclerView.OnScrollListener getScrollListener() {
        if (mScrollListener == null)
            mScrollListener = new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (mDateTopIndicator != null)
                        switch (newState) {
                            case RecyclerView.SCROLL_STATE_IDLE:
                                mHandler.postDelayed(
                                        () -> AnimateUtil.animateHideView(mDateTopIndicator),
                                        3500);
                            case RecyclerView.SCROLL_STATE_SETTLING:
                                AnimateUtil.animateShowView(mDateTopIndicator);
                            case RecyclerView.SCROLL_STATE_DRAGGING:
                                AnimateUtil.animateShowView(mDateTopIndicator);
                        }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!recyclerView.canScrollVertically(-1)) {
                        //List end top.
                        if (mOnLoadMoreItemsListener != null) {
                            mOnLoadMoreItemsListener.loadMoreItem(mAdapter.getMessageList().get(mAdapter.getItemCount() - 1), mAdapter.getItemCount());
                        }
                    } else if (!recyclerView.canScrollVertically(1)) {
                        //List end bottom
                    } else if (dy < 0 || dy > 0) {
                        //List scroll up ou down
                        updateDateTopIndicator();
                    }
                }
            };
        return mScrollListener;
    }

    private void updateDateTopIndicator() {
        LinearLayoutManager llm = (LinearLayoutManager) mRecyclerView.getLayoutManager();
        int topPosition = llm.findFirstCompletelyVisibleItemPosition();
        Message messageTop = mAdapter.getMessage(topPosition);
        Message messageOut = mAdapter
                .getMessage(mAdapter.getItemCount() == topPosition + 1 ? topPosition : topPosition + 1);
        if (!DateUtil.compareSameDay(messageTop.getDateTime(), messageOut.getDateTime())
                && mDateTopIndicator != null)
            mDateTopIndicator.setText(DateUtil.formatDate(messageTop.getDateTime()));
    }

    private RecyclerView.Adapter getAdapter() {
        if (mAdapter == null) mAdapter = new ChatAdapter();
        return mAdapter;
    }

    public void setOnLoadMoreItemListener(OnLoadMoreItemsListener onLoadMoreItemsListener) {
        this.mOnLoadMoreItemsListener = onLoadMoreItemsListener;
    }

    private void registerUserTextInputListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty()) {
                    mSenderButton.setEnabled(true);
                } else {
                    mSenderButton.setEnabled(false);
                }
            }
        });
    }

    interface OnLoadMoreItemsListener {
        void loadMoreItem(Message latestItem, int NumTotalItems);
    }
}
