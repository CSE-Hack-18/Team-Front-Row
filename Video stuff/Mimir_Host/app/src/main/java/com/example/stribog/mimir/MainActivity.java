package com.example.stribog.mimir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.vidyo.VidyoClient.Connector.*;
import com.vidyo.VidyoClient.Device.Device;
import com.vidyo.VidyoClient.Device.LocalCamera;
import com.vidyo.VidyoClient.Device.RemoteCamera;
import com.vidyo.VidyoClient.Endpoint.Participant;

import static com.vidyo.VidyoClient.Connector.Connector.ConnectorDisconnectReason.VIDYO_CONNECTORDISCONNECTREASON_ConnectionTimeout;


public class MainActivity extends AppCompatActivity implements Connector.IConnect, Connector.IRegisterRemoteCameraEventListener {

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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String ourToken = "cHJvdmlzaW9uAEhvc3RAMjc3NmMxLnZpZHlvLmlvADYzNjkyODQ0MTMwAAAxZTMzMmJmZDIwYzdhNDlmOWVhZThjZDdkMGNjZGJiZmRlNTMyMzY0ZjMwNWI4NzI2MmJlYmIwZmVlNmY5YTM0MjdhZWU4YzFhNjk2MjJmMjkwODZjYmQwMDA4NTAwMDE=";
        vc.connect("prod.vidyo.io", ourToken, "Hostr", "DemoRoom", this);

    }

    public void Connect(View v) {
        String ourToken = "cHJvdmlzaW9uAEhvc3RAMjc3NmMxLnZpZHlvLmlvADYzNjkyODQ0MTMwAAAxZTMzMmJmZDIwYzdhNDlmOWVhZThjZDdkMGNjZGJiZmRlNTMyMzY0ZjMwNWI4NzI2MmJlYmIwZmVlNmY5YTM0MjdhZWU4YzFhNjk2MjJmMjkwODZjYmQwMDA4NTAwMDE=";
        vc.connect("prod.vidyo.io", ourToken, "Host", "DemoRoom", this);
    }

    public void Disconnect(View v){

                vc.disconnect();
    }
    @Override
    public void onSuccess() {
        // on successful connection:


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


    @Override
    public void onRemoteCameraAdded(RemoteCamera remoteCamera, Participant participant) {

    }

    @Override
    public void onRemoteCameraRemoved(RemoteCamera remoteCamera, Participant participant) {

    }

    @Override
    public void onRemoteCameraStateUpdated(RemoteCamera remoteCamera, Participant participant, Device.DeviceState deviceState) {

    }
}
