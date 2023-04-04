/**
 * This Java Class is part of the Impro-Visor Application.
 *
 * Copyright (C) 2017 Robert Keller and Harvey Mudd College.
 *
 * Impro-Visor is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version.
 *
 * Impro-Visor is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of merchantability or fitness
 * for a particular purpose. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Impro-Visor; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA
 */

package imp.cluster.motif;

import java.util.Comparator;

/**
 * @author Joseph Yaconelli
 */
public class MotifCountComparer implements Comparator<Motif> {

    /**
     * Compares the counts of two Motifs. Gives reverse ordering so a higher count
     * is considered an order closer to first in a PriorityQueue
     * @param m1 First Motif to compare
     * @param m2 Second Motif to compare
     * @return An integer compare of the negatives of the two counts
     * @see Motif
     */
    @Override
    public int compare(Motif m1, Motif m2) {
        int res = 0;
        
        if(m1 == null || m2 == null)
            throw new NullPointerException("One of the motifs was null");
        
        // negative numbers reverse ordering, so the min priority queue becomes a max priority queue
        return Integer.compare(-1*m1.getCount(), -1*m2.getCount());
               
    }
    
}
