package org.szymie;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {

        String inputFileName = args[0];
        String outputFileName = "";

        if(args.length > 1) {
            outputFileName = args[1];
        }

        Stream<String> lines = Files.lines(Paths.get(inputFileName));

        double result = lines.skip(1).map(line -> {

            String[] splitLine = line.split(",");

            Map<String, String> map = new HashMap<>();

            map.put("usage", splitLine[1]);

            return map;
        }).mapToLong(resultMap -> {
            return Long.parseLong(resultMap.get("usage"));
        }).average().getAsDouble();

        lines.close();

        List<String> results = Arrays.asList(
                "CPU usage: " + String.format("%.2f", result / 1000) + "%"
        );

        results.forEach(System.err::println);

        if(!outputFileName.isEmpty()) {
            Files.write(Paths.get(outputFileName), results, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        }
    }
}
