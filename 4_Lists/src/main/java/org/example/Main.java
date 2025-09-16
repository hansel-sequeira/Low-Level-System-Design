package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*

List interface
    -> LinkedList (which also implements Deque) // all methods of List and addFirst/addLast/
                                                    removeFirst/removeLast/peekFirst/peekLast

               // Collections.synchronizedList(new LinkedList<>())
               List<Integer> list = Collections.synchronizedList(new LinkedList<>());
               Can then, only use the List methods and not the Deque methods.

    -> Vector // existed before ArrayList. Is thread-safe.
        -> Stack // also thread-safe.

    -> ArrayList // Collections.synchronizedList(new ArrayList<>())

    -> why doesn't the normal iterator provide the add() method, when it has the remove() method?
            -> Because Iterator is meant for all Collection types (not Map!). For sets, priority queues, which are unordered, it doesn't make sense to have an add().

    certain methods in List: add(idx, element), addAll(idx, Collection), set(idx, element),
    indexOf(element), lastIndexOf(element), remove(idx / elt), sort(Comparator<T>),
    List<E> subList(fromIndex -> inclusive, toIndex -> exclusive)
    * Any change in sublist will change the main list too! Sublist is not a new list created in memory. It's a 'view' of the internal backing primary list.
        -> sublist doesn't create any list. Sublist is an inner class of List. When you do sublist(0, 10) -> internally, sublist calls the same
        get(), add() methods of the parentList, i.e ArrayList - it just does arithmetic computation for the offset (size, idx = endRange-startRage , etc).
 */


import java.util.*;

public class Main {
    public static void main(String[] args) {

        ArrayList<Integer> primaryList = new ArrayList<>(5); // no empty slots yet. Internally, Lists use Object[]/
        // defining the initialCap just makes sure the internal backing Object array has that many slots at the minimum.
        // but to iterate/set over the list, you need to prefill with null.

        Collections.synchronizedList(new ArrayList<>());
        ArrayList<Integer> arrayList = new ArrayList<>();
        for(int i=0;i<10;i++)
                arrayList.add(i);
        List<Integer> subList = arrayList.subList(0, 2); // returns List<> type and does not create a deep copy.
        subList.set(0, -100);
        arrayList.forEach(elt -> System.out.println(elt)); // this mutates the original array.

        arrayList.sort((Integer a, Integer b) -> a-b); // sort() is an exclusive method present in ArrayList which must take in a comparator.

        ListIterator<Integer> listIterator = arrayList.listIterator(); // -> returns an object of ListIterator. (extends Iterator which is an interface).
        /*
            ListIterator in addition to having Iterator's next(), hasNext(), remove(), also provides:
            previous(), hasPrevious(), add();
         */
        while(listIterator.hasNext()) {
            int val = listIterator.next();
            if(val == 3) {
                listIterator.add(333);
            }
        }
        listIterator = arrayList.listIterator(arrayList.size()); // backward iterator
        while(listIterator.hasPrevious()) {
            int val = listIterator.previous();
            if(val == 3) {
                listIterator.set(-3);
            }
        }
    }
}