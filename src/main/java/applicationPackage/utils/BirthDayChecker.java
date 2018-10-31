package applicationPackage.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.util.Calendar;
import java.util.StringTokenizer;

public class BirthDayChecker {
    public static boolean checkDate(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        String[] delimBrithDay = new String[3];
        int i = 0;
        while (st.hasMoreElements()) {
            delimBrithDay[i] = (String) st.nextElement();
            i++;
        }
        boolean isDate = false;
        Calendar calendar = Calendar.getInstance();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        try {
            if (Integer.parseInt(delimBrithDay[1]) > 0 && Integer.parseInt(delimBrithDay[1]) <= 12 &&
                    Integer.parseInt(delimBrithDay[2]) > 1920 &&
                    (year - Integer.parseInt(delimBrithDay[2]) > 5)) {
                switch (Integer.parseInt(delimBrithDay[1])) {
                    case 1:
                    case 3:
                    case 5:
                    case 7:
                    case 8:
                    case 10:
                    case 12:
                        if (Integer.parseInt(delimBrithDay[0]) < 0 && Integer.parseInt(delimBrithDay[0]) >= 31)
                            break;
                    case 4:
                    case 6:
                    case 9:
                    case 11:
                        if (Integer.parseInt(delimBrithDay[0]) < 0 && Integer.parseInt(delimBrithDay[0]) >= 30)
                            break;
                    case 2:
                        if (Integer.parseInt(delimBrithDay[0]) < 0 && Integer.parseInt(delimBrithDay[0]) >= 29)
                            break;
                    default:
                        isDate = true;
                        break;
                }
            }
        } catch (ClassCastException e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage("incorrect input of Date", null));
        }
        return isDate;
    }
}
