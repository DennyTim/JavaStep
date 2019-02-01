package logging;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {
    private final static String fileRoute = "tmp/log/application.log";
    private final static SimpleDateFormat SDF =  new SimpleDateFormat("dd/MM/yyyy hh:mm");
    private final static File file = new File(fileRoute);
    private final static File dir = new File("tmp/log");

    private Logger(){}

    public static void info(String msg){
        try{
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            String text = msg + " " + SDF.format(new Date()) + "\n";
            Files.write(Paths.get(fileRoute), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void error(String msg){
        try{
            if(!dir.exists()){
                dir.mkdirs();
            }
            if(!file.exists()){
                file.createNewFile();
            }
            String text ="[ERROR] " + msg + " " + SDF.format(new Date()) + "\n";
            Files.write(Paths.get(fileRoute), text.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
