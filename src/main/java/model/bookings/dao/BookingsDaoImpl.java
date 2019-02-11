package model.bookings.dao;


import auth.UserData;
import com.google.gson.Gson;
import contracts.DAO;
import model.dto.FlightOffer;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class BookingsDaoImpl implements DAO<FlightOffer> {

    private List<FlightOffer> bookedFlights;
    private static final String PATH = "src/data/users.json";
    private static final JSONParser parser = new JSONParser();
    private static final Gson gson = new Gson();
    private UserData actualUser;
    private static BookingsDaoImpl dao;

    private BookingsDaoImpl(){}

    public static BookingsDaoImpl instance() {
        if (dao == null) {
            dao = new BookingsDaoImpl();
        }

        return dao;
    }

    public BookingsDaoImpl setActualUser(UserData actualUser) {
        this.actualUser = actualUser;
        bookedFlights = actualUser.getBookedFlights();
        return this;
    }


    @Override
    public List<FlightOffer> getAll() {
        return bookedFlights;
    }

    @Override
    public FlightOffer get(int index) {
        return bookedFlights.get(index);
    }

    @Override
    public void add(FlightOffer element) {
        bookedFlights.add(element);
        updateFileData();
    }


    @Override
    public void remove(int index) {
        bookedFlights.remove(index);
        updateFileData();
    }


    private void updateFileData() {
        try {
            FileReader reader = new FileReader(PATH);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray arr = (JSONArray) jsonObject.get("users");

            UserData user = null;
            int userIndex = 0;
            for (int i = 0; i < arr.size(); i++) {
                UserData aUser = gson.fromJson(String.valueOf(arr.get(i)), UserData.class);
                if(aUser.getLogin().equals(actualUser.getLogin()) && aUser.getPassword().equals(actualUser.getPassword())){
                    userIndex = i;
                    user = aUser;
                    break;
                }
            }
            user.setBookedFlights(bookedFlights);

            arr.set(userIndex,user.getUserControlToJson());

            FileWriter file = new FileWriter(PATH);

            file.write(jsonObject.toJSONString());
            file.flush();
        }  catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }


}
