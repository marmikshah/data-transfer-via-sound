package com.csit.data_transfer_via_sound;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;

public class ChatWindow extends AppCompatActivity {

    /** Booleans **/
    private boolean isRecording = false;

    /** Permission Variables **/
    private int readPermission;
    private int writePermission;
    private int recordPermission;

    /** String Variables **/
    String message = "Hello World, Marmik Shah";
    byte[] messageInByteArray = new byte[0];

    /** Sound Generation & Recordin **/
    Record recorder;



    /** UI Objects **/
    Button record;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //Record Button, allocateMemory and setListener
        record = getRecord();
        setRecordListener();
        //---------------------
        recorder = new Record();
        checkForPermissions();
        messageInByteArray = message.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream inputStream = new ByteArrayInputStream(messageInByteArray);
        Tone myTone = new Tone(message);
        myTone.playTone();
    }



    /** Listeners & UI Setup **/
    public Button getRecord(){
        return (Button)findViewById(R.id.record);
    }
    public void setRecordListener (){
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    isRecording = false;
                    recorder.stopRecording();
                } else {
                    isRecording = true;
                    recorder.startRecording();
                }
            }
        });
    }



    /** Permission Handing **/

    private void checkForPermissions(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == -1) getReadPermission();
        if ( ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == -1) getWritePermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == -1) getRecordPermission();
    }

    protected void getReadPermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},readPermission);
    }

    protected void getWritePermission(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},writePermission);
    }

    protected  void getRecordPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},recordPermission);
    }




}

