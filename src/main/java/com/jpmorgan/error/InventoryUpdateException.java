package com.jpmorgan.error;

import java.util.Set;

public class InventoryUpdateException extends  RuntimeException{

    public InventoryUpdateException() {
        super(" Please clear pending orders for the product before deletion.");
    }

}
