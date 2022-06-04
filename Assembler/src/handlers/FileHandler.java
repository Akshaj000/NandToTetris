package handlers;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    public static ArrayList<String> readFile(String filename){
        File file = new File(filename);
        BufferedReader bis = null;
        FileReader  fis= null;
        String line;
        ArrayList<String> data = new ArrayList<>();
        try {
            fis = new FileReader(file);
            bis = new BufferedReader(fis);
            while( (line = bis.readLine()) != null ){
                data.add(line.trim());
            }
        } catch(FileNotFoundException fnfe) {
            System.out.println("The specified file not found" + fnfe);
        } catch(IOException ioe) {
            System.out.println("I/O Exception: " + ioe);
        } finally {
            try{
                if(bis != null) {
                    fis.close();
                    bis.close();
                }
            }catch(IOException ioe) {
                System.out.println("Error in InputStream close(): " + ioe);
            }
        }
        return data;
    }

    public static void writeToFile(String filename, ArrayList<String> data){
        FileOutputStream fos = null;
        File file;
        StringBuilder mycontent = new StringBuilder();
        for (String line : data) {
            mycontent.append(line).append("\n");
        }
        try {
            file = new File(filename);
            fos = new FileOutputStream(file);
            if (!file.exists()) {
                var newFile = file.createNewFile();
            }
            byte[] bytesArray = mycontent.toString().getBytes();
            fos.write(bytesArray);
            fos.flush();
            System.out.println("File Written Successfully as "+filename);
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            }
            catch (IOException ioe) {
                System.out.println("Error in closing the Stream");
            }
        }
    }
}
