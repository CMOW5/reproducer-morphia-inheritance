package com.foo;

import dev.morphia.annotations.Entity;

//@Entity("test_inheritance")
public class SubClassB extends AbstractSuperClass {
    
    private String subclassBField = "SubClassB";

    public SubClassB() {
        super();
    }
    
    public SubClassB(long id, String clientAlias) {
        super(id, clientAlias);
    }

    public String getSubclassBField() {
        return subclassBField;
    }

    public void setSubclassBField(String subclassBField) {
        this.subclassBField = subclassBField;
    }
}
