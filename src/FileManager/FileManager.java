package FileManager;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.Buffer;
import java.util.*;

/**
 * This is an input/output class that handles all of the dirty work in the read and write operations of the entity classes.
 * It is instantiated in each entity class, and gets called by the read/write functions of all the entity classes.
 * @author Ng Man Chun
 * @version 1.0
 * @since 2018-11-15
 */
public class FileManager {

    /**
     * This method does the actual file access. It takes a file name as string and returns a HashMap containing the data
     * in the file.
     * Data is stored as properties in a plain text file, which helps with debugging.
     * @param fileName
     * @return HashMap of file data
     */
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

    /**
     * This method does the actual file access. It takes some data as hashmap and a file name as string and writes it to the file.
     * The data is stored as properties in a plain text file.
     * @param map
     * @param fileName
     */
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


    /**
     * This method takes a hash map and converts it into a string, with formatting similar to JSON.
     * It is necessary because of hash map nesting with heterogeneous objects in the application-
     * hashmaps can contain objects, string, or arrays of objects, so it is not possible to instantiate a nested hashmap.
     * Our solution was to compress nested hashmaps to string so that when instantiating hashmaps, the data fields are
     * always strings.
     * @param map
     * @return compressed map as string
     */
    public static String compressMap(Map<String,String> map){
        StringBuilder mapString = new StringBuilder();


        for(String key : map.keySet()){
            mapString.append("{" + key + ":" + map.get(key) + "},");
        }

        return mapString.toString();
    }

    /**
     * This method is the same as compressMap(String), but with some different string formatting to handle arrays of objects
     * @param map
     * @param isArray
     * @return compressed map as string
     */
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


    /**
     * This method takes a compressed hashmap in the form of string, and extracts data from it, returning the original hashmap
     * @param mapString
     * @return decompressed hashmap
     */
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

    /**
     * This method is same as decompressMap(String), but with different string formatting to handle object arrays.
     * @param mapString
     * @param isArray
     * @return
     */
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


    /**
     * This method is called by each entity class to handle writing to file. It handles all of the hashmap compression
     * necessary and updates the listFile which contains a list of all the names of objects stored in the main file.
     * The method is separate from the actual read/write to the main file because originally I/O was handled by JSON in our application
     * During the conversion from JSON to plain java read/write we simply wrote new methods to handle the file access.
     * @param objID
     * @param map
     * @param fileName
     * @param listFileName
     */
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

    /**
     * This method is an overloaded version of saveToFile(int, Map, String, String). It handles cases where two objects
     * with some relation need to be stored together (i.e. entries for registration and marks)
     * @param objID1
     * @param objID2
     * @param map
     * @param fileName
     * @param listFileName
     */
    public static void saveToFile(int objID1,int objID2, Map<String,String> map, String fileName, String listFileName){
        HashMap<String,String> file = FileManager.readFile(fileName);


        //compress course object to string and add it to file map object
        String objString = FileManager.compressMap(map);
        file.put(Integer.toString(objID1)+"."+Integer.toString(objID2), objString);

        //write map object for file to file
        FileManager.writeFile(file, fileName);



        //add new course to courseid cache
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(listFileName, true))){
            bw.write(objID1+"."+objID2+"\n");
        } catch (IOException ex){
            System.out.println("IOException! List file not found?");
            ex.printStackTrace();
        }
    }

    /**
     * This method handles editing an object in file. It takes a new map containing modified data of the object and fully
     * overwrites the original data.
     * @param objID
     * @param map
     * @param fileName
     * @param listFileName
     */
    public static void editFile(int objID, Map<String,String> map, String fileName, String listFileName){
        HashMap<String,String> file = readFile(fileName);

        file.remove(Integer.toString(objID));
        file.put(Integer.toString(objID), compressMap(map));

        FileManager.writeFile(file, fileName);
    }

    /**
     * This method deletes an object in the file. It then updates the listFile (which contains a list of all objects stored).
     * @param objID
     * @param fileName
     * @param listFileName
     */
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
                    bw.write("\n");
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

    /**
     * This method is an overloaded version of deleteInFile(int, String, String). It handles cases where one data entry
     * in file containing two related objects need to be deleted (i.e. entries in registration and marks).
     * @param objID1
     * @param objID2
     * @param fileName
     * @param listFileName
     */
    public static void deleteInFile(int objID1,int objID2, String fileName, String listFileName){
        HashMap<String,String> file = readFile(fileName);
        file.remove(Integer.toString(objID1)+"."+Integer.toString(objID2));
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
                if (!line.equals(Integer.toString(objID1)+"."+Integer.toString(objID2))){
                    bw.write(line);
                    bw.write("\n");
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

    /**
     * This method simply reads an object from the file
     * @param objID
     * @param fileName
     * @return object data as hashmap
     */
    public static HashMap<String,String> accessFile(int objID, String fileName){
        HashMap<String,String> file = readFile(fileName);


        String objString = file.get(Integer.toString(objID));

        return decompressMap(objString);
    }

    /**
     * This method is an overloaded version of accessFile(int, String). It handles cases where the entry that the user
     * wants to read contains two related objects.
     * @param objID1
     * @param objID2
     * @param fileName
     * @return objects data as hashmap
     */
    public static HashMap<String,String> accessFile(int objID1,int objID2, String fileName){
        HashMap<String,String> file = readFile(fileName);

        String objString = file.get(Integer.toString(objID1)+"."+Integer.toString(objID2));

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
