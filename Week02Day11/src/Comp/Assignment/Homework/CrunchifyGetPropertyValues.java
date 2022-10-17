package Comp.Assignment.Homework;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Crunchify.com
 *
 */
public class CrunchifyGetPropertyValues {

    public String getPropValues(String key) throws IOException {

        InputStream inputStream = null;
        String ret = "";

        try {
            Properties prop = new Properties();
            String propFileName = "config2.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // get the property value and print it out
            ret = prop.getProperty(key);
            System.out.println(key + " = " + ret);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return ret;
    }

    public static void main(String args []) throws IOException {
        CrunchifyGetPropertyValues obj = new CrunchifyGetPropertyValues();

        obj.getPropValues(("IP"));
        obj.getPropValues("PORT");
        obj.getPropValues("USER");
        obj.getPropValues("PASS");
        obj.getPropValues("NAMADB");


    }
}