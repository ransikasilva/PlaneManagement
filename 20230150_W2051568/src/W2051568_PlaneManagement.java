import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;

public class W2051568_PlaneManagement {
    private static final int totalRows = 4; // Total rows in the plane
    private static final int[] seatsperRows = {14, 12, 12, 14}; // Number of seats in each row
    private static final int totalSeats = 52; // Total number of seats in the plane
    private static final int[][] seatAvailability = new int[totalRows][]; // the array for track the seat availability
    private static final Ticket[] soldTickets = new Ticket[totalSeats]; // Sold tickets storing array
    private static final Scanner input = new Scanner(System.in);


    public static void main(String[] args) {
        /* this method displaying the menu and looping the menu after every is task is done.
         * added a do while loop for menu until user input 0.
         * added a switch case for menu selection.
         * try catch for inputmissmatchexecption handling.
         */
        System.out.println("Welcome to the Plane Management System.");
        initializeSeats();

        int choice;
        do {
            //display menu
            System.out.println("***********************************");
            System.out.println("*             Menu                *");
            System.out.println("***********************************");
            System.out.println("    1. Buy a seat");
            System.out.println("    2. Cancel a seat");
            System.out.println("    3. Find first available");
            System.out.println("    4. Show seating plan");
            System.out.println("    5. Print tickets info");
            System.out.println("    6. Search ticket");
            System.out.println("    0. Quit");
            System.out.println("***********************************");
            System.out.print("Enter your choice: ");
            try {
                choice = input.nextInt();
                switch (choice) {
                    case 0:
                        System.out.println("Thank you for using the Plane Management System. Goodbye!");
                        break;
                    case 1:
                        buySeat();
                        break;
                    case 2:
                        cancelSeat();
                        break;
                    case 3:
                        findFirstAvailable();
                        break;
                    case 4:
                        showSeatingPlan();
                        break;
                    case 5:
                        printTicketsInfo();
                        break;
                    case 6:
                        searchTicket();
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                input.next();
                choice = -1; //setting the choice to -1 for stay in the loop
            }
        } while (choice != 0);
    }

    private static void initializeSeats() {
        /* this method runs in main method. this method initialize all seats as available.
         */
        for (int i = 0; i < totalRows; i++) {
            seatAvailability[i] = new int[seatsperRows[i]];
            for (int j = 0; j < seatsperRows[i]; j++) {
                seatAvailability[i][j] = 0; //marking all seats available
            }
        }
    }
    private static void buySeat() {
        /* It prompts the user for seat selection.
         *  validates the input determines the price based on seat number.
         *  collects person information.
         *  creates a Ticket object.
         *  marks the seat as sold.
         *  saves ticket information to a file.
         */
        char row;
        int seatNumber;
        int price;

        while (true) {
            //prompting and validating row letter and seat number
            System.out.print("Enter row letter (A-D): ");
            String rowInput = input.next().toUpperCase();
            if (!rowInput.matches("[A-D]")) {
                System.out.println("Invalid row letter. Please enter a letter from A to D.");
                continue;
            }
            row = rowInput.charAt(0);
            int rowIndex = row - 'A';
            int maxSeatNumber = seatsperRows[rowIndex];
            System.out.print("1-14 for rows A and D, 1-12 for rows B and C): ");
            try {
                seatNumber = input.nextInt();
                if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                    System.out.println("Invalid seat number for the selected row.");
                    System.out.println("1-14 for rows A and D, 1-12 for rows B and C");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the seat number.");
                input.nextLine();
            }
        }
        //assigning prices based on seat number
        if (seatNumber <= 5) {
            price = 200;
        } else if (seatNumber <= 9) {
            price = 150;
        } else {
            price = 180;
        }
        //checking if the seat is already sold
        if (seatAvailability[row - 'A'][seatNumber - 1] == 1) {
            System.out.println("Seat " + row + seatNumber + " is already sold.");
            return;
        }
        //getting person information
        System.out.print("Enter name: ");
        String name = input.next();
        System.out.print("Enter surname: ");
        String surname = input.next();
        System.out.print("Enter email: ");
        String email = input.next();
        Person person = new Person(name, surname, email);
        Ticket ticket = new Ticket(row, seatNumber, price, person);

        seatAvailability[row - 'A'][seatNumber - 1] = 1;//marking seat as sold
        //adding ticket to list of sold tickets
        for (int i = 0; i < soldTickets.length; i++) {
            if (soldTickets[i] == null) {
                soldTickets[i] = ticket;
                break;
            }
        }
        ticket.save();//saving the ticket info to a txt file
        System.out.println("Seat " + row + seatNumber + " sold. Price = €" + price + ".");
    }
    private static void cancelSeat() {
        /* This method handles the process of cancelling a seat.
         *  It prompts the user for seat selection.
         *  validates the input.
         *  marks the seat as available
         *  deletes the corresponding ticket file and removes the ticket from the list of sold tickets.
         */
        char row;
        int seatNumber;
        while (true) {
            //prompting and validating row letter and seat number
            System.out.print("Enter row letter (A-D): ");
            String rowInput = input.next().toUpperCase();
            if (!rowInput.matches("[A-D]")) {
                System.out.println("Invalid row letter. Please enter a letter from A to D.");
                continue;
            }
            row = rowInput.charAt(0);
            int rowIndex = row - 'A';
            int maxSeatNumber = seatsperRows[rowIndex];

            System.out.print("1-14 for rows A and D, 1-12 for rows B and C ): ");
            try {
                seatNumber = input.nextInt();
                if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                    System.out.println("Invalid seat number for the selected row.");
                    System.out.println("1-14 for rows A and D, 1-12 for rows B and C");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the seat number.");
                input.nextLine();
            }
        }
        int seatIndex = seatNumber - 1;
        //checking if the seat is already sold
        if (seatAvailability[row - 'A'][seatIndex] == 0) {
            System.out.println("Seat " + row + seatNumber + " is not sold.");
            return;
        }
        seatAvailability[row - 'A'][seatIndex] = 0;//marking seat as available
        //removing ticket from list of sold tickets
        for (int i = 0; i < soldTickets.length; i++) {
            if (soldTickets[i] != null && soldTickets[i].getRow() == row && soldTickets[i].getSeat() == seatNumber) {
                String fileName = row + String.valueOf(seatNumber) + ".txt";
                File file = new File(fileName);
                file.delete();//deleting the txt file
                soldTickets[i] = null;
                break;
            }
        }
        System.out.println("Seat " + row + seatNumber + " has been cancelled.");
    }

    private static void findFirstAvailable() {
        /* This method finds the first available seat in the plane.
         *  It iterates through all seats and rows to find the first available seat and prints its information.
         */
        char[] rows = {'A', 'B', 'C', 'D'};
        //iterating through each row
        for (char row : rows) {
            int rowIndex = row - 'A';
            int maxSeatNumber = seatsperRows[rowIndex];
            // Iterate through each seat in the row
            for (int seatNumber = 1; seatNumber <= maxSeatNumber; seatNumber++) {
                int seatIndex = seatNumber - 1;
                // Check if the seat is available
                if (seatAvailability[rowIndex][seatIndex] == 0) {
                    System.out.println("First available seat: Row " + row + ", Seat " + seatNumber);
                    return;
                }
            }
        }
        System.out.println("All seats are sold.");
    }
    private static void showSeatingPlan() {
        /*This method displays the seating plan of the plane.
         *It prints a visual representation of the seating plan with available (O) and sold (X) seats.
         */
        System.out.println("\nSeating Plan:");
        System.out.println("    1  2  3  4  5  6  7  8  9  10 11 12 13 14");
        for (int i = 0; i < totalRows; i++) {
            char row = (char) ('A' + i);
            System.out.print(row + "  ");
            for (int j = 0; j < seatsperRows[i]; j++) {
                // Check if the current seat is available (0) or sold (1)
                if (seatAvailability[i][j] == 0) {
                    System.out.print(" O ");
                } else {
                    System.out.print(" X ");
                }
            }
            System.out.println();
        }
    }
    private static void printTicketsInfo() {
        /*This method prints information about all sold tickets.
         *  It iterates through the list of sold tickets, prints ticket information, and calculates the total amount earned.
         */
        System.out.println("\nTickets Information:\n");
        int totalAmount = 0;
        // Iterate through the array of sold tickets
        for (int i = 0; i < soldTickets.length; i++) {
            Ticket ticket = soldTickets[i]; // Get the current ticket
            if (ticket != null) {
                ticket.printInfo1();
                totalAmount += ticket.getPrice();
            }
        }
        System.out.println("Total amount: £" + totalAmount);
    }
    private static void searchTicket() {
        /*  This method searches for a ticket based on user input.
         *  It prompts the user for seat selection.
         *  validates the input and searches for the ticket.
         *  If found, it prints ticket information; otherwise, it indicates that the seat is available.
         */
        char row;
        int seatNumber;
        int maxSeatNumber;

        while (true) {
            //prompting and validating row letter and seat number
            System.out.print("Enter row letter (A-D): ");
            String rowInput = input.next().toUpperCase();
            if (!rowInput.matches("[A-D]")) {
                System.out.println("Invalid row letter. Please enter a letter from A to D.");
                continue;
            }
            row = rowInput.charAt(0);
            if (row == 'A' || row == 'D') {
                maxSeatNumber = 14;
            } else {
                maxSeatNumber = 12;
            }
            System.out.print("Enter seat number (1-14 for rows A and D, 1-12 for rows B and C): ");
            try {
                seatNumber = input.nextInt();
                if (seatNumber < 1 || seatNumber > maxSeatNumber) {
                    System.out.println("Invalid seat number for the selected row.");
                    System.out.println("1-14 for rows A and D, 1-12 for rows B and C");
                    continue;
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer for the seat number.");
                input.nextLine();
            }
        }
        // Search for the ticket
        boolean ticketFound = false;
        for (Ticket ticket : soldTickets) {
            if (ticket != null && ticket.getRow() == row && ticket.getSeat() == seatNumber) {
                System.out.println("Ticket found:\n");
                ticket.printInfo1();
                ticketFound = true;
                break;
            }
        }
        // If ticket not found, indicate that the seat is available
        if (!ticketFound) {
            System.out.println("This seat is available " + row + seatNumber + ".");
        }
    }
}