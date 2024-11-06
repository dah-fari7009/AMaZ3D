package org.example.amaz3d;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class Constants {
    public static String PACKAGE_NAME = "org/example/amaz3d";
    public static String RESOURCES_PATH = "src/main/resources" + File.separator + PACKAGE_NAME;
    public static String TEXT_RESOURCES_PATH = RESOURCES_PATH  + File.separator + "txt";
    public static String SOUND_RESOURCES_PATH = RESOURCES_PATH + File.separator + "sounds";
    public static String IMAGE_RESOURCES_PATH = RESOURCES_PATH + File.separator + "images";
    public static String SAVING_RESOURCES_PATH = RESOURCES_PATH + File.separator + "savings"; //TODO move savings, shouldn't be in resources
    public static String FXML_RESOURCES_PATH = RESOURCES_PATH + File.separator + "fxml";
    public static String CSS_RESOURCES_PATH = RESOURCES_PATH + File.separator + "css";



    public static String getCssResourcePath(String value){
        return Constants.class.getResource("css/").toExternalForm() + value;
        //return Constants.class.getResource("css/" + value).toExternalForm();
    }

    public static URL getSoundResource(String value){
        return Constants.class.getResource("sounds/" + value);
        //return Constants.class.getResource("sounds/" +  value).toExternalForm();
    }

    public static String getSoundResourcePath(String value){
        return Constants.class.getResource("sounds/").toExternalForm() + value;
        //return Constants.class.getResource("sounds/" +  value).toExternalForm();
    }

    public static String getTxtResourcePath(String value){
        return Constants.class.getResource("txt/").toExternalForm() + value;
        //return Constants.class.getResource("txt/" + value).toExternalForm();
    }

    public static String getSavingResourcePath(String value){
        return Constants.class.getResource("savings/").toExternalForm() + value;
        //return Constants.class.getResource("savings/" +  value).toExternalForm();
    }

    public static String getImgResourcePath(String value){
        return Constants.class.getResource("images/").toExternalForm() + value;
        //return Constants.class.getResource("images/" + value).toExternalForm();
    }

    public static String getFxmlResourcePath(String value){
        return Constants.class.getResource("fxml/").toExternalForm()  + value;
        //return Constants.class.getResource("fxml/" + value).toExternalForm();
    }

    public static URL getFxmlResource(String value){
        return Constants.class.getResource("fxml/" + value);
    }

    public static InputStream getResourceAsStream(String path){
        return Constants.class.getResourceAsStream(path);
    }
}
