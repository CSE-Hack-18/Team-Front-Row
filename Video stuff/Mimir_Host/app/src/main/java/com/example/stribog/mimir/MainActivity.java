package com.example.stribog.mimir;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.vidyo.VidyoClient.Connector.*;
import com.vidyo.VidyoClient.Device.Device;
import com.vidyo.VidyoClient.Device.LocalCamera;


public class MainActivity extends AppCompatActivity implements Connector.IConnect, Connector.IRegisterLocalCameraEventListener {

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

    }

    @Override
    public void onDisconnected(Connector.ConnectorDisconnectReason connectorDisconnectReason) {

    }



    @Override
    public void onLocalCameraAdded(LocalCamera localCamera){

    }

    @Override
    public void onLocalCameraRemoved(LocalCamera localCamera) {

    }

    @Override
    public void onLocalCameraSelected(LocalCamera localCamera) {

    }

    @Override
    public void onLocalCameraStateUpdated(LocalCamera localCamera, Device.DeviceState deviceState) {

    }
}
