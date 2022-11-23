package sdm_aas;import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.StringJoiner;

public class Resource implements IController {
// required objects for a resource
	public ResourceType type;
	// list contains possible processes of a resource
	public List<Process> capabilities;
	public static List<Float> location;
	public int capacity;
	public Process process1;
	public Process process2;
	public Process process3;

	/**
	 * creates a resource with the id i resource 1 has the processes 1 & 2 resource
	 * 2 has the processes 2 & 3
	 * 
	 * @param i id of resource
	 */
	public Resource(int i) {
		if (i == 1) {
			process1 = new Process(1);
			process2 = new Process(2);
			capabilities = new ArrayList<>();
			capabilities.add(process1);
			capabilities.add(process2);
		} else if (i == 2) {
			process2 = new Process(2);
			process3 = new Process(3);
			capabilities = new ArrayList<>();
			capabilities.add(process2);
			capabilities.add(process3);
		}
	}
// implement the functions for the controller

	@Override
	public Process determine_next_product_to_process() {
		return null;
	}

	@Override
	public Process request_process_for_product(Product product) {
		return null;
	}

	public List<Process> get_capabilities() {
		return capabilities;
	}

// returns a string of possible processes of a resource
	public String get_capabilitiesString() {
		List<Integer> output = new ArrayList<>();
		for (int i = 0; i < capabilities.size(); i++) {
			Process process = capabilities.get(i);
			output.add(process.id);
		}
		return "The resource has processes with the id's: " + output.toString();
	}

// returns a list of available / non active processes
	public List<Process> get_available_processes() {
		List<Process> available_processes = new ArrayList<>();
		for (int i = 0; i < capabilities.size(); i++) {
			Process process = capabilities.get(i);
			if (process.isActive != true) {
				available_processes.add(process);
				System.out.println("process with ID " + process.id + " is available");
			}
		}
		return available_processes;
	}

// performs a process -> sets booleans isActive on true if the process is inactive
// if the process is already active there will come a notification
	public void perform_process(Process process) {
		if (!process.isActive) {
			System.out.println("perform non active process with id " + process.id);
			process.isActive = true;
		} else {
			System.out.println("process with id: " + process.id + " is already active");
		}
	}

// stops a process -> sets booleans isActive on false if the process is active
// if the process is already inactive there will come a notification
	public void stop_process(Process process) {
		if (process.isActive) {
			System.out.println("deactivate process with id " + process.id);
			process.isActive = false;
		} else {
			System.out.println("process with id: " + process.id + " is already inactive");
		}
	}
	/**
	 * public void deactivate_resource() { List<Process> capabilities =
	 * get_capabilities(); for (int i = 0; i < capabilities.size(); i++) { Process
	 * process = capabilities.get(i); if (process.isActive = true) {
	 * process.isActive = false; } } }
	 **/

}
