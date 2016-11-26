
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
    public StringBuffer message = new StringBuffer();
    FrequencyScanner frequencyScanner = new FrequencyScanner();
    private Boolean isRecording = false;
    public void startRecording(){
        System.out.print(isRecording);

        isRecording = true;
        r = new Thread(){

            public void run(){
                boolean hasBegun = false;
                setPriority(Thread.MAX_PRIORITY);
                Log.d("","Is Recording now");
                int minBufferSize = AudioRecord.getMinBufferSize(44100,AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT);
                AudioRecord recorder = new AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO,AudioFormat.ENCODING_PCM_16BIT, minBufferSize);
                short data[] = new short[minBufferSize];
                double frequency;
                recorder.startRecording();
                while(isRecording) {
                    recorder.read(data, 0, data.length);
                    if(data[0] != 0 ) {
                        frequency = frequencyScanner.extractFrequency(data, 44100);
                        Decode decoder = new Decode(frequency);
                        char alphabet = decoder.convertFrequencyToArray();
                        System.out.println(alphabet + "  :  " + frequency);
                        if(alphabet == '-') {

                        } else {
                            message.append(alphabet);
                        }
                    }
                }
                recorder.stop();
                recorder.release();
            }
        };
        r.start();
    }

    public String stopRecording(){
        isRecording = false;
        try {
            r.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        r = null;
        return message.toString();
    }
}
