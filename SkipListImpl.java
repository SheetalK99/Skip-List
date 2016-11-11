/**
 * SkipList Implementation
 * @author Soorya Prasanna Ravichandran
 **/
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;

// Contains the implementation of SkipList
public class SkipListImpl<T extends Comparable<? super T>> implements SkipList<T> 
{
	SkipListNode<T> head,tail;	
	public static int max = 100;		//maximum level that can be created
	public int size;

	// Constructor to initialize head and tail pointers
	public SkipListImpl()
	{
		head = new SkipListNode<T>(null, max);
		tail = new SkipListNode<T>(null, max);

		//Setting all next pointers of head to tail initially
		for(int i = 0;i < max;i++)
		{
			head.next[i] =tail;
		}
		size = 0;
	}

	/* 
	 * Method to randomly generate a level where new nodes can be placed
	 */
	public int randomLevel(int level) 
	{
		int i = 1;
		Random r = new Random();
		i += r.nextInt(level);
		return i;				// Returns the level as random number
	}

	/*
	 * Iterator class is used to iterate over the SkipList 
	 * and to maintain the position
	 */
	private class SkipListIterator implements Iterator
	{
		SkipListNode<?> currNode;
		public SkipListIterator() 
		{
			currNode = head; 
		}
		public boolean hasNext() 
		{
			return currNode.next[0] != tail;
		}
		public Object next()
		{
			Object nxt = currNode.next[0].data;
			currNode =  currNode.next[0];
			return nxt;
		}
	}

	/*
	 * Before adding a node we need to find the position,
	 * this method finds the previous pointer where the element should be added
	 * We can do previous.next to add the element.
	 * Find method will be used to add / remove an element,
	 * to check whether the element is present in the list
	 */
	public SkipListNode<T>[] find(T x) 
	{
		SkipListNode currNode = head;
		SkipListNode prev[] = new SkipListNode[max];

		for (int i = max - 1; i >= 0; i--) 
		{
			while (currNode.next[i] != tail && currNode.next[i].data.compareTo(x) < 0) 
			{
				currNode = currNode.next[i];
			}
			prev[i] = currNode;
		}
		return prev;
	}

	/*
	 * Add an element to the list
	 * Returns true if the element is added, false otherwise
	 */
	@Override
	public boolean add(T x) 
	{
		//Before adding we need to find the position. Find method is called
		SkipListNode<T> prev[] = find(x);  
		SkipListNode currNode = prev[0].next[0];

		if(currNode.data != null && currNode.data.compareTo(x) == 0)  
		{
			currNode.data = x;
			return false;
		}
		else
		{
			int level = randomLevel(max);
			SkipListNode<T> newEntry = new SkipListNode<T>(x, level);

			for(int i = 0; i < level; i++)
			{
				newEntry.next[i] = prev[i].next[i];
				prev[i].next[i] = newEntry;
			}
			size++;
		}
		return true;
	}

	/* 
	 * To check whether the element is present in the list
	 * Returns true if it is present, false otherwise
	 */
	@Override
	public boolean contains(T x) 
	{
		SkipListNode[] prev = find(x);
		if(prev[0].next[0].data != null && prev[0].next[0].data.compareTo(x) == 0)
			return true;
		else
			return false;
	}

	// Returns the element at index n in the list
	@Override
	public T findIndex(int n) 
	{
		SkipListNode<?> currNode = head;

		if(n <= size)
		{
			for(int i = 1;i <= n;i++)
			{
				currNode = currNode.next[0];
			}
			return (T) currNode.data;
		}
		else
		{
			return null;
		}
	}

	// Return the first element of the list
	@Override
	public T first() 
	{
		if(head.next[0] != null) 
			return (T) head.next[0].data;
		else
			return null;
	}

	/*
	 * Finds the least element that is greater than or equal to the number x.
	 * Returns null if there are no such elements
	 */
	@Override
	public T ceiling(T x) 
	{
		SkipListNode<T>[] prev = find(x);
		SkipListNode<?> currNode = prev[0].next[0];

		if(prev != null && currNode != null)
			return (T) currNode.data;
		else
			return null;
	}

	/*
	 * Finds the greatest element that is less than or equal to x
	 * Returns null if there are no such elements
	 */
	@Override
	public T floor(T x) 
	{
		SkipListNode[] prev = find(x);
		SkipListNode currNode = prev[0].next[0];

		if(currNode.data != null && currNode.data.compareTo(x) == 0)
		{
			return (T) currNode.data;
		}
		else if(prev[0].data != null)
		{
			return (T) prev[0].data;
		}
		else
			return null;
	}

	// To check whether the list is empty
	@Override
	public boolean isEmpty() 
	{
		if(size == 0)
			return true;
		else
			return false;
	}

	// Returns the iterator for the list
	@Override
	public Iterator<T> iterator() 
	{
		return new SkipListIterator();
	}

	// Returns the last element in the list
	@Override
	public T last() 
	{
		SkipListNode<?> tail = head;

		for(int i = max-1;i >= 0;i--)
		{
			while(tail.next[i].data != null)
			{
				tail = tail.next[i];
			}
		}
		return (T) tail.data; 
	}

	// Remove x from this list; returns null if x is not in list
	@Override
	public T remove(T x) 
	{
		SkipListNode[] prev = find(x);
		SkipListNode delete = prev[0].next[0];
		boolean set = false;
		if(delete.data != null && delete.data.compareTo(x) != 0)
		{
			return null;
		}
		else
		{
			for(int i = 0;i < max;i++)
			{
				if (prev[i].next[i] == delete)
				{
					prev[i].next[i] = delete.next[i];
					set = true;
				}
				else
					break;
			}
			if(true) 
			{ 
				size--; 
			}
			return (T) delete.data;
		}
}

	// Returns the number of elements in the list
	@Override
	public int size() 
	{
		return size;
	}

	// Method to rebuild this list into a perfect skip list
	@Override
	public void rebuild() 
	{
		int n = size();
		SkipListNode tempHead = head;
		SkipListNode<T> [] node;
		/* 
		 * We rebuild ONLY when the size of list increases by power of 2
		 * 2 power x = size, Log is base 2.
		 * We need to convert size in terms of power 2 to get the new maxlevel
		 */
		int maxlevel = (int) Math.ceil((Math.log(n+1) / Math.log(2)));

		for(int i = 1;i <= maxlevel ;i++)
		{
			tempHead = head;
			while(tempHead.next!=null && tempHead.next[0]!=null && tempHead.next[i-1]!=null)
			{
				node = tempHead.next[i-1].next;
				if(node[0]== null)	
				{
					tempHead.setNextIndex(i, tempHead.next[i-1]);
				}
				else
				{
					tempHead.setNextIndex(i, node[i-1]);
				}
				tempHead= tempHead.next[i];
			}
		}
		max = maxlevel;
	}
}