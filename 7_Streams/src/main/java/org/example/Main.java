package org.example;


/*
    Invoking a stream on a collection creates a pipeline upon which various operations can be tacked on at different
    stages. The real power of streams is showcased when dealing with bulk/parallel processing of elements.

    step 1: create a stream out of the collection
    step 2: add intermediate operations (peek(), filter(), sorted(), map(), boxed(), distinct(), mapToInt(),
    limit(3) -> limit the max elements to first 3)

        -> the output of these operations is still a stream
        -> they are lazy in nature. they only get executed when the terminal operation is invoked.
    step 3: terminating operation (forEach(), toArray(), toList(), collect(), reduce(), count(),
     min()/max(), findFirst(), findAny(), anyMatch()

    -> No need to put data type in the lambda implementation.
    The original collection is not affected! You create a new collection out of the stream.

    -> whenever a terminal operation is invoked on a stream, that stream can't be reused later.

 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        // List.of() returns an immutable list type. You can add a max of 10 elements if you use .of().
        // No nulls allowed!
        List<Integer> salaryList = new ArrayList<>(List.of(3000, 4100, 9000, 1000, 3500)); // salaryList is mutable though!

       List<Integer> arrayList = Arrays.asList(1, 2, 3); // Arrays.asList(n number of args) returns a List<T>
        /*
        Arrays.asList() creates a fixed-size list backed by the original array.
        you can only mutate the existing elements via set(idx, elt), structural modifications like add(), remove()
        will throw an RTE. Nulls allowed.
         */

        Integer[] integerArray = {3000, 4100, 9000, 1000, 3500};
        // under the hood, compiler does integerArray = new Integer[4] and assigns the boxed value to the slots.
        // so the int array resides on the stack.
        // crucial functions when dealing with auto/unboxing: boxedObject.intValue(), Integer.valueOf(primitive).
        Integer xBoxed = 100;
        int x = xBoxed.intValue();
        Integer yBoxed = Integer.valueOf(x);

        int[] primitiveIntegerArray = {1, 2, 3};

        // To create a stream out of the array:
        long a = Arrays.stream(integerArray).filter(elt -> elt > 3000).count();
        long b = Arrays.stream(primitiveIntegerArray).filter(elt_a -> elt_a > 3000).count();
        long c = Arrays.stream(primitiveIntegerArray).boxed().filter(elt_a -> elt_a > 3000).count();

        List<Integer> collectorsList = Arrays.stream(primitiveIntegerArray).
                boxed()
                .filter(elt_a -> elt_a > 3000)
                .peek(element -> System.out.println(element))
                .collect(Collectors.toList());

        // reduce() produces an Optional<T> as output -> since there's no guarantee that the stream has atleast
       // min/max(Comparator<T>) also produces an Optional<T>
        // 2 elements to reduce!
        // peek() is an intermediate operation best used to peek at the intermediate state.
        // if you use forEach() which is terminating, it does not return anything!
        // toArray() if operating on a Stream<T> will return an Object[]/
        // toArray() if operating on a IntStream will return an integer[]
        // <..Stream<Integer..>.mapToInt(Integer::intValue).toArray() -> returns an int[]


        Set<Integer> immutableSet = Set.of(1, 2, 3); // Set.of() return type is an immutable set type.
        // to create a mutable set, do this:
        Set<Integer> mutableSet = new HashSet<>(Set.of(1, 2, 3));

        long count = salaryList.stream().filter(elt_b -> elt_b > 3000).count();
        Set<Integer> collectedSet = salaryList.stream().collect(Collectors.toSet());
        System.out.println("The count is: " + count);

        Stream<Integer> staticStream = Stream.of(1, 2);

        // flatMap when used once, reduces the dimension of nesting by 1.
        // flatMap takes each element (which is a collection) -> maps it to a stream -> does the same for all other
        // elements and flattens all those streams into one big continuous stream.
        // -> flatMap expects a function that returns a stream!

        List<List<Integer>> list2D = List.of(
                List.of(1, 2, 3),
                List.of(4, 5)
        );

        List<Integer> flattened2D = list2D.stream() // at this stage, creates a big stream where each element is a 1d list
                .flatMap(oneDList -> oneDList.stream()).toList();


        List<List<List<Integer>>> list3D = List.of(
                List.of(List.of(1, 2), List.of(3)),
                List.of(List.of(4, 5))
        );

        List<Integer> flattened3D = list3D.stream()
                .flatMap(twoDListElt -> twoDListElt.stream())  // at this point, I have a stream of a 2d list
                .flatMap(oneDListElt -> oneDListElt.stream()) // each elt was a 1D list
                .toList();


        List<String> sentences = List.of("hello world", "java streams");
        List<String> words = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split(" "))) // sentence.split() returns String[]
                .toList();

        System.out.println(words); // [hello, world, java, streams]

        int arr[] = {1, 2, 3};
        int[] sorted = Arrays.stream(arr).boxed()
                .sorted((p, q) -> q - p)
                .mapToInt(Integer::intValue) // stream of primitive int. returns IntStream
                .toArray(); // toArray on an InStream returns int[]. if we didnt have mapToInt(), the output of
            // sorted() is Stream<Integer> and toArray() on that will return array of Object[].


        // .toList() vs .collect(Collectors.toList()). The former produces an immutable collection.
        // The latter produces a mutable list.


        List<Integer> numbers = Arrays.asList(2, 1, 4, 7, 10, 12, 1, 2, 3, 4, 5, 6, 7,7 ,889, 9, 99, 9,9 );
        List<Integer> numbersList = numbers.stream()
                .filter(val -> val >= 3)
                .peek(val -> System.out.println("After filter: " + val))
                .map(val -> val*-1)
                .peek(val -> System.out.println("After negating: " + val))
                .sorted()
                .peek(val -> System.out.println("After sorting: " + val)).collect(Collectors.toList());

        // for intermediate operations whose input can be just a sole element, each element simply 'trickles' down
        // until it hits an operation that operates on a group.

        Optional<Integer> reducedNumber = numbersList.stream().reduce((intA, intB) -> intA+intB);

        Optional<Integer> minimum = numbers.stream().min((xy, yz) -> {
         return xy-yz;
        });

        boolean greaterThan1000 = numbers.stream().anyMatch(anyElt -> anyElt > 1000);
        // similarly, we have allMatch(), noneMatch()

      Optional<Integer> firstValueGreaterThan1000 = numbers.stream()
              .filter(number -> number > 1000)
              .findFirst();

      // findFirst(), findAny() takes in a predicate as the input and return an Optional.


      // Parallel Streams.

      /*
        Helps to perform multiple operations on streams concurrently, taking advantage of multiple CPU cores.
        parallelStream() in Collection is used rather than stream()
        => internally, it does (task splitting): forking the data into multiple smaller chunks
        and (task submission with parallel processing) -> uses fork-join-pool technique.

       */

      long sequentialProcessingTime = System.currentTimeMillis();
      numbers.stream().map(numA -> numA*numA).forEach(numA -> System.out.println(numA));
     System.out.println("Sequential processing time: " + (System.currentTimeMillis() - sequentialProcessingTime));


     long parallelProcessingTime = System.currentTimeMillis();
     numbers.parallelStream().map(numA -> numA*numA).forEach(numA -> System.out.println(numA));
     System.out.println("Parallel processing time: " + (System.currentTimeMillis() - parallelProcessingTime));

     // sequential sometimes takes lesser time than parallel processing.
     /*

      Parallel streams split the work across multiple threads using the ForkJoinPool.
      Steps include:
      1. Splitting the data into chunks.
      2. Scheduling tasks for multiple threads.
      3. Merging results after processing.

      For small datasets, this overhead can outweigh any benefit of parallelism.
      ArrayList → supports fast splitting for parallel streams.
      LinkedList → poor splitting, parallel stream often slower.
      */
    }
}