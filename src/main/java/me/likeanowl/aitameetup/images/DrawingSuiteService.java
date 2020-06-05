package me.likeanowl.aitameetup.images;

import me.likeanowl.aitameetup.config.ImagesProperties;
import me.likeanowl.aitameetup.images.suites.DrawingSuiteWithResource;
import me.likeanowl.aitameetup.model.BoardingPass;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class DrawingSuiteService {

    private static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd / MM / yy");
    private static final DateTimeFormatter TIME_PATTERN = DateTimeFormatter.ofPattern("h:mm a");

    private final ImagesProperties.BoardingPass properties;

    public DrawingSuiteService(ImagesProperties properties) {
        this.properties = properties.getBoardingPass();
    }

    public List<DrawingSuiteWithResource<?>> buildDrawingSuites(BoardingPass boardingPass, Image barcodeImage) {
        return Stream.concat(
                buildDrawingSuites(boardingPass).stream(),
                buildDrawingSuites(barcodeImage).stream())
                .collect(Collectors.toList());
    }

    private List<DrawingSuiteWithResource<String>> buildDrawingSuites(BoardingPass boardingPass) {
        var resourceMap = buildResourceMap(boardingPass);
        return properties.getTextDrawingSuites()
                .stream()
                .map(suite -> new DrawingSuiteWithResource<>(suite, resourceMap.get(suite.getName())))
                .collect(Collectors.toList());
    }

    private List<DrawingSuiteWithResource<Image>> buildDrawingSuites(Image barcodeImage) {
        return properties.getBarcodeDrawingSuites()
                .stream().map(suite -> new DrawingSuiteWithResource<>(suite, barcodeImage))
                .collect(Collectors.toList());
    }

    private Map<String, String> buildResourceMap(BoardingPass boardingPass) {
        var fullName = boardingPass.getFullName().toUpperCase();
        var destination = boardingPass.getDestination().toUpperCase();
        var date = boardingPass.getArrivalDate().toLocalDate().format(DATE_PATTERN);
        var time = boardingPass.getArrivalDate().toLocalTime().format(TIME_PATTERN);
        return Map.of(
                "mainName", fullName,
                "mainDestination", destination,
                "mainDate", date,
                "mainTime", time,
                "ticketName", fullName,
                "ticketDestination", destination,
                "ticketDate", date,
                "ticketTime", time
        );
    }
}
