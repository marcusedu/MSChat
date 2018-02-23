package info.marcussoftware.mschatexample;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import info.marcussoftware.mschat.interfaces.Message;
import info.marcussoftware.mschat.view.contract.MSChatPresenter;
import info.marcussoftware.mschat.view.contract.MSChatView;

public class MainActivity extends AppCompatActivity implements MSChatPresenter {
    private MSChatView msChatView;
    private String myUserId = "12";
    private Handler mHandler = new Handler();
    private int msgId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msChatView = findViewById(R.id.mainMSChat);
        msChatView.setPresenter(this);
        msChatView.setSenderUserID(myUserId);
    }

    @Override
    public void userTyping(boolean typing, @Nullable String text) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(typing ? "I'm typing.." : null);
        }
    }

    @Override
    public void userSendMessage(Calendar dateMessage, String s) {
        MinhaMensagem message = new MinhaMensagem();
        message.setMessageId("abc" + msgId++)
                .setStatus(Message.Status.PENDING)
                .setDateTime(dateMessage)
                .setMessage(s)
                .setUserName("Marcus")
                .setUserId(myUserId);
        msChatView.showMessage(message);

        mHandler.postDelayed(() -> {
            message.setStatus(Message.Status.SENDED);
            msChatView.showMessage(message);
        }, 500);
        mHandler.postDelayed(() -> {
            message.setStatus(Message.Status.RECEIVED);
            msChatView.showMessage(message);
        }, 1500);
        mHandler.postDelayed(() -> {
            message.setStatus(Message.Status.VISUALISED);
            msChatView.showMessage(message);
        }, 2500);

        mHandler.postDelayed(() -> {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setSubtitle("User typing..");
            }
            mHandler.postDelayed(() -> {
                msChatView.showMessage(message.setMessageId("acc" + msgId++).setDateTime(dateMessage).setMessage(s).setUserName("Cliente").setUserId("13"));
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setSubtitle(null);
                }
            }, s.length() * 100);
        }, (((int) (Math.random() * 5)) * 1000) + 2500);
    }
}
