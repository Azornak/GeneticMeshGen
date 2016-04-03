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
public class OrganismTest extends Organism {
    
    private String string;
    
    public OrganismTest(Parameters params) {
        super(params);
        this.string = "";
    }
    
    
    public static OrganismTest template(int strlen) {
        OrganismTest org = new OrganismTest(null);
        
        for(int i=0;i<strlen;i++) {
            org.string += " ";
        }
        
        return org;
    }

    @Override
    public Organism mutate() {
        OrganismTest newOrg = this.clone();
        
        for(int i=0;i<newOrg.string.length();i++) {
            if(parameters.random.nextFloat() < parameters.evolutionMutationChance) {
                char c = newOrg.string.charAt(i);
                c += parameters.random.nextBoolean() ? -1 : 1;
                
                newOrg.string = newOrg.string.substring(0,i) + c + newOrg.string.substring(i+1);
            }
        }
        
        return newOrg;
    }
    
    public String getString() {
        return this.string;
    }
    
    
    public OrganismTest clone() {
        OrganismTest myClone = new OrganismTest(parameters);
        myClone.string = this.string;
        return myClone;
    }
    
    
    
}
