/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geneticmeshgen.geneticalgorithm.custom;

import geneticmeshgen.geneticalgorithm.Organism;
import geneticmeshgen.geneticalgorithm.Parameters;
import geneticmeshgen.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author JPiolho
 */
public class OrganismMeshGen extends Organism {
    private ArrayList<Float> vertexes;
    private ArrayList<Float> uvs;
    private int[] texture;
    
    private float fitness;
    
    protected ParametersMeshGen parameters;
    
    
    public OrganismMeshGen(Parameters params) {
        super(params);
        this.parameters = (ParametersMeshGen)params;
        
        this.vertexes = new ArrayList<>();
        this.uvs = new ArrayList<>();
        
        
        this.texture = new int[parameters.organismTextureWidth * parameters.organismTextureHeight];
    }
    
    public OrganismMeshGen(OrganismMeshGen copy,Parameters params)
    {
        super(params);
        
        
    }
    
    public Float[] getVertexes() {
        return vertexes.toArray(new Float[vertexes.size()]);
    }
    
    public Float[] getUVs() {
        return uvs.toArray(new Float[uvs.size()]);
    }
    
    public int[] getTextureRGB() {
        return texture.clone();
    }

    @Override
    public Organism mutate() {
        OrganismMeshGen newOrg = this.clone();
        

        switch(parameters.random.nextInt(3)) {
            case 0: // Vertex
                int pos = parameters.random.nextInt(newOrg.vertexes.size());
                newOrg.vertexes.set(pos, newOrg.vertexes.get(pos) + ((parameters.random.nextFloat() * 2f - 1f) * parameters.organismVertexMutationRate));
                break;
            case 1: // UVs
                pos = parameters.random.nextInt(newOrg.uvs.size());
                newOrg.uvs.set(pos, newOrg.uvs.get(pos) + ((parameters.random.nextFloat() * 2f - 1f) * parameters.organismUVMutationRate));
                break;
            case 2: // Texture
                pos = parameters.random.nextInt(newOrg.texture.length);
                newOrg.texture[pos] = MathUtils.clamp(newOrg.texture[pos] + (parameters.random.nextInt(parameters.organismColorMutationRate*2) - parameters.organismColorMutationRate),0,Integer.MAX_VALUE);
                break;
        }
        
        return newOrg;
    }

    

    @Override
    public OrganismMeshGen clone() {
        OrganismMeshGen newOrg = new OrganismMeshGen(parameters);
        
        newOrg.vertexes = new ArrayList<>();
        for(int i=0;i<this.vertexes.size();i++) newOrg.vertexes.add(this.vertexes.get(i));
        
        newOrg.uvs = new ArrayList<>();
        for(int i=0;i<this.uvs.size();i++) newOrg.uvs.add(this.uvs.get(i));
        
        newOrg.texture = new int[parameters.organismTextureWidth * parameters.organismTextureHeight];
        for(int i=0;i<this.texture.length;i++) newOrg.texture[i] = this.texture[i];
        
        return newOrg;
    }
    
    
    
}
