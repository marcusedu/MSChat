package info.marcussoftware.mschat.util;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import info.marcussoftware.mschat.R;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 22/02/2018.
 */

public class MSChatStyleHelper {
    private int mRecipientBackgroundColor, mSenderBackgroundColor, mDatetimeBackgroundColor;
    private Drawable mRecipientBackground, mSenderBackground, mDatetimeBackground, mTextAreaBackground;
    private int mSenderMessagePosition, mRecipientMessagePosition;
    private boolean mShowRecipientName, mShowSenderName;
    private final Context mContext;

    public MSChatStyleHelper(Context context) {
        mContext = context;
    }

    public MSChatStyleHelper setRecipientBackgroundColor(int mRecipientBackgroundColor) {
        this.mRecipientBackgroundColor = mRecipientBackgroundColor;
        return this;
    }

    public MSChatStyleHelper setSenderBackgroundColor(int mSenderBackgroundColor) {
        this.mSenderBackgroundColor = mSenderBackgroundColor;
        return this;
    }

    public MSChatStyleHelper setDatetimeBackgroundColor(int mDatetimeBackgroundColor) {
        this.mDatetimeBackgroundColor = mDatetimeBackgroundColor;
        return this;
    }

    public MSChatStyleHelper setRecipientBackground(@Nullable Drawable mRecipientBackground) {
        if (mRecipientBackground == null) {
            this.mRecipientBackground = mContext.getResources().getDrawable(R.drawable.background_recipient_message);
        } else {
            this.mRecipientBackground = mRecipientBackground;
        }
        return this;
    }

    public MSChatStyleHelper setSenderBackground(@Nullable Drawable mSenderBackground) {
        if (mSenderBackground == null) {
            this.mSenderBackground = mContext.getResources().getDrawable(R.drawable.background_sender_message);

        } else {
            this.mSenderBackground = mSenderBackground;
        }
        return this;
    }

    public MSChatStyleHelper setDatetimeBackground(@Nullable Drawable mDatetimeBackground) {
        if (mDatetimeBackground == null) {
            this.mDatetimeBackground = mContext.getResources().getDrawable(R.drawable.background_date);
        } else {
            this.mDatetimeBackground = mDatetimeBackground;
        }
        return this;
    }

    public MSChatStyleHelper setTextAreaBackground(@Nullable Drawable mTextAreaBackground) {
        this.mTextAreaBackground = mTextAreaBackground;
        return this;
    }

    public MSChatStyleHelper setSenderMessagePosition(int mSenderMessagePosition) {
        this.mSenderMessagePosition = mSenderMessagePosition;
        return this;
    }

    public MSChatStyleHelper setRecipientMessagePosition(int mRecipientMessagePosition) {
        this.mRecipientMessagePosition = mRecipientMessagePosition;
        return this;
    }

    public MSChatStyleHelper setShowRecipientName(boolean mShowRecipientName) {
        this.mShowRecipientName = mShowRecipientName;
        return this;
    }

    public MSChatStyleHelper setShowSenderName(boolean mShowSenderName) {
        this.mShowSenderName = mShowSenderName;
        return this;
    }

    public void styleDateTime(TextView mTextView) {
        Drawable temp = mDatetimeBackground.getConstantState().newDrawable();
        if (mDatetimeBackgroundColor != 0) {
            temp.setColorFilter(mDatetimeBackgroundColor, PorterDuff.Mode.SRC_ATOP);
        }
        mTextView.setBackground(temp);
    }

    public void styleSenderMessage(View mView, TextView mName, TextView mMsg, TextView mTime) {
        mName.setVisibility(mShowSenderName ? View.VISIBLE : View.GONE);
        Drawable temp = mSenderBackground.getConstantState().newDrawable();
        if (mSenderBackgroundColor != 0)
            temp.setColorFilter(mSenderBackgroundColor, PorterDuff.Mode.SRC_ATOP);
        mView.setBackground(temp);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = mSenderMessagePosition;
        mView.setLayoutParams(params);
    }

    public void styleRecipientMessage(View mView, TextView mName, TextView mMsg, TextView mTime) {
        mName.setVisibility(mShowRecipientName ? View.VISIBLE : View.GONE);
        Drawable temp = mRecipientBackground.getConstantState().newDrawable();
        if (mRecipientBackgroundColor != 0)
            temp.setColorFilter(mRecipientBackgroundColor, PorterDuff.Mode.SRC_ATOP);
        mView.setBackground(temp);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(-2, -2);
        params.gravity = mRecipientMessagePosition;
        mView.setLayoutParams(params);
    }
}
