package com.example.stribog.mimir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.vidyo.VidyoClient.Connector.*;


public class MainActivity extends AppCompatActivity implements Connector.IConnect {

    private Connector vc;
    private FrameLayout videoFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectorPkg.setApplicationUIContext(this);
        ConnectorPkg.initialize();
        videoFrame = (FrameLayout)findViewById(R.id.videoFrame);
    }

    public void Start(View v){
        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 16, "", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
    }

    public void Connect(View v) {
        String ourSexyAssToken = "cHJvdmlzaW9uAENhbGxlckAyNzc2YzEudmlkeW8uaW8ANjM2OTI3NDAyNzcAAGYyM2NhNTdiZWZkMzJlMzI3OTE2YTRjYzE2ODcyYjBkYmU2MDY2NGE5ZjY1MDE2Y2MyM2ZmMzIzNThiYzY1ZWU1N2E1NjJjZmU5ODFhYjY3MDc3Y2UzYjZmOWNkMDAyMw==";
        vc.connect("prod.vidyo.io", ourSexyAssToken, "Caller", "DemoRoom", this);
    }

    public void Disconnect(View v){
        vc.disconnect();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {

    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {

    }
}
