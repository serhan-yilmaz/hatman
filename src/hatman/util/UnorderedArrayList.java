/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class UnorderedArrayList<E> implements Collection<E>{
    private final ArrayList<E> arrayList;
    
    public UnorderedArrayList(){
        this.arrayList = new ArrayList<>();
    }
    
    public UnorderedArrayList(Collection<E> collection){
        this.arrayList = new ArrayList<>(collection);
    }
    
    @Override
    public int size() {
        return arrayList.size();
    }

    @Override
    public boolean isEmpty() {
        return arrayList.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Iterator<E> iterator() {
        return new UnorderedArrayListIterator<>(this);
    }

    @Override
    public Object[] toArray() {
        return arrayList.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return arrayList.toArray(a);
    }

    @Override
    public boolean add(E e) {
        return arrayList.add(e);
    }
    
    public E get(int index){
        return arrayList.get(index);
    }

    public boolean isLastElement(int index){
        return index == this.size() - 1;
    }
    
    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Not supported.");
    }
    
    public boolean remove(int index) {
        if(index >= size()){
            return false;
        }
        if(!isLastElement(index)){
            E lastElement = arrayList.get(this.size() - 1);
            arrayList.set(index, lastElement);
        }
        arrayList.remove(this.size() - 1);
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return arrayList.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return arrayList.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
        }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void clear() {
        arrayList.clear();
    }
    
}
