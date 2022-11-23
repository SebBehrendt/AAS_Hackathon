package sdm_aas;
import java.sql.SQLException;
import java.util.List;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;
import org.eclipse.basyx.submodel.metamodel.map.qualifier.LangStrings;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

public class Process implements ITimeModel {

	public int id;
	boolean isActive = false;
	double minimum_batchsize;
	double maximum_batchsize;
	double precision;
	double quality_rate;
	List<Product> target_products;
	List<Product> raw_products;

	// creates a process with specific ids
	public Process(int i) {
		id = i;
	}

// states whether the process is active or not
	public boolean isActive() {
		return isActive;
	}

// returns random changing numbers for the time of a process
	public double get_next_time() {
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return Math.random() * 10 + 10;
		}
	}

	/**
	 * creates a process submodel which can be added to a shell
	 * 
	 * @return process submodel
	 */
	public static Submodel createProcessSubmodel() {
		Submodel ProcessSubmodel = new Submodel();
		// creates properties for each process with a id short and an object (String)
		// an english description is set for the properties
		Property process1 = new Property("P1", "function_time_model_1");
		LangStrings descriptionProcess1 = new LangStrings("english", "Process 1");
		process1.setDescription(descriptionProcess1);
		ProcessSubmodel.addSubmodelElement(process1);

		Property process2 = new Property("P2", "function_time_model_2");
		LangStrings descriptionProcess2 = new LangStrings("english", "Process 2");
		process2.setDescription(descriptionProcess2);
		ProcessSubmodel.addSubmodelElement(process2);

		Property process3 = new Property("P3", "function_time_model_3");
		LangStrings descriptionProcess3 = new LangStrings("english", "Process 3");
		process3.setDescription(descriptionProcess3);
		ProcessSubmodel.addSubmodelElement(process3);

		return ProcessSubmodel;
	}
}
