package org.example;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
    Set (part of the Iterable/Collection family)
        -> HashSet: uses a HashMap
        -> LinkedHashSet: uses a LinkedHashMap (maintains the insertion order)
        -> SortedSet
            -> NavigableSet
                -> TreeSet : uses a TreeMap

    -> does not contain duplicate values, only one null value
    -> unordered*. In LinkedHashSet, we can have insertion (not access -> value passed is treated as dummy) order though.

    -> Iterable interface has iterator() which returns an instance of Iterator (.remove(), .next(), .hasNext() and
     forEach(Consumer). forEach iterates over all the elements calling the Consumer on each one.

    -> For collections like Lists, Sets, Maps, etc, structural modifications while iterating using iterator or
      enhanced-for-each loop (iterator under the hood), will throw a CME.
    -> This is because the iterator implementation being used is fail-fast. The iterator keeps track of the modCount
        at each step.
    -> The safe way to go about it (synchronized block won't help!) is to invoke add/remove using the iterator
        methods. listIterator.add()/iterator.remove().
    ->  Iterator does not have add() because it doesn't make sense for unordered collections which have no guaranteed insertion order -> like Sets.
        It makes no sense to say 'add here'.
    -> The 'concurrent' in cme is not about concurrency/threads - it's about simultaneously structurally updating
        the collection whilst iterating over it.
    -> If you modify the collection during iteration (outside of iterator.remove()), the iterator may behave unexpectedly:
        Skip elements, miss newly added elements
        CME is thrown to catch these bugs early, rather than let subtle, unpredictable behavior propagate.
    -> is ConcurrentModificationException only thrown on add? What about set(idx, val)?
        If the collection is structurally modified while iterating (i.e., the modCount changes unexpectedly),
        the iterator throws ConcurrentModificationException.
        Structural modification = changing the size of the collection: add(E e), remove(Object o), clear()
        These increment modCount, which invalidates iterators.
 */

public class Main {
    public static void main(String[] args) {
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(4); // internally calls hashmap.put(4, new Object());
        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        // thread safe set:
        Set<Integer> safeSet = Collections.synchronizedSet(new HashSet<>());
        // it is imperative to still have a monitor lock on the returned set while iterating to prevent cme
        // Under the hood, the enhanced-for loop calls safeSet.iterator()

        synchronized (safeSet) {
            for (Integer i : safeSet) {
                System.out.println(i);
                // even though safeSet is a synchronized wrapper, if you do safeSet.remove() it will throw a CME.
                // structural changes need to go through the iterator.
            }
        }

        //Special case: ConcurrentHashMap.newKeySet()
        //  If you use a concurrent collection instead:
        Set<Integer> concurrentSet = ConcurrentHashMap.newKeySet();
        concurrentSet.add(1);
        concurrentSet.add(2);
        for (Integer i : concurrentSet) {
            concurrentSet.remove(i); // no CME, iterator is weakly consistent
        }
//        Concurrent collections’ iterators are weakly consistent, not fail-fast.

        /*
        Java iterators don’t work like raw pointers In C/C++, an iterator might literally be a pointer into an array.
         If you shrink the array but keep using the pointer, you could wander into garbage memory.
        In Java, iterators are objects that wrap the collection and know how to traverse it safely. They never do something like "blindly increment a pointer into memory."

        Example with ArrayList
        List<Integer> list = new ArrayList<>(List.of(10, 20, 30, 40, 50));
        Iterator<Integer> it = list.iterator();
        list.remove(2); // remove "30"
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        The iterator was created with an expected modCount (modification count).
        When you call it.next(), it checks if the collection’s modCount still matches.
        Since you modified the list behind its back, modCount changed → iterator throws ConcurrentModificationException.
        It never blindly walks past the end; it just fails fast.

        Example with ConcurrentHashMap.newKeySet()
        Set<Integer> set = ConcurrentHashMap.newKeySet();
        set.add(1); set.add(2); set.add(3); set.add(4); set.add(5);

        Iterator<Integer> it = set.iterator();
        set.remove(3); // while iterating!
        while (it.hasNext()) {
            System.out.println(it.next());
        }

        Here, iterators are weakly consistent. That means the iterator just scans the internal table of the map as it exists when you walk it.
        If the element was removed before reaching it, you won’t see it.
        The iterator doesn’t keep a "count of 5 items" — it just walks bucket by bucket in the hash table.
        So, they will not try to read a “5th element” when only 4 exist — they either stop when there’s no more, or throw an exception.

         */

    }
}