package com.csit.data_transfer_via_sound;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

/**
 * Created by kaizoku on 21/11/2016.
 */

public class Tone {
    Thread t;
    int samplingRate = 44100;
    boolean isRunning = false;
    String message;

    public Tone() {
        this.message = "Hello World";
    }

    public Tone(String message) {
        this.message = message;
    }

    public void playTone(){
        t = new Thread() {
            public void run(){
                isRunning = true;
                setPriority(Thread.MAX_PRIORITY);
                int buffsize = AudioTrack.getMinBufferSize(samplingRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT);
                // create an audiotrack object
                AudioTrack audioTrack = new AudioTrack(
                        AudioManager.STREAM_MUSIC, samplingRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, buffsize,
                        AudioTrack.MODE_STREAM);

                short samples[] = new short[buffsize];
                int amp = 10000;
                double twopi = 8. * Math.atan(1.);
                double fr = 440.f;
                audioTrack.play();
                short c = (short)message.charAt(0);
                while (isRunning) {
                    for (int j = 0;j<message.length();j++){
                        c = (short)message.charAt(j);
                        double ph = 0.0;
                        int part = buffsize/message.length() * j;
                        for (int i = 0; i < buffsize; i++) {

                            short s = (short) (amp * Math.sin(ph));
                            if (s > 0.0) {
                                samples[i] = (short)(32767 - (c * 100));
                            }
                            if (s < 0.0) {
                                samples[i] = (short)(-32767 + (c * 100));
                            }
                            System.out.println(samples[i]);
                            ph += twopi * fr / samplingRate;
                        }
                    }
                    audioTrack.write(samples, 0, buffsize);
                }
                audioTrack.stop();
                audioTrack.release();
            }
        };
        t.start();
    }

    public void stopTone(){
        isRunning = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = null;
    }

}
