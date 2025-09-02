public interface PriorityQueueADT<T extends Comparable<T>> {
  public boolean isQueueEmpty();
  public boolean enqueue(T e);
  public T dequeue();
  public int size();
  public T viewBest();
}
