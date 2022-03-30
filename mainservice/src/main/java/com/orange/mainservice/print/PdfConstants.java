package com.orange.mainservice.print;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
final class PdfConstants {

    public static final int HEADER_BOTTOM_MARGIN = 10;
    public static final int SECTION_HEADER_TOP_MARGIN = 15;
    public static final int SECTION_HEADER_BOTTOM_MARGIN = 5;
    public static final int SUBHEADER_TOP_MARGIN = 3;
    public static final int SUBHEADER_LEFT_MARGIN = 2;
    public static final int TEXT_TOP_MARGIN = 1;
    public static final int TEXT_LEFT_MARGIN = 5;

    public static final int IMAGE_BOTTOM_MARGIN = 5;
    public static final int IMAGE_WIDTH = 300;
    public static final int IMAGE_HEIGHT = 180;

    public static final String DIRECTIONS_SECTION_HEADER_TEXT = "Directions";
}
