package model.bookings.dao;

import auth.UserData;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BookingsDaoImpl implements BookingsDao{
    private UserData actualUser;
    private List<Map<String, Map<String, String>>> bookedFlights;
    private static final String PATH = "src/data/users.json";
    private static final File USERS = new File(PATH);
    private static final JSONParser parser = new JSONParser();
    private static final Gson gson = new Gson();

    public BookingsDaoImpl(UserData actualUser) {
        this.actualUser = actualUser;
        bookedFlights = actualUser.getBookedFlights();
    }

    @Override
    public void delete(int index) {
        bookedFlights.remove(index);
        save();
    }

    @Override
    public void add(Map<String, Map<String, String>> flight) {
        bookedFlights.add(flight);
        save();
    }

    @Override
    public List<Map<String, Map<String, String>>> getAll() {
        return bookedFlights;
    }

    @Override
    public Map<String, Map<String, String>> get(int index) {
        return bookedFlights.get(index);
    }

    @Override
    public void save() {
        try {


            FileReader reader = new FileReader(PATH);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray arr = (JSONArray) jsonObject.get("users");

            UserData user = null;
            for (int i = 0; i < arr.size(); i++) {
                UserData aUser = gson.fromJson(String.valueOf(arr.get(i)), UserData.class);
                if(aUser.getLogin().equals(actualUser.getLogin()) && aUser.getPassword().equals(actualUser.getPassword())){
                    user = aUser;
                    break;
                }
            }

            user.setBookedFlights(bookedFlights);

            arr.set(0,user.getUserControlToJson());

            FileWriter file = new FileWriter(PATH);

            file.write(jsonObject.toJSONString());
            file.flush();
        }  catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}
