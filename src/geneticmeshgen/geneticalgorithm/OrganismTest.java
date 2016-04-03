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
        
        int pos = parameters.random.nextInt(newOrg.string.length());
        
        char c = newOrg.string.charAt(pos);
        c += parameters.random.nextBoolean() ? -1 : 1;
                
        newOrg.string = newOrg.string.substring(0,pos) + c + newOrg.string.substring(pos+1);
        
        return newOrg;
    }
    
    public Organism[] crossover(Organism other) {
        OrganismTest mate = (OrganismTest)other;

        OrganismTest a = this.clone();
        OrganismTest b = mate.clone();
        
        // 1 point crossover
        int max = Math.min(a.string.length(),b.string.length());
        int p = parameters.random.nextInt(max);
        
        
        a.string = this.string.substring(0,p) + mate.string.substring(p);
        b.string = mate.string.substring(0,p) + this.string.substring(p);
        
        
        return new Organism[] { a, b };
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
