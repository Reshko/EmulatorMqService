package ru.example.emulator.services;

import org.apache.commons.lang3.StringUtils;

public class MusicRq {
    public static String MusicRq(String msgText){

        final String RqUID = StringUtils.substringBetween(msgText, "<RqUID>", "</RqUID>");

        return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<MusicRs>" +
                "<Name>MusicRs</Name>"+
                "</MusicRs>";
    }
}
