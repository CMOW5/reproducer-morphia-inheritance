package com.foo;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;

@Entity("test_inheritance")
public abstract class AbstractSuperClass {

    public static final String CLIENT_NAME_PROPERTY = "name";

    public AbstractSuperClass() {}
    
    public AbstractSuperClass(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Id
    private long id;
    
    @Property(CLIENT_NAME_PROPERTY)
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
