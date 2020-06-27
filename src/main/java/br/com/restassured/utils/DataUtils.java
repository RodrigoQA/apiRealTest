package br.com.restassured.utils;




import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DataUtils {
    public static String getDataDiferencaDias(Integer qtsDias){
        Calendar calendar =  Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, qtsDias);
return getDataFormatada(calendar.getTime());
    }
public static String getDataFormatada(Date data){
    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    return format.format(data);
}
}
