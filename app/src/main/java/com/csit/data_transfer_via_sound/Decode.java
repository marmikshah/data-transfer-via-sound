package com.csit.data_transfer_via_sound;

/**
 * Created by kaizoku on 25/11/2016.
 */

public class Decode {
    private char[] alphaBet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','~'};
    private double[] lowerBound = {896,1152,1408,1664,1920,2176,2432,2688,2944,3200,3456,3712,3968,4224,4480,4736,4992,5248,5504,5760,6016,6272,6528,6784,7040,7296,7552,8400};
    private double[] upperBound = {1152,1408,1664,1920,2176,2432,2688,2944,3200,3456,3712,3968,4224,4480,4736,4992,5248,5504,5760,6016,6272,6528,6784,7040,7296,7552,7808,8500};
    private double frequency;

    Decode(double frequency) {
        this.frequency = frequency;
    }

    char convertFrequencyToArray(){
        int alphaIndex = 0;
        for(int i = 0;i <lowerBound.length;i++) {
            if(frequency >= lowerBound[i] && frequency<=upperBound[i]){
                alphaIndex = i;
                break;
            } else {
                alphaIndex = -1;
            }
        }
        if(alphaIndex == -1) {
            return '-';
        } else {
            return alphaBet[alphaIndex];
        }
    }

}
