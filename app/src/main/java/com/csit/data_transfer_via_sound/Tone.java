package com.csit.data_transfer_via_sound;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.effect.Effect;
import android.util.Log;

import static java.lang.Math.sin;

/**
 * Created by kaizoku on 21/11/2016.
 */

public class Tone {
    Thread t;
    boolean isRunning = false;
    String message;
    static final int samplingRate = 44100;
    static final float duration = 1f;
    static final int sample_size = Math.round(duration * samplingRate);

    public Tone() {
        this.message = "abcdefghijklmnopqrstuvwxyz";
    }

    Tone(String message) {
        this.message = message;
    }

    void playTone(){
        t = new Thread() {
            public void run(){
                isRunning = true;
                setPriority(Thread.MAX_PRIORITY);


                AudioTrack audioTrack = new AudioTrack(
                        AudioManager.STREAM_MUSIC, samplingRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, sample_size,
                        AudioTrack.MODE_STREAM);

                audioTrack.play();
                audioTrack.write(generateSineInTimeDomain(10240),0,sample_size/2);

                    for (int i = 0; i < message.length(); i++) {
                        int stepValue = ((int)message.charAt(i)-97) * 256;
                        int frequency = 1024 + stepValue;
                        short[] samples = generateSineInTimeDomain(frequency);
                        audioTrack.write(samples,0,sample_size/2);
                    }


                audioTrack.write(generateSineInTimeDomain(9216),0,sample_size/2);
                audioTrack.stop();
                audioTrack.release();
            }
        };

        t.start();

    }

    void stopTone(){
        isRunning = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = null;
    }



    private short[] generateSineInTimeDomain(float frequency) {
        short sample[] = new short[sample_size/2];
        for(int i = 0; i < sample.length; ++i) {
            float currentTime = (float)(i) / samplingRate;
            sample[i] = (short) (Short.MAX_VALUE * sin(Math.PI * 2 * frequency * currentTime));
        }
        return sample;
    }


}
