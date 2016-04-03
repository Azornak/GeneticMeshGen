/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.utils;

/**
 *
 * @author JPiolho
 */
public class MathUtils {
    public static int clamp(int value,int min,int max) {
        if(value < min) return min;
        if(value > max) return max;
        return value;
    }
    
    public static float clamp(float value,float min,float max) {
        if(value < min) return min;
        if(value > max) return max;
        return value;
    }
}
