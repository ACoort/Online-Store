package resources;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

/**
 * This is the class that is responsible for creating and maintaining the GUI as
 * per the model-view-controller method, it updates the GUI as it receives
 * notification from the Model to do so
 *
 * @author Ayden Coort and Edwin Gonzalez Dos Santos
 * @date 2017-12-04
 */

public class ViewController extends JFrame implements Observer, ActionListener {

	private static final long serialVersionUID = 2L;

	private Model mainModel; // model

	private JPanel startScreen; // JPanel

	// buttons
	private JButton startNext;
	private JButton exit;
	private JButton previousBack;
	private JButton checkout;
	private JButton searchButton;
	private JButton addCart;
	private JButton viewBtn[];

	// Labels
	private JLabel greeting;
	private JLabel copywriteDesc;
	private JLabel searchText;
	private JLabel quantityPrompt;
	private JLabel cartLBLs[];
	private JLabel itemName[];
	private JLabel itemPrice[];
	private JLabel itemQuantity[];
	private JLabel itemImages[];
	private JLabel paymentPrompt[];

	// TextFields
	private JTextField search;
	private JTextField paymentInfo[];

	// Spinner
	private JSpinner quantity;

	// integers
	private int offsetX = 50;
	private int offsetY = 100;

	// statics
	private static final Dimension DEFAULT_DIMENSION = new Dimension(900, 1000);
	private static final String COMPANY_NAME = "Ayden and Edwin Co.";
	private static final Dimension DEFAULT_BUTTON_SIZE = new Dimension(150, 50);
	private Font defaultButtonFont;

	private ProductInformation product;

	private ArrayList<ItemPurchases> cart;

	/**
	 * Creates all the required elements for the whole program and sets them
	 * into their initial states
	 * 
	 * @param model
	 *            the Model
	 */
	public ViewController(Model model) {
		this.mainModel = model; // sets Model

		this.cart = new ArrayList<ItemPurchases>(); // sets an array to store
													// the items purchased

		// initial window setup
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(DEFAULT_DIMENSION);
		this.setTitle(COMPANY_NAME);
		this.setLocationByPlatform(true);
		this.getContentPane().setLayout(new BorderLayout());

		// initial JFrame setup
		this.startScreen = new JPanel();
		this.startScreen.setBackground(Color.WHITE);
		this.startScreen.setLayout(null);
		this.startScreen.setLocation(0, 0);
		this.startScreen.setVisible(true);

		// sets greeting text
		this.greeting = new JLabel();
		this.greeting.setText("Default text");
		this.greeting.setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 42));
		this.greeting.setVisible(true);
		this.startScreen.add(this.greeting);

		this.defaultButtonFont = new Font(greeting.getFont().getName(), Font.PLAIN, 18); // basis
																							// for
																							// most
																							// fonts

		// other label setups
		this.copywriteDesc = new JLabel();
		this.copywriteDesc.setText(COMPANY_NAME + " LTD (2017)");
		this.copywriteDesc.setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 12));
		this.copywriteDesc.setSize(500, 50);
		this.copywriteDesc.setLocation(350, 850);
		this.copywriteDesc.setVisible(true);
		this.startScreen.add(this.copywriteDesc);

		this.searchText = new JLabel();
		this.searchText.setText("Search: ");
		this.searchText.setLocation(50, 0);
		this.searchText.setSize(100, 50);
		this.searchText.setVisible(true);
		this.searchText.setFont(defaultButtonFont);
		this.startScreen.add(this.searchText);

		// button setups
		this.startNext = new JButton();
		this.startNext.setText("startNext button");
		this.startNext.setSize(DEFAULT_BUTTON_SIZE);
		this.startNext.setEnabled(true);
		this.startNext.setVisible(true);
		this.startScreen.add(this.startNext);
		this.startNext.addActionListener(this);

		this.exit = new JButton();
		this.exit.setText("Exit");
		this.exit.setEnabled(true);
		this.exit.setVisible(true);
		this.startScreen.add(this.exit);
		this.exit.addActionListener(this);

		this.previousBack = new JButton();
		this.previousBack.setText("previousNext button");
		this.previousBack.setSize(DEFAULT_BUTTON_SIZE);
		this.previousBack.setEnabled(false);
		this.previousBack.setVisible(true);
		this.startScreen.add(this.previousBack);
		this.previousBack.addActionListener(this);

		this.checkout = new JButton();
		this.checkout.setText("Checkout");
		this.checkout.setSize(DEFAULT_BUTTON_SIZE);
		this.checkout.setEnabled(true);
		this.checkout.setVisible(true);
		this.startScreen.add(this.checkout);
		this.checkout.addActionListener(this);

		this.searchButton = new JButton();
		this.searchButton.setText("Search");
		this.searchButton.setFont(defaultButtonFont);
		this.searchButton.setSize(150, 50);
		this.searchButton.setLocation(700, 0);
		this.searchButton.setVisible(true);
		this.searchButton.setEnabled(true);
		this.startScreen.add(this.searchButton);
		this.searchButton.addActionListener(this);

		this.addCart = new JButton();
		this.addCart.setText("Add to Cart");
		this.addCart.setFont(defaultButtonFont);
		this.addCart.setSize(DEFAULT_BUTTON_SIZE);
		this.addCart.setLocation(0, 0);
		this.addCart.setVisible(true);
		this.addCart.setEnabled(true);
		this.startScreen.add(this.addCart);
		this.addCart.addActionListener(this);

		// textField setup
		this.search = new JTextField();
		this.search.setLocation(150, 0);
		this.search.setFont(defaultButtonFont);
		this.search.setSize(550, 50);
		this.search.setEnabled(true);
		this.search.setVisible(true);
		this.startScreen.add(this.search);

		// initialize arrays of certain JFrame elements that are the same type
		// and appear on screen mostly at the same time
		this.viewBtn = new JButton[6];
		this.itemName = new JLabel[7];
		this.itemPrice = new JLabel[7];
		this.itemQuantity = new JLabel[7];
		this.itemImages = new JLabel[7];
		this.cartLBLs = new JLabel[6];

		for (int i = 0; i < 6; i++) { // initialize the above elements to their
										// starting states
			this.viewBtn[i] = new JButton();
			this.viewBtn[i].setText("View Item");
			this.viewBtn[i].setSize(DEFAULT_BUTTON_SIZE);
			this.viewBtn[i].setFont(defaultButtonFont);
			this.viewBtn[i].setLocation(50 + this.offsetX, 275 + this.offsetY);
			this.viewBtn[i].setEnabled(true);
			this.viewBtn[i].setVisible(true);
			this.startScreen.add(this.viewBtn[i]);
			this.viewBtn[i].addActionListener(this);

			this.itemName[i] = new JLabel();
			this.itemName[i].setText("Item name: " + i);
			this.itemName[i].setFont(defaultButtonFont);
			this.itemName[i].setLocation(this.offsetX, 200 + this.offsetY);
			this.itemName[i].setSize(200, 50);
			this.itemName[i].setVisible(true);
			this.startScreen.add(this.itemName[i]);

			this.itemPrice[i] = new JLabel();
			this.itemPrice[i].setText("Price: $" + i);
			this.itemPrice[i].setFont(defaultButtonFont);
			this.itemPrice[i].setLocation(this.offsetX, 225 + this.offsetY);
			this.itemPrice[i].setSize(150, 50);
			this.itemPrice[i].setVisible(true);
			this.startScreen.add(this.itemPrice[i]);

			this.itemQuantity[i] = new JLabel();
			this.itemQuantity[i].setText("QTY: " + i);
			this.itemQuantity[i].setHorizontalAlignment(SwingConstants.RIGHT);
			this.itemQuantity[i].setFont(defaultButtonFont);
			this.itemQuantity[i].setLocation(75 + this.offsetX, 225 + this.offsetY);
			this.itemQuantity[i].setSize(150, 50);
			this.itemQuantity[i].setVisible(true);
			this.startScreen.add(this.itemQuantity[i]);

			this.itemImages[i] = new JLabel();
			this.itemImages[i].setLocation(this.offsetX, this.offsetY);
			this.itemImages[i].setSize(200, 200);
			this.itemImages[i].setVisible(true);
			this.startScreen.add(this.itemImages[i]);

			this.cartLBLs[i] = new JLabel();
			this.cartLBLs[i].setFont(defaultButtonFont);
			this.cartLBLs[i].setVerticalAlignment(JLabel.TOP);
			this.cartLBLs[i].setVisible(true);
			this.startScreen.add(this.cartLBLs[i]);
			if (this.offsetX >= 500) {
				this.offsetX = 50;
				this.offsetY += 400;
			} else {
				this.offsetX += 275;
			}
		}

		// setting up elements for the product description screen
		this.itemName[6] = new JLabel();
		this.itemName[6].setText("Item price: 6");
		this.itemName[6].setLocation(550, 100);
		this.itemName[6].setSize(300, 100);
		this.itemName[6].setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 26));
		this.itemName[6].setVisible(true);
		this.startScreen.add(this.itemName[6]);

		this.itemPrice[6] = new JLabel();
		this.itemPrice[6].setText("Item price: 6");
		this.itemPrice[6].setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 26));
		this.itemPrice[6].setLocation(550, 200);
		this.itemPrice[6].setSize(300, 50);
		this.itemPrice[6].setVisible(true);
		this.startScreen.add(this.itemPrice[6]);

		this.itemQuantity[6] = new JLabel();
		this.itemQuantity[6].setText("Item Quantity: 6");
		this.itemQuantity[6].setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 26));
		this.itemQuantity[6].setLocation(550, 250);
		this.itemQuantity[6].setSize(300, 50);
		this.itemQuantity[6].setVisible(true);
		this.startScreen.add(this.itemQuantity[6]);

		this.itemImages[6] = new JLabel();
		this.itemImages[6].setLocation(50, 100);
		this.itemImages[6].setSize(500, 500);
		this.itemImages[6].setVisible(true);
		this.startScreen.add(this.itemImages[6]);

		this.quantityPrompt = new JLabel();
		this.quantityPrompt.setLocation(550, 350);
		this.quantityPrompt.setSize(250, 50);
		this.quantityPrompt.setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 26));
		this.quantityPrompt.setText("Number to buy:");
		this.quantityPrompt.setVisible(true);
		this.startScreen.add(this.quantityPrompt);

		this.quantity = new JSpinner();
		this.quantity.setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 26));
		this.quantity.setSize(100, 50);
		this.quantity.setLocation(750, 350);
		this.quantity.setVisible(true);
		this.quantity.setEnabled(true);
		this.startScreen.add(this.quantity);

		// setup of the Information input screen fields
		this.paymentInfo = new JTextField[10];
		this.paymentPrompt = new JLabel[10];

		for (int i = 0; i < 10; i++) {
			this.paymentInfo[i] = new JTextField();
			this.paymentInfo[i].setFont(defaultButtonFont);
			this.paymentInfo[i].setVisible(true);
			this.paymentInfo[i].setEnabled(true);
			this.startScreen.add(this.paymentInfo[i]);

			this.paymentPrompt[i] = new JLabel();
			this.paymentPrompt[i].setFont(defaultButtonFont);
			this.paymentPrompt[i].setVisible(true);
			this.paymentPrompt[i].setEnabled(true);
			this.startScreen.add(this.paymentPrompt[i]);
		}

		this.getContentPane().add(this.startScreen);

		// initialize and start the frame
		setUpStart();

		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.startNext)) {
			switch (this.mainModel.getState()) {
			case Model.START_STATE: // pressing enters the store
				JOptionPane.showMessageDialog(new JFrame(),
						"Note: Searching nothing will return all the products in the store");
				this.mainModel.setState(Model.STORE_SCREEN);
				break;
			case Model.STORE_SCREEN: // pressing proceeds to next shop page
				this.mainModel.nextPage();
				setUpStore(); // reloads to update
				break;
			case Model.INFORMATION: // pressing confirms the information input
				if (allFilledOut()) {
					JOptionPane.showMessageDialog(new JFrame(), "Purchase Confirmed! Exiting");
					System.exit(0);
				} else
					JOptionPane.showMessageDialog(new JFrame(), "Please fill in ALL the information");
				break;
			default:
				break;
			}
		} else if (e.getSource().equals(this.previousBack)) {
			switch (this.mainModel.getState()) {
			case Model.STORE_SCREEN: // pressing shows the previous shop page
				this.mainModel.previousPage();
				setUpStore(); // reloads to update
				break;
			case Model.ITEM_PURCHASE: // pressing sets user back to store screen
				this.mainModel.setState(Model.STORE_SCREEN);
				if (this.mainModel.getPage() == 1) { // ensures there is a
														// previous page to
														// display
					this.previousBack.setEnabled(false);
				}
				if (this.mainModel.getPage() < this.mainModel.numberOfPages()) {
					this.startNext.setEnabled(true);
				}
				break;
			case Model.CART_CHECKOUT: // pressing sets user back to store screen
				this.mainModel.setState(Model.STORE_SCREEN);
				break;
			case Model.INFORMATION: // pressing sets user back to the checkout
									// screen
				this.mainModel.setState(Model.CART_CHECKOUT);
				break;
			default:
				break;
			}
		} else if (e.getSource().equals(this.exit)) {
			System.exit(0); // exits
		} else if (e.getSource().equals(this.searchButton)) {
			// upon pressing, search term is taken and if there are relevant
			// hits, they are returned
			this.mainModel.searchTerm(this.search.getText());
			if (this.mainModel.getState() != Model.STORE_SCREEN) {
				this.mainModel.setState(Model.STORE_SCREEN);
			} else {
				setUpStore();
			}

		} else if (e.getSource().equals(this.checkout)) {
			switch (this.mainModel.getState()) {
			case Model.STORE_SCREEN: // proceeds to checkout screen
				this.mainModel.setState(Model.CART_CHECKOUT);
				break;
			case Model.ITEM_PURCHASE: // proceeds to checkout screen
				this.mainModel.setState(Model.CART_CHECKOUT);
				break;
			case Model.CART_CHECKOUT: // proceeds to information screen
				this.mainModel.setState(Model.INFORMATION);
				break;
			default:
				break;
			}
		} else if (e.getSource().equals(this.viewBtn[0])) { // tracks which item
															// was selected from
															// the 6
			this.product = this.mainModel.productClicked(0);
			this.mainModel.setState(Model.ITEM_PURCHASE);
		} else if (e.getSource().equals(this.viewBtn[1])) { // see above
			this.product = this.mainModel.productClicked(1);
			this.mainModel.setState(Model.ITEM_PURCHASE);
		} else if (e.getSource().equals(this.viewBtn[2])) { // see above
			this.product = this.mainModel.productClicked(2);
			this.mainModel.setState(Model.ITEM_PURCHASE);
		} else if (e.getSource().equals(this.viewBtn[3])) { // see above
			this.product = this.mainModel.productClicked(3);
			this.mainModel.setState(Model.ITEM_PURCHASE);
		} else if (e.getSource().equals(this.viewBtn[4])) { // see above
			this.product = this.mainModel.productClicked(4);
			this.mainModel.setState(Model.ITEM_PURCHASE);
		} else if (e.getSource().equals(this.viewBtn[5])) { // see above
			this.product = this.mainModel.productClicked(5);
			this.mainModel.setState(Model.ITEM_PURCHASE);
		} else if (e.getSource().equals(this.addCart)) { // adds desired item
															// and quantity to
															// the cart when
															// pressed
			this.mainModel.cartProduct(Double.valueOf(this.quantity.getValue().toString()).intValue(), this.product);
			setUpDesc(); // reloads to update quantity
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	@Override
	public void update(Observable o, Object arg) {
		if (this.mainModel.getState() == Model.START_STATE) {
			setUpStart();
		} else if (this.mainModel.getState() == Model.STORE_SCREEN) {
			setUpStore();
		} else if (this.mainModel.getState() == Model.ITEM_PURCHASE) {
			setUpDesc();
		} else if (this.mainModel.getState() == Model.CART_CHECKOUT) {
			setUpPayment();
		} else if (this.mainModel.getState() == Model.INFORMATION) {
			setUpCheckout();
		}
	}

	/**
	 * Sets up the screen for display when the state is
	 * <code>MODEL.START_STATE</code>
	 */
	private void setUpStart() {
		hideAll();

		this.greeting.setText(
				"<html><div style='text-align: center;'>Welcome to " + COMPANY_NAME + "'s online storefront!</html>");
		this.greeting.setLocation(50, 200);
		this.greeting.setSize(800, 300);
		this.greeting.setVisible(true);

		this.startNext.setText("Enter the store");
		this.startNext.setSize(200, 75);
		this.startNext.setFont(new Font(this.startNext.getFont().getName(), Font.PLAIN, 20));
		this.startNext.setLocation(625, 700);
		this.startNext.setVisible(true);

		this.exit.setSize(200, 75);
		this.exit.setFont(new Font(this.exit.getFont().getName(), Font.PLAIN, 20));
		this.exit.setLocation(50, 700);
		this.exit.setVisible(true);

		this.copywriteDesc.setVisible(true);

		this.startScreen.setVisible(true);

		this.pack();
	}

	/**
	 * Sets up the screen for display when the state is
	 * <code>MODEL.STORE_STATE</code>
	 */
	private void setUpStore() {
		hideAll();
		searchBar();

		this.previousBack.setText("<- Previous");
		this.previousBack.setSize(DEFAULT_BUTTON_SIZE);
		this.previousBack.setFont(defaultButtonFont);
		this.previousBack.setLocation(525, 875);
		this.previousBack.setVisible(true);
		if (this.mainModel.getPage() > 1) {
			this.previousBack.setEnabled(true);
		} else {
			this.previousBack.setEnabled(false);
		}

		this.startNext.setText("Next ->");
		this.startNext.setSize(DEFAULT_BUTTON_SIZE);
		this.startNext.setFont(defaultButtonFont);
		this.startNext.setLocation(700, 875);
		this.startNext.setVisible(true);
		if (this.mainModel.numberOfPages() <= 1 || this.mainModel.numberOfPages() == this.mainModel.getPage()) { // checks
																													// to
																													// see
																													// if
																													// there
																													// is
																													// actually
																													// a
																													// next
																													// page
			this.startNext.setEnabled(false);
		} else {
			this.startNext.setEnabled(true);
		}

		this.exit.setVisible(true);
		this.exit.setSize(DEFAULT_BUTTON_SIZE);
		this.exit.setFont(defaultButtonFont);
		this.exit.setLocation(50, 875);

		this.checkout.setText("Checkout");
		this.checkout.setVisible(true);
		this.checkout.setSize(DEFAULT_BUTTON_SIZE);
		this.checkout.setFont(defaultButtonFont);
		this.checkout.setLocation(300, 875);

		for (int i = 0; i < 6; i++) { // sets up the products with the
										// information and displays the correct
										// amount of results for that page (i.e.
										// if there are 9 total results, the 2nd
										// page will only have 3 products
										// displayed)
			if (this.mainModel.moreItems(i)) {
				this.viewBtn[i].setVisible(true);

				Image image = this.mainModel.getProductImage(i).getImage(); // transform
																			// it
				Image newimg = image.getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH); // scale
																								// it
																								// the
																								// smooth
																								// way
				ImageIcon imgIcon = new ImageIcon(newimg); // transform it back

				this.itemImages[i].setIcon(imgIcon);
				this.itemImages[i].setVisible(true);

				this.itemName[i].setText(this.mainModel.getProductName(i));
				this.itemName[i].setVisible(true);

				this.itemPrice[i].setText("Price: $" + String.format("%.2f", this.mainModel.getProductPrice(i)));
				this.itemPrice[i].setVisible(true);

				this.itemQuantity[i].setText("QTY: " + String.valueOf(this.mainModel.getProductQuantity(i)));
				this.itemQuantity[i].setVisible(true);
			}
		}

		this.pack();
	}

	/**
	 * Sets up the screen for display when the state is
	 * <code>MODEL.ITEM_PURCHASE</code>
	 */
	private void setUpDesc() {
		hideAll();
		searchBar();

		Image image = this.mainModel.getProductImageID(this.product.ProductNumber).getImage(); // transform
																								// it
		Image newimg = image.getScaledInstance(500, 500, java.awt.Image.SCALE_SMOOTH); // scale
																						// it
																						// the
																						// smooth
																						// way
		ImageIcon imgIcon = new ImageIcon(newimg); // transform it back

		this.itemImages[6].setIcon(imgIcon);
		this.itemImages[6].setVisible(true);

		this.itemName[6].setText("<html><div style='text-align: center;'>" + this.product.ProductName + "</html>");
		this.itemName[6].setVisible(true);

		this.itemPrice[6].setText("Price: $" + String.format("%.2f", this.product.Price));
		this.itemPrice[6].setVisible(true);

		this.itemQuantity[6].setText("Quantity: " + String.valueOf(this.product.ProductQuantity));
		this.itemQuantity[6].setVisible(true);

		this.quantityPrompt.setVisible(true);

		this.quantity.setModel(new SpinnerNumberModel(0, 0, this.product.ProductQuantity, 1.0));
		this.quantity.setVisible(true);

		this.copywriteDesc
				.setText("<html> <b> Product Description </b> <br />" + this.product.ProductDescription + "</html>");
		this.copywriteDesc.setFont(new Font(greeting.getFont().getName(), Font.PLAIN, 20));
		this.copywriteDesc.setLocation(50, 500);
		this.copywriteDesc.setSize(800, 300);
		this.copywriteDesc.setVisible(true);

		this.addCart.setLocation(600, 425);
		this.addCart.setVisible(true);

		this.previousBack.setText("Back");
		this.previousBack.setLocation(700, 875);
		this.previousBack.setVisible(true);
		this.previousBack.setEnabled(true);

		this.exit.setLocation(50, 875);
		this.exit.setVisible(true);

		this.checkout.setText("Checkout");
		this.checkout.setLocation(375, 875);
		this.checkout.setVisible(true);
	}

	/**
	 * Sets up the screen for display when the state is
	 * <code>MODEL.INFORMATION</code>
	 */
	private void setUpCheckout() {
		hideAll();

		this.paymentPrompt[0].setText("First Name");
		this.paymentPrompt[0].setSize(375, 50);
		this.paymentPrompt[0].setLocation(50, 150);
		this.paymentPrompt[0].setVisible(true);

		this.paymentInfo[0].setSize(375, 50);
		this.paymentInfo[0].setLocation(50, 200);
		this.paymentInfo[0].setVisible(true);

		this.paymentPrompt[1].setText("Last Name");
		this.paymentPrompt[1].setSize(375, 50);
		this.paymentPrompt[1].setLocation(475, 150);
		this.paymentPrompt[1].setVisible(true);

		this.paymentInfo[1].setSize(375, 50);
		this.paymentInfo[1].setLocation(475, 200);
		this.paymentInfo[1].setVisible(true);

		this.paymentPrompt[2].setText("Address");
		this.paymentPrompt[2].setSize(500, 50);
		this.paymentPrompt[2].setLocation(50, 250);
		this.paymentPrompt[2].setVisible(true);

		this.paymentInfo[2].setSize(800, 50);
		this.paymentInfo[2].setLocation(50, 300);
		this.paymentInfo[2].setVisible(true);

		this.paymentPrompt[3].setText("City");
		this.paymentPrompt[3].setSize(400, 50);
		this.paymentPrompt[3].setLocation(50, 350);
		this.paymentPrompt[3].setVisible(true);

		this.paymentInfo[3].setSize(400, 50);
		this.paymentInfo[3].setLocation(50, 400);
		this.paymentInfo[3].setVisible(true);

		this.paymentPrompt[4].setText("Prov / State");
		this.paymentPrompt[4].setSize(150, 50);
		this.paymentPrompt[4].setLocation(500, 350);
		this.paymentPrompt[4].setVisible(true);

		this.paymentInfo[4].setSize(150, 50);
		this.paymentInfo[4].setLocation(500, 400);
		this.paymentInfo[4].setVisible(true);

		this.paymentPrompt[5].setText("Country");
		this.paymentPrompt[5].setSize(150, 50);
		this.paymentPrompt[5].setLocation(700, 350);
		this.paymentPrompt[5].setVisible(true);

		this.paymentInfo[5].setSize(150, 50);
		this.paymentInfo[5].setLocation(700, 400);
		this.paymentInfo[5].setVisible(true);

		this.paymentPrompt[6].setText("E-mail Address");
		this.paymentPrompt[6].setSize(500, 50);
		this.paymentPrompt[6].setLocation(50, 450);
		this.paymentPrompt[6].setVisible(true);

		this.paymentInfo[6].setSize(800, 50);
		this.paymentInfo[6].setLocation(50, 500);
		this.paymentInfo[6].setVisible(true);

		this.paymentPrompt[7].setText("Credit Card Number");
		this.paymentPrompt[7].setSize(300, 50);
		this.paymentPrompt[7].setLocation(50, 550);
		this.paymentPrompt[7].setVisible(true);

		this.paymentInfo[7].setSize(300, 50);
		this.paymentInfo[7].setLocation(50, 600);
		this.paymentInfo[7].setVisible(true);

		this.paymentPrompt[8].setText("CVV");
		this.paymentPrompt[8].setSize(100, 50);
		this.paymentPrompt[8].setLocation(400, 550);
		this.paymentPrompt[8].setVisible(true);

		this.paymentInfo[8].setSize(100, 50);
		this.paymentInfo[8].setLocation(400, 600);
		this.paymentInfo[8].setVisible(true);

		this.paymentPrompt[9].setText("Credit Card Provider");
		this.paymentPrompt[9].setSize(300, 50);
		this.paymentPrompt[9].setLocation(550, 550);
		this.paymentPrompt[9].setVisible(true);

		this.paymentInfo[9].setSize(300, 50);
		this.paymentInfo[9].setLocation(550, 600);
		this.paymentInfo[9].setVisible(true);

		this.startNext.setText("Confirm");
		this.startNext.setSize(DEFAULT_BUTTON_SIZE);
		this.startNext.setLocation(675, 875);
		this.startNext.setEnabled(true);
		this.startNext.setVisible(true);

		this.exit.setLocation(50, 875);
		this.exit.setVisible(true);
	}

	/**
	 * Sets up the screen for display when the state is
	 * <code>MODEL.CART_CHECKOUT</code>
	 */
	private void setUpPayment() {
		hideAll();

		String productNames = "<html><b> Product Name </b> <br /><br />"; // product
																			// names
		String productPrices = "<html><b> Price </b> <br /><br />"; // product
																	// prices
		String productQuantity = "<html><b> Quantity </b> <br /><br />"; // quantity
																			// ordered
		double total = 0; // total before taxes

		this.cart = this.mainModel.giveCart();

		this.greeting.setLocation(50, 50);
		this.greeting.setSize(450, 50);
		this.greeting.setText("Checkout");
		this.greeting.setVisible(true);

		this.cartLBLs[0].setLocation(50, 125);
		this.cartLBLs[0].setSize(500, 525);
		this.cartLBLs[0].setVisible(true);

		this.cartLBLs[1].setLocation(600, 125);
		this.cartLBLs[1].setSize(100, 525);
		this.cartLBLs[1].setVisible(true);

		this.cartLBLs[2].setLocation(750, 125);
		this.cartLBLs[2].setSize(100, 525);
		this.cartLBLs[2].setVisible(true);

		for (int i = 0; i < this.cart.size(); i++) { // populates the cart with
														// the items put into
														// the labels
			if (i < 8) { // maximum 8 products displayed
				productNames = productNames + this.cart.get(i).product.ProductName + "<br /><br />";
				productPrices = productPrices + String.format("%.2f", this.cart.get(i).product.Price) + "<br /><br />";
				productQuantity = productQuantity + this.cart.get(i).quantityToBuy + "<br /><br />";
			} else if (i == 8) { // if more than 8, say how many more are not
									// displayed
				productNames = productNames + "and " + (this.cart.size() - 8) + " more";
			}

			total += this.cart.get(i).quantityToBuy * this.cart.get(i).product.Price; // running
																						// total
																						// before
																						// taxes

			if (this.cart.get(i).product.ProductName.length() > 50) { // for
																		// spacing
																		// purposes
				productPrices = productPrices + "<br />";
				productQuantity = productQuantity + "<br />";
			}
		}
		productNames = productNames + "</html>";
		productPrices = productPrices + "</html>";
		productQuantity = productQuantity + "</html>";

		// set all the labels appropriately
		this.cartLBLs[0].setText(productNames);
		this.cartLBLs[1].setText(productPrices);
		this.cartLBLs[2].setText(productQuantity);

		this.cartLBLs[3].setText("<html><b>Subtotal</b>: $" + String.format("%.2f", total) + "</hmtl>");
		this.cartLBLs[3].setSize(250, 50);
		this.cartLBLs[3].setLocation(600, 650);
		this.cartLBLs[3].setVisible(true);

		this.cartLBLs[4]
				.setText("<html><b>Taxes</b>: &nbsp &nbsp $" + String.format("%.2f", (total * .13)) + "</hmtl>");
		this.cartLBLs[4].setSize(250, 50);
		this.cartLBLs[4].setLocation(600, 700);
		this.cartLBLs[4].setVisible(true);

		this.cartLBLs[5]
				.setText("<html><b>Total</b>: &nbsp &nbsp &nbsp $" + String.format("%.2f", (total * 1.13)) + "</hmtl>");
		this.cartLBLs[5].setSize(250, 50);
		this.cartLBLs[5].setLocation(600, 750);
		this.cartLBLs[5].setVisible(true);

		this.exit.setLocation(50, 875);
		this.exit.setVisible(true);

		this.checkout.setText("Pay");
		this.checkout.setLocation(375, 875);
		this.checkout.setVisible(true);

		this.previousBack.setText("Back");
		this.previousBack.setLocation(700, 875);
		this.previousBack.setVisible(true);
		this.previousBack.setEnabled(true);
	}

	/**
	 * Makes every object invisible as a first step in setting up a certain page
	 */
	private void hideAll() {

		this.startNext.setVisible(false);
		this.exit.setVisible(false);
		this.previousBack.setVisible(false);
		this.checkout.setVisible(false);
		this.searchButton.setVisible(false);
		this.addCart.setVisible(false);

		this.greeting.setVisible(false);
		this.copywriteDesc.setVisible(false);
		this.searchText.setVisible(false);

		this.search.setVisible(false);

		this.quantityPrompt.setVisible(false);

		this.quantity.setVisible(false);

		for (int i = 0; i < 10; i++) {
			this.paymentInfo[i].setVisible(false);
			this.paymentPrompt[i].setVisible(false);

			if (i < 7) {
				if (i < 6) {
					this.viewBtn[i].setVisible(false);
					this.cartLBLs[i].setVisible(false);
				}
				this.itemName[i].setVisible(false);
				this.itemPrice[i].setVisible(false);
				this.itemQuantity[i].setVisible(false);
				this.itemImages[i].setVisible(false);
			}
		}
	}

	/**
	 * For displaying the search bar
	 */
	private void searchBar() {
		this.search.setVisible(true);

		this.searchButton.setVisible(true);

		this.searchText.setVisible(true);
	}

	/**
	 * @return true if all the fields on the information screen are non-empty
	 */
	private boolean allFilledOut() {
		boolean completed = true;

		for (int i = 0; i < 10; i++) {
			completed &= (this.paymentInfo[i].getText().length() != 0);
		}

		return completed;
	}

}
