/* Name: Sarah Centeno
   Course: CNT 4714 – Fall 2019
   Assignment title: Program 1 – Event-driven Programming
   Date: Sunday September 22, 2019
*/

import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// The class transaction will implement the booked ordered by the user and be put into
// the array list of bookOrder. This will get the overall price total with tax of all
// books purchased and when the order is complete, there will be a popup window of the
// receipt of the overall transaction and then prints that transaction into the
// transaction output file.
public class transaction {

    private List<bookOrder> books;
    private Date date;
    private final double tax = 0.06;

    public transaction() {
        // Adds books that are being ordered into the array list
        // with the date of when it was added to the transaction
        this.books = new ArrayList<bookOrder>();
        this.date = new Date();
    }

    // Returns the number of books in the transaction
    public int size() {
        return this.books.size();
    }

    // Adds the book into the overall transaction order
    public void addBook(final bookOrder book) {
        this.books.add(book);
    }

    // Gets the total price of all the books purchased
    public double totalCost() {
        double total = 0.0;
        for (final bookOrder book : this.books) {
            total +=book.totalCost();
        }
        return total;
    }

    // Calculates the price of tax
    public double taxPrice() {
        return this.totalCost() * tax;
    }

    public String toString() {
        String string = "";
        for (int i = 0; i < this.books.size(); ++i) {
            string = string + (i + 1) + ". " + this.books.get(i).toString(false) + "\n";
        }
        return string;
    }

    // Popup window printing the receipt of the total transaction
    public String transactionWindow() {
        final SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy hh:mm:ss a z");
        String string = "Date: " + formatter.format(this.date) + "\n\n";
        string += "Number of line items: " + this.books.size() + "\n\n";
        string += "Item# / ID / Title / Price / Qty / Disc % / Subtotal:\n\n";
        string = string + this.toString() + "\n";
        string = string + "Order subtotal: $" + String.format("%.2f", this.totalCost()) + "\n\n";
        string += "Tax rate: 6%\n\n";
        string = string + "Tax amount: $" + String.format("%.2f", this.taxPrice()) + "\n\n";
        string = string + "Order total: $" + String.format("%.2f", this.totalCost() + this.taxPrice()) + "\n\n";
        string += "Thanks for shopping at the Ye Olde Book Shoppe!";
        return string;
    }

    // Adds total transaction into an output text file
    public void transactionOutput() {
    final SimpleDateFormat purchaseDate = new SimpleDateFormat("MM/dd/yy hh:mm:ss a z");
    final SimpleDateFormat transactionID = new SimpleDateFormat("ddMMyyyyhhmm");
        try {
            final PrintWriter pw = new PrintWriter(new FileWriter("transactions.txt", true));
            for (final bookOrder book : this.books) {
                pw.print(transactionID.format(this.date) + ", ");
                pw.print(book.toString(true) + ", ");
                pw.println(purchaseDate.format(this.date));
            }
            pw.flush();
            pw.close();
        }
        catch (Exception ex) {}
    }
}
