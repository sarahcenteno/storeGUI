import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YeOldeBookShoppe extends JFrame {

    private inventory inventory;
    private bookOrder bookOrder;
    private transaction transaction;
    private int itemsLimit;
    private JTextField[] textFields;
    private JLabel[] labels;
    private JButton[] buttons;

    // YeOldeBookShoppe GUI window components
    public YeOldeBookShoppe() {
        this.setLayout(new BorderLayout());
        this.setTitle("Ye Olde Book Shoppe");
        this.setSize(900, 300);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.inventory = new inventory();
        this.textFields = new JTextField[5];
        this.labels = new JLabel[5];
        this.buttons = new JButton[6];
        this.initialize();
        this.newOrder();
    }

    // This will initialize the frame components within the window
    private void initialize() {
        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.setBackground(Color.BLACK);
        this.add("Center", panel);
        for (int i = 0; i < this.textFields.length; i++) {
            this.textFields[i] = new JTextField();
            panel.add(this.labels[i] = new JLabel("", 4));
            panel.add(this.textFields[i]);
        }
        this.textFields[3].setEditable(false);
        this.textFields[4].setEditable(false);
        this.labels[0].setText("Enter number of items in this order:");
        this.labels[0].setForeground(Color.yellow);
        panel = new JPanel(new FlowLayout());
        panel.setBackground(Color.BLUE);
        this.add("South", panel);
        final ButtonsActionListener actionListener = new ButtonsActionListener();
        for (int j = 0; j < 6; j++) {
            (this.buttons[j] = new JButton()).addActionListener(actionListener);
            panel.add(this.buttons[j]);
        }
        this.buttons[2].setText("View Order");
        this.buttons[3].setText("Finish Order");
        this.buttons[4].setText("New Order");
        this.buttons[5].setText("Exit");
    }

    // This will update the labels after each book that is being ordered
    private void updateLabels() {
        this.labels[1].setText("Enter Book ID for Item #" + (this.transaction.size() + 1) + ":");
        this.labels[1].setForeground(Color.yellow);
        this.labels[2].setText("Enter quantity for Item #" + (this.transaction.size() + 1) + ":");
        this.labels[2].setForeground(Color.yellow);
        this.labels[3].setText("Item #" + (this.transaction.size() + 1) + " info:");
        this.labels[3].setForeground(Color.yellow);
        this.labels[4].setText("Order subtotal for " + this.transaction.size() + " item(s):");
        this.labels[4].setForeground(Color.yellow);
        this.textFields[1].setText("");
        this.textFields[2].setText("");
        this.buttons[0].setText("Process Item #" + (this.transaction.size() + 1));
        this.buttons[1].setText("Confirm Item #" + (this.transaction.size() + 1));
        this.buttons[0].setEnabled(true);
        final JComponent[] toDisable = {this.buttons[1], null, null, null};
        if (this.itemsLimit != -1 && this.transaction.size() >= this.itemsLimit) {
            this.labels[1].setText("");
            this.labels[2].setText("");
            toDisable[1] = this.buttons[0];
            toDisable[2] = this.textFields[1];
            toDisable[3] = this.textFields[2];
        }
        for (final JComponent component : toDisable) {
            if (component != null) {
                component.setEnabled(false);
            }
        }
    }

    // This is for the New Order button, if user presses this button it will create
    // a whole new order starting from item #1
    private void newOrder() {
        this.itemsLimit = -1;
        this.transaction = new transaction();
        final JComponent[] toEnable = { this.buttons[0], this.textFields[0], this.textFields[1], this.textFields[2] };
        final JComponent[] toDisable = { this.buttons[1], this.buttons[2], this.buttons[3] };
        final JTextField[] toClear = { this.textFields[0], this.textFields[3], this.textFields[4] };
        for (final JComponent component : toEnable) {
            component.setEnabled(true);
        }
        for (final JComponent component : toDisable) {
            component.setEnabled(false);
        }
        for (final JTextField component2 : toClear) {
            component2.setText("");
        }
        this.textFields[0].setEditable(true);
        this.updateLabels();
    }

    private boolean tryParseInt(final String value) {
        try {
            Integer.parseInt(value);
        }
        catch (Exception e) {
            return false;
        }
        return true;
    }

    // This is for the Process Item button, when the user is ready to process the item they are
    // willing to purchase.
    private void processItem() {
        int bookID = 0;
        int quantity = 0;
        // A popup window will display if user entered letters instead of numbers in the text fields
        if (!this.tryParseInt(this.textFields[0].getText()) || !this.tryParseInt(this.textFields[1].getText()) || !this.tryParseInt(this.textFields[2].getText())) {
            JOptionPane.showMessageDialog(this, "Required: Number of items, Book ID, and Quantity as numbers.");
            return;
        }
        // A popup window will display if user enters a number less than or equal to 0 in the text fields
        this.itemsLimit = Integer.parseInt(this.textFields[0].getText());
        bookID = Integer.parseInt(this.textFields[1].getText());
        quantity = Integer.parseInt(this.textFields[2].getText());
        if (this.itemsLimit <= 0 || quantity <= 0) {
            JOptionPane.showMessageDialog(this, "Required: Number of items and quantity to be greater than 0.");
            return;
        }
        // A popup window will display if the book ID entered by user is not in the inventory text file.
        final bookInfo book = this.inventory.findBook(bookID);
        if (book == null) {
            JOptionPane.showMessageDialog(this, "The Book ID " + bookID + " is not in file.");
            return;
        }
        this.bookOrder = new bookOrder(book, quantity);
        this.textFields[3].setText(this.bookOrder.toString(false));
        this.textFields[0].setEditable(false);
        this.buttons[0].setEnabled(false);
        this.buttons[1].setEnabled(true);
    }

    // This is for the Confirm Item button. This allows the user to check to see if the right book ID
    // was inputted and sees if the user wants to buy the book.
    private void confirmItem() {
        // Once the user presses the Confirm Item button, a popup window will show that the item was accepted
        JOptionPane.showMessageDialog(this, "Item #" + (this.transaction.size() + 1) + " accepted");
        this.transaction.addBook(this.bookOrder);
        this.buttons[2].setEnabled(true);
        this.buttons[3].setEnabled(true);
        // Confirm button and labels will be updated for the next item number
        this.updateLabels();
        this.textFields[4].setText("$" + String.format("%.2f", this.transaction.totalCost()));
    }

    // This is for the View Order button. When the user presses this button, it will show a
    // popup window of what the user has ordered so far.
    private void viewOrder() {
        JOptionPane.showMessageDialog(this, this.transaction.toString());
    }

    // This is for the Finish Order button. This allows the user to press this button when
    // the user is done with the order. Once it is pressed, a popup window will appear of the
    // receipt of the total transaction and have the GUI ready for a new order
    private void finishOrder() {
        JOptionPane.showMessageDialog(this, this.transaction.transactionWindow());
        this.transaction.transactionOutput();
        this.newOrder();
    }

    // This will set the YeOldeBookShoppe GUI to be visible to the user.
    public static void main(final String[] args) {
        new YeOldeBookShoppe().setVisible(true);
    }

    // This allows the buttons in the YeOldeBookShoppe GUI to perform the
    // necessary actions for each button.
    private class ButtonsActionListener implements ActionListener {
        public void actionPerformed(final ActionEvent e) {
            final Object source = e.getSource();
            if (source == YeOldeBookShoppe.this.buttons[0]) {
                YeOldeBookShoppe.this.processItem();
            }
            else if (source == YeOldeBookShoppe.this.buttons[1]) {
                YeOldeBookShoppe.this.confirmItem();
            }
            else if (source == YeOldeBookShoppe.this.buttons[2]) {
                YeOldeBookShoppe.this.viewOrder();
            }
            else if (source == YeOldeBookShoppe.this.buttons[3]) {
                YeOldeBookShoppe.this.finishOrder();
            }
            else if (source == YeOldeBookShoppe.this.buttons[4]) {
                YeOldeBookShoppe.this.newOrder();
            }
            else if (source == YeOldeBookShoppe.this.buttons[5]) {
                YeOldeBookShoppe.this.dispose();
                System.exit(0);
            }
        }
    }
}
