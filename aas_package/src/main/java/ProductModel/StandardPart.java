package ProductModel;

import java.util.HashMap;
import java.util.Map;

public class StandardPart extends Component{

    Map<String, String> listOfDescribingNorms = new HashMap<>(); //Part designation, Norm of designation
    Map<String, String> listDescriptionParameters = new HashMap<>(); //Material,
    int partCount;

    public StandardPart(String nameOfComponent) {
        super(nameOfComponent);

    }
}
