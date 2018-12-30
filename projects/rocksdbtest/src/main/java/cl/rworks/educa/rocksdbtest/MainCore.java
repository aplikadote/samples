/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.educa.rocksdbtest;

import com.google.common.io.Files;
import io.permazen.CopyState;
import io.permazen.JTransaction;
import io.permazen.Permazen;
import io.permazen.PermazenFactory;
import io.permazen.SnapshotJTransaction;
import io.permazen.ValidationMode;
import io.permazen.core.Database;
import io.permazen.kv.rocksdb.RocksDBAtomicKVStore;
import io.permazen.kv.rocksdb.RocksDBKVDatabase;
import io.permazen.kv.simple.XMLKVDatabase;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.NavigableSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import org.rocksdb.Options;

/**
 *
 * @author aplik
 */
public class MainCore {

    public static void main(String[] args) {
        final RocksDBAtomicKVStore kvstore = new RocksDBAtomicKVStore();
        kvstore.setDirectory(new File("D:\\storage"));
        
        Options options = new Options();
        kvstore.setOptions(options);
        
        final RocksDBKVDatabase rocksdb = new RocksDBKVDatabase();
        rocksdb.setKVStore(kvstore);
        rocksdb.start();
        
        final Database coreDb = new Database(rocksdb);

        final Permazen jdb = new PermazenFactory()
                .setDatabase(coreDb)
                .setSchemaVersion(-1)
                .setModelClasses(CsvRow.class)
                .newPermazen();

//        File file = new File("D:\\trabajo\\comar\\share\\gtin.csv");
//        JTransaction jtx = jdb.createTransaction(true, ValidationMode.AUTOMATIC);
//        JTransaction.setCurrent(jtx);
//        try (Stream<String> stream = Files.readLines(file, Charset.defaultCharset()).stream()) {
//            stream.skip(1).forEach(line -> {
//                String[] split = line.split(";");
//                if (split.length == 2) {
//                    String code = split[0].trim();
//                    String description = split[1].trim();
//                    CsvRow.create(code, description);
//                }
//            });
//
//            jtx.commit();
//        } catch (Exception ex) {
//            Logger.getLogger(MainCore.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            JTransaction.setCurrent(null);
//        }
        SnapshotJTransaction sjtx = null;
        JTransaction jtx = jdb.createTransaction(true, ValidationMode.AUTOMATIC);
        JTransaction.setCurrent(jtx);
        try {
            NavigableSet<CsvRow> all = CsvRow.getAll();
            sjtx = jtx.createSnapshotTransaction(ValidationMode.AUTOMATIC);
//            jtx.copyTo(sjtx, new CopyState(), all);
        } catch (Exception ex) {
            Logger.getLogger(MainCore.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JTransaction.setCurrent(null);
        }

        JTransaction.setCurrent(sjtx);
        System.out.println(CsvRow.getAll().size());

    }
}
