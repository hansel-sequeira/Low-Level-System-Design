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

    -> For collections like Lists, Sets, Maps, etc, structural modifications while iterating using iterator or
      enhanced-for-each loop (iterator under the hood), will throw a CME.
    -> This is because the iterator implementation being used is fail-fast. The iterator keeps track of the modCount
        at each step.
    -> The safe way to go about it (synchronized block won't help!) is to invoke add/remove using the iterator
        methods. listIterator.add()/iterator.remove().
    -> The 'concurrent' in cme is not about concurrency/threads - it's about simultaneously structurally updating
        the collection whilst iterating over it.
    -> CME is a fail-fast mechanism to prevent unpredictable iteration behavior when the collection is
       structurally modified during iteration. add() can cause infinite loops if the iterator doesn’t know
        about new elements; remove() can invalidate the iterator’s internal cursor.
    -> is ConcurrentModificationException only thrown on add? What about set(idx, val)?
        If the collection is structurally modified while iterating (i.e., the modCount changes unexpectedly),
        the iterator throws ConcurrentModificationException.
        Structural modification = changing the size of the collection: add(E e), remove(Object o), clear()
        These increment modCount, which invalidates iterators.
 */
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        HashSet<Integer> hashSet = new HashSet<>();
        hashSet.add(4); // internally calls hashmap.put(4, new Object());
        Object o = new Object();

        LinkedHashSet<Integer> linkedHashSet = new LinkedHashSet<>();
        // thread safe set:
        Set<Integer> safeSet = Collections.synchronizedSet(new HashSet<>());
        // it is imperative to still have a monitor lock on the returned set while iterating
        // to prevent concurrentModification exceptions.
        synchronized (safeSet) {
            for (Integer i : safeSet) {
                System.out.println(i);
            }
        }

    }
}