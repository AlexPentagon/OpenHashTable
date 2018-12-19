package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class OpenHashTable<T> implements Set<T> {
    T[] table;
    boolean[] deleted;
    private float loadFactor = 0.75f;
    private int size;

    OpenHashTable(){
        table = (T[])(new Object[16]);
        deleted = new boolean[16];
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        if(o == null) throw new NullPointerException();
        return searchIndex(o) != -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new OpenHashTableIterator();

    }
//______________________________________________________________________________________________________________________
    class OpenHashTableIterator implements Iterator<T> {
        int currentIndex;
        OpenHashTableIterator(){
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            int o = findNext();
            return o != -1;
        }

        @Override
        public T next() {
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
        int j = 0;
        for (T elem : this) {
            result[j] = elem;
            j++;
        }
        return result;
    }

    @Override
    public boolean add(T value) {
        if(value == null) throw new NullPointerException();
        if(contains(value)) return false;

        int x = value.hashCode()*31 % table.length;
        int y = value.hashCode() %(table.length - 1) + 1;
        for(int i = 0;i < table.length;i++){
            if(x < 0) x *= -1;
            if(table[x] == null || deleted[x]){
                table[x] = value;
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
//        if(value == null) throw new NullPointerException();
//       int x = value.hashCode()*31 % table.length;
//       int y = value.hashCode() %(table.length - 1) + 1;
//        for(int i = 0;i < table.length;i++){
//            if(x < 0) x *= -1;
//            if(table[x] != null){
//                if(table[x].equals(value)) {
//                    if(deleted[x]){
//                        x = (x + i * y) % table.length;
//                        continue;
//                    }
//                    deleted[x] = true;
//                    size--;
//                    return true;
//                }
//            }else return false;
//            x = (x + i * y) % table.length;
//        }
//        return false;
        Integer index = searchIndex(value);
        if(index == -1) return false;
        else {
            deleted[index] = true;
            size--;
            return true;
        }
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        for(T o : c){
            if(contains(o)) return false;
        }
        for(T o : c){
            add(o);
        }
        return true;
    }

    public boolean addAllArr(T[] objects,boolean[] deleted) {
        boolean result = true;
        List<Object> toDelete = new ArrayList<>();
        for(int i = 0;i < objects.length;i++){
            if(objects[i] != null)result = add(objects[i]);
            if(deleted[i]) toDelete.add(objects[i]);
        }
        removeAll(toDelete);

        return result;
    }

    @Override
    public void clear() {
        int capacity = table.length;
        table = (T[]) new Object[capacity];
        deleted = new boolean[capacity];
        size = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Set)) return false;
        for (T object : this){
            if(!((Set) o).contains(object)) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        for(Object o : table){
            if(o != null && !deleted[searchIndex(o)]) result += o.hashCode();
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for(Object o : c){
            remove(o);
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        List<T> list = new ArrayList<>();
        for(Object o : c){
            if(contains(o))list.add((T)o);
        }
        clear();
        addAll(list);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
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
        T[] copyT = table;
        boolean[] copyD = deleted;

        table = (T[])new Object[capacity];
        deleted = new boolean[capacity];
        size = 0;
        addAllArr(copyT,copyD);
    }

    public Integer searchIndex(Object value){
        if(value == null) throw new NullPointerException();
        int x = value.hashCode()*31 % table.length;
        int y = value.hashCode() %(table.length - 1) + 1;
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

