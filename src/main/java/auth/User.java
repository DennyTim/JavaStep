package auth;

import org.json.JSONObject;


public class User{

    private String name;
    private String surname;
    private int age;

    public User(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public JSONObject getUserToJson(){
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("name",name);
        jsonUser.put("surname",surname);
        jsonUser.put("age",age);
        return jsonUser;
    }

}
