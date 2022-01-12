package com.foo;

import com.antwerkz.bottlerocket.BottleRocket;
import com.antwerkz.bottlerocket.BottleRocketTest;
import com.github.zafarkhaja.semver.Version;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.mapping.DiscriminatorFunction;
import dev.morphia.mapping.MapperOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.filters.Filters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

public class ReproducerTest extends BottleRocketTest {
    private Datastore datastore;

    public ReproducerTest() {
        MongoClient mongo = getMongoClient();
        MongoDatabase database = getDatabase();
        database.drop();

        MapperOptions mapper = MapperOptions.builder()
                                            .discriminatorKey("className")
                                            .discriminator(DiscriminatorFunction.className())
                                            .enablePolymorphicQueries(true)
                                            .build();

        datastore = Morphia.createDatastore(mongo, getDatabase().getName(), mapper);
    }

    @NotNull
    @Override
    public String databaseName() {
        return "morphia_repro";
    }

    @Nullable
    @Override
    public Version version() {
        return BottleRocket.DEFAULT_VERSION;
    }

    @Test
    public void reproduceWorking() {
        // arrange
        long _id = 1L;
        String clientOneAlias = "client1Alias";
        datastore.save(new SubClassA(_id, clientOneAlias));

        // act
        AbstractSuperClass entity = datastore.find(AbstractSuperClass.class)
                                             .filter(Filters.eq("_id", _id))
                                             .first();

        Assert.assertNotNull("client should be an instance of com.foo.SubClassA but is null", entity);
        Assert.assertTrue("client should be an instance of com.foo.SubClassA but its " + entity.getClass(), entity instanceof SubClassA);
    }
    
    @Test
    public void reproduce() {
        // arrange
        long _id = 1L;
        String clientOneAlias = "client1Alias";
        datastore.save(new SubClassA(_id, clientOneAlias));

        // act
        AbstractSuperClass entity = datastore.find(AbstractSuperClass.class)
                                             .filter(
                                                  Filters.eq(AbstractSuperClass.CLIENT_ALIAS_PROPERTY, clientOneAlias)
                                             )
                                             .first();
        // assert
        Assert.assertNotNull("client should be an instance of com.foo.SubClassA but is null", entity);
        Assert.assertTrue("client should be an instance of com.foo.SubClassA but its " + entity.getClass(), entity instanceof SubClassA);
    }
}
