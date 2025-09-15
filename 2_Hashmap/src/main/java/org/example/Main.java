package org.example;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*

-> why is Map not a child of Collection? Collection was centrally devised around lists (linear/grouped elements)
    Since a map is a key-value structure, the methods provided in Collection is not applicable to maps.

        Map (interface)
            -> Hashmap
            -> Hashtable
            -> LinkedHashMap

            ---> Interface SortedMap
                -> TreeMap

  -> core operations: put(K, V), get(K), remove(K), isEmpty(), size(), containsKey(K), containsValue(V),
                      getOrDefault(K, "def");
  -> putIfAbsent(K, V): if <K> doesn't exist or if K is mapped to null.
  -> both key and value can be null in a hashmap, not so in a hashtable.
  -> initial cap: 16. load factor: 0.75

  -> why does the key and value have to be an object, not a primitive? Because hashCode() is from the Object class.
     Also, most importantly, generics does not work with primitives, only object references. Why? mainly because of
     type erasure. Erasure expects everything to map to Object. Generic types only make sense up until
     compile time - List<String> : the compiler enforces that only String type can go into the list.
     But at runtime, there is no such 'generic' construct. JVM only sees a list and all generic type code is
     'erased'. So after erasure, it becomes like:

        List names = new ArrayList(); // raw type
        names.add("Alice");
        String s = (String) names.get(0); // returns object, cast back to String.

     Suppose generics allowed primitives:
        List<int> numbers = new ArrayList<>();
        numbers.add(5);

     After type erasure, the JVM would treat it as:
        List numbers = new ArrayList();
        numbers.add(5); // but this is incorrect, because 5 is not an Object type. Java instead autoboxes primitives.'

     List<Integer> arr1 = new ArrayList<Integer>();
     List arr2 = arr1; // at this stage, the compiler shuts off any generic type checking. It treats the List as pre-generics List that could hold any type.
     arr2.set(0, "Abc"); // this compiles fine.

     Java allows this behavior for backward compatibility with pre-generics code. Raw types bypass type checking.

     So, this too is allowed:
        List<EntryNode<Integer, String>> arr1 = new ArrayList<>();
        List arr2 = arr1; // at this stage, the compiler shuts off any generic type checking. It treats the List as pre-generics List that could hold any type.
        arr2.set(0, 1234); // this compiles fine.
        arr2.set(1, new EntryNode<>(123, 456));

  -> worst case will be log n due to treeify operation (treeify threshold for a bucket)

  -> for(Map.Entry<K,V> entryElement : customMap.entrySet()> {
    K key = entryElement.getKey();
    V value = entryElement.getValue();
  }

  -> for(K key: customMap.keySet()) {}
  -> for(V value: customMap.values()) {}

  -> collision resolution techniques:
     : linear chaining
     : open addressing: linear probing, quadratic probing. h(x) + Linear Fn.
  -> HashTable is thread safe unlike a hashMap. HashMap does not maintain the insertion order.
  -> HashTable also does not allow storage of null keys or null values. Why? NPE when calling hashCode on null key.
     Hashmap has a special handling, mapping null key to bucket 0. Null values - ambiguity whether value is absent or null.
     Hashtable predates Hashmap. Ambiguity is resolved in hashMap due to containsKey().
     Java 1.0's hashtable had no containsKey()
  -> difference between == and equals()?
        == checks if two references points to the same object in memory.
           In a primitive context, just checks for primitive value.
        .equals() -> checks if the value of the objects are equal: implementation dependent: method can be overridden.
        String a = new String("hi");
        String b = new String("hi");
        a.equals(b); // true.

        By default, if you don't override equals(), Object.equals() is used.
        public boolean equals(Object obj) {
            return this == obj;
        }

    contract between hashCode and equals?
        : if obj1 == obj2, their hashcode ought to be same.
        : if obj1's hash is same as obj2's hash, it need not mean that they're equal.
        : if you are overriding the equals() method, you must override the hashCode().
            -> so if you make two objects seemingly equal, their hashcode ought to be equal as well.
            -> what if you override with a shoddy implementation of hashCode(), or don't bother about overriding hashCode()?
                : This will cause issues when iterating in a hashMap.
                Example:
                equals() -> on the basis of the roll number
                hashCode() -> Object.hashCode() implementation or some random num generator.
                s1 = new Student("A); s2 = new Student("A")
                map.put(s1, "A") -> hashCode: 123
                map.put(s2, "A") -> hashCode: 456

                123 -> (s1, "A")
                456 -> (s2, "A")

                This is a violation of the basic invariant of a hashmap. Elements which are equal (equals() checks out),
                they need to be tied to the same bucket and same position in the bucket.

         Student {
            private int rollNo;

            @Override
            public boolean equals(Student o2) {return this.rollNo == o2.rollNo;}

            @Override
            public int hashCode() {
                return rollNo;
            }
         }
         Student s1 = new Student(1);
         Student s2 = new Student(1);
         // s1.equals(s2);
         // s1.hashCode() == s2.hashCode().



         Q) equals() always returns false. hashCode() always return 123;
         -> Student s1 = new Student("ABC");
            Student s2 = new Student("EFG");

            s1.equals(s1) => false;
            s1.equals(s2) => false;

            map.put(s1, "A");
            map.put(s2, "B);
            map.put(s1, "C);
            map.put(s1, "D");

            Output:
            Bucket 123: (s1, "A") -> (s2, "B") -> (s1, "C) -> (s1, "D")
 */


import java.util.*;

class CustomHashMap<K, V> {

    private static final int INITIAL_CAPACITY = (1 << 4);
    private static final int MAX_CAPACITY = Integer.MAX_VALUE;
    private int CURRENT_CAPACITY = INITIAL_CAPACITY; // this should not be static as other instances can get affected
    private static final double LOAD_FACTOR = 0.50;
    private int CURRENT_LOAD = 0; // this should not be static as other instances can get affected

    static class EntryNode<K, V> implements Map.Entry<K,V> { // It makes sense to make an inner class static if you want to create its instance outside the enclosing class
        // without having to instantiate the enclosing class. Also, because the inner class is static, it can only access the static outer fields.
        // This saves up a lot in memory.
        // Outer.Inner inner = new Outer.Inner();

        private final K key;
        private V value;
        private EntryNode<K,V> next;
        EntryNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
        public K getKey() {
            return key;
        }
        public V getValue() {
            return value;
        }
        public V setValue(V value) {
            this.value = value;
            return this.value;
        }
    }

    private List<EntryNode<K,V>> buckets;

    /* private EntryNode<K,V>[] entryNodes = new EntryNode<K,V>[100];
            -> arrays don't support generic type creation.

        This is allowed: private EntryNode<K,V>[] entryNodes = new EntryNode[100]; // raw type.
        The above statement compiles with an unchecked warning: compiler can't enforce type safety.

        // This would be problematic if allowed:
        EntryNode<String,Integer>[] array = new EntryNode<String, Integer>[10]; // let's say this were allowed.
        EntryNode[] objArray = array;
        objArray[0] = new EntryNode<Integer, Integer>(1, 2); // Runtime exception

         In a covariant system: Dog[] is  considered a subtype of Animal[]. You can assign a Dog[] instance to an Animal[] reference but not vice versa.

        Arrays are COVARIANT
        Dog[] dogs = new Dog[10];
        Animal[] animals = dogs;  // this compiles fine. But it can lead to runtime issues

        Invariant = If Dog is a subtype of Animal, then in an invariant system: You CANNOT assign a List<Dog> to a List<Animal> reference.

        Lists are INVARIANT
        List<Dog> dogs = new ArrayList<>();
        List<Animal> animals = dogs;  // compile time error

        Covariance can be dangerous:
        Dog[] dogs = new Dog[10];
        Animal[] animals = dogs;        // Legal because arrays are covariant
        animals[0] = new Cat();         // Runtime error! Can't put Cat in Dog array

        Invariance prevents this:
        List<Dog> dogs = new ArrayList<>();
        List<Animal> animals = dogs;    // Compilation error!

        Java arrays are covariant (dangerous with generics), while Java collections are invariant (safe).

        With collections, you'd have to explicitly break the rules using raw types:
        List<Dog> dogList = new ArrayList<>();
        List rawList = dogList;                          // Explicit raw type usage
        List<Animal> animalList = rawList;               // You're explicitly choosing danger
        With arrays, the danger would come from normal, legal array operations that you might not realize are dangerous.

        Main Goal: Prevent runtime exceptions when dealing with generics.

        Collections Solution:
        Made them invariant (not covariant) ✅
        Safe by default - runtime exceptions only if you explicitly use raw types ✅

        Arrays Problem:
        Pre-generics legacy - already covariant ✅
        If generics were allowed with arrays, you'd get runtime exceptions through normal, legal operations ✅

        Java's solution: "Rather than change how arrays work (breaking old code), just don't allow generic arrays at all."

        Assume A is the parent of B

        List<A> a = new ArrayList<>();
        List b = a;
        b.add(new A());
        b.add(new B());
        b.add(123); // these above lines compile fine

        List a = new ArrayList();
        List<B> b = a;
        b.add(new B());
        b.add(new A()); // this line fails compilation

        B[] b = new B[10];
        A[] a = b; // covariance: parent can reference the child.
        a[0] = new A();
        a[0] = new B(); // these above lines compile fine

        So why does everything post-generics, post-compilation still revolve around raw types?

        1: Raw Type Support for old .class Files
            -> New JVMs must support raw types to run existing pre-Java 5 .class files without modification
        2: Type Erasure Minimizes JVM Changes
            -> By erasing to raw types, the JVM runtime engine needed very few changes since it's still just dealing with the same old raw type bytecode

        Type erasure was an engineering compromise that achieved maximum compatibility with minimum JVM changes.
        The compiler does all the heavy lifting (type checking + cast insertion), while the runtime stays mostly the same.
    */

    CustomHashMap() {
        buckets = new ArrayList<>(INITIAL_CAPACITY);
        for(int i=0;i<INITIAL_CAPACITY;i++)
            buckets.add( null);

    }

    CustomHashMap(int initialCapacity) {
        CURRENT_CAPACITY = Math.max(CURRENT_CAPACITY, Math.min(initialCapacity, MAX_CAPACITY));
        buckets = new ArrayList<>(CURRENT_CAPACITY);
        for(int i=0;i<CURRENT_CAPACITY;i++)
            buckets.add(i, null);
    }

    private int getBucketIndex(K key) {
        return key.hashCode()%CURRENT_CAPACITY;
    }

    private void resize() {
        System.out.println("Calling resize");
        int newCapacity = Math.min((CURRENT_CAPACITY << 1), MAX_CAPACITY);
        List<EntryNode<K,V>> rehashedBuckets = new ArrayList<>(newCapacity);
        for(int i=0;i<newCapacity;i++)
            rehashedBuckets.add(null);
        for(EntryNode<K,V> entryNode : entrySet()) {
            K key = entryNode.getKey();
            entryNode.next = null; // this is important when adjusting links.
            int bucketIndex = key.hashCode()%newCapacity;
            EntryNode<K, V> iterator = rehashedBuckets.get(bucketIndex);
            if(iterator == null) {
                rehashedBuckets.set(bucketIndex, entryNode);
                continue;
            }
            while(iterator.next != null)
                iterator = iterator.next;
            iterator.next = entryNode;
        }
        CURRENT_CAPACITY = newCapacity;
        buckets = rehashedBuckets;
    }

    public V get(K key) {
        int bucketIndex = getBucketIndex(key);
        EntryNode<K,V> iterator = buckets.get(bucketIndex);
        while(iterator != null) {
            if (iterator.getKey().equals(key)) {
                return iterator.getValue();
            } else iterator = iterator.next;
        }
        return null;
    }

    public void put(K key, V value) {
        int bucketIndex = getBucketIndex(key);
        EntryNode<K, V> entryNode = new EntryNode<>(key, value);
        EntryNode<K, V> iterator = buckets.get(bucketIndex);
        if(iterator == null) {
            buckets.set(bucketIndex, entryNode);
            if(++CURRENT_LOAD >= LOAD_FACTOR*CURRENT_CAPACITY)
                resize();
            return;
        }
        if(iterator.getKey().equals(key)) {
            iterator.setValue(value);
            return;
        }
        while(iterator.next != null) {
            if(iterator.getKey().equals(key)) {
                iterator.setValue(value);
                return;
            } else iterator = iterator.next;
        }
        iterator.next = entryNode;
        if(++CURRENT_LOAD >= LOAD_FACTOR*CURRENT_CAPACITY)
            resize();
    }

    public Set<EntryNode<K,V>> entrySet() {
        Set<EntryNode<K,V>> entryNodeSet = new HashSet<>();
        for (EntryNode<K, V> bucket : buckets) {
            if (bucket == null)
                continue;
            EntryNode<K, V> iterator = bucket;
            while (iterator != null) {
                entryNodeSet.add(iterator);
                iterator = iterator.next;
            }
        }
        return entryNodeSet;
    }
}


class Outer<A> {
    class Inner<B> {

    }
}




public class Main {
    public static void main(String[] args) {
        CustomHashMap<String, Integer> customHashMap = new CustomHashMap<>();
        customHashMap.put("ABC", 123);
        customHashMap.put("XYZ", 678);
        customHashMap.put("LMNO", 456);

        /* In a third party class: to define the reference type of the inner class

            if the inner class were non-static:
            Outer outer = new Outer();   || Outer<K,V> outer = new Outer<>();
            Outer.Inner inner = outer.new Inner(); || Outer<K,V>.Inner<K,V> inner = outer.new Inner<>();

            Remember: if the inner class is non-static, it will reference the instance fields of the outer class. Hence, we instantiate it using the
            outer class instance.

            so, when referencing the instances of the inner object, you will use "Outer.Inner" || "Outer<K,V>.Inner<K,V> = innerObject.
            Why Outer<K,V>.Inner<K,V>? Why not just Outer.Inner<K,V>? Because Outer instances can be <A,B> or <C,D> and inner instances can be <X,Y>.
            In that case, what reference is the compiler supposed to tie the instance to? Ambiguity. This ambiguity won't be there if the inner class
            were static since the same inner class is referenced by all the outer class instances.

            for a static nested class: there is no need to instantiate the outer class to instantiate the inner static class
            Outer.Inner inner = new Outer.Inner(); || Outer.Inner<K,V> inner = new Outer.Inner<>();

            so, when referencing the instances of the inner object, you will use: "Outer.Inner" || "Outer.Inner<K,V" = innerObject.

         */

        for(CustomHashMap.EntryNode<String, Integer> entryNode: customHashMap.entrySet()) {
            // The above line could also be for(Map.Entry<String,Integer> entryNode, since EntryNode implements from Map.Entry
            System.out.println("Key: " + entryNode.getKey());
            System.out.println("Value: " + entryNode.getValue());
        }
    }
}