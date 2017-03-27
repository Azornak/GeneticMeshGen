/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Vegard
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
    
    
    /**
     * Finish the epoch.
     * Create new population using crossover, mutation and elitism.
     */
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
        
        //Save the old population temporarily
        ArrayList<Organism> oldOrgs = (ArrayList<Organism>)organisms.clone();
        this.organisms.clear();
        
        // Always add the best one if parameter is enabled
        if(parameters.evolutionKeepBest) this.organisms.addAll(elite);
       
        // Populate the rest of the organisms with either mutated or mated ones
        while(this.organisms.size() < parameters.populationSize)
        {
            ArrayList<Organism> organismsToAdd = new ArrayList<>();
            
            //Do crossover
            if(parameters.random.nextFloat() < parameters.evolutionCrossoverChance) {
                Organism[] children = rankSelection(oldOrgs).crossover(rankSelection(oldOrgs));
                
                organismsToAdd.addAll(Arrays.asList(children));
            }
            //Add mutated organism
            if(parameters.random.nextFloat() < parameters.evolutionMutationChance && this.organisms.size() + organismsToAdd.size() < parameters.populationSize) {
                organismsToAdd.add(rankSelection(oldOrgs).mutate());
            }
            this.organisms.addAll(organismsToAdd);
            organismsToAdd.clear();
        }
        
        if(this.callback != null) {
            this.callback.finishedEvaluation(generation, bestOrganismLastGeneration);
        }
        
        generation++;
    }
    
    /**
     * Gets the next organism that should be validated/have it's fitness calculated
     * @return 
     */
    public Organism getNextValidationOrganism() {
        return this.organisms.get(curOrganism);
    }
    
    /**
     * Called when organism has been successfully validated
     * @param fitness 
     */
    public void finishValidationOrganism(float fitness) {
        this.organisms.get(curOrganism).setFitness(fitness);
        curOrganism++;
        
        if(curOrganism >= this.organisms.size()) {
            finishEpoch();
            curOrganism = 0;
        }
    }
    
    
    /**
     * Gets the best organism from the previous generation
     * @return The best organism
     */
    public Organism getBestOrganism() {
        return bestOrganismLastGeneration;
    }
    
    public int getGeneration() {
        return generation;
    }
    
    public Organism rankSelection(ArrayList<Organism> orgs){
        for(int i = 0; i < orgs.size(); i++){
            orgs.get(i).setWeight(1f / (float)i + 1f);
        }
        
        return chooseOnWeight(orgs);
    }

    /**
     * Choose a "random" with weight according to rank
     * @param items
     * @return The selected organism
     */
    public Organism chooseOnWeight(ArrayList<Organism> items) {
        double completeWeight = 0.0;
        for (Organism item : items)
            completeWeight += item.getWeight();
        double r = Math.random() * completeWeight;
        double countWeight = 0.0;
        for (Organism item : items) {
            countWeight += item.getWeight();
            if (countWeight >= r)
                return item;
        }
        throw new RuntimeException("Something went really wrong");
    }

    
}
