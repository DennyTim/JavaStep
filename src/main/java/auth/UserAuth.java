package auth;
import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UserAuth {

    private static final String PATH = "src/data/users.json";
    private static final File USERS = new File(PATH);
    private static final JSONParser parser = new JSONParser();
    private static final Gson gson = new Gson();
    private static final Scanner in = new Scanner(System.in);
    private static List<UserData> userControlList;
    private static List<Map<String, Map<String,String>>> actualUserBookedFlights;
    private static FileReader reader;
    private static JSONObject jsonObject;

    static {
        try {
            if(!fileExists()){
                appendUser(generateAdmin());
            }
            reader = new FileReader(PATH);
            jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");

            userControlList = convertJSONArrayToList(jsonArray);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static UserData returnActualUser() {
        UserData actualUser = null;

        generateCommands();
        String ans = in.nextLine();
        if (ans.equals("1")) {
            actualUser = signInActions();
        } else if (ans.equals("2")) {
            actualUser = signUpActions();
            appendUser(actualUser);
        }

        if(actualUser != null){
            actualUserBookedFlights = actualUser.getBookedFlights();
        }
        return actualUser;
    }

    private static UserData signUpActions() {
        System.out.println("Enter your name");
        String name = in.nextLine();

        System.out.println("Enter your surname");
        String surname = in.nextLine();

        System.out.println("Enter your age");
        String ageStr = in.nextLine();

        int age = Integer.parseInt(ageStr);
        User user = new User(name, surname, age);

        System.out.println("Enter login");
        String login = in.nextLine();

        while (checkLogin(login) != null) {
            System.out.println("User with such login already exists");
            System.out.println("Enter login once more");
            login = in.nextLine();
        }

        System.out.println("Enter password");
        String password = in.nextLine();

        System.out.println("Confirm password");
        String confirmPassword = in.nextLine();

        while (!confirmPassword.equals(password)){
            System.out.println("Incorrect password. Enter once more");
            confirmPassword = in.nextLine();
        }

        return new UserData(user, login, password);
    }

    private static UserData signInActions() {
        UserData actualUser = null;

        loginLoop:
        while (true) {
            System.out.println("Enter your login");
            String login = in.nextLine();
            actualUser = checkLogin(login);

            while (actualUser == null) {
                System.out.println("Incorrect login");
                continue loginLoop;
            }

            passwordLoop:
            while (true) {
                System.out.println("Enter password");
                String password = in.nextLine();

                while (!actualUser.getPassword().equals(password)) {
                    System.out.println("Incorrect password");
                    continue passwordLoop;
                }

                return actualUser;
            }
        }
    }

    private static UserData checkLogin(String login) {
        UserData user = null;
        for (UserData aUser : userControlList) {
            if (aUser.getLogin().equals(login)) {
                user = aUser;
            }
        }

        return user;
    }

    private static List<UserData> convertJSONArrayToList(JSONArray jsonArray) {
        List<UserData> list = new ArrayList<>();
        for (Object o : jsonArray) {
            list.add(gson.fromJson(String.valueOf(o), UserData.class));
        }
        return list;
    }


    private static void appendUser(UserData user) {
        try {
            FileReader reader = new FileReader(PATH);
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray arr = (JSONArray) jsonObject.get("users");
            arr.add(user.getUserControlToJson());
            FileWriter file = new FileWriter(PATH);

            file.write(jsonObject.toJSONString());
            file.flush();
        }  catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean fileExists() {
        boolean flag = true;
        try {
            if (!USERS.exists()) {
                USERS.createNewFile();
                JSONArray arr = new JSONArray();
                JSONObject obj = new JSONObject();
                obj.put("users", arr);
                FileWriter file = new FileWriter(PATH);
                file.write(obj.toJSONString());
                file.close();
                flag = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            return flag;
        }

    }

    private static UserData generateAdmin(){
        User test = new User("Test","Test",33);
        UserData testUser = new UserData(test,"test","test");
        return testUser;
    }

    private static void generateCommands() {
        System.out.println("1.Sign in\n" +
                "2.Sign up");
    }

}
