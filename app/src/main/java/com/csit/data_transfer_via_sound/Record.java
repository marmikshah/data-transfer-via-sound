package com.csit.data_transfer_via_sound;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

/**
 * Created by kaizoku on 21/11/2016.
 */

public class Record {

    Thread t;
    private Boolean isRecording = false;
    public void startRecording(){
        isRecording = true;
        t = new Thread(){
            public void run(){
                AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT,AudioRecord.getMinBufferSize(44100,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT));
                short data[] = new short[AudioRecord.getMinBufferSize(44100,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)];
                recorder.startRecording();
                while(isRecording) {
                    recorder.read(data, 0, data.length);
                }
                recorder.stop();
                recorder.release();
            }
        };

    }

    public void stopRecording(){
        isRecording = false;
    }

}
