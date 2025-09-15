package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*

    LinkedHashMap extends HashMap and implements Map.
        -> maintains the insertion order OR access order.
        access order: most recently used element will be at the tail. Least recently used element at the head.
        -> uses a doubly linked list implementation.
        -> get() for insertionOrder directly invokes the get() of HashMap. For accessOrder, it's a custom implementation.
        -> Similarly for put(). The put() of the HashMap is invoked, after which doubly linked list links are handled in LinkedHashMap.

        Entry<K,V> for a LinkedHashMap structure:
        {
            K key;
            V value;
            EntryNode<K,V> next;
            EntryNode<K,V> before;
            EntryNode<K,V> after
        }

        In addition, it also makes sense for the enclosing LinkedHashMap class to store two entries - head and tail
        of type EntryNode -> (extends Map.Entry and adds before/after attributes)

        map.put(1, "A");
        map.put(21, "B");
        map.put(23, "C");
        map.put(141, "D");
        map.put(25, "E");

        1 <-> 21 <-> 23 <-> 141 <-> 25

        The first node (A) for an empty bucket: next, after, before : all null.
        Let's say node (B) hashes to the same bucket. A's next obviously points to B.
        In addition, A's after points to B and B's before points to A.

        If 'C' is slotted to a different bucket, the 'before' and 'after' of C/B will reference each other, but the 'next'
        of C will remain null.
        "Next" is only for the linear chain within the bucket - helps in knowing when the list ends for the bucket.
        "Before" and "After" are to generate the complete entry set in insertion order.

        -> LinkedHashMap() : accessOrder = false by default.
        -> LinkedHashMap(initialCapacity, loadFactor, accessOrder)

        if access order is true:
        map.put(1, "A");
        map.put(21, "B");
        map.put(23, "C");
        map.put(141, "D");
        map.put(25, "E");

        map.get(23);

         1 <-> 21 <-> 141 <-> 25 <-> 23

        -> TC is same as HashMap: linear time avg, worst case is n/log n.
        -> Not thread safe, there is no thread safe implementation available for this. You need to explicitly make
        the collection thread safe like this:
        Map<Integer,String> map = Collections.synchronizedMap(new LinkedHashMap<>());

        Collections.synchronizedMap() actually returns a custom Map in the Collections class which has all the standard
        map methods - except it is synchronized and under the hood invokes your base class's methods.
        Iteration still needs synchronization though!

        -> pollFirstEntry(), pollLastEntry(), firstEntry(), lastEntry(), reversed()

        TreeMap (extends from NavigableMap which extends from SortedMap which extends from Map)
            -> Map is sorted according to its natural ordering of its key or by the Comparator provided during map creation.
            -> The data structure used is a Red-black tree. Average time complexity: logn.

            Map<Integer,String> map1 = new TreeMap<>((Integer key1, Integer key2) -> key2-key1); // descending order
            map1.put(21, "SJ"); map1.put(11, "PJ"); map1.put(13, "KJ"); map1.put(5, "TJ");
            map1.forEach((Integer key, String value) -> System.out.println(key + ":" + value);
            21:SJ , 13:KJ, 11:PJ, 5:TJ

            Node structure:

            {
                left
                parent
                key, value
                right
            }

            -> is it fair to say that a TreeMap is literally just a balanced BST? Yes.

            Methods added in SortedMap:
            SortedMap<K,V> headMap(K toKey), SortedMap<K,V> tailMap(K fromKey), K firstKey(), K lastKey()

            map.headMap(13); -> returns a map from head to toKey (not-inclusive)
            map.tailMap(13); -> returns a map starting from fromKey (inclusive)
            map.firstKey(); map.lastKey();

            Methods added in NavigableMap:

            K lowerKey(K key) -> strictly lower. Similarly, K higherKey(K key)
            K floorKey(K key), Similarly floorEntry(K key)
            Map.Entry<K,V> lowerEntry(K key), Similarly higherEntry(K key)
            Map.Entry<K,V> ceilingEntry(K key) -> returns greater than or equal, Similarly ceilingKey(K key)

            pollFirstEntry(), pollLastEntry(), Map.Entry<K,V> firstEntry(), lastEntry(), reversed() -> returns a reversed map
            tailMap(fromKey, boolean inclusive), headMap(toKey, boolean inclusive)

 */


import java.util.*;


class Vehicle implements Comparable<Vehicle>{
    int vehicleID;

    public Vehicle(int vehicleID) {
        this.vehicleID = vehicleID;
    }

    public Vehicle(){}

    @Override
    public int compareTo(Vehicle o) {
        return 0;
    }
}

class Car extends Vehicle {}

class AscendingComparator implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle s1, Vehicle s2) {
        return s1.vehicleID- s2.vehicleID;
    }
}

public class Main {
    public static void main(String[] args) {
        /*

        Queue: add() returns true if successful, else exception. Null element insertion is not allowed.
               offer() returns true or false. Null element insertion is allowed.
               remove() returns NoSuchElementException if queue is empty
               poll() returns null if queue is empty.
               peek()

               PriorityQueue and ArrayDeque implements the Queue. (ArrayDeque implements Deque which extends Queue)

               The thread safe versions are ConcurrentLinkedDeque and PriorityBlockingQueue.
         */

        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.offerFirst(1); q.offerLast(24);
        q.peekFirst(); q.peekLast();
        q.pollFirst(); q.pollLast();
        q.push(2); q.pop(); // Push() and Pop() -> Stack operations are provided in Deque/ArrayDeque!

        /*
          @FunctionalInterface
          interface Consumer<T> {
            accept(T obj);
          }

          default method present in Iterable<T> forEach(Consumer<? super T> c) {
            for (T t : this) {
                c.accept(t);
            }
          }

          Iterable interface -> used to traverse the collection
          provides Iterator<T> iterator(), forEach(Consumer<T> c). Iterator is an interface.
          Iterator object returned by Iterable interface's iterator(), provides hasNext(), next(),
           remove() -> removes the last element returned by the iterator.


           Core Collection methods: isEmpty(), contains(), clear(), add(), remove(), addAll(Collection),
            removeAll(Collection), stream(), parallelStream(), iterator().

           Collections is a utility class -> static methods: sort(), reverse(), swap(), min(), max(), binarySearch()

         */

        Collections.max(q);
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.iterator();
        arrayList.remove(2); // removes at index 2.
        arrayList.remove(Integer.valueOf(2)); // removes the element;

        Iterator<Integer> iterator = q.iterator();
        while(iterator.hasNext()) {
            int val = iterator.next();
            if(val == 3)
                iterator.remove();
        }

        q.forEach((Integer a) -> {
            System.out.println(a);
        });

        Integer[] arr = {1, 2, 3, 4};
        Arrays.sort(arr, (Integer val1, Integer val2)->{
            return val2-val1; // negative swaps.
        });

        Vehicle[] vehiclesArr = new Vehicle[2];
        vehiclesArr[0] = new Vehicle(1);
        vehiclesArr[1] = new Vehicle(2);
        List<Vehicle> vehicleList = new ArrayList<>();

        Collections.sort(vehicleList); // if Vehicle doesn't implement Comparable, this throws a compile-time error.
        Collections.sort(vehicleList, new AscendingComparator());
        Arrays.sort(vehiclesArr); // This compiles, even if no Comparable. Runtime exception if no Comparable though.
        Arrays.sort(vehiclesArr, new AscendingComparator());

        /* Arrays.sort(Object[] arr) takes in an array, not a list.
           Collections.sort(List<T> list) literally creates an array out of the List: Object[] arr = list.toArray();
           Why is Collections.sort() smart enough to throw a compile time exception if no Comparable implementation? Why isn't Arrays.sort() doing that?
           Arrays.sort()'s signature expects an Object arr[] -> it has no generic bounds to check if something is a subtype of Comparable.

            But for Collections.sort(), this is the signature:   public static <T extends Comparable<? super T>> void sort(List<T> list)
            "The class T should be a subclass/class of some class that implements Comparable"

            If you look at Arrays.sort(T arr[], Comparator<? super T> c);
            why is the Comparator generic class taking in subclasses of T? Why not strictly T?

            <T extends Comparable<T>> : in generics, 'extends' means 'is a subType' which includes both inheritance and implementing an interface.
            <T extends Comparable<T>> means T must implement Comparable<T>

            class Child implements Comparable<Child> {}

            List<Child> list = new ArrayList<>();
            Collections.sort(list); // works if signature was <T extends Comparable<T>>


            class Parent implements Comparable<Parent> {}

            class Child extends Parent {
                // inherits compareTo(Parent)
            }

            List<Child> list = new ArrayList<>();
            Collections.sort(list);

            If the method used <T extends Comparable<T>>, this would not compile because Child does not implement Comparable<Child>.
            But with <T extends Comparable<? super T>>, it does compile because Child is comparable to its supertype (Parent). You can compare T or any class which is super to T.

         */

        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        treeMap.put(1, 2);
        treeMap.put(21, 4);
        treeMap.put(13, 6);

        /*
            -> Comparator vs Comparable
                : Comparator<T> is a functional interface. You can have various comparison strategies that implement Comparator and override the compare(T obj1, T obj2) method.
                  Comparable<T>, too, is a functional interface. If you want your custom type to have a standard sorting criteria - you use this. It provides compareTo(T other) method.
                  So, it is required that your custom class itself implement this functional interface.
                  If you plan on using the sort() from Collections -> you need to implement natural ordering strategy (using Comparable) for your custom class, or provide a Comparator strategy.
         */

        PriorityQueue<Integer> pq = new PriorityQueue<>((Integer a, Integer b) -> b-a); // default priority Queue is a min-heap
        pq.add(1); pq.add(2); pq.add(100);
        while(!pq.isEmpty()) {
            System.out.println(pq.poll());
        }

        LinkedHashMap<Integer, String> map = new LinkedHashMap<>();
        map.put(1, "A"); map.put(2, "B"); // this directly calls HashMap's put().

        LinkedHashMap<Integer, String> accessOrderMap = new LinkedHashMap<>(10, 0.75f, true);
        accessOrderMap.put(1, "ABC");

        for(Map.Entry<Integer, String> entry: map.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
        SortedMap<Integer, String> map2 = new TreeMap<>();
        map2.put(1, "A"); map2.put(2, "B"); map2.put(3, "C");
        map2 = map2.reversed();
        map2.forEach((Integer k, String v)->{
            System.out.println(k + " : " + v);
        });
        SortedMap<Integer, String> trimmedMap = map2.headMap(399);
        trimmedMap.forEach((Integer k, String v) -> { //  Map interface has default implementation: forEach(BiConsumer<? super K, ? super V> action)
            System.out.println(k + ":" + v);
        });
        NavigableMap<Integer, String> map3 = new TreeMap<>((Integer a, Integer b) -> a-b);
    }
}