package resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

/**
 * This class is responsible for the interaction between the application and the
 * database, it also is responsible for searching the database
 *
 * @author Ayden Coort and Edwin Gonzalez Dos Santos
 * @date 2017-12-04
 */

public class DatabaseHandler {
	static Gson gson = new Gson();
	static File ProductFile = new File("resources/", "ProductInformation.json");

	public static ProductInformation[] ReadProductInformation() {
		// Checks if the database exists
		if (!ProductFile.exists()) {
			throw new java.lang.Error("Data base file does not exist. Send Help");
		}

		JsonReader reader;
		try {
			reader = new JsonReader(new FileReader(ProductFile));
			// Converts the result into a array of ProductInfromation
			ProductInformation[] AllProducts = gson.fromJson(reader, ProductInformation[].class);
			return AllProducts;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ProductInformation[] FilterProducts(String SearchQuery) {

		List<ProductInformation> CurrentProducts = Arrays.asList(ReadProductInformation());
		List<ProductInformation> FilteredProducts = new ArrayList<ProductInformation>();

		if (SearchQuery == "")
			return CurrentProducts.toArray(new ProductInformation[CurrentProducts.size()]);

		// Goes through the list and looks for any product that contains the
		// search query in the product name
		for (ProductInformation ThisProduct : CurrentProducts) {
			if (ThisProduct.ProductName.toLowerCase().contains(SearchQuery.toLowerCase()))
				FilteredProducts.add(ThisProduct);
		}
		// Returns the array of matching products
		return FilteredProducts.toArray(new ProductInformation[FilteredProducts.size()]);
	}
}
