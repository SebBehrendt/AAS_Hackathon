package Helper;
//Move to Framework


import ProductModel.Product_abstract;
import org.eclipse.basyx.submodel.metamodel.map.Submodel;

 public interface ISubmodel {
    Submodel createSubmodel(IAAS abstactShellObject);
}
