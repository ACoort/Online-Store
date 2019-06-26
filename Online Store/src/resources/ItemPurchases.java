package resources;

/**
 * A class designed solely for the purpose of the user's cart
 *
 * @author Ayden Coort and Edwin Gonzalez Dos Santos
 * @date 2017-12-04
 */

public class ItemPurchases {
	public ProductInformation product;
	public int quantityToBuy;

	/**
	 * @param p
	 *            some ProductInformation
	 * @param q
	 *            some quantity to buy
	 */
	public ItemPurchases(ProductInformation p, int q) {
		this.product = p;
		this.quantityToBuy = q;
	}
}
