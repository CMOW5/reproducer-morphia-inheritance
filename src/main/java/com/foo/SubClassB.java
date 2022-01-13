package com.foo;

public class SubClassB extends AbstractSuperClass {
    
    private String subclassBField = "SubClassB";

    public SubClassB() {
        super();
    }
    
    public SubClassB(long id, String name) {
        super(id, name);
    }

    public String getSubclassBField() {
        return subclassBField;
    }

    public void setSubclassBField(String subclassBField) {
        this.subclassBField = subclassBField;
    }
}
