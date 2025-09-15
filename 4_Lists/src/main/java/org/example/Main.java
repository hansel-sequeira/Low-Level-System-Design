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

    certain methods in List: add(idx, element), addAll(idx, Collection), set(idx, element),
    indexOf(element), lastIndexOf(element), remove(idx / elt), sort(Comparator<T>),
    List<E> subList(fromIndex -> inclusive, toIndex -> exclusive)
    * Any change in sublist will change the main list too!
        -> WHY?
 */


import java.util.*;

public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.remove(2);
        arrayList.remove(Integer.valueOf(2));
        arrayList.sort((Integer a, Integer b) -> a-b);

        ListIterator<Integer> listIterator = arrayList.listIterator(); // -> returns an object of ListIterator.
        /*
            ListIterator in addition to having Iterator's next(), hasNext(), remove(), also provides:
            previous(), hasPrevious(), add;
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