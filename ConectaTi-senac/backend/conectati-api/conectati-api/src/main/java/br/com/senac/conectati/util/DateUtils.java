package br.com.senac.conectati.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DateUtils {


    private DateUtils() {
    }



    public static String formatarData(LocalDate data){

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);


        return data.format(formatter);

    }



    public static LocalDate converterData(String data){

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern(Constants.DATE_FORMAT);


        return LocalDate.parse(data, formatter);

    }

}