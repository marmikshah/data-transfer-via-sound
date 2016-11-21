package com.csit.data_transfer_via_sound;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by kaizoku on 21/11/2016.
 */

public class Record {

    Thread r;
    private Boolean isRecording = false;
    public void startRecording(){
        System.out.print(isRecording);

        isRecording = true;
        r = new Thread(){

            public void run(){
                setPriority(Thread.MAX_PRIORITY);
                Log.d("","Is Recording now");
                AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT,AudioRecord.getMinBufferSize(44100,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT));
                short data[] = new short[AudioRecord.getMinBufferSize(44100,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT)];
                recorder.startRecording();
                while(isRecording) {
                    recorder.read(data, 0, data.length);
//                    for(int i = 0;i < data.length;i++) {
//
//                    }
                    if(data[0] > 0 ){
                        int c = (-data[0] + 32767)/100;
                        System.out.println((char)c);
                    }
                    if(data[0] < 0) {
                        int c = (data[0] - 32767)/100;
                        System.out.println((char)c);
                    }
                }
                recorder.stop();
                recorder.release();
            }
        };
        r.start();
    }

    public void stopRecording(){
        isRecording = false;
        try {
            r.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        r = null;
    }

}
