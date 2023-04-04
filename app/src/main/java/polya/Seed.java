/**
 * Polya library: Implements Lisp-like structures in Java.
 * <p>
 * Copyright (C) 2009 Robert Keller
 * <p>
 * Impro-Visor is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * <p>
 * Impro-Visor is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * merchantability or fitness for a particular purpose.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with Impro-Visor; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */

package polya;

/**
 * A Seed can grow into any other Object.
 **/

class Seed {
    Object value;


    /**
     * Create a Seed.
     *@arg growable the Growable from which the final value is produced.
     **/

    Seed(Growable growable) {
        this.value = growable;
    }

    /**
     * grow the seed, iterating as long as the result is a Growable,
     * returning the final value.
     **/

    Object grow() {
        while (value instanceof Growable) {
            value = ((Growable) value).grow();
        }
        return value;
    }
}
