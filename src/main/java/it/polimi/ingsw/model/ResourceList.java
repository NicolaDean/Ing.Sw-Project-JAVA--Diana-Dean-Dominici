package it.polimi.ingsw.model;

import java.util.*;


public class ResourceList implements List
{

    List<Resource> resources;

    public ResourceList()
    {
        resources = new ArrayList<>();
    }

    @Override
    public int size() {
       return resources.size();
    }

    @Override
    public boolean isEmpty() {
        return resources.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return resources.contains(o);
    }

    @Override
    public Iterator iterator() {
       return this.resources.iterator();
    }

    @Override
    public Object[] toArray() {
        return this.resources.toArray();
    }

    /**
     *
     * ADD resources by grouping them by type
     * @param o New Element
     * @return true if all goes well
     */
    @Override
    public boolean add(Object o) {
        Resource r = (Resource) o;

        int pos=0;

        if (r == null) return false;
        if(this.resources.isEmpty())
        {
            this.resources.add(r);
            return true;
        }
        else
        {
            for(Resource res :this.resources)
            {
                if(res!= null)
                {
                    if(res.getType() == r.getType())
                    {
                        try {
                            this.resources.set(pos,ResourceOperator.sum(res,r));
                            return true;
                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                }

                pos++;

            }
            this.resources.add(pos,r);
            return true;
        }

    }

    @Override
    public boolean remove(Object o) {
        return this.resources.remove(o);
    }

    @Override
    public boolean addAll(Collection c) {
        return false;
    }

    @Override
    public boolean addAll(int index,Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Object get(int index) {
        return this.resources.get(index);
    }

    @Override
    public Object set(int index, Object element) {
        return this.resources.set(index,(Resource) element);
    }

    @Override
    public void add(int index, Object element) {
        this.resources.add(index,(Resource) element);
    }

    @Override
    public Object remove(int index) {
        return this.resources.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return this.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return this.resources.lastIndexOf(o);
    }

    @Override
    public ListIterator listIterator() {
        return this.resources.listIterator();
    }

    @Override
    public ListIterator listIterator(int index) {
        return this.resources.listIterator(index);
    }

    @Override
    public List subList(int fromIndex, int toIndex) {
        return this.resources.subList(fromIndex,toIndex);
    }

    @Override
    public boolean retainAll( Collection c) {
        return this.resources.retainAll(c);
    }

    @Override
    public boolean removeAll( Collection c) {
        return this.resources.removeAll(c);
    }

    @Override
    public boolean containsAll( Collection c) {
        return this.resources.containsAll(c);
    }

    @Override
    public Object[] toArray( Object[] a) {
        return this.resources.toArray();
    }
}