package tsp;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

class RecursivePowerKSet
{

    static public <E> Set<Set<E>> computeKPowerSet(final Set<E> source, final int k)
    {
        if (k==0 || source.size() < k) {
            Set<Set<E>> set = new HashSet<Set<E>>();
            set.add(Collections.EMPTY_SET);
            return set;
        }

        if (source.size() == k) {
            Set<Set<E>> set = new HashSet<Set<E>>();
            set.add(source);
            return set;
        }

        Set<Set<E>> toReturn = new HashSet<Set<E>>();

        // distinguish an element
        for(E element : source) {
            // compute source - element
            Set<E> relativeComplement = new HashSet<E>(source);
            relativeComplement.remove(element);

            // add the powerset of the complement
            Set<Set<E>> completementPowerSet = computeKPowerSet(relativeComplement,k-1);
            toReturn.addAll(withElement(completementPowerSet,element));
        }

        return toReturn;
    }

    /** Given a set of sets S_i and element k, return the set of sets {S_i U {k}} */
    static private <E> Set<Set<E>> withElement(final Set<Set<E>> source, E element)
    {

        Set<Set<E>> toReturn = new HashSet<Set<E>>();
        for (Set<E> setElement : source) {
            Set<E> withElementSet = new HashSet<E>(setElement);
            withElementSet.add(element);
            toReturn.add(withElementSet);
        }

        return toReturn;
    }

    public static void main(String[] args)
    {
        Set<String> source = new HashSet<String>();
        source.add("1");
        source.add("2");
        source.add("3");
        source.add("4");
        source.add("5");

        Set<Set<String>> powerset = computeKPowerSet(source,2);

        for (Set<String> set : powerset) {
            for (String item : set) {
                System.out.print(item+" ");
            }
            System.out.println();
        }
    }
}
