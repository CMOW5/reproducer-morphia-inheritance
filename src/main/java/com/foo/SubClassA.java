package com.foo;

public class SubClassA extends AbstractSuperClass {

    private String subclassAField = "SubClassA";

    public SubClassA() {
        super();
    }
    
    public SubClassA(long id, String name) {
        super(id, name);
    }

    public String getSubclassAField() {
        return subclassAField;
    }

    public void setSubclassAField(String subclassAField) {
        this.subclassAField = subclassAField;
    }
}
