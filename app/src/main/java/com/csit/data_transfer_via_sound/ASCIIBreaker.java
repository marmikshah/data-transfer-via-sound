package com.csit.data_transfer_via_sound;

/**
 * Created by kaizoku on 26/11/2016.
 */

public class ASCIIBreaker {

    private int[] numbers = {0,1,2,3,4,5,6,7,8,9};
    private int lowerBound[] = {896, 1408, 1920, 2432, 2944, 3456, 3968, 4480, 4992, 5504};
    private int upperBound[] = {1152, 1664, 2176, 2688, 3200, 3712, 4224, 4736, 5248, 5760};
    private int frequency[] = {1024, 1536, 2048, 2560, 3072, 3584, 4096, 4608, 5120, 5632};
    private int ASCIIValue;

    public ASCIIBreaker(int ASCIIValue) {
        this.ASCIIValue = ASCIIValue;
        //System.out.println(this.ASCIIValue);
    }

    public ASCIIBreaker(){

    }

    public int[] ASCIIToFrequency(){
        int frequencies[] = new int[String.valueOf(ASCIIValue).length()*2];

        for(int i = 0;i<frequencies.length;i++) {
            if(i%2 == 0) {
                int digit = Character.getNumericValue(String.valueOf(ASCIIValue).charAt(i/2));
                //System.out.println("Digit :" +  digit);
                int index = indexOf(digit);
                if (index != -1)
                    frequencies[i] = frequency[index];
            } else {
                frequencies[i] = 9000;
            }

        }
        return frequencies;
    }

    public int frequencyToNumber(int frequency){
        for(int i = 0;i< lowerBound.length;i++) {
            if(frequency >= lowerBound[i] && frequency <= upperBound[i]){
                return numbers[i];
            }
        }
        return 0;
    }

    private int indexOf(int digit){
        for(int i = 0;i<numbers.length;i++){
            if(numbers[i] == digit) {
                //System.out.println(numbers[i] + " " + digit);
                return i;
            }
        }
        return -1;
    }

}
