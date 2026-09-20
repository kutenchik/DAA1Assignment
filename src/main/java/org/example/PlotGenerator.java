package org.example;

//lan eto full gpt sdelal

import javax.imageio.ImageIO;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class PlotGenerator {
    private static final String[] ALGORITHMS = {"MergeSort", "QuickSort", "QuickSelect"};
    private static final String[] INPUTS = {"random", "sorted", "duplicates"};
    private static final Color[] COLORS = {new Color(31, 119, 180), new Color(255, 127, 14), new Color(44, 160, 44), new Color(214, 39, 40), new Color(148, 103, 189), new Color(140, 86, 75), new Color(227, 119, 194), new Color(127, 127, 127), new Color(23, 190, 207)};

    private PlotGenerator() {
    }

    public static void create(Path csv, Path directory) throws IOException {
        Files.createDirectories(directory);
        List<Row> rows = read(csv);
        draw(rows, directory.resolve("time_vs_n.png"), "Time vs n", "time (ms)", Value.TIME);
        draw(rows, directory.resolve("depth_vs_n.png"), "Max recursion depth vs n", "depth", Value.DEPTH);
        draw(rows, directory.resolve("ratio_vs_n.png"), "Comparison ratio vs n", "comparisons / growth", Value.RATIO);
    }

    private static List<Row> read(Path csv) throws IOException {
        List<Row> rows = new ArrayList<>();
        List<String> lines = Files.readAllLines(csv);
        for (String line : lines.subList(1, lines.size())) {
            String[] parts = line.split(",");
            rows.add(new Row(parts[0], parts[1], Integer.parseInt(parts[2]), Double.parseDouble(parts[3]), Long.parseLong(parts[4]), Integer.parseInt(parts[5])));
        }
        return rows;
    }

    private static void draw(List<Row> rows, Path output, String title, String yLabel, Value value) throws IOException {
        int width = 1200;
        int height = 760;
        int left = 95;
        int right = 270;
        int top = 70;
        int bottom = 85;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = image.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        double maximum = rows.stream().mapToDouble(row -> row.value(value)).max().orElse(1);
        maximum *= 1.1;
        graphics.setColor(Color.DARK_GRAY);
        graphics.setFont(new Font("SansSerif", Font.BOLD, 22));
        graphics.drawString(title, left, 38);
        graphics.setFont(new Font("SansSerif", Font.PLAIN, 14));
        for (int step = 0; step <= 5; step++) {
            int y = top + (height - top - bottom) * step / 5;
            double label = maximum * (5 - step) / 5;
            graphics.setColor(new Color(225, 225, 225));
            graphics.drawLine(left, y, width - right, y);
            graphics.setColor(Color.DARK_GRAY);
            graphics.drawString(String.format(Locale.ROOT, "%.2f", label), 18, y + 5);
        }
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawLine(left, top, left, height - bottom);
        graphics.drawLine(left, height - bottom, width - right, height - bottom);
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};
        for (int i = 0; i < sizes.length; i++) {
            int x = x(sizes[i], left, width - right);
            graphics.drawString(String.format("%,d", sizes[i]), x - 22, height - bottom + 24);
        }
        graphics.drawString("n (log scale)", (width - right + left) / 2 - 40, height - 25);
        graphics.drawString(yLabel, left, 58);
        int color = 0;
        for (String algorithm : ALGORITHMS) {
            for (String input : INPUTS) {
                Color lineColor = COLORS[color++];
                graphics.setColor(lineColor);
                graphics.setStroke(new BasicStroke(2.5f));
                Row previous = null;
                for (Row row : rows) {
                    if (row.algorithm.equals(algorithm) && row.input.equals(input)) {
                        int x = x(row.n, left, width - right);
                        int y = y(row.value(value), maximum, top, height - bottom);
                        if (previous != null) {
                            graphics.drawLine(x(previous.n, left, width - right), y(previous.value(value), maximum, top, height - bottom), x, y);
                        }
                        graphics.fillOval(x - 3, y - 3, 6, 6);
                        previous = row;
                    }
                }
                graphics.setFont(new Font("SansSerif", Font.PLAIN, 13));
                int legendY = 95 + (color - 1) * 25;
                graphics.fillRect(width - right + 20, legendY - 9, 14, 14);
                graphics.setColor(Color.DARK_GRAY);
                graphics.drawString(algorithm + " " + input, width - right + 42, legendY + 3);
            }
        }
        graphics.dispose();
        ImageIO.write(image, "png", output.toFile());
    }

    private static int x(int n, int left, int right) {
        return (int) (left + (Math.log10(n) - 3) / 3 * (right - left));
    }

    private static int y(double value, double maximum, int top, int bottom) {
        return (int) (bottom - value / maximum * (bottom - top));
    }

    private enum Value { TIME, DEPTH, RATIO }

    private record Row(String algorithm, String input, int n, double time, long comparisons, int depth) {
        double value(Value value) {
            return switch (value) {
                case TIME -> time;
                case DEPTH -> depth;
                case RATIO -> algorithm.equals("QuickSelect") ? comparisons / (double) n : comparisons / (n * Math.log(n) / Math.log(2));
            };
        }
    }
    public static void main(String[] args) throws IOException {
        create(Path.of("results.csv"), Path.of("."));
    }
}
