package auth;

import model.dto.FlightOffer;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserData{

    private User user;
    private String login;
    private String password;
    private List<FlightOffer> bookedFlights = new ArrayList<>();

    public UserData(User user, String login, String password) {
        this.user = user;
        this.login = login;
        this.password = password;
    }

    public JSONObject getUserControlToJson(){
        JSONObject jsonUserControl = new JSONObject();
        jsonUserControl.put("user",user.getUserToJson());
        jsonUserControl.put("login",login);
        jsonUserControl.put("password",password);
        jsonUserControl.put("bookedFlights",bookedFlights);

        return jsonUserControl;
    }


    public List<FlightOffer> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(List<FlightOffer> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
