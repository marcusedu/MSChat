package info.marcussoftware.mschat.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

import info.marcussoftware.mschat.R;
import info.marcussoftware.mschat.interfaces.Message;
import info.marcussoftware.mschat.interfaces.OnLoadMoreItemsListener;
import info.marcussoftware.mschat.util.AnimateUtil;
import info.marcussoftware.mschat.util.DateUtil;
import info.marcussoftware.mschat.util.MSChatStyleHelper;
import info.marcussoftware.mschat.view.adapter.ChatAdapter;
import info.marcussoftware.mschat.view.adapter.util.ChatWrapper;
import info.marcussoftware.mschat.view.contract.MSChatPresenter;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 21/01/2018.
 */

public class MSChatView extends FrameLayout implements info.marcussoftware.mschat.view.contract.MSChatView {
    private RecyclerView mRecyclerView;
    private TextView mDateTopIndicator;
    private EditText mMessageEditor;
    private ImageButton mSenderButton;
    private ChatAdapter mAdapter;
    private RecyclerView.OnScrollListener mScrollListener;
    private View mScrollCounter;
    private OnLoadMoreItemsListener mOnLoadMoreItemsListener;
    private Handler mHandler = new Handler(Looper.myLooper());
    private MSChatPresenter mPresenter;
    LinearLayoutManager llm = new LinearLayoutManager(getContext());
    private ImageView chatBackground;
    private Drawable mBackgroundDrawable;
    private int mBackgroundColor;
    private MSChatStyleHelper mStyleHelper;

    public MSChatView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        initAttributes(attrs);
    }

    public MSChatView(@NonNull Context context) {
        super(context);
        init();
    }

    public MSChatView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initAttributes(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MSChatView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
        initAttributes(attrs);
    }

    private void init() {
        inflate(getContext(), R.layout.ms_chat_layout, this);
        mStyleHelper = new MSChatStyleHelper(getContext());
        initViews();
        initRecyclerView();
    }

    private void initAttributes(AttributeSet attrs) {
        TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attrs,
                R.styleable.MSChatView,
                0, 0);
        try {
            mStyleHelper.setRecipientBackgroundColor(typedArray.getColor(R.styleable.MSChatView_recipientBackgroundColor, 0))
                    .setSenderBackgroundColor(typedArray.getColor(R.styleable.MSChatView_senderBackgroundColor, 0))
                    .setDatetimeBackgroundColor(typedArray.getColor(R.styleable.MSChatView_dateTimeBackgroundColor, 0))
                    .setRecipientBackground(typedArray.getDrawable(R.styleable.MSChatView_recipientBackground))
                    .setSenderBackground(typedArray.getDrawable(R.styleable.MSChatView_senderBackground))
                    .setDatetimeBackground(typedArray.getDrawable(R.styleable.MSChatView_dateTimeBackground))

                    .setTextAreaBackground(typedArray.getDrawable(R.styleable.MSChatView_textAreaBackground))

                    .setSenderMessagePosition(typedArray.getInteger(R.styleable.MSChatView_senderMessagePosition, 8388613))
                    .setRecipientMessagePosition(typedArray.getInteger(R.styleable.MSChatView_recipientMessagePosition, 8388611))

                    .setShowRecipientName(typedArray.getBoolean(R.styleable.MSChatView_showRecipientName, true))
                    .setShowSenderName(typedArray.getBoolean(R.styleable.MSChatView_showSenderName, false));
        } finally {
            typedArray.recycle();
        }
    }

    private void initViews() {
        mRecyclerView = findViewById(R.id.chatRecyclerView);
        mDateTopIndicator = findViewById(R.id.chatDateTopIndicator);
        mMessageEditor = findViewById(R.id.chatEditText);
        mSenderButton = findViewById(R.id.chatSendButton);
        mSenderButton.setOnClickListener(v -> {
            String msg = mMessageEditor.getText().toString();
            mMessageEditor.setText("");
            if (mPresenter != null)
                mPresenter.userSendMessage(Calendar.getInstance(), msg);
            else
                throw new IllegalStateException("You need implement and set MSChatPresenter!");
        });
        mScrollCounter = findViewById(R.id.scrollBottom);
        chatBackground = findViewById(R.id.chatBackground);
        if (mBackgroundDrawable != null || mBackgroundColor == 0) {
            chatBackground.setImageDrawable(mBackgroundDrawable);
            chatBackground.setBackgroundColor(mBackgroundColor);
        }

        registerUserTextInputListener(mMessageEditor);
    }

    private void initRecyclerView() {
        llm.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(llm);
        mRecyclerView.setAdapter(getAdapter());
        getAdapter().registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {

            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int msgs = getAdapter().getItemCount();
                int las = llm.findLastVisibleItemPosition();
                if (las == -1 ||
                        (positionStart >= (msgs - 1) && las == (positionStart - 1)))
                    mRecyclerView.scrollToPosition(positionStart);
            }
        });
        mRecyclerView.addOnScrollListener(getScrollListener());
    }

    private void showScrollBottomCounter(int count) {
//        TextView counter = mScrollCounter.findViewById(R.id.counter);
//        counter.setText(String.valueOf(count));
        if (mScrollCounter.getVisibility() != VISIBLE)
            AnimateUtil.animateShowView(mScrollCounter);
        mScrollCounter.setOnClickListener(v -> {
            mRecyclerView.scrollToPosition(getAdapter().getItemCount() - 1);
            AnimateUtil.animateHideView(mScrollCounter);
        });
    }

    private RecyclerView.OnScrollListener getScrollListener() {
        if (mScrollListener == null)
            mScrollListener = new RecyclerView.OnScrollListener() {
                private Runnable hideDateIndicator = () -> AnimateUtil.animateHideView(mDateTopIndicator);

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    if (mDateTopIndicator != null)
                        switch (newState) {
                            case RecyclerView.SCROLL_STATE_IDLE:
                                mHandler.postDelayed(hideDateIndicator, 3500);
                                break;
                            case RecyclerView.SCROLL_STATE_SETTLING:
                            case RecyclerView.SCROLL_STATE_DRAGGING:
                                if (mDateTopIndicator.getVisibility() != VISIBLE
                                        && !mDateTopIndicator.getText().toString().isEmpty()) {
                                    mHandler.removeCallbacks(hideDateIndicator);
                                    AnimateUtil.animateShowView(mDateTopIndicator);
                                }
                                break;
                        }
                }

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    if (!recyclerView.canScrollVertically(-1)) {
                        //List end top.
                        if (mOnLoadMoreItemsListener != null) {
                            mOnLoadMoreItemsListener.loadMoreItem(
                                    mAdapter.getMessageList().get(mAdapter.getItemCount() - 1).getMessage(),
                                    mAdapter.getItemCount());
                        }
                    } else if (!recyclerView.canScrollVertically(1)) {
                        //List end bottom
                    } else if (dy < 0 || dy > 0) {
                        //List scroll up ou down
                        updateDateTopIndicator();
                        if (llm.findLastVisibleItemPosition() < getAdapter().getItemCount())
                            showScrollBottomCounter(0);
                    }
                }
            };
        return mScrollListener;
    }

    private void updateDateTopIndicator() {
        int topPosition = llm.findFirstCompletelyVisibleItemPosition();
        ChatWrapper messageTop = mAdapter.getMessage(topPosition);
        if (messageTop.getType() != ChatWrapper.WrapperType.DATE) {
            mDateTopIndicator.setText(DateUtil.formatDate(messageTop.getMessage().getDateTime()));
        }
    }

    private ChatAdapter getAdapter() {
        if (mAdapter == null) mAdapter = new ChatAdapter(mStyleHelper);
        return mAdapter;
    }

    private void registerUserTextInputListener(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mPresenter != null)
                    mPresenter.userTyping(count > 0, s.toString());
                else
                    throw new IllegalStateException("You need implement and set MSChatPresenter!");
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

    @Override
    public void setPresenter(MSChatPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showMessage(Message message) {
        getAdapter().addMessage(message);
    }

    @Override
    public void showListMessageMerge(List<Message> messages) {
        getAdapter().addListMessage(messages);
    }

    @Override
    public void showListMessageReplace(List<Message> messages) {
        getAdapter().replaceMessage(messages);
    }

    @Override
    public void showError(@Nullable String messageError) {
        Toast.makeText(getContext(), messageError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(@Nullable int messageError) {
        Toast.makeText(getContext(), messageError, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setOnLoadMoreItemsListener(OnLoadMoreItemsListener onLoadMoreItemsListener) {
        this.mOnLoadMoreItemsListener = onLoadMoreItemsListener;
    }

    @Override
    public void setSenderUserID(String userId) {
        getAdapter().setMyUserId(userId);
    }

    @Override
    public void setBackground(Drawable background) {
        mBackgroundDrawable = background;
    }

    @Override
    public void setBackgroundColor(int color) {
        mBackgroundColor = color;
    }

    @Override
    public void setBackgroundResource(int resid) {
        mBackgroundDrawable = getResources().getDrawable(resid);
    }

    @Override
    public void setBackgroundDrawable(Drawable background) {
        mBackgroundDrawable = background;
    }
}