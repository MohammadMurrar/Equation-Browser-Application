package application;

public interface StackInterface<M> {

	//Abstract stack methods
	void push(M data);
	
	M pop();
	
	M peek();
	
	boolean isEmpty();
	
	void clear();
}
