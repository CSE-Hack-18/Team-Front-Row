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

        vc = new Connector(videoFrame, Connector.ConnectorViewStyle.VIDYO_CONNECTORVIEWSTYLE_Default, 0, "", "", 0);
        vc.showViewAt(videoFrame, 0, 0, videoFrame.getWidth(), videoFrame.getHeight());
        vc.cycleCamera();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ourToken = "cHJvdmlzaW9uAENhbGxlckAyNzc2YzEudmlkeW8uaW8ANjM2OTI4NDQyODEAADZiNGQzYzc5ZjE4ZTVjOWI3ZDNmMmU5Y2QwMWRkYzFiYTU1ZTAyYWY3YjYxMjZjNDRjY2I4OGRhZmQ1YTk5OTRjNWVmZDI5OTYxMjJiM2E3M2YwMWE3N2ZiOTMyNDBmYQ==";
        vc.connect("prod.vidyo.io", ourToken, "Caller", "DemoRoom", this);
    }

    public void Connect(View v) {
        //String ourToken = "cHJvdmlzaW9uAENhbGxlckAyNzc2YzEudmlkeW8uaW8ANjM2OTI4NDQyODEAADZiNGQzYzc5ZjE4ZTVjOWI3ZDNmMmU5Y2QwMWRkYzFiYTU1ZTAyYWY3YjYxMjZjNDRjY2I4OGRhZmQ1YTk5OTRjNWVmZDI5OTYxMjJiM2E3M2YwMWE3N2ZiOTMyNDBmYQ==";
        //vc.connect("prod.vidyo.io", ourToken, "Caller", "DemoRoom", this);
    }

    public void Disconnect(View v){
        vc.disconnect();
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onFailure(Connector.ConnectorFailReason connectorFailReason) {
        System.out.println("TIMED OUT TIMED OUT TIMED OUT ---------------------------------------------------------");
        if(connectorFailReason == Connector.ConnectorFailReason.VIDYO_CONNECTORFAILREASON_ConnectionTimeout){

            System.out.println("RECONNECTINGGGGGG    ---------------------------------------------------------");
            this.Connect(videoFrame);
        }
    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {

    }
}
