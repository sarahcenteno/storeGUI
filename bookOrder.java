// Ckass for a single book order and applies the quantity of how many books
// the user wants to buy for one book. In addition, it will apply the discount
// and get the total cost for that one book.
public class bookOrder {

    private bookInfo book;
    private int quantity;

    // Gets the quantity that the user purchased per book
    public bookOrder(final Object book, final int quantity) {
        this.book = (bookInfo) book;
        this.quantity = quantity;
    }

    public int discount() {
        // If user purchases 15 or more of the same book will be 20% discount
        if (this.quantity >= 15) {
            return 20;
        }
        // If user purchases between 10-14 of the same book will get 15% discount
        if (this.quantity >= 10) {
            return 15;
        }
        // If user purchases between 5-9 of the same book will get 10% discount
        if (this.quantity >= 5) {
            return 10;
        }
        return 0;
    }

    // Gets the total cost with the discount
    public double totalCost() {
        return this.book.bookPrice() * this.quantity - this.book.bookPrice() * this.quantity * (this.discount() / 100.0);
    }

    public String toString(final boolean comma) {
        String string = this.book.toString(comma);
        if (comma) {
            string = string + ", " + this.quantity + ", " + this.discount() + "%, $" + String.format("%.2f", this.totalCost());
        }
        else {
            string = string + " " + this.quantity + " " + this.discount() + "% $" + String.format("%.2f", this.totalCost());
        }
        return string;
    }
}
