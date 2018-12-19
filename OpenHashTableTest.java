package sample;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OpenHashTableTest {

    OpenHashTable<Object> hashTable = new OpenHashTable<>();

    @org.junit.jupiter.api.Test
    void size() {
        assertEquals(0,hashTable.size());
        hashTable.add(1);
        hashTable.add(2);
        assertEquals(2,hashTable.size());
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(6);
        assertEquals(6,hashTable.size());
        hashTable.clear();
        assertEquals(0,hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        assertTrue(hashTable.isEmpty());
        hashTable.add(1);
        assertFalse(hashTable.isEmpty());
    }

    @org.junit.jupiter.api.Test
    void contains() {
        assertFalse(hashTable.contains(1));
        hashTable.add(1);
        assertTrue(hashTable.contains(1));
        assertFalse(hashTable.contains(2));
        hashTable.remove(1);
        assertFalse(hashTable.contains(1));
    }

    @org.junit.jupiter.api.Test
    void iterator() {
        Iterator iterator = hashTable.iterator();
        assertFalse(iterator.hasNext());
        assertNull(iterator.next());
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(6);
        hashTable.add(5);
        int expected = 6;
        while(iterator.hasNext()){
            assertEquals(iterator.next(),expected);
            expected--;
        }
    }

    @org.junit.jupiter.api.Test
    void toArray() {
        Object[] arr = new Object[4];
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        arr[0] = "d";
        arr[1] = "c";
        arr[2] = "b";
        arr[3] = "a";
        for(int i = 0;i < 4;i++) {
            assertEquals(hashTable.toArray()[i], arr[i]);
        }
    }

    @org.junit.jupiter.api.Test
    void add() {
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        assertEquals(4,hashTable.size());
        assertFalse(hashTable.add("c"));
        assertFalse(hashTable.add("d"));
        assertEquals(4,hashTable.size());
        try{
            hashTable.add(null);
        }catch (NullPointerException e){ }
    }

    @org.junit.jupiter.api.Test
    void remove() {
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        assertEquals(4,hashTable.size());
        hashTable.remove("c");
        assertTrue(hashTable.remove("d"));
        assertEquals(2,hashTable.size());
        try{
            hashTable.remove(null);
        }catch (NullPointerException e){ }
    }

    @org.junit.jupiter.api.Test
    void addAll() {
        List<Object> arr = new ArrayList<>();
        arr.add("a");
        arr.add("b");
        arr.add("c");
        arr.add("d");
        hashTable.addAll(arr);
        for (int i = 0; i < 4; i++) {
            assertTrue(hashTable.contains(arr.get(i)));
        }
    }

    @org.junit.jupiter.api.Test
    void clear() {
        assertEquals(0,hashTable.size());
        hashTable.add(1);
        hashTable.add(2);
        hashTable.add(3);
        hashTable.add(4);
        hashTable.add(5);
        hashTable.add(6);
        assertEquals(6,hashTable.size());
        hashTable.clear();
        assertEquals(0,hashTable.size());
    }

    @org.junit.jupiter.api.Test
    void equals() {
        OpenHashTable<String> ht = new OpenHashTable<>();
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        hashTable.add("e");
        hashTable.add("f");
        hashTable.add("g");
        hashTable.add("h");
        hashTable.add("i");
        hashTable.add("j");
        hashTable.add("k");
        hashTable.add("l");
        ht.add("l");
        ht.add("k");
        ht.add("j");
        ht.add("i");
        ht.add("h");
        ht.add("g");
        ht.add("f");
        ht.add("e");
        ht.add("d");
        ht.add("c");
        ht.add("b");
        ht.add("a");
        assertTrue(hashTable.equals(ht));
    }


    @org.junit.jupiter.api.Test
    void removeAll() {
        OpenHashTable ht = new OpenHashTable();
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        ht.add("a");
        ht.add("t");
        ht.add("f");
        ht.add("d");
        assertEquals(4,hashTable.size());
        hashTable.removeAll(ht);
        assertEquals(2,hashTable.size());
        assertFalse(hashTable.contains("a"));
        assertFalse(hashTable.contains("d"));
        assertTrue(hashTable.contains("b"));
        assertTrue(hashTable.contains("c"));
    }

    @org.junit.jupiter.api.Test
    void retainAll() {

        OpenHashTable ht = new OpenHashTable();
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        ht.add("a");
        ht.add("t");
        ht.add("f");
        ht.add("d");
        assertEquals(4,hashTable.size());
        hashTable.retainAll(ht);
        assertEquals(2,hashTable.size());
        assertFalse(hashTable.contains("t"));
        assertFalse(hashTable.contains("b"));
        assertFalse(hashTable.contains("c"));
        assertFalse(hashTable.contains("f"));
        assertTrue(hashTable.contains("a"));
        assertTrue(hashTable.contains("d"));
    }

    @org.junit.jupiter.api.Test
    void containsAll() {
        List arr = new ArrayList<Object>();
        arr.add("a");
        arr.add("b");
        arr.add("c");
        arr.add("d");
        assertFalse(hashTable.containsAll(arr));
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        assertTrue(hashTable.containsAll(arr));
    }


    @org.junit.jupiter.api.Test
    void search() {
        hashTable.add("a");
        hashTable.add("b");
        hashTable.add("c");
        hashTable.add("d");
        assertEquals("a",hashTable.search("a"));
        assertNull(hashTable.search("1"));
    }
}