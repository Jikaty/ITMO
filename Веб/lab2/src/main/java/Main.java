import com.fastcgi.FCGIInterface;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Main{
    private static final List<Row> history = new ArrayList<>();
    private static final List<Double> R_VALUE = List.of(1.0, 1.5, 2.0, 2.5, 3.0);
    private static final Pattern NUMBER_PATTERN = Pattern.compile("[+-]?\\d+");
    private static final Pattern NUMBER_POINT_PATTERN = Pattern.compile("[+-]?\\d+([.,]\\d+)?");

    private record Row(int x, double y, double r, boolean hit, String time, long workTime) {
        String toJson() {
            return "{\"x\":" + x + ",\"y\":" + y + ",\"r\":" + r + ",\"hit\":" + hit
                    + ",\"time\":\"" + time + "\",\"workTime\":" + workTime + "}";
        }
    }

    public static void main(String[] args) {
        FCGIInterface fcgi = new FCGIInterface();
        while (fcgi.FCGIaccept() >= 0) {
            long start = System.nanoTime();
            try {
                String query = FCGIInterface.request.params.getProperty("QUERY_STRING");
                Map<String, String> params = parse(query);

                if (params.containsKey("clear")) {
                    history.clear();
                    send(200, response(null));
                } else if (params.isEmpty()) {
                    send(200, response(null));
                } else {
                    int x = parseX(params.get("x"));
                    double y = parseY(params.get("y"));
                    double r = parseR(params.get("r"));
                    boolean hit = isHit(x, y, r);
                    long workTime = (System.nanoTime() - start) / 1000;
                    Row row = new Row(x, y, r, hit, Instant.now().truncatedTo(ChronoUnit.MILLIS).toString(), workTime);
                    history.add(row);
                    send(200, response(row));
                }
            } catch (IllegalArgumentException e) {
                send(400, "{\"ok\":false,\"error\":\"" + e.getMessage() + "\"}");
            } catch (Exception e) {
                send(500, "{\"ok\":false,\"error\":\"internal error\"}");
            }
        }
    }

    private static Map<String, String> parse(String query) {
        Map<String, String> map = new HashMap<>();
        if (query == null || query.isBlank()) return map;
        for (String pair : query.split("&")) {
            String[] kv = pair.split("=", 2);
            String key = URLDecoder.decode(kv[0], StandardCharsets.UTF_8);
            String value = kv.length == 2 ? URLDecoder.decode(kv[1], StandardCharsets.UTF_8) : "";
            map.put(key, value);
        }
        return map;
    }

    private static int parseX(String value) {
        if (value == null || !NUMBER_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("x должен быть от -4 до 4");
        }
        int x = Integer.parseInt(value);
        if (x < -4 || x > 4) {
            throw new IllegalArgumentException("x должен быть от -4 до 4");
        }
        return x;
    }

    private static double parseY(String value) {
        if (value == null || !NUMBER_POINT_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("y должен быть от -3 до 5 не включительно");
        }
        double y = Double.parseDouble(value.replace(',', '.'));
        if (y <= -3 || y >= 5) {
            throw new IllegalArgumentException("y должен быть от -3 до 5 не включительно");
        }
        return y;
    }

    private static double parseR(String value) {
        if (value == null || !NUMBER_POINT_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("r должен быть: 1, 1.5, 2, 2.5, 3");
        }
        double r = Double.parseDouble(value.replace(',', '.'));
        if (!R_VALUE.contains(r)) {
            throw new IllegalArgumentException("r должен быть: 1, 1.5, 2, 2.5, 3");
        }
        return r;
    }

    private static boolean isHit(double x, double y, double r) {
        boolean rectangle = x >= -r && x <= 0 && y >= 0 && y <= r;
        boolean circle = x <= 0 && y <= 0 && x * x + y * y <= r * r;
        boolean triangle = x >= 0 && y >= 0 && x + y <= r / 2;
        return rectangle || circle || triangle;
    }

    private static String response(Row current) {
        StringBuilder sb = new StringBuilder("{\"ok\":true,\"current\":");
        sb.append(current == null ? "null" : current.toJson());
        sb.append(",\"history\":[");
        for (int i = 0; i < history.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append(history.get(i).toJson());
        }
        return sb.append("]}").toString();
    }

    private static void send(int status, String body) {
        byte[] content = body.getBytes(StandardCharsets.UTF_8);
        String head = "Status: " + status + "\r\n"
                + "Content-Type: application/json; charset=utf-8\r\n"
                + "Content-Length: " + content.length + "\r\n\r\n";
        byte[] header = head.getBytes(StandardCharsets.US_ASCII);
        System.out.write(header, 0, header.length);
        System.out.write(content, 0, content.length);
        System.out.flush();
    }
}

//"http://localhost:8080/'fcgi-bin/app'/?x=1&y=2
