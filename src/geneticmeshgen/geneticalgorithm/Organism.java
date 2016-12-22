/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

/**
 *
 * @author Vegard
 */
public class Organism {
    private float fitness;
    
    protected Parameters parameters;
    
    private float weight;
    
    protected Organism(Parameters parameters) {
        this.parameters = parameters;
    }
    
    public Organism mutate() { throw new UnsupportedOperationException(); }
    public Organism[] crossover(Organism mate) { throw new UnsupportedOperationException(); }
    public Organism clone() { throw new UnsupportedOperationException(); }

    public float getFitness() {
        return fitness;
    }
    
    void setFitness(float value) {
        fitness = value;
    }
    
    
    void setParameters(Parameters params) {
        parameters = params;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }
    
    
}
