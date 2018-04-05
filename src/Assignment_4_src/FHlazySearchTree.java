package Assignment_4_src;

import java.util.NoSuchElementException;

import cs_1c.*;

public class FHlazySearchTree<E extends Comparable< ? super E > >
implements Cloneable
{
	protected int mSize;
	protected int mSizeHard;
	protected FHs_treeNode<E> mRoot;

	public FHlazySearchTree() { clear(); }
	public boolean empty() { return (mSize == 0); }
	public int size() { return mSize; }
	public void clear() { mSize = 0; mRoot = null;   }
	public int showHeight() { return findHeight(mRoot, -1); }


	public int sizeHard(){
		return mSizeHard;
	}

	public E findMin() 
	{
		if (mRoot == null || mSize == 0)
			throw new NoSuchElementException();
		return findMin(mRoot).data;
	}

	public E findMax() 
	{
		if (mRoot == null || mSize == 0)
			throw new NoSuchElementException();
		return findMax(mRoot).data;
	}

	public E find( E x )
	{
		FHs_treeNode<E> resultNode;
		resultNode = find(mRoot, x);
		if (resultNode == null)
			throw new NoSuchElementException();
		return resultNode.data;
	}
	public boolean contains(E x)  { return find(mRoot, x) != null; }

	public boolean insert( E x )
	{
		int oldSize = mSize;
		mRoot = insert(mRoot, x);
		return (mSize != oldSize);
	}

	public boolean remove( E x )
	{
		if (mRoot == null){
			return false;
		}
		int oldSize = mSize;
		removeLazy(mRoot, x);
		return (mSize != oldSize);
	}

	public < F extends Traverser<? super E > > 
	void traverse(F func)
	{
		traverse(func, mRoot);
	}

	public Object clone() throws CloneNotSupportedException
	{
		FHlazySearchTree<E> newObject = (FHlazySearchTree<E>)super.clone();
		newObject.clear();  // can't point to other's data

		newObject.mRoot = cloneSubtree(mRoot);
		newObject.mSize = mSize;

		return newObject;
	}
	public void collectGarbage(){
		mRoot = collectGarbage(mRoot);
	}

	// private helper methods ----------------------------------------
	protected FHs_treeNode<E> findMin( FHs_treeNode<E> root ) 
	{
		if (root == null){
			return null;
		}
		//bottom 
		FHs_treeNode<E> temp = findMin(root.lftChild);

		if (temp != null){
			return temp;
		}
		if (root.deleted){
			return findMin(root.rtChild);
		}
		else{
			return root;
		}

	}

	protected FHs_treeNode<E> findMax( FHs_treeNode<E> root ) 
	{
		if (root == null){
			return null;
		}
		FHs_treeNode<E> tempNode = findMax(root.rtChild);

		if(tempNode != null){
			return tempNode;
		}
		if (root.deleted){
			return findMax(root.lftChild);
		}
		else{
			return root;
		}

	}


	protected FHs_treeNode<E> insert( FHs_treeNode<E> root, E x )
	{
		int compareResult;  // avoid multiple calls to compareTo()

		if (root == null)
		{
			mSize++;
			mSizeHard++;			
			return new FHs_treeNode<E>(x, null, null);
		}
		compareResult = x.compareTo(root.data); 
		if ( compareResult < 0 )
			root.lftChild = insert(root.lftChild, x);
		else if ( compareResult > 0 )
			root.rtChild = insert(root.rtChild, x);
		else if (root.deleted){						//if deleted reset incr size
			root.deleted = false;
			mSize++;
		}

		return root;
	}



	protected FHs_treeNode<E> removeHard( FHs_treeNode<E> root, E x  )
	{
		if (root.lftChild != null && root.rtChild != null)
		{
			FHs_treeNode<E> minRightNode = findMin(root.rtChild);
			root.data = minRightNode.data;
			root.deleted = false;
			root.rtChild = removeHard(root.rtChild, root.data);
		}
		else
		{	

			root =
					(root.lftChild == null)? root.rtChild : root.lftChild;
			mSizeHard--;
		}

		return root;
	}

	protected void removeLazy(FHs_treeNode<E> root, E x){

		FHs_treeNode<E> temp = find(root,x);
		if(temp != null && temp.deleted == false){
			temp.deleted = true;
			mSize--;
		}
	}

	protected FHs_treeNode<E> collectGarbage(FHs_treeNode<E> root){
		if (root == null){
			return null;
		}

		if (root.lftChild != null){
			root.lftChild = collectGarbage(root.lftChild);
		}
		if (root.rtChild != null){
			root.rtChild = collectGarbage(root.rtChild);
		}
		if (root.deleted){
			root = removeHard(root, root.data);
		}
		// traverse and call find to hard remove found node***
		return root;
	}


	protected <F extends Traverser<? super E>> 
	void traverse(F func, FHs_treeNode<E> treeNode){
		if (treeNode == null){
			return;
		}
		traverse(func, treeNode.lftChild);
		if (treeNode.deleted == false){
			func.visit(treeNode.data);
		}
		traverse(func, treeNode.rtChild);

	}
	protected FHs_treeNode<E> find( FHs_treeNode<E> root, E x )
	{
		int compareResult;  // avoid multiple calls to compareTo()

		if (root == null)
			return null;

		compareResult = x.compareTo(root.data); 
		if (compareResult < 0)
			return find(root.lftChild, x);
		if (compareResult > 0)
			return find(root.rtChild, x);
		return root;   // found
	}

	protected FHs_treeNode<E> cloneSubtree(FHs_treeNode<E> root)
	{

		FHs_treeNode<E> newNode;
		if (root == null)
			return null;

		// does not set myRoot which must be done by caller
		newNode = new FHs_treeNode<E>
		(
				root.data, 
				cloneSubtree(root.lftChild), 
				cloneSubtree(root.rtChild)
				);
		return newNode;
	}

	protected int findHeight( FHs_treeNode<E> treeNode, int height ) 
	{
		int leftHeight, rightHeight;
		if (treeNode == null)
			return height;
		height++;
		leftHeight = findHeight(treeNode.lftChild, height);
		rightHeight = findHeight(treeNode.rtChild, height);
		return (leftHeight > rightHeight)? leftHeight : rightHeight;
	}
}
class FHs_treeNode<E extends Comparable< ? super E > >
{
	// use public access so the tree or other classes can access members 
	public FHs_treeNode<E> lftChild, rtChild;
	public E data;
	public FHs_treeNode<E> myRoot;  // needed to test for certain error
	public boolean deleted;

	public FHs_treeNode( E d, FHs_treeNode<E> lft, FHs_treeNode<E> rt )
	{
		lftChild = lft; 
		rtChild = rt;
		data = d;
		deleted = false;
	}

	public FHs_treeNode()
	{
		this(null, null, null);
	}

	// function stubs -- for use only with AVL Trees when we extend
	public int getHeight() { return 0; }
	boolean setHeight(int height) { return true; }
}

