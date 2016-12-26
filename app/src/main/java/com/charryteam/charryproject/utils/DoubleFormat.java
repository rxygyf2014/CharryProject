package com.charryteam.charryproject.utils;

import java.text.DecimalFormat;

/**
 * Created by xiabaikui on 2015/12/1.
 */
public class DoubleFormat {
    private static DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    public static DecimalFormat getDecimalFormat() {
        return decimalFormat;
    }
}
