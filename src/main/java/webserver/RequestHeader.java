package webserver;

import utils.RequestHeaderParser;

import java.util.List;

public class RequestHeader {
    private static final String STATIC_PATH_PREFIX = "./static";
    private static final String TEMPLATE_PATH_PREFIX = "./templates";
    private final List<String> lines;

    private RequestHeader(List<String> lines) {
        this.lines = lines;
    }

    public static RequestHeader of(List<String> lines) {
        return new RequestHeader(lines);
    }

    @Override
    public String toString() {
        return String.join("\n", lines);
    }

    public String getPath() {
        String path = RequestHeaderParser.getRequestPath(lines.get(0));
        if (requiresStaticResource(path)) {
            return STATIC_PATH_PREFIX + path;
        }
        if (requiresTemplate(path)) {
            return TEMPLATE_PATH_PREFIX + path;
        }
        return path;
    }

    private boolean requiresTemplate(String path) {
        return path.endsWith(".ico") ||
                path.endsWith(".html");
    }

    private boolean requiresStaticResource(String path) {
        return path.startsWith("/js") ||
                path.startsWith("/css") ||
                path.startsWith("/fonts") ||
                path.startsWith("/images");
    }
}
