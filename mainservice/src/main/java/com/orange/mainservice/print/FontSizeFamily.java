package com.orange.mainservice.print;

import lombok.AllArgsConstructor;

@AllArgsConstructor
enum FontSizeFamily {

    DEFAULT(15, 14, 12, 11),
    BIG(16, 14, 13, 12);

    int header;
    int sectionHeader;
    int subHeader;
    int text;
}
