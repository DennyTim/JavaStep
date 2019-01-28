package model.bookings.dao;

import java.util.List;
import java.util.Map;

public interface BookingsDao {

    void delete(int index);
    void add(Map<String, Map<String,String>> flight);
    List<Map<String, Map<String,String>>> getAll();
    Map<String, Map<String,String>> get(int index);
    void save();
}
