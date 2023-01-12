package OrderModel;

import Helper.AASHelper;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.SubmodelElementCollection;
import org.eclipse.basyx.submodel.metamodel.map.submodelelement.dataelement.property.Property;

public class CustomerInformation {
    private static final String SMC_CUSTOMER_INFORMATION_SHORT_ID = "Customer_Information";
    private static String DEFAULT = "";
    String nameOfContact;
    String companyName;
    String customerIdentification;
    //Address Data
    String streetAndNumber;
    String zipCode;
    String place;
    String country;
    // Contact
    String telephoneNumber;
    String mailAddress;

    public CustomerInformation(String companyName, String contactName, String customerIdentification)
    {
        this.companyName = companyName;
        this.nameOfContact = contactName;
        this.customerIdentification = customerIdentification;
    }
    public CustomerInformation(String contactName){
        this.nameOfContact = contactName;
        this.companyName = DEFAULT;
        this.customerIdentification = DEFAULT;
    }
    public void setCustomerIdentification(String customerIdentification) {
        this.customerIdentification = customerIdentification;
    }
    public void setContactInfo (String telephoneNumber, String mailAddress)
    {
        this.telephoneNumber = telephoneNumber;
        this.mailAddress = mailAddress;
    }
    public void setAddress(String streetAndNumber, String zipCode, String place, String country)
    {
        this.streetAndNumber = streetAndNumber;
        this.zipCode = zipCode;
        this.place = place;
        this.country = country;
    }
    protected SubmodelElementCollection createCustomerInfoSMC ()
    {
        SubmodelElementCollection customerInfoSMC = new SubmodelElementCollection(SMC_CUSTOMER_INFORMATION_SHORT_ID) ;
        customerInfoSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(CONTACT_NAME), this.nameOfContact));
        customerInfoSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(CUSTOMER_IDENTIFICATION), this.customerIdentification));
        customerInfoSMC.addSubmodelElement(new Property(AASHelper.nameToIdShort(COMPANY_NAME), this.companyName));

        if (this.streetAndNumber != null && this.zipCode != null)
        {
            SubmodelElementCollection smcAddress = new SubmodelElementCollection(SMC_ADDRESS_SHORT_ID);
            smcAddress.addSubmodelElement(new Property(AASHelper.nameToIdShort(STREET_AND_NUMBER), this.streetAndNumber));
            smcAddress.addSubmodelElement(new Property(AASHelper.nameToIdShort(ZIP_CODE), this.zipCode));
            smcAddress.addSubmodelElement(new Property(AASHelper.nameToIdShort(CITY_TOWN), this.place));
            smcAddress.addSubmodelElement(new Property(AASHelper.nameToIdShort(COUNTRY), this.country));

            customerInfoSMC.addSubmodelElement(smcAddress);
        }
        if (this.mailAddress != null && this.telephoneNumber != null)
        {
            SubmodelElementCollection smcContactData = new SubmodelElementCollection(AASHelper.nameToIdShort(SMC_CONTACT_SHORT_ID));
            smcContactData.addSubmodelElement(new Property(AASHelper.nameToIdShort(E_MAIL_ADDRESS), this.mailAddress));
            smcContactData.addSubmodelElement(new Property(AASHelper.nameToIdShort(TELEPHONE_NUMBER), this.telephoneNumber));

            customerInfoSMC.addSubmodelElement(smcContactData);
        }


        return customerInfoSMC;
    }
    private static final String SMC_ADDRESS_SHORT_ID = "Address";
    private static final String CONTACT_NAME = "Contact_Name";
    private static final String CUSTOMER_IDENTIFICATION = "Customer_Identification";
    private static final String COMPANY_NAME= "Company_Name";
    private static final String STREET_AND_NUMBER = "Street_and_Number";
    private static final String ZIP_CODE = "ZIP_Code";
    private static final String CITY_TOWN = "City_Town";
    private static final String COUNTRY = "COUNTRY";
    private static final String E_MAIL_ADDRESS = "E_Mail_Address";
    private static final String TELEPHONE_NUMBER = "Telephone_Number";
    private static final String SMC_CONTACT_SHORT_ID = "Contact";

}
