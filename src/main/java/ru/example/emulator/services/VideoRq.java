package ru.example.emulator.services;

import org.apache.commons.lang3.StringUtils;

public class VideoRq {
    public static String VideoRq(String msgText){

        final String RqUID = StringUtils.substringBetween(msgText, "<RqUID>", "</RqUID>");

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<VideoRs>" +
                "<Name>VideoRs</Name>" +
                "</VideoRs>";
    }
}
