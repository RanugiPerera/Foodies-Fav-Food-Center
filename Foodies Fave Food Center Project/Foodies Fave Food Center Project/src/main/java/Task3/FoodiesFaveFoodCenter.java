/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package Task3;

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

    private static final int MAX_CUSTOMERS_QUEUE1 = 2;
    private static final int MAX_CUSTOMERS_QUEUE2 = 3;
    private static final int MAX_CUSTOMERS_QUEUE3 = 5;
    private static final int MAX_BURGERS_STOCK = 50;
    private static final int BURGERS_PER_CUSTOMER = 5;
    private static final int PRICE_OF_A_BURGER = 650;

    private static int remainingBurgers = MAX_BURGERS_STOCK;

    private static ArrayList<FoodQueue> queues = new ArrayList<>();
    private static String[][] queueDisplayList = new String[5][3];

    public static void main(String[] args) {
        queues.add(new FoodQueue(MAX_CUSTOMERS_QUEUE1));
        queues.add(new FoodQueue(MAX_CUSTOMERS_QUEUE2));
        queues.add(new FoodQueue(MAX_CUSTOMERS_QUEUE3));

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

                case "110":
                case "IFQ":
                    viewIncomeOfEachQueue();
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

        System.out.println("Hello World!");

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
        System.out.println("110 or IFQ: View Income of each Queue.");
        System.out.println("999 or EXT: Exit the Program.");
        System.out.print("Enter your option: ");
    }

    private static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers    *");
        System.out.println("*****************");

        initializeQueueDisplayList();

        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 3; column++) {
                System.out.print(queueDisplayList[row][column]);
            }
            System.out.println();
        }

    }

    public static void initializeQueueDisplayList() {
        for (int row = 0; row < 5; row++) {
            for (int column = 0; column < 3; column++) {
                if (queues.get(column).getCapacityOfQueue() < (row + 1)) {
                    queueDisplayList[row][column] = "  ";
                } else if (queues.get(column).getLengthOfQueue() < (row + 1)) {
                    queueDisplayList[row][column] = "X ";
                } else {
                    queueDisplayList[row][column] = "O ";
                }
            }
        }
    }




    private static void viewEmptyQueues() {
        System.out.println("*****************");
        System.out.println("* Empty Queues *");
        System.out.println("*****************");

        int count = 1;
        int noOfEmptySlots = 0;
        for (FoodQueue queue : queues) {
            int lengthOfQueue = queue.getLengthOfQueue();
            noOfEmptySlots = queue.getCapacityOfQueue() - lengthOfQueue;
            System.out.println(" Queue " + count + " has " + noOfEmptySlots + " Empty slots.");
            count += 1;
            noOfEmptySlots = 0;
        }

    }

    public static Customer getCustomerDetails(Scanner scanner) {
        System.out.print("Enter the customer first name: ");
        String customerFirstName = scanner.next();
        System.out.print("Enter the customer second name: ");
        String customerSecondName = scanner.next();
        System.out.print("Enter the customer required number of burgers: ");
        int noOfBurgers = scanner.nextInt();

        Customer customer = new Customer(customerFirstName, customerSecondName, noOfBurgers);
        return customer;
    }

    public static void addCustomer(Scanner scanner) {
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        if (queueNumber >= 1 && queueNumber <= 3) {
            int queueIndex = queueNumber - 1;
            int customerCountInSelectQueue = getCustomersCountFromQueues(queueIndex);

            FoodQueue queueToAdd = queues.get(queueIndex);

            Customer customer = getCustomerDetails(scanner);

            if (queueToAdd.getCapacityOfQueue() != customerCountInSelectQueue) {

                queueToAdd.addCustomerToQueue(customer, PRICE_OF_A_BURGER);

                /* Assume each customer is served 5 burgers */
                remainingBurgers -= customer.getNoOfBurgersRequired();
                if (remainingBurgers <= 10) {
                    System.out.println("Warning: Low stock of burgers!");
                }

                System.out.println("Customer added to Queue " + queueNumber + ".");
            } else {

                /*Adding customer to the waiting list of the queue*/
                System.out.println("Queue " + queueNumber + " is full. Customer will be added to the waiting list.");
                queueToAdd.addWaitingCustomerToQueue(customer);
            }
        } else {
            System.out.println("Invalid queue number. Please try again.");
        }
        System.out.println();
    }

    public static void addWaitingCustomerToTheQueueAfterRemovingACustomer(int queueNumber, Customer waitingCustomer) {

        if (queueNumber >= 1 && queueNumber <= 3) {
            int queueIndex = queueNumber - 1;
            int customerCountInSelectQueue = getCustomersCountFromQueues(queueIndex);
            FoodQueue queueToAdd = queues.get(queueIndex);

            if (queueToAdd.getCapacityOfQueue() != customerCountInSelectQueue) {

                queueToAdd.addCustomerToQueue(waitingCustomer, PRICE_OF_A_BURGER);
                /*Removing the customer from waiting list*/
                queueToAdd.getWaitingCustomersList().remove(0);
                remainingBurgers -= waitingCustomer.getNoOfBurgersRequired();
                if (remainingBurgers <= 10) {
                    System.out.println("Warning: Low stock of burgers!");
                }

                System.out.println("Waiting Customer " + waitingCustomer.getFirstName() + " added to Queue " + queueNumber + ".");
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
                FoodQueue queue = queues.get(queueIndex);
                ArrayList<Customer> customersOfQueue = queue.getCustomers();
                Customer customer = customersOfQueue.get(locationIndex);
                int minusIncome = customer.getNoOfBurgersRequired() * PRICE_OF_A_BURGER;
                customersOfQueue.remove(locationIndex);

                /*reducing length of queue*/
                queue.setLengthOfQueue(queue.getLengthOfQueue() - 1);

                /*reducing income of queue*/
                queue.setIncomeOfQueue(queue.getIncomeOfQueue() - minusIncome);

                System.out.println("Customer removed from Queue number:- " + queueNumber + ".");

                /*Add the first waiting customer to the queue*/
                ArrayList<Customer> waitingCustomers = queue.getWaitingCustomersList();
                if (!waitingCustomers.isEmpty()) {
                    addWaitingCustomerToTheQueueAfterRemovingACustomer(queueNumber, waitingCustomers.get(0));
                }
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
        System.out.print("Served Customer is the first customer in any selected queue.\n");
        System.out.print("Enter the queue number (1, 2, or 3): ");
        int queueNumber = scanner.nextInt();

        if (queueNumber >= 1 && queueNumber <= 3) {
            int queueIndex = queueNumber - 1;

            int locationIndex = 0;

            Boolean isLocationNotEmpty = getCustomerByQueueLocation(queueIndex, locationIndex);

            if (isLocationNotEmpty) {
                /* Remove Customer */
                FoodQueue queue = queues.get(queueIndex);
                ArrayList<Customer> customersOfQueue = queue.getCustomers();
                customersOfQueue.remove(locationIndex);
                queue.setLengthOfQueue(queue.getLengthOfQueue() - 1);

                System.out.println("Served Customer removed from Queue number:- " + queueNumber + ".");

                /*Add the first waiting customer to the queue*/
                ArrayList<Customer> waitingCustomers = queue.getWaitingCustomersList();
                if (!waitingCustomers.isEmpty()) {
                    addWaitingCustomerToTheQueueAfterRemovingACustomer(queueNumber, waitingCustomers.get(0));
                }

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
        for (FoodQueue queue : queues) {
            ArrayList<Customer> customersOfQueue = queue.getCustomers();
            for (Customer customer : customersOfQueue) {
                customerNames.add(customer.getFirstName());
            }
        }

        // Sort the customer names in alphabetical order
        Collections.sort(customerNames);

        for (String customer : customerNames) {
            System.out.println(customer);
        }

    }

    public static int getCustomersCountFromQueues(int queueNumber) {

        return queues.get(queueNumber).getLengthOfQueue();
    }

    public static boolean getCustomerByQueueLocation(int queueNumber, int location) {

        if (queues.get(queueNumber).getLengthOfQueue() == 0 || queues.get(queueNumber).getCustomers().get(location) == null) {
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

    public static void viewIncomeOfEachQueue() {
        System.out.println("********************");
        System.out.println("* Income of each Queue *");
        System.out.println("********************");
        int queue_num = 1;
        for (FoodQueue queue : queues) {
            System.out.println("Income of Queue " + queue_num + " : " + queue.getIncomeOfQueue());
            queue_num += 1;
        }
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


        try (FileWriter writer = new FileWriter("C:\\Users\\Ranugi Thihansa\\Downloads\\Foodies Fave Food Center Project\\Foodies Fave Food Center Project\\src\\FoodiesFaveFoodCenter.txt")) {
            // Store the remaining burgers count
            writer.write("Remaining Burgers: " + remainingBurgers + "\n");
            writer.write("\n");

            // Store the queue data
            writer.write("*****************" + "\n");
            writer.write("*   Cashiers    *" + "\n");
            writer.write("*****************" + "\n");


            for (FoodQueue queue : queues) {
                ArrayList<Customer> customers = queue.getCustomers();
                for (int j = 0; j < queue.getCapacityOfQueue(); j++) {
                    if (j < queue.getLengthOfQueue()) {
                        writer.write("O  ");
                    } else {
                        writer.write("X  ");
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
                    new FileReader("C:\\Users\\Ranugi Thihansa\\Downloads\\Foodies Fave Food Center Project\\Foodies Fave Food Center Project\\src\\FoodiesFaveFoodCenter.txt"));
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
