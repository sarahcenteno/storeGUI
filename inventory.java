import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;

// Class inventory will read the inventory text file to see which books are available
// for purchase for the user. It will read line by line on the input text file and then
// add to the array list of bookInfo.
public class inventory {

    private List<bookInfo> books;

    public inventory() {
        // Creates an array of all the books in the inventory text file
        this.books = new ArrayList<bookInfo>();
        try {
            String filename = "inventory.txt";
            final BufferedReader filereader = new BufferedReader(new FileReader(filename));
            for (String line = filereader.readLine(); line != null; line = filereader.readLine()) {
                final String[] data = line.split(",");
                final int id = Integer.parseInt(data[0].trim());
                final String title = data[1].trim();
                final double price = Double.parseDouble(data[2].trim());
                this.books.add(new bookInfo(id, title, price));
            }
            filereader.close();
        }
        catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    // If the book ID that the user entered matches to the book ID on the input file, then
    // it will return to the book variable.
    public bookInfo findBook(final int id){
        for (final bookInfo book: this.books) {
            if (book.bookID() == id) {
                return book;
            }
        }
        return null;
    }
}
