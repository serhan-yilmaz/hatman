/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hatman.util;

import java.util.Iterator;

/**
 *
 * @author Serhan Yilmaz <github.com/serhan-yilmaz>
 */
public class UnorderedArrayListIterator<E> implements Iterator<E>{
    private final UnorderedArrayList<E> array;
    private int index = -1;
    
    public UnorderedArrayListIterator(UnorderedArrayList<E> array){
        this.array = array;
    }
    
    @Override
    public boolean hasNext() {
        return (index + 1) < array.size();
    }

    @Override
    public E next() {
        return array.get(++index);
    }
    
    @Override
    public void remove(){
        int tmp_index = index;
        if(!array.isLastElement(index)){
            index--;
        }
        array.remove(tmp_index);
    }
    
}
