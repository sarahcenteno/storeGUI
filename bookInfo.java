// Class for all the important information of each book in inventory
// this will include the book's ID, title, price
public class bookInfo {

    private int id;
    private String title;
    private double price;

    public bookInfo(final int id, final String title, final double price) {
        this.id = id;
        this.title = title;
        this.price = price;
    }

    public String toString(final boolean comma) {
        if (comma) {
            return this.id + ", " + this.title + ", $" + String.format("%.2f", this.price);
        }
        return this.id + " " + this.title + " $" + String.format("%.2f", this.price);
    }

    public int bookID() {
        return this.id;
    }

    public String bookTitle() {
        return this.title;
    }

    public double bookPrice() {
        return this.price;
    }
}
