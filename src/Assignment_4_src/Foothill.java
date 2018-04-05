package Assignment_4_src;
// CIS 1C Assignment #4
// Instructor Solution Client

import java.util.NoSuchElementException;

import cs_1c.*;

class PrintObject<E> implements Traverser<E>
{
	public void visit(E x)
	{
		System.out.print( x + " ");
	}
};

//------------------------------------------------------
public class Foothill
{
	// -------  main --------------
	public static void main(String[] args) throws Exception
	{
		int k;
		FHlazySearchTree<Integer> searchTree = new FHlazySearchTree<Integer>();
		PrintObject<Integer> intPrinter = new PrintObject<Integer>();

		searchTree.traverse(intPrinter);

		System.out.println( "\ninitial size: " + searchTree.size() );
		searchTree.insert(50);
		searchTree.insert(20);
		searchTree.insert(30);
		searchTree.insert(70);
		searchTree.insert(10);
		searchTree.insert(60);
		searchTree.insert(100);
		System.out.println("searchTree min 10 : " + searchTree.findMin());
		System.out.println("searchTree max 100 : " + searchTree.findMax());

		System.out.println( "After populating -- traversal and sizes: ");
		searchTree.traverse(intPrinter);
		System.out.println( "\ntree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "Collecting garbage on new tree - should be " +
				"no garbage." );
		searchTree.collectGarbage();
		System.out.println( "tree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		// test assignment operator
		FHlazySearchTree<Integer> searchTree2 
		= (FHlazySearchTree<Integer>)searchTree.clone();

		System.out.println( "\n\nAttempting 1 removal: ");
		if (searchTree.remove(20))
			System.out.println( "removed " + 20 );
		System.out.println( "tree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "Collecting Garbage - should clean 1 item. " );
		searchTree.collectGarbage();
		System.out.println( "tree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "Collecting Garbage again - no change expected. " );
		searchTree.collectGarbage();
		System.out.println( "tree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		// test soft insertion and deletion:

		System.out.println( "Adding 'hard' 22 - should see new sizes. " );
		searchTree.insert(22);
		searchTree.traverse(intPrinter);
		System.out.println( "\ntree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "\nAfter soft removal. " );
		searchTree.remove(22);
		searchTree.traverse(intPrinter);
		System.out.println( "\ntree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "Repeating soft removal. Should see no change. " );
		searchTree.remove(22);
		searchTree.traverse(intPrinter);
		System.out.println( "\ntree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "Soft insertion. Hard size should not change. " );
		searchTree.insert(22);
		searchTree.traverse(intPrinter);
		System.out.println( "\ntree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		System.out.println( "\n\nAttempting 100 removals: " );
		for (k = 100; k > 0; k--)
		{
			if (searchTree.remove(k))
				System.out.println( "removed " + k );
		}
		searchTree.collectGarbage();

		System.out.println( "\nsearch_tree now:");
		searchTree.traverse(intPrinter);
		System.out.println( "\ntree 1 size: " + searchTree.size() 
		+ "  Hard size: " + searchTree.sizeHard() );

		searchTree2.insert(500);
		searchTree2.insert(200);
		searchTree2.insert(300);
		searchTree2.insert(700);
		searchTree2.insert(100);
		searchTree2.insert(600);
		searchTree2.remove(700);

		System.out.println(
				"SearchTree2 findMax removed 700 : " + searchTree2.findMax());
		System.out.println(
				"SearchTree2 findMin removed 700 : " + searchTree2.findMin());


		//		System.out.println(searchTree2.sizeHard());
		//		System.out.println(searchTree2.size());
		System.out.println("SearchTree2 Max : " + searchTree2.findMax());
		System.out.println("SearchTree2 Min : " + searchTree2.findMin());
		System.out.println( "\nsearchTree2:\n" );

		searchTree2.traverse(intPrinter);

		System.out.println( "\ntree 2 size: " + searchTree2.size() 
		+ "  Hard size: " + searchTree2.sizeHard() );

		System.out.println("Removing 10");
		searchTree2.remove(10);
		System.out.println("Garbage Collecting");
		searchTree2.collectGarbage();
		System.out.println("post remove hard size: " + searchTree2.sizeHard());
		System.out.println(
				"post garbage collect remove size: " + searchTree2.size());
		System.out.println("Post remove 10 tree: " );
		searchTree2.traverse(intPrinter);
		System.out.println();

		System.out.println(""
				+ "Post remove min should equal 20 : " + searchTree2.findMin());
		searchTree2.traverse(intPrinter);

		System.out.println();
		searchTree2.remove(600);
		searchTree2.remove(500);
		searchTree2.remove(300);
		searchTree2.remove(200);
		searchTree2.remove(100);
		searchTree2.remove(70);
		searchTree2.remove(60);
		searchTree2.remove(50);
		searchTree2.remove(30);
		System.out.println("Size should be 1 : " + searchTree2.size());
		System.out.println(
				"Min last remaining node 20 : " + searchTree2.findMin());
		System.out.println(
				"Max last remaining node 20 : " + searchTree2.findMax());


		FHlazySearchTree<Integer> searchTree3 
		= (FHlazySearchTree<Integer>)searchTree.clone();
		searchTree3.traverse(intPrinter);
		System.out.println("Cloning empty searchTree with all nodes removed");
		System.out.println("Now findMin findMax on hard removed empty tree \n");
		System.out.println(
				"Tree hard size mSizeHard : " + searchTree3.sizeHard());

		try{
			System.out.println(searchTree3.findMax());
		}
		catch(NoSuchElementException e){
			System.out.println("Exception caught can't findMax on empty Tree!");
		}

		try{
			System.out.println(searchTree3.findMin());
		}
		catch(NoSuchElementException e){
			System.out.println("Exception caught can't findMax on empty Tree!");
		}

		System.out.println("Now findMin findMax on soft removed nodes: ");
		searchTree3.insert(100);
		searchTree3.insert(200);
		searchTree3.insert(300);
		System.out.println("Search 3 Max : " + searchTree3.findMax());
		System.out.println("Search 3 Min : " + searchTree3.findMin());
		searchTree3.insert(400);
		System.out.println("Inserting 400 : ");
		System.out.println("SearchTree new max: " + searchTree3.findMax());
		System.out.println("Removing 100 : ");
		searchTree3.remove(100);
		System.out.println("SearchTree new min: " + searchTree3.findMin());

		searchTree3.remove(200);
		searchTree3.remove(300);
		searchTree3.remove(400);
		System.out.println("Tree soft size mSize : " + searchTree3.mSize);
		try{
			searchTree3.findMax();
		}
		catch(NoSuchElementException e){
			System.out.println("Exception caught can't findMax on empty Tree!");
		}
		try{
			searchTree3.findMin();
		}
		catch(NoSuchElementException e){
			System.out.println("Exception caught can't findMin on empty Tree!");
		}
		
		System.out.println("searchTree3 adding duplicate items: \n");
		searchTree3.insert(100);
		searchTree3.insert(100);
		searchTree3.insert(100);
		searchTree3.insert(100);
		System.out.println("SearchTree 3 : \n");
		searchTree3.traverse(intPrinter);

	}
}

