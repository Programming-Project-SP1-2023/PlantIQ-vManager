package com.plantiq.vsmarthomehub.services;

import java.io.*;

public class FileService {

    public static void saveSession(String session){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(System.getProperty("user.dir")+"/session.txt"));
            writer.write(session);

            writer.close();
        }catch (IOException e){
            System.out.println("Failed to save login session");
        }
    }

    public static String loadSession(){

        String session;

        try{
            BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/session.txt"));
            session = reader.readLine();

        } catch (IOException e) {
            return null;
        }

        return session;
    }
}
