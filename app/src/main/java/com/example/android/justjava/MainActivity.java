
/*
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */

    public void submitOrder(View view) {
        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        EditText customerName = findViewById(R.id.customer_name);
        String nameInput = customerName.getText().toString();
        createOrderSummary(price, hasWhippedCream, hasChocolate, nameInput);
    }

    public void increment(View view) {
        if (quantity >= 100) {
            Toast.makeText(getApplicationContext(), "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
        }
        quantity = quantity + 1;
        displayQuantity(quantity);

    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getApplicationContext(), "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /*
     * This method displays the given text on the screen.
     */

    /**
     * Calculates the price of the order.
     *
     * quantity        is the number of cups of coffee ordered
     * basePrice       is the price of each cup of coffee ordered with optional chocolate/whipped cream added
     * hasWhippedCream specifies whether the user checked to add whipped cream or not
     * hasChocolate    specifies whether the user checked to add chocolate or not
     * nameInput       specifies the name of the customer that they input into the app
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int basePrice = 5;
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }

    private void createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String nameInput) {
        String orderText = getString(R.string.order_input_name, nameInput);
        orderText = orderText + "\n" + getString(R.string.add_whipped_cream) + hasWhippedCream;
        orderText = orderText + "\n" + getString(R.string.add_chocolate) + hasChocolate;
        orderText = orderText + "\n" + getString(R.string.quantity_colon) + quantity;
        orderText = orderText + "\n" + getString(R.string.total_colon) + price;
        orderText = orderText + "\n" + getString(R.string.thank_you);
        Intent orderSummary = new Intent(Intent.ACTION_SENDTO);
        orderSummary.setData(Uri.parse("mailto:"));
        orderSummary.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject));
        orderSummary.putExtra(Intent.EXTRA_TEXT, orderText);
        try {
            startActivity(Intent.createChooser(orderSummary, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}