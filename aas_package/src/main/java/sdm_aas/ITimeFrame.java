package sdm_aas;import java.util.Date;

/**
 * creates basic framework for time frame
 * 
 * @author marieke
 *
 */
public interface ITimeFrame {
	public Date get_start_date();

	public Date get_optimum_finish_date();

	public Date get_due_date();

}
