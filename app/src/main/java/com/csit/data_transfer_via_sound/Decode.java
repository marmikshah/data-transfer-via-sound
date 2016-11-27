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
 * This class helps to convert the frequency to a respected alphabet
 * @version 0.1
 * This class was used in our first build where the frequencies were limited to lowercase alphabets and space only
 * alphaBet : [ An array that hold all the variables to get the index ]
 * lowerBound : [ An array that will hold the lower bound of the frequency for a specific alphabet. It will be accessed by the index that is returned while searching for that alphabet in the alphaBet array ]
 * upperBound : [ An array that will hold the upper bound of the frequency for a specific alphabet. It will be accessed by the index that is returned while searching for that alphabet in the alphaBet array ]
 * frequency : The variable that stores frequency which is sent from the Record class.
 *
 *
 * @deprecated in this version
 *
 * **/

public class Decode {

    private char[] alphaBet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',' ','~'};
    private double[] lowerBound = {896,1152,1408,1664,1920,2176,2432,2688,2944,3200,3456,3712,3968,4224,4480,4736,4992,5248,5504,5760,6016,6272,6528,6784,7040,7296,7552,8400};
    private double[] upperBound = {1152,1408,1664,1920,2176,2432,2688,2944,3200,3456,3712,3968,4224,4480,4736,4992,5248,5504,5760,6016,6272,6528,6784,7040,7296,7552,7808,8500};
    private double frequency;

    Decode(double frequency) {
        this.frequency = frequency;
    }

    /** Maps the frequency to its specific lower and upper bound and then returns a character **/
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
