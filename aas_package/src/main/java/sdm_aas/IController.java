package sdm_aas;/**
 * creates basic framework for the controller
 * 
 * @author marieke
 *
 */
public interface IController {

	public Process determine_next_product_to_process();

	public Process request_process_for_product(Product product);

}
