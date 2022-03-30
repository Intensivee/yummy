package com.orange.mainservice.print;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.orange.mainservice.exception.EmptyPdfTableDataException;
import com.orange.mainservice.exception.ImageConversionException;
import org.springframework.core.io.InputStreamResource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

class PdfFileGenerator {

    Document document;
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    FontFamily fontFamily;

    public PdfFileGenerator(FontFamily fontFamily) {
        this.fontFamily = fontFamily;
        document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, outputStream);
        document.open();
    }

    public void addHeader(String text, int alignment) {
        Paragraph paragraph = createParagraph(alignment, this.fontFamily.header);
        paragraph.add(text);
        paragraph.setSpacingAfter(PdfConstants.HEADER_BOTTOM_MARGIN);
        document.add(paragraph);
    }

    public void addSectionHeader(String text, int alignment) {
        Paragraph paragraph = createParagraph(alignment, this.fontFamily.sectionHeader);
        paragraph.add(text);
        paragraph.setSpacingBefore(PdfConstants.SECTION_HEADER_TOP_MARGIN);
        paragraph.setSpacingAfter(PdfConstants.SECTION_HEADER_BOTTOM_MARGIN);
        document.add(paragraph);
    }

    public void addSubheader(String text, int alignment) {
        Paragraph paragraph = createParagraph(alignment, this.fontFamily.subheader);
        paragraph.add(text);
        paragraph.setSpacingBefore(PdfConstants.SUBHEADER_TOP_MARGIN);
        paragraph.setIndentationLeft(PdfConstants.SUBHEADER_LEFT_MARGIN);
        document.add(paragraph);
    }

    public void addInlineTextWithSubheader(String subheader, String text, int alignment) {
        Paragraph paragraph = createParagraph(alignment, this.fontFamily.subheader);
        paragraph.add(subheader);
        paragraph.setFont(this.fontFamily.text);
        paragraph.add(text);
        paragraph.setSpacingBefore(PdfConstants.SUBHEADER_TOP_MARGIN);
        paragraph.setIndentationLeft(PdfConstants.SUBHEADER_LEFT_MARGIN);
        document.add(paragraph);
    }

    public void addText(String text, int alignment) {
        Paragraph paragraph = createParagraph(alignment, this.fontFamily.text);
        paragraph.add(text);
        paragraph.setSpacingBefore(PdfConstants.TEXT_TOP_MARGIN);
        paragraph.setIndentationLeft(PdfConstants.TEXT_LEFT_MARGIN);
        document.add(paragraph);
    }

    public void addTable(List<List<String>> rows) {
        if (rows.isEmpty() || rows.get(0).isEmpty()) {
            throw new EmptyPdfTableDataException();
        }
        List<String> headerRow = rows.get(0);
        PdfPTable table = new PdfPTable(headerRow.size());
        addRowToTable(headerRow, table, fontFamily.subheader);
        rows.stream().skip(1)
                .forEach(row -> addRowToTable(row, table, fontFamily.text));
        document.add(table);
    }

    public void addImage(byte[] imageByteArray, int alignment) {
        try {
            Image image = Image.getInstance(imageByteArray);
            image.setAlignment(alignment);
            image.scaleAbsolute(PdfConstants.IMAGE_WIDTH, PdfConstants.IMAGE_HEIGHT);
            image.setSpacingAfter(PdfConstants.IMAGE_BOTTOM_MARGIN);
            this.document.add(image);
        } catch (IOException e) {
            throw new ImageConversionException();
        }
    }

    public InputStreamResource generateInputStream() {
        document.close();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(byteArrayInputStream);
    }

    private void addRowToTable(List<String> row, PdfPTable table, Font font) {
        row.stream()
                .map(cellText -> new Phrase(cellText, font))
                .forEach(table::addCell);
    }

    private Paragraph createParagraph(int alignment, Font font) {
        Paragraph paragraph = new Paragraph();
        paragraph.setAlignment(alignment);
        paragraph.setFont(font);
        return paragraph;
    }
}
