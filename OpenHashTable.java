package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OpenHashTable<T> implements Set {
    T[] table;
    boolean[] deleted;
    private float loadFactor = 0.75f;
    private int size;

    OpenHashTable(){
        table = (T[])(new Object[16]);
        deleted = new boolean[16];
    }

    public T[] getTable(){
        return table;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        if(size == 0) return true;
        else return false;
    }

    @Override
    public boolean contains(Object o) {
        if(o == null) throw new NullPointerException();
        if(search(o) != null && !deleted[searchIndex(o)]) return true;
        else return false;
    }

    @Override
    public Iterator iterator() {
        return new OpenHashTableIterator();

    }
//______________________________________________________________________________________________________________________
    class OpenHashTableIterator implements Iterator{
        int currentIndex;
        OpenHashTableIterator(){
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            int o = findNext();
            if (o != -1){
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
           int o = findNext();
           if (o != -1){
               currentIndex = o + 1;
               return table[o];
           }
           return null;
        }

        public int findNext(){
            for(int i = currentIndex;i < table.length;i++){
                if(table[i] != null && !deleted[i]) {
                    return i;
                }
            }
            return -1;
        }
    }
//______________________________________________________________________________________________________________________
    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        Iterator iterator = new OpenHashTableIterator();
        int j = 0;
        while(iterator.hasNext()){
            result[j] = iterator.next();
            j++;
        }
        return result;
    }

    @Override
    public boolean add(Object value) {
        if(value == null) throw new NullPointerException();
        value = value.toString();
        int x = value.hashCode() %(table.length - 1) + 1;
        int y = value.hashCode()*31 % table.length;
        for(int i = 0;i < table.length;i++){
            if(x < 0) x *= -1;
            if(table[x] == null || deleted[x]){
                table[x] = (T) value.toString();
                deleted[x] = false;
                size++;
                if((float)size / table.length > loadFactor) resize();
                return true;
            }
            x = (x + i * y) % table.length;
        }
        return false;
    }
// value%(size - 1) + 1;
// value.hashcode()*31 % size
    @Override
    public boolean remove(Object value) {
        if(value == null) throw new NullPointerException();
        value = value.toString();
       int x = value.hashCode() %(table.length - 1) + 1;
       int y = value.hashCode()*31 % table.length;
        for(int i = 0;i < table.length;i++){
            if(x < 0) x *= -1;
            if(table[x] != null){
                if(table[x].equals(value)) {
                    if(deleted[x]){
                        x = (x + i * y) % table.length;
                        continue;
                    }
                    deleted[x] = true;
                    size--;
                    return true;
                }
            }else return false;
            x = (x + i * y) % table.length;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection c) {
        for(Object o : c){
            add(o);
        }
        return true;
    }

    public boolean addAllArr(Object[] objects) {
        for(Object o : objects){
            if(o != null)add(o);
        }
        return true;
    }

    @Override
    public void clear() {
        int capacity = table.length;
        table = (T[]) new Object[capacity];
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        OpenHashTable ht = (OpenHashTable) o;
         Object[] oTable = ht.getTable();
        for (int i = 0; i < table.length;i++){
            if(table[i] != null && !table[i].equals(oTable[i])) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for(Object o : table){
            if(o != null) result += o.hashCode();
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection c) {
        for(Object o : c){
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection c) {
        List<Object> list = new ArrayList<>();
        for(Object o : c){
            if(contains(o))list.add(o);
        }
        clear();
        addAll(list);
        return true;
    }

    @Override
    public boolean containsAll(Collection c) {
        for (Object o : c){
            if(!contains(o)) return false;
        }
        return true;
    }

    @Override
    public Object[] toArray(Object[] a) {
        Iterator iterator = new OpenHashTableIterator();
        int j = 0;
        while(iterator.hasNext()){
            a[j] = iterator.next();
            j++;
        }
        return a;
    }

    private void resize(){
        int length = table.length;
        int capacity = length * 2;
        Object[] copy = table;

        table = (T[])new Object[capacity];
        deleted = new boolean[capacity];
        size = 0;
        addAllArr(copy);
    }

    public Integer searchIndex(Object value){
        if(value == null) throw new NullPointerException();
        value = value.toString();
        int x = value.hashCode() %(table.length - 1) + 1;
        int y = value.hashCode()*31 % table.length;
        for(int i = 0;i < table.length;i++){
            if(x < 0) x *= -1;
            if(table[x] != null){
                if(table[x].equals(value) && !deleted[x]) {
                    return x;
                }
            }
            else return -1;
            x = (x + i * y) % table.length;
        }
        return -1;
    }

    public Object search(Object value){
        int index = searchIndex(value);
        if(index!= -1) return table[index];
        else return null;
    }
}

