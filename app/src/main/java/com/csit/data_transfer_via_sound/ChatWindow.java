package com.csit.data_transfer_via_sound;

import android.Manifest;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChatWindow extends AppCompatActivity {

    /** Booleans **/
    private boolean isRecording = false;
    private boolean isPlaying = false;

    /** Permission Variables **/
    private int readPermission;
    private int writePermission;
    private int recordPermission;

    /** String Variables **/

    /** Sound Generation & Recording **/
    Record recorder;
    Tone myTone;


    /** UI Objects **/
    Button record;
    Button play;

    EditText chatBox;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        //Record Button, allocateMemory and setListener
        record = getRecord();
        setRecordListener();
        //---------------------

        //Play Button, allocateMemory and setListener
        play = getPlay();
        setPlayListener();
        //---------------------

        //Chat Box, allocateMemory and setListener
        chatBox = getChatBox();

        //---------------------
        recorder = new Record();
        checkForPermissions();


    }



    /** Listeners & UI Setup **/
    // Record Button
    private Button getRecord(){
        return (Button)findViewById(R.id.record);
    }
    private void setRecordListener (){
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    isRecording = false;
                    record.setText("Stop");
                    recorder.stopRecording();
                } else {
                    isRecording = true;
                    record.setText("Start");
                    System.out.print(isRecording);
                    recorder.startRecording();
                }
            }
        });
    }

    //Play Button
    private Button getPlay() {
        return (Button)findViewById(R.id.playAudio);
    }
    private void setPlayListener(){
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlaying) {
                    isPlaying = true;
                    play.setText("Stop");
                    myTone = new Tone("hello");
                    myTone.playTone();
                } else {
                    myTone.stopTone();
                    play.setText("Play");
                    isPlaying = false;
                }
            }
        });
    }

    //Chat Box
    private EditText getChatBox(){
        return (EditText) findViewById(R.id.textView);
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

