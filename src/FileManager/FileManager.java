package FileManager;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.Buffer;
import java.util.*;


public class FileManager {
    public static HashMap<String,String> readFile(String fileName){
        HashMap<String, String> map = new HashMap<String, String>();
        Properties properties = new Properties();

        try {
        properties.load(new FileInputStream(fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for (String key : properties.stringPropertyNames()) {
            map.put(key, properties.get(key).toString());
        }

        return map;
    }

    public static void writeFile(Map<String,String> map, String fileName){
        Properties properties = new Properties();

        for (Map.Entry<String,String> entry : map.entrySet()) {
            properties.put(entry.getKey(), entry.getValue());
        }

        try {
            properties.store(new FileOutputStream(fileName), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public static String compressMap(Map<String,String> map){
        StringBuilder mapString = new StringBuilder();


        for(String key : map.keySet()){
            mapString.append("{" + key + ":" + map.get(key) + "},");
        }

        return mapString.toString();
    }

    public static String compressMap(Map<String,String> map, boolean isArray){
        if(isArray){
            StringBuilder mapString = new StringBuilder();


            for(String key : map.keySet()){
                mapString.append("{" + key + ":" + map.get(key) + "}");
            }

            return mapString.toString();
        }
        else {
            return compressMap(map);
        }
    }


    public static HashMap<String,String> decompressMap(String mapString){
        HashMap<String,String> map = new HashMap<String,String>();
        String[] KeyVal;
        String[] temp;

        //strips formatting to get ready to put key value pairs in map
        mapString = StringUtils.stripStart(mapString, "{");
        mapString = StringUtils.stripEnd(mapString, "},");
        if (mapString == null || mapString.equals("")) return map;
        KeyVal = mapString.split("},\\{");

        //put each key value pair in map
        for(String keyVal : KeyVal) {
            temp = keyVal.split(":");
            if(temp.length == 2){
                map.put(temp[0], temp[1]);
            } else {
                //wrong formatting in string
                String tempString = temp[1];
                for (int i = 2; i < temp.length; i++) {
                    tempString += (":" + temp[i]);
                }
                map.put(temp[0], tempString);
            }
        }

        return map;
    }

    public static HashMap<String,String> decompressMap(String mapString, boolean isArray) {
        if (isArray){
            HashMap<String,String> map = new HashMap<String,String>();
            String[] KeyVal;
            String[] temp;

            //strips formatting to get ready to put key value pairs in map
            mapString = StringUtils.stripStart(mapString, "{");
            mapString = StringUtils.stripEnd(mapString, "}");

            KeyVal = mapString.split("}\\{");

            //put each key value pair in map
            for(String keyVal : KeyVal) {
                temp = keyVal.split(":");
                if (temp.length == 2) {
                    map.put(temp[0], temp[1]);
                } else if (temp[0].equals("markweights")) {
                    //wrong formatting in string
                    //System.out.println("DEBUG Wrong Formatting in MapString!");

                    String tempString = temp[1];
                    for (int i = 2; i < temp.length; i++) {
                        tempString += (":" + temp[i]);
                    }
                    map.put(temp[0], tempString);
                }
            }

            return map;
        }
        else {
            return decompressMap(mapString);
        }
    }




    public static void saveToFile(int objID, Map<String,String> map, String fileName, String listFileName){
        HashMap<String,String> file = FileManager.readFile(fileName);


        //compress course object to string and add it to file map object
        String objString = FileManager.compressMap(map);
        file.put(Integer.toString(objID), objString);

        //write map object for file to file
        FileManager.writeFile(file, fileName);



        //add new course to courseid cache
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(listFileName, true))){
            bw.write(objID+"\n");
        } catch (IOException ex){
            System.out.println("IOException! List file not found?");
            ex.printStackTrace();
        }
    }

    public static void editFile(int objID, Map<String,String> map, String fileName, String listFileName){
        HashMap<String,String> file = readFile(fileName);

        file.remove(Integer.toString(objID));
        file.put(Integer.toString(objID), compressMap(map));

        FileManager.writeFile(file, fileName);
    }

    public static void deleteInFile(int objID, String fileName, String listFileName){
        HashMap<String,String> file = readFile(fileName);
        file.remove(Integer.toString(objID));

        FileManager.writeFile(file, fileName);


        File readFile = null;
        File tempFile = null;
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            //remove entry from course list file
            readFile = new File(listFileName);
            tempFile = File.createTempFile("file",".txt", readFile.getParentFile());
            br = new BufferedReader(new FileReader(readFile));
            bw = new BufferedWriter(new FileWriter(tempFile));

            for (String line=""; line != null; line = br.readLine()){
                if (!line.equals(Integer.toString(objID))){
                    bw.write(line);
                }
            }
            //System.out.println("Delete error. CourseID not found.");
        } catch (IOException ex){
            System.out.println("IOException! List file not found?");
            ex.printStackTrace();
        } finally {
            try {
                if (readFile != null && tempFile != null && br != null && bw != null) {
                    br.close();
                    bw.close();
                    readFile.delete();
                    tempFile.renameTo(readFile);
                }
            } catch (IOException ex){
                ex.printStackTrace();
            }
        }
    }

    public static HashMap<String,String> accessFile(int objID, String fileName){
        HashMap<String,String> file = readFile(fileName);

        String objString = file.get(Integer.toString(objID));

        return decompressMap(objString);
    }









//
//    public static void writeFile(Map map, String fileName){
//
//        try {
//            BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
//            if (map == null) {
//                out.write("null");
//            } else {
//                boolean first = true;
//                Iterator iter = map.entrySet().iterator();
//                out.write(123);
//
//                while(iter.hasNext()) {
//                    if (first) {
//                        first = false;
//                    } else {
//                        out.write(44);
//                    }
//
//                    Map.Entry entry = (Map.Entry)iter.next();
//                    out.write(34);
//                    out.write(escape(String.valueOf(entry.getKey())));
//                    out.write(34);
//                    out.write(58);
//                    JSONValue.writeJSONString(entry.getValue(), out);
//                }
//
//                out.write(125);
//            }
//        } catch (IOException ex) {
//            System.out.println("IOException!");
//            ex.printStackTrace();
//        }
//    }
//
//
//
//
//
//
//    //Convert escape characters in string input to pure string
//    //You don't really need this but it's for input sanitization
//    public static String escape(String s) {
//        if (s == null) {
//            return null;
//        } else {
//            StringBuffer sb = new StringBuffer();
//            escape(s, sb);
//            return sb.toString();
//        }
//    }
//
//    //convert escape characters in string input to pure string
//    static void escape(String s, StringBuffer sb) {
//
//        for(int i = 0; i < s.length(); ++i) {
//            char ch = s.charAt(i);
//            switch(ch) {
//                case '\b':
//                    sb.append("\\b");
//                    continue;
//                case '\t':
//                    sb.append("\\t");
//                    continue;
//                case '\n':
//                    sb.append("\\n");
//                    continue;
//                case '\f':
//                    sb.append("\\f");
//                    continue;
//                case '\r':
//                    sb.append("\\r");
//                    continue;
//                case '"':
//                    sb.append("\\\"");
//                    continue;
//                case '/':
//                    sb.append("\\/");
//                    continue;
//                case '\\':
//                    sb.append("\\\\");
//                    continue;
//            }
//
//            if (ch >= 0 && ch <= 31 || ch >= 127 && ch <= 159 || ch >= 8192 && ch <= 8447) {
//                String ss = Integer.toHexString(ch);
//                sb.append("\\u");
//
//                for(int k = 0; k < 4 - ss.length(); ++k) {
//                    sb.append('0');
//                }
//
//                sb.append(ss.toUpperCase());
//            } else {
//                sb.append(ch);
//            }
//        }
//
//    }
}
