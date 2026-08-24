/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.hospitaladmissionsystem;
import java.util.Scanner;
/**
 *
 * @author Kabelo Mogale
 */
public class HospitalAdmissionSystem {

   //Configuration constants
    private static final int MAX_PATIENTS = 50; //Maximum number of patients
    private static final int WARD_ROWS = 4; //Number of ward rows
    private static final int WARD_COLS = 5; //Number of ward columns
    private static final int TOTAL_BEDS = WARD_ROWS * WARD_COLS; //Total Number of beds
    private static final int WARD_NUMBER = 1; // this system manages a single ward
    private static final String NOT_ASSIGNED = "Not Assigned"; //Return when a bed is not assigned
    
    
    private static Patient[] patients = new Patient[MAX_PATIENTS];
    private static int patientCount = 0;
 
    private static Bed[] beds = new Bed[TOTAL_BEDS];
 
    private static Scanner scanner = new Scanner(System.in);
 
    public static void main(String[] args) {
        setUpWard();
        
        //Main Menu
        boolean running = true;
        while (running) {
            System.out.println("\n========== MediCare Hospital Admission System ==========");
            System.out.println("1. Patient Management");
            System.out.println("2. Bed Management");
            System.out.println("3. Reports");
            System.out.println("0. Exit");
            System.out.println("==========================================================");
 
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    patientManagementMenu();
                    break;
                case 2:
                    bedManagementMenu();
                    break;
                case 3:
                    reportsMenu();
                    break;
                case 0:
                    running = false;
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select an option from the menu.");
            }
        }
 
        scanner.close();
    }
 
    //Creating beds B01 to B20 (TOTAL_BEDS beds), all empty.
    private static void setUpWard() {
        for (int i = 0; i < TOTAL_BEDS; i++) {
            int bedPosition = i + 1; // 1, 2, 3, ... TOTAL_BEDS
 
            String bedNumber;
            if (bedPosition < 10) {
                //Single-digit positions get a leading zero(from 1-9)
                bedNumber = "B0" + bedPosition;
            } else {
                //Already has two digits(starting from 10)
                bedNumber = "B" + bedPosition;
            }
 
            beds[i] = new Bed(bedNumber);
        }
    }
 
    
    // FEATURE 1: PATIENT MANAGEMENT
    private static void patientManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- Patient Management -----");
            System.out.println("1. Register a new patient");
            System.out.println("2. Search for a patient by Patient ID");
            System.out.println("3. Update an existing patient's details");
            System.out.println("4. Delete a patient");
            System.out.println("5. Display all registered patients");
            System.out.println("0. Back to main menu");
            
            //Prompting the user to enter their choice
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    registerPatient();
                    break;
                case 2:
                    searchPatient();
                    break;
                case 3:
                    updatePatient();
                    break;
                case 4:
                    deletePatient();
                    break;
                case 5:
                    displayAllPatients();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    //For registering a new patient
    private static void registerPatient() {
        if (patientCount >= MAX_PATIENTS) {
            System.out.println("Cannot register patient: the system is full.\n"); //Stops registering when the system is full
            return;
        }
 
        System.out.println("\n--- Register New Patient ---");
        
        //Entering patient's details
        String id = readNonEmptyString("Enter Patient ID: ");
        if (findPatientIndex(id) != -1) {
            System.out.println("A patient with this ID already exists. Registration cancelled.\n"); //Return if the patient already exists
            return;
        }
        
        /* Used "non-empty strings" to prompt the user to actually enter information, 
        the program will not read anything without its real value(empty spaces) */
        String firstName = readNonEmptyString("Enter First Name: ");
        String lastName = readNonEmptyString("Enter Last Name: ");
        int age = readPositiveInt("Enter Age: ");
        String gender = readNonEmptyString("Enter Gender: ");
        String condition = readNonEmptyString("Enter Medical Condition: ");
        PatientCategory category = readCategory();
 
        Patient newPatient;
        if (category == PatientCategory.INPATIENT) {
            /*An Inpatient starts with no bed - that is assigned separately 
            in Bed Management, once the bed is actually allocated.*/
            newPatient = new Inpatient(id, firstName, lastName, age, gender, condition,
                    WARD_NUMBER, NOT_ASSIGNED);
        } else {
            newPatient = new Patient(id, firstName, lastName, age, gender, condition, category); //Stores patient information
        }
 
        patients[patientCount] = newPatient;
        patientCount++;
 
        System.out.println("Patient registered successfully!\n");
    }
    
    //Searching for a patient
    private static void searchPatient() {
        System.out.println("\n--- Search Patient ---");
        String id = readNonEmptyString("Enter Patient ID to search: ");
        
        int index = findPatientIndex(id);
        if (index == -1) {
            System.out.println("No patient found with ID: " + id + "\n"); //Return message if the patient is not found
        } else {
            System.out.println("Patient found:");//Return message if the patient is found
            patients[index].displayDetails();
            System.out.println();
        }
    }
    //Updating a patient
    private static void updatePatient() {
        System.out.println("\n--- Update Patient ---");
        String id = readNonEmptyString("Enter Patient ID to update: ");
 
        int index = findPatientIndex(id);
        if (index == -1) {
            System.out.println("No patient found with ID: " + id + "\n");//Invalid patient ID
            return;
        }
        
        
        Patient patient = patients[index];
        System.out.println("Current details:");
        patient.displayDetails();
        System.out.println("Leave a field blank and press Enter to keep its current value.");
        System.out.println("(Category cannot be changed here - delete and re-register if needed.)\n");
        
        //Prompting the user to enter new name for the update
        System.out.print("New First Name [" + patient.getFirstName() + "]: ");
        String firstName = scanner.nextLine();
        if (!firstName.isBlank()) {
            patient.setFirstName(firstName.trim());
        }
        //Prompting the user to enter new last name for the update
        System.out.print("New Last Name [" + patient.getLastName() + "]: ");
        String lastName = scanner.nextLine();
        if (!lastName.isBlank()) {
            patient.setLastName(lastName.trim());
        }
        
        //Prompting the user to enter new age for the update
        System.out.print("New Age [" + patient.getAge() + "]: ");
        String ageInput = scanner.nextLine();
        if (!ageInput.isBlank()) {
            try {
                int age = Integer.parseInt(ageInput.trim());
                if (age > 0) {
                    patient.setAge(age);
                } else {
                    System.out.println("Age must be positive. Age was not changed.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Age was not changed.");
            }
        }
        //Prompting the user to enter new gender for the update
        System.out.print("New Gender [" + patient.getGender() + "]: ");
        String gender = scanner.nextLine();
        if (!gender.isBlank()) {
            patient.setGender(gender.trim());
        }
        
        //Prompting the user to enter new medical condition for the update
        System.out.print("New Medical Condition [" + patient.getMedicalCondition() + "]: ");
        String condition = scanner.nextLine();
        if (!condition.isBlank()) {
            patient.setMedicalCondition(condition.trim());
        }
 
        System.out.println("Patient updated successfully!\n");
    }
    
    //For deleting a patient
    private static void deletePatient() {
        System.out.println("\n--- Delete Patient ---");
        String id = readNonEmptyString("Enter Patient ID to delete: "); //Entering patient ID for searching and deleting
        
        //Saarching for patient using ID
        int index = findPatientIndex(id);
        if (index == -1) {
            System.out.println("No patient found with ID: " + id + "\n"); //Return message if ID was not found in the sytem
            return;
        }
        
        //Deleting the patient if found
        Patient patient = patients[index];
        System.out.println("Patient to delete:");
        patient.displayDetails();
        String confirm = readNonEmptyString("Are you sure? (yes/no): "); //Verifying the deletion
 
        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Deletion cancelled.\n"); //Cancels the deletion if the user answered "no"
            return;
        }
 
        /* If this patient currently occupies a bed, it prompts the user to free that bed first
        so the ward doesn't end up with a ghost occupant. */
        if (patient instanceof Inpatient) {
            Inpatient inpatient = (Inpatient) patient;
            if (!inpatient.getBedNumber().equals(NOT_ASSIGNED)) {
                Bed bed = findBedByNumber(inpatient.getBedNumber());
                if (bed != null) {
                    bed.release();
                }
            }
        }
 
        //Shift every following patient one position to the left.
        for (int i = index; i < patientCount - 1; i++) {
            patients[i] = patients[i + 1];
        }
        patients[patientCount - 1] = null;
        patientCount--;
 
        System.out.println("Patient deleted successfully!\n");
    }
    
    //Displaying all registered patients
    private static void displayAllPatients() {
        System.out.println("\n--- All Registered Patients ---");
 
        if (patientCount == 0) {
            System.out.println("No patients are currently registered.\n");//Return message when patients are not registered
            return;
        }
        
        //Counting all registered patients
        for (int i = 0; i < patientCount; i++) {
            System.out.print((i + 1) + ". ");
            patients[i].displayDetails();
        }
        System.out.println("Total patients: " + patientCount + "\n"); //Displaying the number of registered patients
    }
 
    
    // FEATURE 2: BED MANAGEMENT
    
    //Second Main Menu
    private static void bedManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- Bed Management -----");
            System.out.println("1. Allocate a bed to an inpatient");
            System.out.println("2. Release a bed");
            System.out.println("3. Display the complete ward layout");
            System.out.println("4. Display available beds");
            System.out.println("5. Display occupied beds");
            System.out.println("0. Back to main menu");
            
            //Prompting the user to enter their choice
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    allocateBed();
                    break;
                case 2:
                    releaseBed();
                    break;
                case 3:
                    displayWardLayout();
                    break;
                case 4:
                    displayAvailableBeds();
                    break;
                case 5:
                    displayOccupiedBeds();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");//If the user enters an invalid choice, it returns this message
            }
        }
    }
    
    //Diplaying beds' details
    private static void allocateBed() {
        System.out.println("\n--- Allocate Bed ---");
 
        if (countAvailableBeds() == 0) {
            System.out.println("No beds are available. Allocation cannot proceed.\n"); //Return message when all beds are occupied
            return;
        }
        //Searching for a patient
        String id = readNonEmptyString("Enter Patient ID: ");
        int index = findPatientIndex(id);
        if (index == -1) {
            System.out.println("No patient found with ID: " + id + "\n"); //Return message when patient are not found
            return;
        }
        
        //Allowing inpatience to be allocated with beds
        Patient patient = patients[index];
        if (!(patient instanceof Inpatient)) {
            System.out.println("Only Inpatients may be allocated a bed. This patient is "
                    + patient.getCategory() + ".\n"); //Displays patient's category after finding that they are not inpatients
            return;
        }
        //Check if patient is allocated
        Inpatient inpatient = (Inpatient) patient;
        if (!inpatient.getBedNumber().equals(NOT_ASSIGNED)) {
            System.out.println("This patient already occupies bed " + inpatient.getBedNumber() + ".\n");
            return;
        }
        
        //Displaying available beds
        displayAvailableBeds();
        String bedNumber = readNonEmptyString("Enter the bed number to allocate (e.g. B01): ").toUpperCase();
        
        Bed bed = findBedByNumber(bedNumber);
        if (bed == null) {
            System.out.println("There is no bed with that number.\n"); //Invalid bed number
            return;
        }
        if (bed.isOccupied()) {
            System.out.println("That bed is already occupied.\n"); //If/When the bed is already occupied
            return;
        }
        
        //Allocating patients with bed
        bed.occupy(id);
        inpatient.setWardNumber(WARD_NUMBER);
        inpatient.setBedNumber(bedNumber);
 
        System.out.println("Bed " + bedNumber + " allocated to patient " + id + " successfully!\n"); //Diplaying allocated beds info
    }
    
    //Releaing the bed
    private static void releaseBed() {
        System.out.println("\n--- Release Bed ---");
 
        String id = readNonEmptyString("Enter Patient ID whose bed should be released: ");
        int index = findPatientIndex(id);
        if (index == -1) {
            System.out.println("No patient found with ID: " + id + "\n"); //If the entered ID i invalid
            return;
        }
        
        //Not allocating bed if the patient is not inpatient
        Patient patient = patients[index];
        if (!(patient instanceof Inpatient)) {
            System.out.println("This patient is not an Inpatient and does not occupy a bed.\n");
            return;
        }
        //If no bed was allocated
        Inpatient inpatient = (Inpatient) patient;
        if (inpatient.getBedNumber().equals(NOT_ASSIGNED)) {
            System.out.println("This patient does not currently occupy a bed.\n");
            return;
        }
 
        Bed bed = findBedByNumber(inpatient.getBedNumber());
        if (bed != null) {
            bed.release();
        }
        inpatient.setBedNumber(NOT_ASSIGNED);
 
        System.out.println("Bed released successfully!\n");
    }
 
    //Prints the ward as a WARD_ROWS x WARD_COLS grid, e.g. if occupied = B01[X], if not occupied = B02[ ]
    private static void displayWardLayout() {
        System.out.println("\n--- Ward Layout ('X' = occupied, ' ' = available) ---");
        for (int row = 0; row < WARD_ROWS; row++) {
            StringBuilder line = new StringBuilder();
            for (int col = 0; col < WARD_COLS; col++) {
                Bed bed = beds[row * WARD_COLS + col];
                String marker = bed.isOccupied() ? "X" : " ";
                line.append(bed.getBedNumber()).append("[").append(marker).append("]  ");
            }
            System.out.println(line.toString());
        }
        System.out.println();
    }
    
    //Displaying available beds
    private static void displayAvailableBeds() {
        System.out.println("\n--- Available Beds ---");
        boolean any = false;
        for (Bed bed : beds) {
            if (!bed.isOccupied()) {
                System.out.println(bed.getBedNumber());
                any = true;
            }
        }
        if (!any) {
            System.out.println("No beds are currently available."); //Return message for when the beds are unavailable
        }
        System.out.println();
    }
    
    //Displaying occupied beds
    private static void displayOccupiedBeds() {
        System.out.println("\n--- Occupied Beds ---");
        boolean any = false;
        for (Bed bed : beds) {
            if (bed.isOccupied()) {
                System.out.println(bed.getBedNumber() + " - Patient ID: " + bed.getPatientID());
                any = true;
            }
        }
        if (!any) {
            System.out.println("No beds are currently occupied."); //If/When beds are not occupied
        }
        System.out.println();
    }
    //Counting available beds
    private static int countAvailableBeds() {
        int count = 0;
        for (Bed bed : beds) {
            if (!bed.isOccupied()) {
                count++;
            }
        }
        return count;
    }
    
    private static int countOccupiedBeds() {
        return TOTAL_BEDS - countAvailableBeds();
    }
 
    private static Bed findBedByNumber(String bedNumber) {
        for (Bed bed : beds) {
            if (bed.getBedNumber().equalsIgnoreCase(bedNumber)) {
                return bed;
            }
        }
        return null;
    }
 
    
    // FEATURE 3: REPORTS
    private static void reportsMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\n----- Reports -----");
            System.out.println("1. Display all registered patients");
            System.out.println("2. Display all available beds");
            System.out.println("3. Display all occupied beds");
            System.out.println("4. Display total number of registered patients");
            System.out.println("5. Display total number of occupied beds");
            System.out.println("6. Display ward occupancy percentage");
            System.out.println("7. Display full ward report (all of the above)");
            System.out.println("0. Back to main menu");
            
            //Prompting the user to enter their choice
            int choice = readInt("Enter your choice: ");
            switch (choice) {
                case 1:
                    displayAllPatients();
                    break;
                case 2:
                    displayAvailableBeds();
                    break;
                case 3:
                    displayOccupiedBeds();
                    break;
                case 4:
                    System.out.println("Total registered patients: " + patientCount + "\n");
                    break;
                case 5:
                    System.out.println("Total occupied beds: " + countOccupiedBeds() + "\n");
                    break;
                case 6:
                    displayOccupancyPercentage();
                    break;
                case 7:
                    displayFullReport();
                    break;
                case 0:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    //Displaying the occupied beds with percentage
    private static void displayOccupancyPercentage() {
        double percentage = (countOccupiedBeds() * 100.0) / TOTAL_BEDS;
        System.out.printf("Ward occupancy: %.1f%%%n%n", percentage);
    }
    
    //Displaying the full report
    private static void displayFullReport() {
        System.out.println("\n================ WARD REPORT ================");
        displayAllPatients();
        displayAvailableBeds();
        displayOccupiedBeds();
        System.out.println("Total registered patients: " + patientCount);
        System.out.println("Total occupied beds: " + countOccupiedBeds() + " / " + TOTAL_BEDS);
        displayOccupancyPercentage();
        System.out.println("===============================================\n");
    }
 
    
    // Shared helper methods
    
    private static int findPatientIndex(String id) {
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getPatientID().equalsIgnoreCase(id)) {
                return i;
            }
        }
        return -1;
    }
 
    private static String readNonEmptyString(String prompt) {
        String value;
        while (true) {
            System.out.print(prompt);
            value = scanner.nextLine().trim();
            if (!value.isEmpty()) {
                return value;
            }
            System.out.println("This field cannot be empty. Please try again.");
        }
    }
 
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }
 
    private static int readPositiveInt(String prompt) {
        while (true) {
            int value = readInt(prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Please enter a number greater than 0.");
        }
    }
 
    private static PatientCategory readCategory() {
        while (true) {
            String input = readNonEmptyString("Enter Category (Inpatient/Outpatient/Emergency): ");
            if (input.equalsIgnoreCase("Inpatient")) {
                return PatientCategory.INPATIENT;
            } else if (input.equalsIgnoreCase("Outpatient")) {
                return PatientCategory.OUTPATIENT;
            } else if (input.equalsIgnoreCase("Emergency")) {
                return PatientCategory.EMERGENCY;
            }
            System.out.println("Invalid category. Please type Inpatient, Outpatient, or Emergency.");
        }
    }
}