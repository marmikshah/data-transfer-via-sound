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


/** Importing modules to generate and manage Audio **/
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/** Importing modules of Mathematical Functions **/
import static java.lang.Math.sin;


/**
 * Class Name -> Tone
 * The purpose of this class is to generate a tone
 * Methods ->
 * 1. playTone() --> Creates a Thread t which handles the audio player.
 * 2. stopTone() --> Destroys the music player thread.
 * 3. generateSineInTimeDomain() --> Generates a sine wave based on the frequency that has been passed.
 *
 * **/


public class Tone {

    /** General Data Types [Boolean, String, Float, Integer] **/
    // Boolean
    boolean isRunning = false;

    // String
    String message;

    // Float
    static final float duration = 0.5f;

    // Integer
    static final int samplingRate = 44100;
    static final int sample_size = Math.round(duration * samplingRate);

    /** Thread **/
    Thread t;

    AudioTrack audioTrack = new AudioTrack(
            AudioManager.STREAM_MUSIC, samplingRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT, sample_size,
            AudioTrack.MODE_STREAM);

    Tone(String message) {
        this.message = message;
    }



    /**
     *
     * The main function of this class that does the work
     * Flow of control
     * 1. Allocate memory to Thread t
     * ------Inside the thread run() definition function-----
     * 2. Create AudioTrack object with respected parameters [Stream Type, Sampling Rate, Channel Type, Encoding Type, Buffer Size (Sample Size), Mode]
     * 3. Call audioTrack.play()
     * 4. Initialize ASCIIBreaker object and send every character in the loop.
     * 5. Generate frequencies for every digit of the ASCIIValue of an alphabet. breaker.ASCIIFrequency() --> returns an array with frequencies
     *          i.e : "a" ASCIIValue -> 97 . Generate frequencies for [9,7] and return an array of integers that are the frequencies of the wave to be generated
     * 6. audioTrack.write() -> function that writes the sample value to audio buffer and plays it
     * 7. Generate a sine wave with 7000 as the frequency to denote that the character has ended (To decrease redundancy of characters)
     * 8. audioTrack.stop() once all the characters are encoded and played.
     * 9. Release the audioTrack object --> audioTrack.release();
     * ------Exited the thread run() definition function()
     * 10. start the thread t.start()
     *
     * **/

    void playTone(){
        t = new Thread() {
            public void run(){
                isRunning = true;
                setPriority(Thread.MAX_PRIORITY);



                audioTrack.play();

                    for (int i = 0; i < message.length(); i++) {

                        ASCIIBreaker breaker = new ASCIIBreaker((int)message.charAt(i));

                        int frequencies[] = breaker.ASCIIToFrequency();

                        //Generate waves for all different frequencies
                        for(int fre : frequencies) {
                            audioTrack.write(generateSineInTimeDomain(fre),0,sample_size);
                        }

                        audioTrack.write(generateSineInTimeDomain(7000),0,sample_size);
                    }

                audioTrack.stop();
                audioTrack.release();
            }
        };
        t.start();
    }


    /**
     * stopTone() --> The function just stop the audio playback and deallocates the thread.
     *  Flow of control
     *  1. Set the music flag to false [i.e. it has stopped]
     *  2. Try to join the thread and if any exception, do default steps
     *  3. Set t = null
     *  **/

    void stopTone(){
        t.interrupt();
        isRunning = false;
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t = null;
    }


    /**
     *
     * Generate the Sine Wave using frequency that has been passed as a parameter
     * This function has been taken from the assignment.
     *
     *  **/

    private short[] generateSineInTimeDomain(float frequency) {
        short sample[] = new short[sample_size];
        for(int i = 0; i < sample.length; ++i) {
            float currentTime = (float)(i) / samplingRate;
            sample[i] = (short) (Short.MAX_VALUE * sin(Math.PI * 2 * frequency * currentTime));
        }
        return sample;
    }
}
