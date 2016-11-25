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
    int samplingRate = 44100;
    boolean isRunning = false;
    String message;

    public Tone() {
        this.message = "Hello World";
    }

    Tone(String message) {
        this.message = message;
    }

    void playTone(){
        t = new Thread() {
            public void run(){
                isRunning = true;
                setPriority(Thread.MAX_PRIORITY);
                int buffsize = AudioTrack.getMinBufferSize(samplingRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT) ;

                AudioTrack audioTrack = new AudioTrack(
                        AudioManager.STREAM_MUSIC, samplingRate,
                        AudioFormat.CHANNEL_OUT_MONO,
                        AudioFormat.ENCODING_PCM_16BIT, buffsize,
                        AudioTrack.MODE_STREAM);

                audioTrack.play();
                audioTrack.write(generateSineInTimeDomain(1,10240,1,buffsize),0,buffsize);

                    for (int i = 0; i < message.length(); i++) {
                        int stepValue = ((int)message.charAt(i)-97) * 256;
                        int frequency = 1024 + stepValue;
                        short[] samples = generateSineInTimeDomain(1,frequency,1,buffsize);
                        audioTrack.write(samples,0,buffsize);
                    }


                audioTrack.write(generateSineInTimeDomain(1,9216,1,buffsize),0,buffsize);
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



    private short[] generateSineInTimeDomain(float amplitude, float frequency, float duration, int bufferSize) {
        short sample[] = new short[bufferSize];
        int samplesToGenerate = (int)(duration * samplingRate);
        for(int i = 0; i < samplesToGenerate && i < sample.length; ++i) {
            float currentTime = (float)(i) / samplingRate;
            sample[i] = (short) (Short.MAX_VALUE * sin(Math.PI * 2 * frequency * currentTime));
        }
        return sample;
    }
}
