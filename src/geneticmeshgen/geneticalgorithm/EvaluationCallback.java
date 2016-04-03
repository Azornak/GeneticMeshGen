/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm;

/**
 *
 * @author JPiolho
 */
public interface EvaluationCallback {
    void finishedEvaluation(int generation,Organism best);
}
