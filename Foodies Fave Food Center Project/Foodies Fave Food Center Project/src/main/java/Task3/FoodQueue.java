/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task3;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class FoodQueue {

    private int capacityOfQueue;
    private int lengthOfQueue = 0;
    private int incomeOfQueue;

    private ArrayList<Customer> customers = new ArrayList<>(capacityOfQueue);

    private ArrayList<Customer> waitingCustomersList = new ArrayList<>();

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public ArrayList<Customer> getWaitingCustomersList() {
        return waitingCustomersList;
    }

    public void addCustomerToQueue(Customer customer, int priceOfABurger) {
        this.customers.add(customer);
        this.incomeOfQueue += priceOfABurger * customer.getNoOfBurgersRequired();
        this.lengthOfQueue += 1;
    }

    public void addWaitingCustomerToQueue(Customer customer) {
        this.waitingCustomersList.add(customer);

    }

    public FoodQueue(int capacityOfQueue) {
        this.capacityOfQueue = capacityOfQueue;
    }

    /**
     * @return the capacityOfQueue
     */
    public int getCapacityOfQueue() {
        return capacityOfQueue;
    }

    /**
     * @param capacityOfQueue the capacityOfQueue to set
     */
    public void setCapacityOfQueue(int capacityOfQueue) {
        this.capacityOfQueue = capacityOfQueue;
    }

    /**
     * @return the lengthOfQueue
     */
    public int getLengthOfQueue() {
        return lengthOfQueue;
    }

    /**
     * @param lengthOfQueue the lengthOfQueue to set
     */
    public void setLengthOfQueue(int lengthOfQueue) {
        this.lengthOfQueue = lengthOfQueue;
    }

    /**
     * @return the incomeOfQueue
     */
    public int getIncomeOfQueue() {
        return incomeOfQueue;
    }

    /**
     * @param incomeOfQueue the incomeOfQueue to set
     */
    public void setIncomeOfQueue(int incomeOfQueue) {
        this.incomeOfQueue = incomeOfQueue;
    }
}
