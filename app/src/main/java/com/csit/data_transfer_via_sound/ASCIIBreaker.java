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


/**
 *
 * @version 1.0
 * Current Build
 * This is the main class that handles the decoding
 * numbers : [ An array that hold the numbers from 0-9 ]
 * lowerBound : [ An array that will hold the lower bound of the frequency for a specific alphabet. It will be accessed by the index that is returned while searching for that alphabet in the alphaBet array ]
 * upperBound : [ An array that will hold the upper bound of the frequency for a specific alphabet. It will be accessed by the index that is returned while searching for that alphabet in the alphaBet array ]
 * ASCIIValue : an integer variable that stores the ASCII value that is passed while recording.
 *
 * **/

public class ASCIIBreaker {

    /** General Data Types [Boolean, String, Float, Integer] **/

    private int[] numbers = {0,1,2,3,4,5,6,7,8,9};
    private int lowerBound[] = {896, 1408, 1920, 2432, 2944, 3456, 3968, 4480, 4992, 5504};
    private int upperBound[] = {1152, 1664, 2176, 2688, 3200, 3712, 4224, 4736, 5248, 5760};
    private int frequency[] = {1024, 1536, 2048, 2560, 3072, 3584, 4096, 4608, 5120, 5632};
    private int ASCIIValue;

    ASCIIBreaker(int ASCIIValue) {
        this.ASCIIValue = ASCIIValue;
    }

    ASCIIBreaker(){

    }

    /**
     *
     * Encode Function
     * Create an array frequencies[] of length two times the length of the str(ASCIIValue)
     * Loop from [0,len(frequencies)]
     * If the value of i is even, then get the frequency[] value of the index (using indexOf() method defined below) and store it in the frequencies array.
     * If the value is odd, this indicates that a digit of the ASCIIValue has ended and hence load a pseudo frequency of 9000 that will help while decoding
     * @return frequencies[]
     * **/

    int[] ASCIIToFrequency(){
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


    /**
     *
     * Decode Function
     * Maps the frequency to its specific lower and upper bound and then returns digit from the numbers[] at index i
     * @return an integer between [0,9]
     *
     * **/

    int frequencyToNumber(int frequency){
        for(int i = 0;i< lowerBound.length;i++) {
            if(frequency >= lowerBound[i] && frequency <= upperBound[i]){
                return numbers[i];
            }
        }
        return 0;
    }

    /**
     *
     * A simple function to iterate through the numbers array and return the index if a match is found
     *
     * **/


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
