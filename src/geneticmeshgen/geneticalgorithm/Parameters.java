/*
 * Copyright (C) 2017 Vegard Vatn
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package geneticmeshgen.geneticalgorithm;

import java.util.Random;

/**
 *
 * @author Vegard
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
    
    public boolean mutateVertexes, mutateUVs, mutateTexture;
    
}
