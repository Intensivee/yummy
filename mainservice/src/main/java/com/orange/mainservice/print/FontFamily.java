package com.orange.mainservice.print;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;

enum FontFamily {
    HELVETICA_DEFAULT_SIZE(
            FontFactory.getFont(FontFactory.HELVETICA_BOLD),
            FontFactory.getFont(FontFactory.HELVETICA_BOLD),
            FontFactory.getFont(FontFactory.HELVETICA_BOLD),
            FontFactory.getFont(FontFactory.HELVETICA),
            FontSizeFamily.DEFAULT
    ),
    TIMES_DEFAULT_SIZE(
            FontFactory.getFont(FontFactory.TIMES_BOLD),
            FontFactory.getFont(FontFactory.TIMES_BOLD),
            FontFactory.getFont(FontFactory.TIMES_BOLD),
            FontFactory.getFont(FontFactory.TIMES_ROMAN),
            FontSizeFamily.DEFAULT
    );

    Font header;
    Font sectionHeader;
    Font subheader;
    Font text;

    FontFamily(Font header, Font sectionHeader, Font subheader, Font text, FontSizeFamily fontSizeFamily) {
        this.header = header;
        this.sectionHeader = sectionHeader;
        this.subheader = subheader;
        this.text = text;
        initializeFontsSize(fontSizeFamily);
    }

    private void initializeFontsSize(FontSizeFamily fontSizeFamily) {
        header.setSize(fontSizeFamily.header);
        sectionHeader.setSize(fontSizeFamily.sectionHeader);
        subheader.setSize(fontSizeFamily.subHeader);
        text.setSize(fontSizeFamily.text);
    }
}


