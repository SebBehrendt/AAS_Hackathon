package sdm_aas;import java.util.List;

import New_ProcessModel.Process;

/**
 * creates basic framework for process models
 * 
 * @author marieke
 *
 */
public interface IProcessModel {
	List<Process> get_next_possible_processes(List<Product> productlist);

}
