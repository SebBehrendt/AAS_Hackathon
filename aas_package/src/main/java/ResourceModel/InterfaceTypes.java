package ResourceModel;


    public enum InterfaceTypes {
        Electrical("Electrical"),
        fluid("fluid"),
        mechanical("mechanical"),
        anyOther("other");
        String interfaceName;

        InterfaceTypes(String name) {
            this.interfaceName = name;
        }

        public String getInterfaceName() {
            return this.interfaceName;
        }

        public void setOtherInterface(String otherInterface) {
            if (this.equals(InterfaceTypes.anyOther)) {
                this.interfaceName = otherInterface;
            }

        }
    }


