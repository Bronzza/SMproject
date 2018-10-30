package applicationPackage.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailChecker {
    public static boolean checkEmail (String email){
        boolean checkEmail=false;
        String emailPattern = "^\\w+([.\\w]+)*\\w@\\w((.\\w)*\\w+)*\\.\\D{2,3}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) checkEmail=true;
        return checkEmail;
    }
}
