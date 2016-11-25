
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
//                  -  for (int i=0;i<data.length;i++) {
//                        System.out.println(data[i]);
//                    }
//                    for(int i = 0;i < data.length;i++) {
//
//                    }
                    /*if(data[0] > 0 ){
                        int c = (-data[0] + 32767)/100;
                        System.out.println((char)c);
                    }
                    if(data[0] < 0) {
                        int c = (data[0] - 32767)/100;
                        System.out.println((char)c);
                    }*/
                    if(data[0] != 0 ) {
                        frequency = frequencyScanner.extractFrequency(data, 44100);
                        System.out.println((int)frequency + "      ");
                        Decode decoder = new Decode(frequency);
                        char alphabet = decoder.convertFrequencyToArray();
                        if(alphabet == '-') {

                        } else {
                            System.out.print(alphabet);
                        }
//                        if (hasBegun) {
//
//                        } else {
//                            if(frequency <= 10340 && frequency >= 10140  ) {
//                                hasBegun = true;
//                                System.out.print("Has Begun");
//                            }
//                            if(frequency >= 9126 && frequency <= 9316) {
//                                hasBegun = false;
//                                System.out.print("Message Over");
//                                stopRecording();
//                            }
//                        }
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
