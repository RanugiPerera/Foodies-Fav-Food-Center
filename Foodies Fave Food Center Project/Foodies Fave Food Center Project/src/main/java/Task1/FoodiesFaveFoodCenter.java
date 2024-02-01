package Task1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author User
 */

public class FoodiesFaveFoodCenter {

    /* Declare and initialize constant varibales */
    private static final int MAX_CUSTOMERS_QUEUE1 = 2;
    private static final int MAX_CUSTOMERS_QUEUE2 = 3;
    private static final int MAX_CUSTOMERS_QUEUE3 = 5;
    private static final int MAX_BURGERS_STOCK = 50;
    private static final int BURGERS_PER_CUSTOMER = 5;

    /* Declare and initialize 2D array */
    private static String[][] queues = new String[3][];

    /* Declare and initialize variable for store remaining stock count */
    private static int remainingBurgers = MAX_BURGERS_STOCK;

    public static void main(String[] args) {

        queues[0] = new String[MAX_CUSTOMERS_QUEUE1];
        queues[1] = new String[MAX_CUSTOMERS_QUEUE2];
        queues[2] = new String[MAX_CUSTOMERS_QUEUE3];

        /* Scanner object creation */
        Scanner scanner = new Scanner(System.in);

        /* Varibale to store application running status */
        boolean appliactionRunStatus = true;

        while (appliactionRunStatus) {

            /* Display menu function */
            displayMenu();

            /* Varibale to store user input */
            String option = scanner.next();

            switch (option) {
                case "100":
                case "VFQ":
                    viewAllQueues();
                    break;

                case "101":
                case "VEQ":
                    viewEmptyQueues();
                    break;

                case "102":
                case "ACQ":
                    addCustomer(scanner);
                    break;

                case "103":
                case "RCQ":
                    removeCustomer(scanner);
                    break;

                 case "104":
                 case "PCQ":
                    removeServedCustomer(scanner);
                    break;
                case "105":
                case "VCS":
                    viewCustomersSorted();
                    break;

                case "106":
                case "SPD":
                    storeProgramData();
                    break;

                case "107":
                case "LPD":
                    loadProgramData();
                    break;

                case "108":
                case "STK":
                    viewRemainingBurgersStock();
                    break;

                case "109":
                case "AFS":
                    addBurgersToStock(scanner);
                    break;

                case "999":
                    System.out.println("Exiting the program...");
                    appliactionRunStatus = false;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    public static void displayMenu() {
        System.out.println("Menu Options:");
        System.out.println("100 or VFQ: View all Queues.");
        System.out.println("101 or VEQ: View all Empty Queues.");
        System.out.println("102 or ACQ: Add customer to a Queue.");
        System.out.println("103 or RCQ: Remove a customer from a Queue.");
        System.out.println("104 or PCQ: Remove a served customer.");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order.");
        System.out.println("106 or SPD: Store Program Data into file.");
        System.out.println("107 or LPD: Load Program Data from file.");
        System.out.println("108 or STK: View Remaining Burgers Stock.");
        System.out.println("109 or AFS: Add burgers to Stock.");
        System.out.println("999 or EXT: Exit the Program.");
        System.out.print("Enter your option: ");
    }

    private static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        int maxCustomers = Math.max(Math.max(queues[0].length, queues[1].length), queues[2].length);

        for (int i = 0; i < maxCustomers; i++) {
            for (int j = 0; j < queues.length; j++) {
                if (i < queues[j].length) {
                    if (queues[j][i] == null) {
                        System.out.print("X  ");
                    } else {
                        System.out.print("O  ");
                    }
                } else {
                    System.out.print("   ");
                }
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();

    }

    private static void viewEmptyQueues() {
        System.out.println("*****************");
        System.out.println("* Empty Queues *");
        System.out.println("*****************");

        int emptyslots = 0;
        for (int i = 0; i < queues.length; i++) {
            for (int j = 0; j < queues[i].length; j++) {
                if (queues[i][j] == null) {
                    emptyslots++;
                }
            }
            System.out.println((i + 1) + " Queue has " + emptyslots + " Empty slots.");
            emptyslots = 0;
        }
        System.out.println();
        System.out.println();
    }

    public static void addCustomer(Scanner scanner) {
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        if (queueNumber >= 1 && queueNumber <= 3) {
            int queueIndex = queueNumber - 1;
            int customerCountInSelectQueue = getCustomersCountFromQueues(queueIndex);

            if (queues[queueIndex].length != customerCountInSelectQueue) {
                System.out.print("Enter the customer name: ");
                String customerName = scanner.next();

                queues[queueIndex][customerCountInSelectQueue] = customerName;

                /* Assume each customer is served 5 burgers */
                remainingBurgers -= BURGERS_PER_CUSTOMER;
                if (remainingBurgers <= 10) {
                    System.out.println("Warning: Low stock of burgers!");
                }

                System.out.println("Customer added to Queue " + queueNumber + ".");
            } else {
                System.out.println("Queue " + queueNumber + " is full. Customer cannot be added.");
            }
        } else {
            System.out.println("Invalid queue number. Please try again.");
        }
        System.out.println();
    }

    private static void removeCustomer(Scanner scanner) {

        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        if (queueNumber >= 1 && queueNumber <= 3) {
            int queueIndex = queueNumber - 1;

            System.out.print("Enter the customer location (by numbers): ");
            int location = scanner.nextInt();
            int locationIndex = location - 1;

            Boolean isLocationNotEmpty = getCustomerByQueueLocation(queueIndex, locationIndex);

            if (isLocationNotEmpty) {
                /* Remove Customer */
                queues[queueIndex][locationIndex] = null;

                System.out.println("Customer removed from Queue numer:- " + queueNumber + ".");
            } else {
                System.out.println(
                        "Queue " + queueNumber + " Location " + queueNumber + " is empty. Customer cannot be removed.");
            }
        } else {
            System.out.println("Invalid queue number. Please try again.");
        }
        System.out.println();
    }

    private static void removeServedCustomer(Scanner scanner) {
        System.out.print("Served Customer is the first customer in the selected queue\n");
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        if (queueNumber >= 1 && queueNumber <= 3) {
            int queueIndex = queueNumber - 1;

            int locationIndex = 0;

            Boolean isLocationNotEmpty = getCustomerByQueueLocation(queueIndex, locationIndex);

            if (isLocationNotEmpty) {
                /* Remove Customer */
                queues[queueIndex][locationIndex] = null;

                System.out.println("Served Customer removed from Queue numer:- " + queueNumber + ".");
            } else {
                System.out.println(
                        "Queue " + queueNumber + " Location " + queueNumber + " is empty. Customer cannot be removed.");
            }
        } else {
            System.out.println("Invalid queue number. Please try again.");
        }
        System.out.println();
    }

    private static void viewCustomersSorted() {
        System.out.println("***********************");
        System.out.println("* Sorted Customer List *");
        System.out.println("***********************");

        List<String> customerNames = new ArrayList<>();

        // Iterate over the queues and collect customer names
        for (String[] queue : queues) {
            for (String customer : queue) {
                if (customer != null) {
                    customerNames.add(customer);
                }
            }
        }
        // Sort the customer names in alphabetical order
        Collections.sort(customerNames);

        for (String customer : customerNames) {
            System.out.println(customer);
        }

    }

    public static int getCustomersCountFromQueues(int queueNumber) {
        int cuslots = 0;
        for (int j = 0; j < queues[queueNumber].length; j++) {
            if (queues[queueNumber][j] != null) {
                cuslots++;
            }
        }
        return cuslots;
    }

    public static boolean getCustomerByQueueLocation(int queueNumber, int location) {

        if (queues[queueNumber][location] == null) {
            return false;
        }

        return true;
    }

    public static void viewRemainingBurgersStock() {
        System.out.println("********************");
        System.out.println("* Remaining Stock *");
        System.out.println("********************");

        System.out.println("Remaining burgers in stock: " + remainingBurgers);
        System.out.println();
    }

    public static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();

        remainingBurgers += burgersToAdd;

        System.out.println(burgersToAdd + " burgers added to stock.");
        System.out.println();
    }

    public static void storeProgramData() {
        try (FileWriter writer = new FileWriter("C:/Users/Ranugi Thihansa/Downloads/FoodiesFaveFoodCenter.txt")) {
            // Store the remaining burgers count
            writer.write("Remaining Burgers: " + remainingBurgers + "\n");
            writer.write("\n");

            // Store the queue data
            writer.write("*****************" + "\n");
            writer.write("*   Cashiers    *" + "\n");
            writer.write("*****************" + "\n");

            int maxCustomers = Math.max(Math.max(queues[0].length, queues[1].length), queues[2].length);

            for (int i = 0; i < maxCustomers; i++) {
                for (int j = 0; j < queues.length; j++) {
                    if (i < queues[j].length) {
                        if (queues[j][i] == null) {
                            writer.write("X  ");
                        } else {
                            writer.write("O  ");
                        }
                    } else {
                        writer.write("   ");
                    }
                }
                writer.write("\n");
            }


            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while storing program data: " + e.getMessage());
        }
    }

    public static void loadProgramData() {
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("C:/Users/Ranugi Thihansa/Downloads/FoodiesFaveFoodCenter.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
            System.out.println("Program data loaded successfully.");
       } catch (IOException e) {
            System.out.println("Error loading program data: " + e.getMessage());
        }
    }
}
