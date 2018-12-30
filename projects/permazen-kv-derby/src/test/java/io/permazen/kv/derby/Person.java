/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.permazen.kv.derby;

import io.permazen.JObject;
import io.permazen.JTransaction;
import io.permazen.annotation.PermazenType;
import java.util.NavigableSet;

/**
 *
 * @author rgonzalez
 */
@PermazenType
public interface Person extends JObject {

    int getAge();

    String getName();

    void setAge(int age);

    void setName(String name);

    static Person create() {
        return JTransaction.getCurrent().create(Person.class);
    }
    
    public static NavigableSet<Person> getAll(){
        JTransaction jtx = JTransaction.getCurrent();
        return jtx.getAll(Person.class);
    }

}
