package auth;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserData{

    private User user;
    private String login;
    private String password;
    private List<Map<String, Map<String,String>>> bookedFlights = new ArrayList<>();

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

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public List<Map<String, Map<String, String>>> getBookedFlights() {
        return bookedFlights;
    }

    public void setBookedFlights(List<Map<String, Map<String, String>>> bookedFlights) {
        this.bookedFlights = bookedFlights;
    }


    @Override
    public String toString() {
        return "UserControl{" +
                "user=" + user +
                ", login= " + login +
                ", password= " + password +
                "}";
    }
}
