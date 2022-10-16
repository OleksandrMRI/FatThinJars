package ua.shpp.properties;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Main implements Constants {
    private static final String FILENAME = "conf/config.properties";
    private static final String OUTSIDE_PROPERTIES = "config.properties";

    public static void main(String[] args) {
        Properties properties = new Properties();
        File jarPath = new File(Main.class.getProtectionDomain()
                .getCodeSource().getLocation().getPath());
        String propertiesPath = jarPath.getParentFile().getAbsolutePath();

        Human student = new Student();
        Human employee = new Employee();

        properties.setProperty("version", "1.0-SNAPSHOT");
        properties.setProperty("filename", "properties-1.0-SNAPSHOT.jar");
        properties.setProperty("Key1", student.getClass().getName());
        properties.setProperty("Key2", ""/*employee.getClass().getName()*/);

        File file = new File(FILENAME);
        if (file.exists()) {
            try (FileOutputStream out = new FileOutputStream(FILENAME)) {
                properties.store(out, "My comment");
                log.debug("Try unit with store into FILENAME");
            } catch (IOException e) {
                log.error("Oh, come on!FileNotFound, line {}", 28);
            }
        } else {
            log.warn("Launch from {}\n", properties.getProperty("filename"));
        }

        Properties properties1 = new Properties();

        String fullFilePath = propertiesPath + File.separator + OUTSIDE_PROPERTIES;
//        if (new File(fullFilePath).exists()) {
//            try (InputStream inputStream = new FileInputStream(fullFilePath);
//                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//                properties1.load(bufferedReader);
//            } catch (IOException e) {
//                log.error("It`s impossible. FileNotFound final {}, line 45", fullFilePath, e);
//            }
//            fullFilePath = "outside " + propertiesPath + File.separator+ OUTSIDE_PROPERTIES;
//        } else
        if (new File(OUTSIDE_PROPERTIES).exists()) {
            fullFilePath=OUTSIDE_PROPERTIES;
            try (InputStream inputStream = new FileInputStream(fullFilePath);
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                properties1.load(bufferedReader);
            } catch (IOException e) {
                log.error("It`s impossible. FileNotFound final {}, line 45", fullFilePath, e);
            }
            fullFilePath = "outside "  + OUTSIDE_PROPERTIES;
        } else if (new File(FILENAME).exists()) {
            fullFilePath = FILENAME;
            log.error("Reading from file {}", fullFilePath);
            try (FileInputStream fis = new FileInputStream(fullFilePath)) {
                properties1.load(fis);
            } catch (IOException e) {
                log.error("Oh, no! FileNotFound final {}, line 68", fullFilePath, e);
            }
        } else {
            log.error("Any FileNotFound. Fatal Error");
            System.exit(1);
        }

        log.info("These values reading from {}", fullFilePath);
        log.info("\tappVersion is {}", properties1.getProperty("version"));
        log.info("\tKey1 is {}", properties1.getProperty("Key1"));
        log.info("\tKey2 is {}", properties1.getProperty("Key2"));
        log.info("");

        HumanInterface h = null;
        try {
            log.debug("Key2 {}", properties1.getProperty("Key2"));
            h = (Human) Class.forName(properties1.getProperty("Key2")).newInstance();
        } catch (
                IllegalAccessException e) {
            log.error("IllegalAccessException, line 70", e);
        } catch (
                InstantiationException i) {
            log.error("InstantiationException, line 70", i);
        } catch (
                ClassNotFoundException c) {
            log.error("ClassNotFoundException, line 70", c);
        }
        if (h != null) {
            h.printName();
        } else {
            try {
                h.printName();
            } catch (
                    NullPointerException e) {
                log.error("illegal config of value Key1 or Key2,HumanInterface h = null, line 86", e);
            } finally {
                log.error("\n");
            }
        }
    }
}

interface HumanInterface {
    void printName();
}