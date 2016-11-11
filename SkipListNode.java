/**
 * SkipList Definition
 * @author Soorya Prasanna Ravichandran
 **/
import java.lang.reflect.Array;

// Definition for SkipList 
class SkipListNode <T extends Comparable<? super T>> 
{    
	T data;
	SkipListNode<?> next[];

	public SkipListNode(T x, int level) 
	{
		data = x;
		next = new SkipListNode[level];
	}
	
	// This method is used for rebuilding the Skip List
	public void setNextIndex(int level,SkipListNode<T> node) 
	{
		if(next.length - 1 < level)
		{	
			/*
			 *  Existing array's level is less than the new level,
			 *  so I'm copying to new array
			 */
			SkipListNode<T> [] rebuild = (SkipListNode<T> []) Array.newInstance(SkipListNode.class,level+1); 
			System.arraycopy(next, 0, rebuild, 0, next.length);
			rebuild[level] = node;
			next = rebuild;
		}
		else
		{
			next[level] = node;
		}
	}
}