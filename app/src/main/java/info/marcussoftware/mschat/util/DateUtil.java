package info.marcussoftware.mschat.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Marcus Eduardo - marcusedu7@gmail.com on 22/01/2018.
 */

public class DateUtil {
    private static SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
//            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");

    public static String formatDate(Calendar dateTime) {
        Calendar now = Calendar.getInstance();
        if (compareSameDay(dateTime, now))
            return "Hoje";
        now.add(Calendar.DAY_OF_MONTH, -1);
        if (compareSameDay(dateTime, now))
            return "Ontem";
        return simpleDateFormat.format(dateTime.getTime());
    }

    public static boolean compareSameDay(Calendar dateTime, Calendar dateTime1) {
        return dateTime.get(Calendar.YEAR) == dateTime1.get(Calendar.YEAR) &&
                dateTime.get(Calendar.MONTH) == dateTime1.get(Calendar.MONTH) &&
                dateTime.get(Calendar.DAY_OF_MONTH) == dateTime1.get(Calendar.DAY_OF_MONTH);
    }
}
