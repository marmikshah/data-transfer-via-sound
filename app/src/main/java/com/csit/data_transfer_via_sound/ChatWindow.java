package com.csit.data_transfer_via_sound;

/**
 *
 * Code Created and designed by students of CSIT 5110 course @ HKUST
 * [ Marmik Shah, Aninda Choudhary, Vladislav Raznoschikov ]
 * The source code is available at GitHub -->
 * https://github.com/marmikshah/Data-Transfer-Via-Sound/tree/master
 *
 * Comments Legend
 * [ ] --> Description of objects
 * Area comment --> a section of objects, functions, etc of the same type
 * // --> A specific description for the next line or next few lines of code, either inside a function or while declaration of an instance, var, etc.
 *
 *  **/

/** Importing modules of App Setup, Permission, Backward compatibility **/
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/** Import Modules of all the UI Controls [ View, Buttons, Labels, Text Boxes ] **/
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * Activity Main -> ChatWindow
 * This activity is the main and the only activity in the project.
 * When the application loads this activity, 4 UI Controls are shown on the screen.
 *
 * 1. Label [ The TextView object which displays a message when received ]
 * 2. Record Button [ The Button object which acts as a control to enable and disable recording ]
 * 3. Text Field [ The ExitText object which is used to type a message that has to be formatted and played ]
 * 4. Play Button [ The Button object that acts as a control to start playing the formatted text from the EditText Control ]
 *
 * **/

public class ChatWindow extends AppCompatActivity {

    /** General Data Types [String, Boolean] **/

    String message;
    // Boolean
    private boolean isRecording = false;
    private boolean isPlaying = false;

    /** Permission Variables **/
    private int recordPermission;

    /** String Variables **/

    /** Sound Generation & Recording [ Recorder Object, Tone Generator Object ] **/
    Record recorder;
    Tone myTone;

    /** UI Objects [ Record Button, Play Button ] **/
    Button record;
    Button play;

    /** Edit Text Object [ Message typing box ] **/
    EditText chatBox;

    /** Text View Object [ Label to display received message ] **/
    TextView messageBox;

    //-- Declarations End

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
        chatBox.setHint("Enter Message");

        //Message Box
        messageBox = getMessageBox();

        //Record object, allocateMemory.
        recorder = new Record();

        //Checking if the user has provided permissions to access the Device's recorder.
        checkForPermissions();
    }

    /** Listeners & UI Setup **/
    // Record Button
    // Get the button object from View -- >
    private Button getRecord(){
        return (Button)findViewById(R.id.record);
    }

    /**
     *
     * Assign an action event to the record button -->
     * Function :
     * 1. If it is recording currently, then set the isRecording boolean variable to false and change the text of this control to "Record"
     * 2. If it is recording currently, then deallocate the recording thread -> recorder.stopRecording(). Then set the String value returned from the stopRecording function to the value of the Label (TextView)
     * 3. If it is not recording, then set the isRecording boolean variable to true and change the text of this control to "Stop"
     * 4. If it is not recording, then allocate create a Thread t to start recording - > recorder.startRecording()
     *
     * **/
    private void setRecordListener (){
        record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isRecording) {
                    isRecording = false;
                    message  =  new String();
                    record.setText("Start");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    message = recorder.stopRecording();
                    messageBox.setText(message);
                } else {
                    isRecording = true;
                    messageBox.setText("");
                    messageBox.setHint("Receiving Message...");
                    record.setText("Stop");
                    try {
                        Thread.sleep(100);
                        recorder.startRecording();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    // Play Button
    // Get the button object from View -- >
    private Button getPlay() {
        return (Button)findViewById(R.id.playAudio);
    }

    /**
     *
     * Assign an action event to the record button -->
     * Function :
     * 1. If it is playing currently, then set the isPlaying boolean variable to false and change the text of this control to "Play"
     * 2. If it is playing currently, then deallocate the recording thread -> tone.stopTone()
     * 3. If it is not playing, then set the isRecording boolean variable to true and change the text of this control to "Stop"
     * 4. If it is not playing, then, get string from the EditText and pass it to the Tone constructor,
     *       and allocate create a Thread t to start playing - > tone.playTone().
     *
     * **/
    private void setPlayListener(){
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isPlaying) {
                    isPlaying = true;

                    play.setText("Stop");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    myTone = new Tone(chatBox.getText().toString());
                    myTone.playTone();

                } else {
                    isPlaying = false;
                    play.setText("Play");
                    try {
                        Thread.sleep(100);
                        try {
                            myTone.audioTrack.stop();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myTone.stopTone();
                }
            }
        });
    }

    // Chat Box
    // Get the EditText (Text Field to type the message) object from view -- >
    private EditText getChatBox(){
        return (EditText) findViewById(R.id.chatBox);
    }

    //Message Box
    // Get the TextView (Label to display a received message) object from view -- >
    private TextView getMessageBox(){ return (TextView)findViewById(R.id.messageBox); }

    /** Permission Handing **/
    private void checkForPermissions(){
        //Check if the application has permissions [ Recording audio ]. If it does not have permissions, the ask for permission -- > getRecordPermission();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == -1) getRecordPermission();
    }

    protected  void getRecordPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO},recordPermission);
    }

    /**
     *
     * This function decodes the final message which is received from another device.
     * Flow of control -->
     * 1. Create a new string buffer object (Because it is more easy to format a string using the string buffer class)
     * 2. Loop through the string's characters and remove all un-wanted characters ['~' : Used as a character separator, Redundant characters]
     * 3. Return the formatted StringBuffer object as a string (.toString())
     *
     *  **/

    @NonNull
    private String decodeString(){
        StringBuffer formattedMessage = new StringBuffer("");
        for(int i = 0;i<message.length();i++) {
            char alpha = message.charAt(i);
            char bet = alpha;
            int j = i;
            while(bet != '~' && j<message.length()){
                bet = message.charAt(j);
                j++;
            }
            i = j;
            formattedMessage.append(alpha);
        }
        return formattedMessage.toString();
    }
}