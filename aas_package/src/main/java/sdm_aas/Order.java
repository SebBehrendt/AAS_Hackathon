package sdm_aas;import java.util.Date;
import java.util.List;

import org.eclipse.basyx.aas.metamodel.map.descriptor.ModelUrn;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

/**
 * framework for order shell
 * 
 * @author marieke
 *
 */
public class Order implements ITimeFrame, IProcessModel {

	public static Order order;
	public String customer;
	public int priority;
	public OrderType type;
	public Product product_type;
	public int amount_of_products;
	public int current_products;

	public int get_sales() {
		return 100;
	}

	public void set_initial_components() {
	}

	public void get_current_components() {
	}

	public Date get_start_date() {
		Date date = new Date();
		return date;
	}

	public Date get_optimum_finish_date() {
		Date date = new Date(2022, 10, 07);
		return date;
	}

	public Date get_due_date() {
		Date date = new Date(202, 10, 07);
		return date;
	}


	public List<Process> get_next_possible_processes(List<Product> productlist) {
		for (int i = 0; i < productlist.size(); i++) {
			Product product = productlist.get(i);
		}
		return null;

	}

	/**
	 * creates order submodel
	 * 
	 * @return order submodel
	 */
	public static Submodel createOrderSubmodel() {
		// create order with id, identification and category (OrderType)
		order = new Order();
		Submodel productOrder = new Submodel();
		productOrder.setIdShort("order1");
		productOrder.setIdentification(new ModelUrn("order1-Müller"));
		productOrder.setCategory((OrderType.planned_order).toString());

		// create customer for order as a static property
		order.customer = "Hans Müller";
		Property customer = new Property("Customer", order.customer);
		productOrder.addSubmodelElement(customer);

		// create priority as a static property
		order.priority = 12;
		Property priority = new Property("Priority", order.priority);
		productOrder.addSubmodelElement(priority);

		// create a static property containing the number of produced products
		order.amount_of_products = 1000;
		Property amountOfProducts = new Property("AmountOfProducts", order.amount_of_products);
		productOrder.addSubmodelElement(amountOfProducts);

		// create static property with the number of currently produced products
		order.current_products = 44;
		Property current_products = new Property("CurrentProduct", order.current_products);
		productOrder.addSubmodelElement(current_products);

		return productOrder;
	}

}