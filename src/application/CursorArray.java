package application;
/*
  A) Many Languages do not support pointers (e.g. Basic, Fortran). 
  	- If linked lists are required and pointers are not available, then an alternate implementation must be used.
  	- The alternate method we will describe here is known as a cursor implementation. 
  
  B) If data max length is known and can fit in memory, using Array is faster. 
  
  Two features present in a pointer implementation of linked lists: 
	1. The data are stored in array are nodes, each array element (node) contains the data and a pointer
	(int array index) to the next node. 
	
	2. A new node can be obtained from the system’s global memory (array) by a call to malloc
	(memory allocation) and released by a call to free methods. 
*/
public class CursorArray<M extends Comparable<M>> {//CursorArray Stack and some method and declarations of it 

	Node<M>[] CA;

	public CursorArray(int capacity) {
		CA = new Node[capacity];
		initialization();
	}

	public int initialization() {
		for (int i = 0; i < CA.length - 1; i++) {
			CA[i] = new Node(null, i + 1);
		}
		CA[CA.length - 1] = new Node("null", 0);
		return 0;
	}

	public int malloc() {
		int p = CA[0].next;
		CA[0].next = CA[p].next;
		return p;
	}

	public void free(int p) {
		CA[p] = new Node(null, CA[0].next);
		CA[0].next = p;
	}

	public boolean isNull(int l) {
		return CA[l] == null;
	}

	public boolean isEmpty(int l) {
		return CA[l].next == 0;
	}

	public boolean isLast(int p) {
		return CA[p].next == 0;
	}

	public int createList() {
		int l = malloc();
		if (l == 0) {
			System.out.println("Array is full !!!");
		}
		CA[l] = new Node("dummy", 0);
		return l;
	}

	public void insertAtHead(M data, int l) {
		if (isNull(l)) {
			return;
		}
		int p = malloc();
		if (p != 0) {
			CA[p] = new Node(data, CA[l].next);
			CA[l].next = p;
		} else {
			System.out.println("Out of space !!!");
		}
	}

	public int find(M data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			l = CA[l].next;
			if (CA[l].data.equals(data)) {
				return l;
			}
		}
		return -1;
	}

	public int findPrev(M data, int l) {
		while (!isNull(l) && !isEmpty(l)) {
			if (CA[CA[l].next].data.equals(data))
				return l;
			l = CA[l].next;
		}
		return -1;
	}

	public Node delete(M data, int l) {
		int p = findPrev(data, l);
		if (p != -1) {
			int c = CA[p].next;
			Node temp = CA[c];
			CA[p].next = temp.next;
			free(c);
		}
		return null;
	}

	public M deleteFirst(int l) {
		if (!isNull(l) && !isEmpty(l)) {
			int c = CA[l].getNext();
			Node<M> temp = CA[c];
			CA[l].setNext(temp.getNext());
			free(c);
			return temp.getData();
		}
		return null;
	}

	public M returnFirst(int l) {
		int p = CA[l].getNext();
		Node<M> temp = CA[p];
		return temp.getData();
	}

	public void clear(int l) {
		while (!isEmpty(l)) {
			deleteFirst(l);
		}
	}

	public void traversList(int l) {
		System.out.print("list_" + l + "-->");
		while (!isNull(l) && !isEmpty(l)) {
			l = CA[l].next;
			System.out.print(CA[l] + "-->");
		}
		System.out.println("null");
	}

	@Override
	public String toString() {
		String s = "";
		for (int i = 0; i < CA.length; i++) {
			s += i + " : " + CA[i] + "\n";
		}
		return s;
	}

}
