// Create AAS 

 Identifier assetIdentifier = new Identifier(IdentifierType.CUSTOM, "global_id_asset");
        Asset myAsset =  new Asset("name_of_asset", assetIdentifier, AssetKind.INSTANCE);

        //Reminder: according to IDTA the type of the AAS (type/instance) is defined by type or instance
        // declaration of the asset which is referenced of the asset which is referenced. Default: INSTANCE

        Identifier aasIdentifier = new Identifier(IdentifierType.CUSTOM, "global_id_aas");
        AssetAdministrationShell myAAS = new AssetAdministrationShell("name_of_aas",aasIdentifier, myAsset );

        Submodel myFirstSubmodel = new Submodel("first_Submodel",
                new Identifier(IdentifierType.CUSTOM, "first_submodel_id"));

        Property propertyOfFirstSubmodel = new Property("name_of_property","this is the property value");
        Property secondPropertyOfFirstSubmodel = new Property("name_of_second_property", "value");
        SubmodelElementCollection smcOfSubmodel = new SubmodelElementCollection("smc1");

        smcOfSubmodel.addSubmodelElement(secondPropertyOfFirstSubmodel);
        myFirstSubmodel.addSubmodelElement(smcOfSubmodel);

        myFirstSubmodel.addSubmodelElement(propertyOfFirstSubmodel);
		
		myAAS.addSubmodel(myFirstSubmodel); 

        //Now the AAS has one submodel with two properties and one SMC.
        // One property is in the SMC, the other one directly in the submodel, the other one in the SMC.
