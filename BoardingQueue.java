import java.util.NoSuchElementException;
import java.util.Arrays;

public class BoardingQueue implements PriorityQueueADT<Passenger> { 
  private int currentSize; 
  private Passenger[] queueHeap;

  public BoardingQueue(int capacity) {
    if (capacity <= 0) {
      throw new IllegalArgumentException("Capacity is not a positive integer.");
    } else {
      queueHeap = new Passenger[capacity];
      currentSize = 0;
    }
  }

  public Passenger[] toArray() {
    return Arrays.copyOf(this.queueHeap, this.queueHeap.length);
  }

  public BoardingQueue deepCopy() {
    BoardingQueue copy = new BoardingQueue(queueHeap.length);
    copy.queueHeap = Arrays.copyOf(queueHeap, queueHeap.length);
    copy.currentSize = currentSize;
    return copy;
  }

  public void reset() {
    Arrays.fill(queueHeap, 0, currentSize, null);
    currentSize = 0;
  }

  public int capacity() {
    return queueHeap.length;
  }

  public boolean isQueueFull() {
    return queueHeap.length == currentSize;
  }

  public boolean isQueueEmpty() {
    return currentSize == 0;
  }

  public int size() {
    return currentSize;
  }

  @Override
  public String toString() {
    String s = "";
    BoardingQueue deepCopy = this.deepCopy();
    while (!deepCopy.isQueueEmpty()) {
      s += deepCopy.dequeue().toString() + "\n";
    }
    return s.trim();
  }

  protected void siftDown(int index) {
    int smallest = index;

    int rightChild = 2 * index + 2;
    int leftChild = 2 * index + 1;

    if (rightChild < currentSize && queueHeap[rightChild].compareTo(queueHeap[smallest]) < 0) {
      smallest = rightChild;
    }

    if (leftChild < currentSize && queueHeap[leftChild].compareTo(queueHeap[smallest]) < 0) {
      smallest = leftChild;
    }

    if (smallest != index) {
      Passenger temp = queueHeap[index];
      queueHeap[index] = queueHeap[smallest];
      queueHeap[smallest] = temp;
      siftDown(smallest);
    }
  }

  protected void siftUp(int index) {
    if (index > 0) {
      int parent = (index - 1) / 2;

      if (queueHeap[index].compareTo(queueHeap[parent]) < 0) {
        Passenger temp = queueHeap[index];
        queueHeap[index] = queueHeap[parent];
        queueHeap[parent] = temp;
        siftUp(parent);
      }
    }
  }

  public Passenger viewBest() {
    if (!isQueueEmpty()) {
      return queueHeap[0];
    }
    throw new NoSuchElementException("Queue is empty. ");
  }

  public boolean enqueue(Passenger passenger) {
    if (passenger == null) {
      throw new NullPointerException("Passenger is null. ");
    }

    if (isQueueFull()) {
      return false;
    }

    if (isQueueEmpty()) {
      queueHeap[0] = passenger;
      currentSize++;
      return true;
    }

    queueHeap[currentSize] = passenger;
    siftUp(currentSize);
    currentSize++;
    return true;
  }

  @Override
  public Passenger dequeue() {
    if (isQueueEmpty()) {
      throw new NoSuchElementException("Queue is empty.");
    }

    Passenger removed = queueHeap[0];
    queueHeap[0] = queueHeap[currentSize - 1];
    queueHeap[currentSize - 1] = null;

    currentSize--;
    siftDown(0);
    return removed;
  }
}
