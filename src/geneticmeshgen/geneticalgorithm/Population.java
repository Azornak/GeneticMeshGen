/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

import java.util.ArrayList;

/**
 *
 * @author JPiolho
 */
public class Population {
    private ArrayList<Organism> organisms;
    private ArrayList<Float> fitness;
    
    private Parameters parameters;
    
    private int generation;
    
    
    
    public Population(Parameters params) {
        this.parameters = params;
        
        this.organisms = new ArrayList<>();
        for(int i=0;i<params.populationSize;i++) {
            this.organisms.add(new Organism(params));
        }
    }
    
    
    public void setFitness() {
        
    }
}
