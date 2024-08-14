package application;

public class CursorStack<M extends Comparable<M>> implements StackInterface<M> {
	// Sack use the methods of CursorArray to implements
	
	private CursorArray<M> cursor = new CursorArray<>(1000);
	private int l;
	
	public CursorStack(int capacity){
		cursor = new CursorArray<>(capacity);
		l = cursor.createList();
	}
	
	@Override
	public void push(M data) {
		cursor.insertAtHead(data, l);
		
	}

	@Override
	public M pop() {
		return cursor.deleteFirst(l);
	}

	@Override
	public M peek() {
		return cursor.returnFirst(l);
	}

	@Override
	public boolean isEmpty() {
		return cursor.isEmpty(l);
	}

	@Override
	public void clear() {		
	}

}
