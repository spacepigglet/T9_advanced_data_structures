public interface DataStructure<T> {
	public boolean contains(T x);

	public int getRotationCounter();

	public void resetRotationCounter();

	public void insert(T i);
	public void remove(T i);
}
