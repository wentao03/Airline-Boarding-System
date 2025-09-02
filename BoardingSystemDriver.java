import java.util.Scanner;
import java.util.ArrayList;
import java.util.NoSuchElementException;

public class BoardingSystemDriver {
  // welcome, good bye, and syntax error messages
  private static String preBoardingAnnouncement =
          "                      AIRLINE BOARDING SYSTEM \n\n"
          + "Welcome to our airline company.\n"
          + "We invite our passengers to present to the boarding queue as we will start boading in a moment.\n"
          + "Please be ready to present your passport and boarding pass.\n"
          + "Boarding priority depends on your boarding group.\n";

  private static String postBoardingAnnouncement =
      "Thank you for choosing to fly with us. We wish you an enjoyable and pleasant flight!";
  private static String syntaxErrorMessage = "Syntax Error: Please enter a valid command!";

  private Scanner scanner; 
  private ArrayList<Passenger> passengersOnBoard; 
  private BoardingQueue boardingQueue; 

  public BoardingSystemDriver(int capacity) {
    if (capacity < 0)
      throw new IllegalArgumentException("Invalid boarding capacity.");
    scanner = new Scanner(System.in);
    passengersOnBoard = new ArrayList<Passenger>();
    boardingQueue = new BoardingQueue(capacity);
    }

  public static void main(String[] args) {
    new BoardingSystemDriver(20).runApplication();
  }


  public void runApplication() {
    System.out.println(preBoardingAnnouncement); 
    processUserCommands();
    scanner.close();
    System.out.println(postBoardingAnnouncement);
  }

  protected void enqueuePassenger(Passenger p) {
    boolean success = boardingQueue.enqueue(p);
    if (!success) {
      System.out.println("Boarding queue full!");
    }
  }

  protected void dequeuePassenger() {
    try {
      Passenger dequeued = boardingQueue.dequeue();

      if (!dequeued.isCheckedIn()) {
        System.out.println(dequeued.name() + " is not checked in. Skipping.");
      } else {
        passengersOnBoard.add(dequeued);
        System.out.println("Boarding " + dequeued);
      }
    } catch (NoSuchElementException e) {
      System.out.println("Empty Boarding Queue.");
    }
  }


  protected void boardingStart() {
    System.out.println("Starting boarding process...");
    System.out.println("The boarding queue contains " + this.boardingQueue.size() + "passengers");
    while (!boardingQueue.isQueueEmpty()) {
      this.dequeuePassenger();
    }

    System.out.println("Boarding process complete.");
  }

  private Passenger getPassengerToAdd(String commandLine) {
    String[] commands = commandLine.trim().split(" "); 
    if (commands.length < 4) {
      System.out.println(syntaxErrorMessage);
      return null;
    }
    try {
      String name = commands[1];
      Group group = Group.valueOf(commands[2]);
      boolean isCheckedIn = Boolean.valueOf(commands[3]);
      return new Passenger(name, group, isCheckedIn);
    } catch (IllegalArgumentException e) {
      System.out.println(syntaxErrorMessage + " Invalid boarding group.");
      return null; 
    }
  }

  private void menu() {
    System.out.println("Enter one of the following options:");
    System.out.println("[0] Display the main menu");
    System.out.println("[1] Start boarding process");
    System.out.println("[2] List passengers in the boarding queue");
    System.out.println("[3] List boarded passengers");
    System.out.println("[4] Enqueues a new passenger (<name> <group> <isCheckedIn>)");
    System.out.println("[5] Peek next passenger to board");
    System.out.println("[6] Cancel boarding");
    System.out.println("[7] Exit");
    System.out.println("----------------------------------------------------------");
  }

  private void processUserCommands() {
    menu();
    String promptCommand = "Enter command: ";
    System.out.print(promptCommand);
    String command = scanner.nextLine();

    while (command.charAt(0) != '7') {
      try {
        switch (command.charAt(0)) {
          case '0': 
            menu(); 
            break;
          case '1': 
            this.boardingStart();
            break;
          case '2': 
            System.out.println("Passengers in the boarding queue:");
            System.out.println(this.boardingQueue);
            break;
          case '3': 
            System.out.println("List of passengers on board:");
            for (Passenger passenger : this.passengersOnBoard) {
              System.out.println(passenger);
            }
            break;
          case '4': 
            Passenger p = this.getPassengerToAdd(command);
            if (p != null) {
              enqueuePassenger(p);
            }
            break;
          case '5': 
            Passenger nextPassenger = boardingQueue.viewBest();
            if (nextPassenger == null) {
              System.out.println("The boarding queue is empty.");
            } else {
              System.out.println(nextPassenger);
            }
            break;
          case '6': 
            System.out.println("The boarding process has been cancelled.");
            boardingQueue.reset();
            break;
          default:
            System.out.println(syntaxErrorMessage); 

        }
      } catch (Exception e) {
        System.out.println(e.getMessage());
      }
      System.out.print(promptCommand);
      command = scanner.nextLine(); 
    }
  }
}
