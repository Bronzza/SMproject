package applicationPackage.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormater {
    public static String formatData (Date date, boolean withTime){
        if (withTime) return new SimpleDateFormat(" HH:mm dd-MM-yy").format(date);
        return new SimpleDateFormat("dd-MM-yyyy").format(date);
    }
}
