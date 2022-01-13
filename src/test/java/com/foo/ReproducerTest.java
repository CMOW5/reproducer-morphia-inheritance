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
import dev.morphia.query.experimental.filters.Filters;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;
import org.junit.Test;

public class ReproducerTest extends BottleRocketTest {
    private Datastore datastore;

    public ReproducerTest() {
        MongoDatabase database = getDatabase();
        database.drop();

        datastore = createDatastore();
    }

    private Datastore createDatastore() {
        MongoClient mongo = getMongoClient();
        MongoDatabase database = getDatabase();

        MapperOptions mapper = MapperOptions.builder()
                .discriminatorKey("className")
                .discriminator(DiscriminatorFunction.className())
                .enablePolymorphicQueries(true)
                .build();

        return Morphia.createDatastore(mongo, database.getName(), mapper);
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
    public void reproduce() {
        // arrange
        long _id = 1L;
        String clientOneName = "client1Name";
        initializeDataInDB(_id, clientOneName);

        // act
        // important: need to re-initialize the datastore here because using the original reference will make this work!!
        // maybe datastore is caching previously saved entities and that's why it works ???
        // I'm doing this to simulate the real case in which the data is already in mongo and we're just querying for it
        datastore = createDatastore(); // at this point the data is already in mongo, so let's create a new datastore reference
        AbstractSuperClass entity = datastore.find(AbstractSuperClass.class)
                                             .filter(Filters.eq(AbstractSuperClass.CLIENT_NAME_PROPERTY, clientOneName))
                                             .first();
        // assert
        Assert.assertNotNull("client should be an instance of com.foo.SubClassA but is null", entity);
        Assert.assertTrue("client should be an instance of com.foo.SubClassA but its " + entity.getClass(), entity instanceof SubClassA);
    }

    private void initializeDataInDB(long _id, String name) {
        datastore = createDatastore();
        datastore.save(new SubClassA(_id, name));
    }
}
