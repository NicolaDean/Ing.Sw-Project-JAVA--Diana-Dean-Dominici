package it.polimi.ingsw.model.resources;

import it.polimi.ingsw.enumeration.ResourceType;

import java.io.Serializable;
import java.util.*;


public class ResourceList implements List, Serializable
{

    List<Resource> resources;

    public ResourceList()
    {
        resources = new ArrayList<>();

        //Add One Empty Resource for each type
        for(ResourceType r : ResourceType.values())
        {
            resources.add(new Resource(r,0));
        }
    }

    @Override
    public int size() {
       return resources.size();
    }

    @Override
    public boolean isEmpty() {

        //Is null
        if(this.resources.isEmpty())return true;

        //Check all resource has 0 quantity
        for(Resource res: this.resources)
        {
            if(res.getQuantity() !=0) return false;
        }
        return true;
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

        int pos=0;

        if (o == null) return false;
        Resource r = (Resource) o;
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

        Resource r = (Resource) o;

        int pos=0;

        if (r == null) return false;
        if(this.resources.isEmpty())
        {
            return false;
        }
        else
        {
            for(Resource res :this.resources)
            {
                if(res!= null)
                {
                    if(res.getType().equals(r.getType()))
                    {
                        try {
                            if(res.getQuantity() >= r.getQuantity())
                            {
                                this.resources.set(pos,ResourceOperator.sub(res,r));
                                return true;
                            }
                            else{
                                return false;
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                            return false;
                        }
                    }
                }

                pos++;

            }
            return false;
        }
    }

    @Override
    public boolean addAll(Collection c) {
        for(Object res:c)
        {
            this.add(res);
        }
        return true;
    }

    @Override
    public boolean addAll(int index,Collection c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public Resource get(int index) {
        return this.resources.get(index);
    }

    @Override
    public Resource set(int index, Object element) {
        return this.resources.set(index,(Resource) element);
    }

    @Override
    public void add(int index, Object element) {
        this.resources.add(index,(Resource) element);
    }

    @Override
    public Resource remove(int index) {
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

        for(Object res: c)
        {
            this.remove(res);
        }
        return true;
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
