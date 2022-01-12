package com.foo;

import dev.morphia.annotations.Entity;

//@Entity("test_inheritance")
public class SubClassA extends AbstractSuperClass {

    private String subclassAField = "SubClassA";

    public SubClassA() {
        super();
    }
    
    public SubClassA(long id, String clientAlias) {
        super(id, clientAlias);
    }

    public String getSubclassAField() {
        return subclassAField;
    }

    public void setSubclassAField(String subclassAField) {
        this.subclassAField = subclassAField;
    }
}
