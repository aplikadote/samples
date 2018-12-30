/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.permazen.kv.derby;

import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.PermazenFactory;
import io.permazen.ValidationMode;
import io.permazen.core.Database;
import io.permazen.kv.simple.XMLKVDatabase;
import java.io.File;
import java.util.stream.Stream;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author rgonzalez
 */
public class Test {

    public void test() {

//        XMLKVDatabase db = new XMLKVDatabase(new File("/home/rgonzalez/permazen-data.xml"));
        EmbeddedDataSource ds = new EmbeddedDataSource();
        ds.setDatabaseName("storage;create=true");

        DerbyKVDatabase db = new DerbyKVDatabase();
        db.setDataSource(ds);
        db.start();

        final Permazen jdb = new PermazenFactory()
                .setDatabase(new Database(db))
                .setSchemaVersion(-1)
                .setModelClasses(Person.class)
                .newPermazen();

        JTransaction jtx = jdb.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
//            Person p = Person.create();
//            p.setName("pepito");
//            p.setName("5");

            Person.getAll().stream().forEach(System.out::println);

            jtx.commit();

        } finally {
            JTransaction.setCurrent(null);
        }

    }

    public static void main(String[] args) {
        Test test = new Test();
        test.test();
    }

}
