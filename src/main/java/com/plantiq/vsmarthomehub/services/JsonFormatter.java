package com.plantiq.vsmarthomehub.services;

import javafx.scene.text.Text;

import java.util.ArrayList;

public class JsonFormatter {

    public static ArrayList<Text> forTextFlow(String json){

        String[] contents = JsonFormatter.forTextArea(json).split("\n");
        ArrayList<Text> output = new ArrayList<>();

        int i = 0;
        while(i <contents.length){

            String value = contents[i];

            if(value.equals("{") || value.equals("}")){

                String outputValue;
                if (value.equals("{")) {
                    outputValue = value+="\n";
                }else{
                    outputValue = value;
                }

                Text bracket = new Text(outputValue);
                bracket.setStyle("-fx-fill: white");
                output.add(bracket);

            }else{
                if(value.contains(":")){
                    String[] pairs = value.split(":");

                    Text key = new Text(pairs[0]);
                    key.setStyle("-fx-fill: #7ecf2b");
                    output.add(key);

                    Text bracket = new Text(" : ");
                    bracket.setStyle("-fx-fill: white");
                    output.add(bracket);

                    boolean addComer = false;
                    boolean addNewLine = false;
                    String textValue;

                    if(pairs[1].contains(",")){
                        addComer = true;
                        textValue = pairs[1].substring(0,pairs[1].length()-1);
                    }else{
                        addNewLine = true;
                        textValue = pairs[1];
                    }
                    Text keyContents = new Text(textValue);

                    if(JsonFormatter.isFloat(textValue) || JsonFormatter.isBool(textValue)){
                        keyContents.setStyle("-fx-fill: #f0e137");
                    }else{
                        keyContents.setStyle("-fx-fill: #a896ff");
                    }

                    if(addNewLine){
                        keyContents.setText(keyContents.getText()+"\n");
                    }

                    output.add(keyContents);

                    if(addComer){
                        Text comer = new Text(","+"\n");
                        comer.setStyle("-fx-fill: white");
                        output.add(comer);
                    }

                }
            }

            i++;
        }

        return output;
    }

    public static String forTextArea(String text){
        StringBuilder json = new StringBuilder();
        String indentString = "";

        for (int i = 0; i < text.length(); i++) {
            char letter = text.charAt(i);
            switch (letter) {
                case '{':
                case '[':
                    json.append("\n" + indentString + letter + "\n");
                    indentString = indentString + "\t";
                    json.append(indentString);
                    break;
                case '}':
                case ']':
                    indentString = indentString.replaceFirst("\t", "");
                    json.append("\n" + indentString + letter);
                    break;
                case ',':
                    json.append(letter + "\n" + indentString);
                    break;

                default:
                    json.append(letter);
                    break;
            }
        }

        return json.toString();
    }


    public static boolean isFloat(String value){
        try{
            Integer.parseInt(value);
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public static boolean isBool(String value){

        return value.equals("true") || value.equals("false");
    }
}
