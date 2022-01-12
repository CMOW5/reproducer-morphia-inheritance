package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;

@Entity("test_inheritance")
public class AbstractSuperClass {

    public static final String CLIENT_ALIAS_PROPERTY = "clientAlias";

    public AbstractSuperClass() {}
    
    public AbstractSuperClass(long id, String clientAlias) {
        this.id = id;
        this.clientAlias = clientAlias;
    }

    @Id
    private long id;
    
    @Property(CLIENT_ALIAS_PROPERTY)
    private String clientAlias;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getClientAlias() {
        return clientAlias;
    }

    public void setClientAlias(String clientAlias) {
        this.clientAlias = clientAlias;
    }
}
