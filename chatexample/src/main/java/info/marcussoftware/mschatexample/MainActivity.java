package info.marcussoftware.mschatexample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Calendar;

import info.marcussoftware.mschat.view.contract.MSChatPresenter;
import info.marcussoftware.mschat.view.contract.MSChatView;

public class MainActivity extends AppCompatActivity implements MSChatPresenter {
    private MSChatView msChatView;
    private String myUserId = "12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msChatView = findViewById(R.id.mainMSChat);
        msChatView.setPresenter(this);
        msChatView.setSenderUserID(myUserId);
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void userTyping(boolean typing, @Nullable String text) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(typing ? "User typing.." : null);
        }
    }

    @Override
    public void userSendMessage(Calendar instance, String s) {
        MinhaMensagem message = new MinhaMensagem();
        msChatView.showMessage(message.setDateTime(instance).setMessage(s).setUserName("Marcus").setUserId(myUserId));
        msChatView.showMessage(message.setDateTime(instance).setMessage(s).setUserName("Cliente").setUserId("13"));
    }
}
