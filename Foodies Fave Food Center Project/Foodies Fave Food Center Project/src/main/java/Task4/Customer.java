/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Task4;

/**
 *
 * @author User
 */
public class Customer {

    private String firstName;
    private String secondName;
    private int noOfBurgersRequired;

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    public Customer(String firstName, String secondName, int noOfBurgersRequired) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.noOfBurgersRequired = noOfBurgersRequired;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName the secondName to set
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    /**
     * @return the noOfBurgersRequired
     */
    public int getNoOfBurgersRequired() {
        return noOfBurgersRequired;
    }

    /**
     * @param noOfBurgersRequired the noOfBurgersRequired to set
     */
    public void setNoOfBurgersRequired(int noOfBurgersRequired) {
        this.noOfBurgersRequired = noOfBurgersRequired;
    }
}
