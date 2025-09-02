import java.util.NoSuchElementException;

public class BoardingSystemTester {

  public static boolean testSameGroupDifferentArrival() {
    Passenger.resetPassengerOrder();

    Passenger p1 = new Passenger("Alan", Group.A, true);
    Passenger p2 = new Passenger("Bob", Group.A, true);

    return p1.compareTo(p2) < 0 && p2.compareTo(p1) > 0;
  }

  public static boolean testSameGroupSameArrival() {
    Passenger.resetPassengerOrder();

    Passenger p1 = new Passenger("Alan", Group.A, true);
    Passenger p2 = new Passenger("Bob", Group.A, true);

    return p1.compareTo(p2) < 0 && p2.compareTo(p1) > 0;
  }

  public static boolean testDifferentGroup() {
    Passenger.resetPassengerOrder();

    Passenger p1 = new Passenger("Alan", Group.A, true);
    Passenger p2 = new Passenger("Bob", Group.B, true);
    Passenger p3 = new Passenger("Carol", Group.A, true);

    return p1.compareTo(p2) < 0 && p2.compareTo(p1) > 0 && p3.compareTo(p2) < 0
        && p2.compareTo(p3) > 0 && p1.compareTo(p3) < 0 && p3.compareTo(p1) > 0;
  }

  public static boolean testBoardingQueueConstructor() {
    Passenger.resetPassengerOrder();

    try {
      new BoardingQueue(-1);
      return false;
    } catch (IllegalArgumentException e) {
      // Catch the expected exception
    }

    try {
      int capacity = 15;
      BoardingQueue boardingQueue = new BoardingQueue(capacity);
      if (!boardingQueue.isQueueEmpty() || boardingQueue.size() != 0
          || boardingQueue.capacity() != capacity) {
        return false;
      }
      Passenger[] heapArrayCopy = boardingQueue.toArray();

      for (int i = 0; i < capacity; i++) {
        if (heapArrayCopy[i] != null) {
          return false;
        }
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean testViewBestEmptyQueue() {
    Passenger.resetPassengerOrder();

    try {
      BoardingQueue boardingQueue = new BoardingQueue(15);

      boardingQueue.viewBest();
      return false;
    } catch (NoSuchElementException e) {
      return true;
    } catch (Exception e) {
      // Catch any unexpected exceptions
      return false;
    }
  }

  public static boolean testviewBestNonEmptyQueue() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(10);

      Passenger p1 = new Passenger("Alan", Group.A, false);
      Passenger p2 = new Passenger("Bob", Group.B, false);
      Passenger p3 = new Passenger("Carol", Group.C, false);

      boardingQueue.enqueue(p1);
      boardingQueue.enqueue(p2);
      boardingQueue.enqueue(p3);

      if (boardingQueue.viewBest().equals(p1) && boardingQueue.size() == 3) {
        return true;
      }

      return false;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean testEnqueueToEmptyQueue() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(5);
      Passenger p1 = new Passenger("Adam", Group.A, false);
      boolean added = boardingQueue.enqueue(p1);

      if (!added || boardingQueue.size() != 1) {
        return false;
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static boolean testEnqueueToNonEmptyQueue() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(6);

      Passenger p1 = new Passenger("Adam", Group.A, false);
      Passenger p2 = new Passenger("Bonnie", Group.B, false);
      Passenger p3 = new Passenger("Cate", Group.C, false);
      Passenger p4 = new Passenger("Dave", Group.A, false);
      Passenger p5 = new Passenger("Esther", Group.B, false);
      Passenger p6 = new Passenger("Francis", Group.C, false);

      boardingQueue.enqueue(p1);
      boardingQueue.enqueue(p2);
      boardingQueue.enqueue(p3);
      boardingQueue.enqueue(p4);
      boardingQueue.enqueue(p5);

      boolean added = boardingQueue.enqueue(p6);
      if (!added || boardingQueue.size() != 6) {
        return false;
      }

      Passenger[] heapCopyArray = boardingQueue.toArray();
      for (int i = 1; i < heapCopyArray.length; i++) {
        if (heapCopyArray[i].compareTo(heapCopyArray[(i - 1) / 2]) < 0) {
          return false;
        }
      }

      Passenger p7 = new Passenger("Gina", Group.C, false);
      boolean addedWhenFull = boardingQueue.enqueue(p7);
      if (addedWhenFull || boardingQueue.size() != 6) {
        return false;
      }
      return true;

    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public static boolean testDequeueSizeOne() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(10);
      Passenger p1 = new Passenger("Alan", Group.C, false);
      boardingQueue.enqueue(p1);
      Passenger dequeued = boardingQueue.dequeue();

      if (!p1.equals(dequeued) || boardingQueue.size() != 0) {
        return false;
      }

      Passenger[] heapArrayCopy = boardingQueue.toArray();
      for (Passenger passenger : heapArrayCopy) {
        if (passenger != null) {
          return false;
        }
      }
      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public static boolean testDequeueNonEmpty() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(10);

      Passenger p1 = new Passenger("Alan", Group.A, false);
      Passenger p2 = new Passenger("Bob", Group.B, false);
      Passenger p3 = new Passenger("Carol", Group.C, false);
      Passenger p4 = new Passenger("Dan", Group.A, false);
      Passenger p5 = new Passenger("Eden", Group.C, false);
      Passenger p6 = new Passenger("Frank", Group.B, false);

      boardingQueue.enqueue(p1);
      boardingQueue.enqueue(p2);
      boardingQueue.enqueue(p3);
      boardingQueue.enqueue(p4);
      boardingQueue.enqueue(p5);
      boardingQueue.enqueue(p6);

      Passenger dequeued1 = boardingQueue.dequeue();
      Passenger dequeued2 = boardingQueue.dequeue();
      Passenger dequeued3 = boardingQueue.dequeue();
      Passenger dequeued4 = boardingQueue.dequeue();
      Passenger dequeued5 = boardingQueue.dequeue();

      if (!dequeued1.equals(p1) || !dequeued2.equals(p4) || !dequeued3.equals(p2)
          || !dequeued4.equals(p6) || !dequeued5.equals(p3) || boardingQueue.size() != 1) {
        return false;
      }

      if (!boardingQueue.toString().equals(p5.toString())) {
        return false;
      }

      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public static boolean testDequeueEmpty() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(10);
      boardingQueue.dequeue();
      return false;
    } catch (NoSuchElementException e) {
      return true;
    }
  }

  public static boolean testBoardingQueuereset() {
    Passenger.resetPassengerOrder();
    try {
      BoardingQueue boardingQueue = new BoardingQueue(10);

      boardingQueue.reset();

      if (!boardingQueue.isQueueEmpty()) {
        return false;
      }

      Passenger[] heapArrayCopy = boardingQueue.toArray();
      for (int i = 0; i < heapArrayCopy.length; i++) {
        if (heapArrayCopy[i] != null) {
          return false;
        }
      }

      Passenger p1 = new Passenger("Adam", Group.A, false);
      Passenger p2 = new Passenger("Bonnie", Group.B, false);
      Passenger p3 = new Passenger("Cate", Group.C, false);
      boardingQueue.enqueue(p1);
      boardingQueue.enqueue(p2);
      boardingQueue.enqueue(p3);

      boardingQueue.reset();
      if (!boardingQueue.isQueueEmpty()) {
        return false;
      }

      Passenger[] heapArrayCopy2 = boardingQueue.toArray();
      for (int i = 0; i < heapArrayCopy2.length; i++) {
        if (heapArrayCopy2[i] != null) {
          return false;
        }
      }

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public static void main(String[] args) {
    System.out.println("testSameGroupDifferentArrival: "
        + (testSameGroupDifferentArrival() ? "Pass" : "Failed"));
    System.out.println("testSameGroupSameArrival: "
        + (testSameGroupSameArrival() ? "Pass" : "Failed"));
    System.out.println("testDifferentGroup: "
        + (testDifferentGroup() ? "Pass" : "Failed"));
    System.out.println(
        "testBoardingQueueConstructor: " + (testBoardingQueueConstructor() ? "Pass" : "Failed"));
    System.out
        .println("testViewBestEmptyQueue: " + (testViewBestEmptyQueue() ? "Pass" : "Failed"));
    System.out.println(
        "testviewBestNonEmptyQueue: " + (testviewBestNonEmptyQueue() ? "Pass" : "Failed"));
    System.out
        .println("testEnqueueToEmptyQueue: " + (testEnqueueToEmptyQueue() ? "Pass" : "Failed"));
    System.out.println(
        "testEnqueueToNonEmptyQueue: " + (testEnqueueToNonEmptyQueue() ? "Pass" : "Failed"));
    System.out.println("testDequeueSizeOne: "
        + (testDequeueSizeOne() ? "Pass" : "Failed"));
    System.out.println("testDequeueNonEmpty: "
        + (testDequeueNonEmpty() ? "Pass" : "Failed"));
    System.out.println("testDequeueEmpty: " + (testDequeueEmpty() ? "Pass" : "Failed"));
    System.out
        .println("testBoardingQueuereset: " + (testBoardingQueuereset() ? "Pass" : "Failed"));
  }

}
