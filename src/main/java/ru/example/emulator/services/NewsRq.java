package ru.example.emulator.services;

import org.apache.commons.lang3.StringUtils;

public class NewsRq {
    public static String NewsRq(String msgText){

        final String RqUID = StringUtils.substringBetween(msgText, "<RqUID>", "</RqUID>");

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<NewsRs>" +
                "<Name>NewsRs</Name>" +
                "</NewsRs>";
    }
}
