package com.orange.mainservice.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatUtils {

    public static String mapDoubleToDefaultFormat(Double value) {
        DecimalFormat defaultFormat = new DecimalFormat(Constants.DEFAULT_DECIMAL_FORMAT);
        return defaultFormat.format(value);
    }
}
