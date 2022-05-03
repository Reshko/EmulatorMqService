package ru.example.emulator.services;

import org.apache.commons.lang3.StringUtils;

public class MapRq {
    public static String MapRq(String msgText){

        final String RqUID = StringUtils.substringBetween(msgText, "<RqUID>", "</RqUID>");

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<MapRs>" +
                "<Name>MapRS</Name>" +
                "</MapRs>";
    }
}
