/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author JPiolho
 */
public class Population {
    private ArrayList<Organism> organisms;
    
    private Parameters parameters;
    
    private int generation;
    
    private Organism bestOrganismLastGeneration;
    
    private EvaluationCallback callback;
    
    private int curOrganism = 0;
    
    
    public Population(Parameters params,Organism template) {        
        this.parameters = params;
        
        template.setParameters(params);
        
        this.organisms = new ArrayList<>();
        for(int i=0;i<params.populationSize;i++) {
            this.organisms.add(template.mutate());
        }
        
        this.generation = 0;
    }
    
    public void setCallback(EvaluationCallback callback) {
        this.callback = callback;
    }
    
    
    private void finishEpoch() {
        // Sort by fitness
        organisms.sort(new Comparator<Organism>() {

            @Override
            public int compare(Organism t, Organism t1) {
                return Math.round(t1.getFitness() - t.getFitness());
            }
        });
        
       
        
        // Get the elite ones
        ArrayList<Organism> elite = new ArrayList<>();
        
        for(int i=0;i<parameters.evolutionElitismCount;i++) {
            elite.add(organisms.get(i));
        }
        
        bestOrganismLastGeneration = elite.get(0);
        
        this.organisms.clear();
        
        // Always add the best one if parameter is enabled
        if(parameters.evolutionKeepBest) this.organisms.add(elite.get(0).clone());
        
        // Populate the rest of the organisms with either mutated or mated ones
        while(this.organisms.size() < parameters.populationSize)
        {
            ArrayList<Organism> organismsToAdd = new ArrayList<>();
            
            if(parameters.random.nextFloat() < parameters.evolutionCrossoverChance) {
                Organism[] children = elite.get(parameters.random.nextInt(elite.size())).crossover(elite.get(parameters.random.nextInt(elite.size())));
                
                for(int i=0;i<children.length;i++)
                    organismsToAdd.add(children[i]);
            }
            
            for(int i=0;i<organismsToAdd.size() && this.organisms.size() < parameters.populationSize;i++) {
                if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                    this.organisms.add(organismsToAdd.get(i).mutate());
                } else {
                    this.organisms.add(organismsToAdd.get(i));
                }
            }
        }
        
        if(this.callback != null) {
            this.callback.finishedEvaluation(generation, bestOrganismLastGeneration);
        }
        
        generation++;
    }
    
    
    public Organism getNextValidationOrganism() {
        return this.organisms.get(curOrganism);
    }
    
    public void finishValidationOrganism(float fitness) {
        this.organisms.get(curOrganism).setFitness(fitness);
        curOrganism++;
        
        if(curOrganism >= this.organisms.size()) {
            finishEpoch();
            curOrganism = 0;
        }
    }
    
    
    
    public Organism getBestOrganism() {
        return bestOrganismLastGeneration;
    }
    
    public int getGeneration() {
        return generation;
    }
    
}
