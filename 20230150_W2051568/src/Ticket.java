import java.io.FileWriter;
import java.io.IOException;
public class Ticket {
    private char row;
    private int seat;
    private int price;
    private Person person;

    public Ticket(char row, int seat, int price, Person person) {
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public char getRow() {
        return row;
    }

    public void setRow(char row) {
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void printInfo1() {
        System.out.print("Row: " + row + ",");
        System.out.print("Seat: " + seat + ",");
        System.out.println("Price: £" + price);
        System.out.println("Person Information:");
        person.printInfo();
    }
    public void save() {
        String fileName = row + String.valueOf(seat) + ".txt";
        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write("Ticket Information:\n");
            writer.write("Row: " + row + "\n");
            writer.write("Seat: " + seat + "\n");
            writer.write("Price: £" + price + "\n");
            writer.write("Person Information:\n");
            writer.write("Name: " + person.getName() + "\n");
            writer.write("Surname: " + person.getSurname() + "\n");
            writer.write("Email: " + person.getEmail() + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
        }
    }

}
