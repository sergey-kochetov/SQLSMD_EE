package com.juja.sqlcmd_ee.model;

import java.util.List;
import java.util.Set;
import java.util.Map;

public interface DataSet {

    void put(String name, Object value);

    void putAll(Map<String, Object> data);

    List<Object> getValues();

    Set<String> getNames();

    Object get(String name);

    void updateFrom(DataSet newValue);
}
