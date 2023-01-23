package AAS_Framework;
//Move to Framework


import org.eclipse.basyx.submodel.metamodel.map.Submodel;

/**
 * Interface for classes which instantiate a Submodel
 */
 public interface ISubmodel {
    Submodel createSubmodel(IAAS abstractShellObject);
}
