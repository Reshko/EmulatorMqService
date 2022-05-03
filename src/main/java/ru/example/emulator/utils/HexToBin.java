package ru.example.emulator.utils;

import com.ibm.msg.client.commonservices.nls.NLSServices;

import javax.jms.JMSException;
import java.util.HashMap;

public class HexToBin {
    public static byte[] hexToBin(String hex, int start) throws JMSException {

        int length = hex.length() - start;

        byte[] retval;

        if (length == 0) {
            retval = new byte[0];

            return retval;
        } else if (length >= 0 && length % 2 == 0) {
            length /= 2;

            retval = new byte[length];

            for (int i = 0; i < length; ++i) {
                int digit1 = Character.digit(hex.charAt(2 * i + start), 16) << 4;

                int digit2 = Character.digit(hex.charAt(2 * i + start + 1), 16);

                if (digit1 < 0 || digit2 < 0) {
                    HashMap<String, String> inserts = new HashMap();

                    inserts.put("XMSC_INSERT_HEX_STRING", hex.substring(start));

                    JMSException je2 = (JMSException) NLSServices.createException("JMSCMQ1044", inserts);

                    throw je2;
                }

                retval[i] = (byte) (digit1 + digit2);
            }

            return retval;
        } else {
            HashMap<String, String> inserts = new HashMap();

            inserts.put("XMSC_INSERT_HEX_STRING", hex.substring(start));

            JMSException je = (JMSException) NLSServices.createException("JMSCMQ1044", inserts);

            throw je;
        }
    }
}
