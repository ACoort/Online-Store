package resources;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * This is the class responsible for storing information and providing updates
 * to the ViewController as per the model-view-controller design logic
 *
 * @author Ayden Coort and Edwin Gonzalez Dos Santos
 * @date 2017-12-04
 */

public class Model extends Observable {

	public static final int START_STATE = 0; // start screen
	public static final int STORE_SCREEN = 1; // main store screen
	public static final int ITEM_PURCHASE = 2; // detailed item screen
	public static final int CART_CHECKOUT = 3; // cart checkout screen
	public static final int INFORMATION = 4; // screen for entering buyer
												// information

	private int currentState = START_STATE;

	private int currentPage = 1;
	private ProductInformation[] items;

	private ImageIcon productImages[];

	private ArrayList<ItemPurchases> cart;

	/**
	 * Sets up the default state for the model
	 */
	public Model() {
		this.cart = new ArrayList<ItemPurchases>(); // initialize cart
		this.productImages = new ImageIcon[30]; // initialize product images
		searchTerm(""); // will populate items with all the available products
		for (int i = 0; i < 30; i++) { // assign pictures to the array
			BufferedImage picture = null;

			try {
				picture = ImageIO.read(new File("resources/" + this.items[i].ProductNumber + ".jpg"));
			} catch (IOException e1) {

				e1.printStackTrace();
			}

			this.productImages[i] = new ImageIcon(picture);
		}

	}

	/**
	 * @param o
	 *            some state
	 */
	private void modelNotifyObs(Object o) {
		setChanged();
		notifyObservers(o);
	}

	/**
	 * @return the current state
	 */
	public int getState() {
		return this.currentState;
	}

	/**
	 * @param state
	 *            a model state set the model state to the state given and tell
	 *            observers
	 */
	public void setState(int state) {
		this.currentState = state;
		modelNotifyObs(this.currentState);
	}

	/**
	 * @param s
	 *            a search term
	 * 
	 *            Take s and parse the database for any results that match
	 */
	public void searchTerm(String s) {
		this.items = DatabaseHandler.FilterProducts(s);
		if (this.items.length == 0) { // if there are no results that match
										// notify user
			JOptionPane.showMessageDialog(new JFrame(),
					"There were no matching results, displaying all products instead.");
			searchTerm("");
		}
		this.currentPage = 1;
	}

	/**
	 * Increment page counter
	 */
	public void nextPage() {
		this.currentPage++;
	}

	/**
	 * Decrement page counts
	 */
	public void previousPage() {
		this.currentPage--;
	}

	/**
	 * @return the current page
	 */
	public int getPage() {
		return this.currentPage;
	}

	/**
	 * @return the number of pages the storefront will have
	 */
	public int numberOfPages() {
		if (this.items.length % 6 != 0) {
			return ((this.items.length / 6) + 1);
		}
		return (this.items.length / 6);
	}

	/**
	 * @param location
	 *            which product box is calling it
	 * @return the image for the associated product
	 */
	public ImageIcon getProductImage(int location) {
		return this.productImages[this.items[((this.currentPage - 1) * 6) + location].ProductNumber - 1];
	}

	/**
	 * @param id
	 *            productNumber
	 * @return the image associated with the productNumber
	 */
	public ImageIcon getProductImageID(int id) {
		return this.productImages[id - 1];
	}

	/**
	 * @param i
	 *            the productName label that called it
	 * @return the productName to fill the label
	 */
	public String getProductName(int i) {
		return this.items[((this.currentPage - 1) * 6) + i].ProductName;
	}

	/**
	 * @param i
	 *            the Price label that called it
	 * @return the Price to fill the label
	 */
	public double getProductPrice(int i) {
		return this.items[((this.currentPage - 1) * 6) + i].Price;
	}

	/**
	 * @param i
	 *            the productQuantity label that called it
	 * @return the productQuantity to fill the label
	 */
	public int getProductQuantity(int i) {
		return this.items[((this.currentPage - 1) * 6) + i].ProductQuantity;
	}

	/**
	 * @return the number of products that will appear on the last page
	 */
	public int numberOfHitsOnLastPage() {
		return this.items.length % 6;
	}

	/**
	 * @param i
	 *            the viewItem button that was clicked
	 * @return the product associated with the button at the current time
	 */
	public ProductInformation productClicked(int i) {
		return this.items[((this.currentPage - 1) * 6) + i];
	}

	/**
	 * @param i
	 *            the current item layout
	 * @return if there are more items to be displayed
	 */
	public boolean moreItems(int i) {
		return ((this.currentPage - 1) * 6 + i) < this.items.length;
	}

	/**
	 * @param q
	 *            quantity
	 * @param p
	 *            productInformation object
	 */
	public void cartProduct(int q, ProductInformation p) {
		boolean contains = false;
		if (q != 0) { // if the user doesn't want to add 0 items to cart
			for (int i = 0; i < this.cart.size(); i++) { // check if the item is
															// already in the
															// cart
				if (this.cart.get(i).product.ProductNumber == p.ProductNumber) { // if
																					// it
																					// is
																					// increase
																					// the
																					// quantityToBuy
					p.ProductQuantity -= q;
					this.cart.get(i).quantityToBuy += q;
					contains = true;
				}
			}

			if (!contains) { // if the product is not already in the cart add it
				p.ProductQuantity -= q;
				this.cart.add(new ItemPurchases(p, q));
			}
		}
	}

	/**
	 * @return the user's cart
	 */
	public ArrayList<ItemPurchases> giveCart() {
		return this.cart;
	}
}
