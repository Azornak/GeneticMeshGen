/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

import java.util.Random;

/**
 *
 * @author JPiolho
 */
public class Parameters {
    public Random random;
    
    /**
     * How many of the top performing organisms to mutate and mate
     */
    public int evolutionElitismCount;
    /**
     * Always make sure the best one from the previous epoch moves on to the next without any changes
     */
    public boolean evolutionKeepBest;
    
    public float evolutionMutationChance;
    public float evolutionCrossoverChance;
    
    public int populationSize;
    
}
