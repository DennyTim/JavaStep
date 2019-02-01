package auth;

import com.google.gson.Gson;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;
import view.Validation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserAuth {

    private static final String PATH = "src/data/users.json";
    private static final File USERS = new File(PATH);
    private static final JSONParser parser = new JSONParser();
    private static final Gson gson = new Gson();
    private static final Scanner in = new Scanner(System.in);
    private static List<UserData> userDataList;
    private static FileReader reader;
    private static JSONObject jsonObject;

    static {
        try {
            if (!fileExists()) {
                appendUser(generateAdmin());
            }
            reader = new FileReader(PATH);
            jsonObject = (JSONObject) parser.parse(reader);
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");

            userDataList = convertJSONArrayToList(jsonArray);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public static UserData returnActualUser() {
        UserData actualUser = null;
        while (true) {
            generateCommands();
            String ans = in.nextLine();
            if (ans.equals("1")) {
                actualUser = signInActions();
                if (actualUser == null) continue;
            } else if (ans.equals("2")) {
                actualUser = signUpActions();
                if (actualUser == null) continue;
                appendUser(actualUser);
            } else {
                System.out.println("Incorrect input. Enter 1 or 2");
                continue;
            }
            break;
        }

        return actualUser;
    }

    private static UserData signUpActions() {
        UserData actualUser = null;

        System.out.println("Enter your name");
        String name = in.nextLine();
        while (!Validation.checkNameSurname(name)) {
            System.out.println("Incorrect name. Enter once more");
            name = in.nextLine();
        }

        System.out.println("Enter your surname");
        String surname = in.nextLine();

        while (!Validation.checkNameSurname(surname)) {
            System.out.println("Incorrect surname. Enter once more");
            surname = in.nextLine();
        }

        System.out.println("Enter your age");
        String ageStr = in.nextLine();

        while (!Validation.checkAge(ageStr)) {
            System.out.println("Incorrect age.Enter once more");
            ageStr = in.nextLine();
        }

        int age = Integer.parseInt(ageStr);
        User user = new User(name, surname, age);

        System.out.println("Enter login (Write 'menu' to back to menu)");
        String login = in.nextLine();
        if (isBackToMenu(login)) return actualUser;

        while (!Validation.isValidLogin(login)){
            if (isBackToMenu(login)) return actualUser;
            System.out.println("Login must be at list 3 characters, contain word and number characters only. Try again");
            login = in.nextLine();
        }

        while (checkLogin(login) != null) {
            System.out.println("User with such login already exists");
            System.out.println("Enter login once more (Write 'menu' to back to menu)");
            login = in.nextLine();
            if (isBackToMenu(login)) return actualUser;
        }

        System.out.println("Enter password (Write 'menu' to back to menu)");
        String password = in.nextLine();
        if (isBackToMenu(password)) return actualUser;

        while (!Validation.isValidPassword(password)){
            System.out.println("Password must be at list 6 characters, contain word and number characters, symbols: '-', '.', '@', '$', '%' only. Try again");
            password = in.nextLine();
        }
        while (true) {
            System.out.println("Confirm password (Write 'menu' to back to menu)");
            String confirmPassword = in.nextLine();
            if (isBackToMenu(confirmPassword)) return actualUser;

            if (!confirmPassword.equals(password)) {
                System.out.println("Incorrect password. Enter once more");
                continue;
            }
            break;
        }


        actualUser = new UserData(user, login, password);
        return actualUser;
    }

    private static UserData signInActions() {
        UserData actualUser = null;

        loginLoop:
        while (true) {
            System.out.println("Enter your login (Write 'menu' to back to menu)");
            String login = in.nextLine();
            if (isBackToMenu(login)) return actualUser;
            actualUser = checkLogin(login);

            while (actualUser == null) {
                System.out.println("Incorrect login");
                continue loginLoop;
            }

            passwordLoop:
            while (true) {
                System.out.println("Enter password (Write 'menu' to back to menu)");
                String password = in.nextLine();
                if (isBackToMenu(password)) return null;
                while (!actualUser.getPassword().equals(password)) {
                    System.out.println("Incorrect password");
                    continue passwordLoop;
                }
                return actualUser;
            }
        }


    }

    private static boolean isBackToMenu(String input) {
        return input.trim().toLowerCase().equals("menu");
    }

    private static UserData checkLogin(String login) {
        for (UserData aUser : userDataList) {
            if (aUser.getLogin().equals(login)) {
                return aUser;
            }
        }
        return null;
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
        } catch (ParseException | IOException e) {
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
        } finally {
            return flag;
        }

    }

    private static UserData generateAdmin() {
        User test = new User("Test", "Test", 33);
        UserData testUser = new UserData(test, "test", "test");
        return testUser;
    }

    private static void generateCommands() {
        System.out.println("1.Sign in\n" +
                "2.Sign up");
    }

}
