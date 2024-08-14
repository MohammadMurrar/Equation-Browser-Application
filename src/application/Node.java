package application;

public class Node<M extends  Comparable<M>> {
	//Node class contain data and the number of the next node (this class using in CursorArray

	M data;
	int next;
	
	public Node(M data, int next) {
		this.data = data;
		this.next = next;
	}

	public M getData() {
		return data;
	}

	public void setData(M data) {
		this.data = data;
	}

	public int getNext() {
		return next;
	}

	public void setNext(int next) {
		this.next = next;
	}
	
	@Override
	public String toString() {
		return "[" + data + ", next=" + next + "]";
	}
}
