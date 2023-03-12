public interface DataStructure<T> {
	public boolean contains(T x);

	public int getCounter();

	public void resetCounter();

	public void insert(T i);
	public void remove(T i);
}
