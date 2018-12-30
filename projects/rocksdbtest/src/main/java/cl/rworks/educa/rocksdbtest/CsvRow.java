/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.educa.rocksdbtest;

import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.PermazenType;
import java.util.NavigableSet;

/**
 *
 * @author aplik
 */
@PermazenType
public interface CsvRow extends JObject {

    String getCode();

    void setCode(String login);

    String getDescription();

    void setDescription(String fullname);

    public static CsvRow create(String login, String fullname) {
        JTransaction jtx = JTransaction.getCurrent();
        CsvRow user = jtx.create(CsvRow.class);
        user.setCode(login);
        user.setDescription(fullname);
        return user;
    }

    public static NavigableSet<CsvRow> getAll() {
        return JTransaction.getCurrent().getAll(CsvRow.class);
    }
    
}
