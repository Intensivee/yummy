package com.orange.mainservice.print;

import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.orange.mainservice.direction.Direction;
import com.orange.mainservice.ingredient.Ingredient;
import com.orange.mainservice.rate.Rate;
import com.orange.mainservice.recipe.Recipe;
import com.orange.mainservice.recipe.RecipePdfRequest;
import com.orange.mainservice.util.FileUtils;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.core.io.InputStreamResource;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static com.orange.mainservice.print.PdfConstants.DIRECTIONS_SECTION_HEADER_TEXT;
import static com.orange.mainservice.util.FormatUtils.mapDoubleToDefaultFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RecipePdfUtils {

    public static InputStreamResource generatePDFFileForRecipe(Recipe recipe, RecipePdfRequest pdfRequest) {
        PdfFileGenerator pdfFileGenerator = new PdfFileGenerator(FontFamily.HELVETICA_DEFAULT_SIZE);
        pdfFileGenerator.addHeader(recipe.getTitle(), Element.ALIGN_CENTER);
        if (pdfRequest.isImage()) {
            addImage(pdfFileGenerator, recipe.getImgUrl());
        }
        if (pdfRequest.isSocialSection()) {
            addSocialDetailsSection(pdfFileGenerator, recipe);
        }
        if (pdfRequest.isDirectionsSection()) {
            addDirectionsSection(pdfFileGenerator, recipe.getDirections());
        }
        if (pdfRequest.isIngredientsSection()) {
            addIngredientsSection(pdfFileGenerator, recipe.getIngredients());
        }
        return pdfFileGenerator.generateInputStream();
    }

    private static void addImage(PdfFileGenerator pdfFileGenerator, String imageUrl) {
        byte[] fileByteArray = FileUtils.downloadFile(imageUrl);
        pdfFileGenerator.addImage(fileByteArray, Image.MIDDLE);
    }

    private static void addSocialDetailsSection(PdfFileGenerator pdfFileGenerator, Recipe recipe) {
        String username = recipe.getUser().getUsername();
        String averageRateFormatted = getAverageRateFormatted(recipe.getRates());
        String time = "< " + recipe.getTimeType().getValue();

        List<List<String>> tableRows = List.of(
                List.of("Author: ", "Rate: ", "Time: "),
                List.of(username, averageRateFormatted, time)
        );
        pdfFileGenerator.addSectionHeader("Details", Element.ALIGN_LEFT);
        pdfFileGenerator.addTable(tableRows);
    }

    private static void addDirectionsSection(PdfFileGenerator pdfFileGenerator, Set<Direction> directions) {
        pdfFileGenerator.addSectionHeader(DIRECTIONS_SECTION_HEADER_TEXT, Element.ALIGN_LEFT);
        directions.stream()
                .sorted(Comparator.comparing(Direction::getOrder))
                .forEach(direction -> {
                    pdfFileGenerator.addSubheader(direction.getDirectionStepHeader(), Element.ALIGN_LEFT);
                    pdfFileGenerator.addText(direction.getDescription(), Element.ALIGN_LEFT);
                });
    }

    private static void addIngredientsSection(PdfFileGenerator pdfFileGenerator, Set<Ingredient> ingredients) {
        pdfFileGenerator.addSectionHeader("Ingredients", Element.ALIGN_LEFT);
        ingredients.stream()
                .map(Ingredient::toString)
                .forEach(ingredient -> pdfFileGenerator.addText(ingredient, Element.ALIGN_LEFT));
    }

    private static String getAverageRateFormatted(Set<Rate> rates) {
        Double averageRate = rates.stream()
                .mapToDouble(Rate::getValue)
                .average()
                .orElse(0D);
        return mapDoubleToDefaultFormat(averageRate) + "/5,00";
    }
}
