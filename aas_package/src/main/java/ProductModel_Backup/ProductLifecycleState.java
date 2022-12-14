package ProductModel_Backup;

public enum ProductLifecycleState {

        PRODUCTPLANNING (0, "Product_planning"), //1st step in product genesis
        DESIGN (1, "Product_design"), //Construction
        PRODUCTION_PLANNING(2, "Production_planning"),
        PRODUCTION (3, "Production"),
        DELIVERY (4, "Delivery"),
        DELIVERED (5, "Delivered"),
        USAGE(6, "Usage"),
        EOL (7, "End_of_life"),
        REUSAGE (8, "Reusage"),
        UNKNOWN (100, "Unknown");
        int key;
        String productLifecycleState;
        ProductLifecycleState(int i, String productLifecycleState)
        {
            this.key = i;
            this.productLifecycleState = productLifecycleState;
        }
        public int getkey()
        {
            return this.key;
        }
        protected String getState() {
                return this.productLifecycleState;
        }

    }
