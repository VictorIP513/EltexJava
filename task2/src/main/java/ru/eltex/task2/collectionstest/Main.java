package ru.eltex.task2.collectionstest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    private static final Random RANDOM = new Random();

    private static final int COLLECTION_SIZE = 100000;

    public static void main(String[] args) {
        testLists();
        testSets();
        testQueueAndDequeue();
        testVectorAndStack();
        testMaps();
    }

    private static void testLists() {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new LinkedList<>();

        LOGGER.info("Lists:");

        printElapsedTime(ArrayList.class, getElapsedTime(() -> testAddition(list1)), Operation.ADDITION);
        printElapsedTime(LinkedList.class, getElapsedTime(() -> testAddition(list2)), Operation.ADDITION);

        printElapsedTime(ArrayList.class, getElapsedTime(() -> testRemoveFirstElement(list1)), Operation.REMOVE_FIRST);
        printElapsedTime(LinkedList.class, getElapsedTime(() -> testRemoveFirstElement(list2)), Operation.REMOVE_FIRST);

        printElapsedTime(ArrayList.class, getElapsedTime(() -> testRemoveLastElement(list1)), Operation.REMOVE_LAST);
        printElapsedTime(LinkedList.class, getElapsedTime(() -> testRemoveLastElement(list2)), Operation.REMOVE_LAST);

        printElapsedTime(ArrayList.class, getElapsedTime(() -> testSearchInCollection(list1)), Operation.SEARCH);
        printElapsedTime(LinkedList.class, getElapsedTime(() -> testSearchInCollection(list2)), Operation.SEARCH);

        printElapsedTime(ArrayList.class, getElapsedTime(() -> testRandomSearchRandomValue(list1)), Operation.RANDOM_SEARCH);
        printElapsedTime(LinkedList.class, getElapsedTime(() -> testRandomSearchRandomValue(list2)), Operation.RANDOM_SEARCH);
    }

    private static void testSets() {
        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new TreeSet<>();
        Set<Integer> set3 = new LinkedHashSet<>();

        LOGGER.info("Sets:");

        printElapsedTime(HashSet.class, getElapsedTime(() -> testAddition(set1)), Operation.ADDITION);
        printElapsedTime(TreeSet.class, getElapsedTime(() -> testAddition(set2)), Operation.ADDITION);
        printElapsedTime(LinkedHashSet.class, getElapsedTime(() -> testAddition(set3)), Operation.ADDITION);

        printElapsedTime(HashSet.class, getElapsedTime(() -> testRemoveFirstElement(set1)), Operation.REMOVE_FIRST);
        printElapsedTime(TreeSet.class, getElapsedTime(() -> testRemoveFirstElement(set2)), Operation.REMOVE_FIRST);
        printElapsedTime(LinkedHashSet.class, getElapsedTime(() -> testRemoveFirstElement(set3)), Operation.REMOVE_FIRST);

        printElapsedTime(HashSet.class, getElapsedTime(() -> testRemoveLastElement(set1)), Operation.REMOVE_LAST);
        printElapsedTime(TreeSet.class, getElapsedTime(() -> testRemoveLastElement(set2)), Operation.REMOVE_LAST);
        printElapsedTime(LinkedHashSet.class, getElapsedTime(() -> testRemoveLastElement(set3)), Operation.REMOVE_LAST);

        printElapsedTime(HashSet.class, getElapsedTime(() -> testSearchInCollection(set1)), Operation.SEARCH);
        printElapsedTime(TreeSet.class, getElapsedTime(() -> testSearchInCollection(set2)), Operation.SEARCH);
        printElapsedTime(LinkedHashSet.class, getElapsedTime(() -> testSearchInCollection(set3)), Operation.SEARCH);

        printElapsedTime(HashSet.class, getElapsedTime(() -> testRandomSearchRandomValue(set1)), Operation.RANDOM_SEARCH);
        printElapsedTime(TreeSet.class, getElapsedTime(() -> testRandomSearchRandomValue(set2)), Operation.RANDOM_SEARCH);
        printElapsedTime(LinkedHashSet.class, getElapsedTime(() -> testRandomSearchRandomValue(set3)), Operation.RANDOM_SEARCH);
    }

    private static void testQueueAndDequeue() {
        Queue<Integer> queue = new PriorityQueue<>();
        Deque<Integer> deque = new ArrayDeque<>();

        LOGGER.info("Queue and Deque:");

        printElapsedTime(PriorityQueue.class, getElapsedTime(() -> testAddition(queue)), Operation.ADDITION);
        printElapsedTime(ArrayDeque.class, getElapsedTime(() -> testAddition(deque)), Operation.ADDITION);

        printElapsedTime(PriorityQueue.class, getElapsedTime(() -> testRemoveFirstElement(queue)), Operation.REMOVE_FIRST);
        printElapsedTime(ArrayDeque.class, getElapsedTime(() -> testRemoveFirstElement(deque)), Operation.REMOVE_FIRST);

        printElapsedTime(PriorityQueue.class, getElapsedTime(() -> testRemoveLastElement(queue)), Operation.REMOVE_LAST);
        printElapsedTime(ArrayDeque.class, getElapsedTime(() -> testRemoveLastElement(deque)), Operation.REMOVE_LAST);

        printElapsedTime(PriorityQueue.class, getElapsedTime(() -> testSearchInCollection(queue)), Operation.SEARCH);
        printElapsedTime(ArrayDeque.class, getElapsedTime(() -> testSearchInCollection(deque)), Operation.SEARCH);

        printElapsedTime(PriorityQueue.class, getElapsedTime(() -> testRandomSearchRandomValue(queue)), Operation.RANDOM_SEARCH);
        printElapsedTime(ArrayDeque.class, getElapsedTime(() -> testRandomSearchRandomValue(deque)), Operation.RANDOM_SEARCH);
    }

    @SuppressWarnings("squid:S1149") // suppress sonarLint synchronize collection warning
    private static void testVectorAndStack() {
        Vector<Integer> vector = new Vector<>();
        Stack<Integer> stack = new Stack<>();

        LOGGER.info("Vector and Stack:");

        printElapsedTime(Vector.class, getElapsedTime(() -> testAddition(vector)), Operation.ADDITION);
        printElapsedTime(Stack.class, getElapsedTime(() -> testAddition(stack)), Operation.ADDITION);

        printElapsedTime(Vector.class, getElapsedTime(() -> testRemoveFirstElement(vector)), Operation.REMOVE_FIRST);
        printElapsedTime(Stack.class, getElapsedTime(() -> testRemoveFirstElement(stack)), Operation.REMOVE_FIRST);

        printElapsedTime(Vector.class, getElapsedTime(() -> testRemoveLastElement(vector)), Operation.REMOVE_LAST);
        printElapsedTime(Stack.class, getElapsedTime(() -> testRemoveLastElement(stack)), Operation.REMOVE_LAST);

        printElapsedTime(Vector.class, getElapsedTime(() -> testSearchInCollection(vector)), Operation.SEARCH);
        printElapsedTime(Stack.class, getElapsedTime(() -> testSearchInCollection(stack)), Operation.SEARCH);

        printElapsedTime(Vector.class, getElapsedTime(() -> testRandomSearchRandomValue(vector)), Operation.RANDOM_SEARCH);
        printElapsedTime(Stack.class, getElapsedTime(() -> testRandomSearchRandomValue(stack)), Operation.RANDOM_SEARCH);
    }

    private static void testMaps() {
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new TreeMap<>();
        Map<Integer, Integer> map3 = new LinkedHashMap<>();
        Map<Integer, Integer> map4 = new WeakHashMap<>();
        Map<Integer, Integer> map5 = new IdentityHashMap<>();

        LOGGER.info("Maps:");

        printElapsedTime(HashMap.class, getElapsedTime(() -> testPutAtMap(map1)), Operation.MAP_PUT);
        printElapsedTime(TreeMap.class, getElapsedTime(() -> testPutAtMap(map2)), Operation.MAP_PUT);
        printElapsedTime(LinkedHashMap.class, getElapsedTime(() -> testPutAtMap(map3)), Operation.MAP_PUT);
        printElapsedTime(WeakHashMap.class, getElapsedTime(() -> testPutAtMap(map4)), Operation.MAP_PUT);
        printElapsedTime(IdentityHashMap.class, getElapsedTime(() -> testPutAtMap(map5)), Operation.MAP_PUT);

        printElapsedTime(HashMap.class, getElapsedTime(() -> testGetAtMap(map1)), Operation.MAP_GET);
        printElapsedTime(TreeMap.class, getElapsedTime(() -> testGetAtMap(map2)), Operation.MAP_GET);
        printElapsedTime(LinkedHashMap.class, getElapsedTime(() -> testGetAtMap(map3)), Operation.MAP_GET);
        printElapsedTime(WeakHashMap.class, getElapsedTime(() -> testGetAtMap(map4)), Operation.MAP_GET);
        printElapsedTime(IdentityHashMap.class, getElapsedTime(() -> testGetAtMap(map5)), Operation.MAP_GET);

        printElapsedTime(HashMap.class, getElapsedTime(() -> testRemoveAtMap(map1)), Operation.MAP_REMOVE);
        printElapsedTime(TreeMap.class, getElapsedTime(() -> testRemoveAtMap(map2)), Operation.MAP_REMOVE);
        printElapsedTime(LinkedHashMap.class, getElapsedTime(() -> testRemoveAtMap(map3)), Operation.MAP_REMOVE);
        printElapsedTime(WeakHashMap.class, getElapsedTime(() -> testRemoveAtMap(map4)), Operation.MAP_REMOVE);
        printElapsedTime(IdentityHashMap.class, getElapsedTime(() -> testRemoveAtMap(map5)), Operation.MAP_REMOVE);
    }

    private static void testAddition(Collection<Integer> collection) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            collection.add(i);
        }
    }

    private static void testRemoveFirstElement(Collection<Integer> collection) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            collection.remove(i);
        }
    }

    private static void testRemoveLastElement(Collection<Integer> collection) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            collection.remove(COLLECTION_SIZE - i + 1);
        }
    }

    private static void testSearchInCollection(Collection<Integer> collection) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            //noinspection ResultOfMethodCallIgnored
            collection.contains(COLLECTION_SIZE - i + 1);
        }
    }

    private static void testRandomSearchRandomValue(Collection<Integer> collection) {
        List<Integer> values = new ArrayList<>();
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            values.add(i);
        }
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            int randomValue = RANDOM.nextInt(COLLECTION_SIZE - i);
            //noinspection ResultOfMethodCallIgnored
            collection.contains(values.remove(randomValue));
        }
    }

    private static void testPutAtMap(Map<Integer, Integer> map) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            map.put(i, i);
        }
    }

    private static void testGetAtMap(Map<Integer, Integer> map) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            map.get(i);
        }
    }

    private static void testRemoveAtMap(Map<Integer, Integer> map) {
        for (int i = 0; i < COLLECTION_SIZE; ++i) {
            map.remove(i);
        }
    }

    private static void printElapsedTime(Class collection, long elapsedTime, Operation operation) {
        long elapsedTimeMillis = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
        LOGGER.info("Collection: {}, operation - {}, time: {}ms", collection.getName(), operation, elapsedTimeMillis);
    }

    private static long getElapsedTime(Runnable r) {
        long start = System.nanoTime();
        r.run();
        long end = System.nanoTime();
        return end - start;
    }
}
