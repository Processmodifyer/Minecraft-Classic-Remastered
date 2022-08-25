/*
 * 
 */
package net.classic.remastered.client.chat;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.classic.remastered.client.gui.FontRenderer;

public class ChatClickData {
    public final String message;
    private final String urlPattern = "\\(?(?:(?:[a-z]{2,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[a-z0-9\\-]+(?:\\.[a-z0-9\\-]+)+|www\\.[a-z0-9\\-]+(?:\\.[a-z0-9\\-]+)+)(?::\\d{1,5})?(?:\\/[\\+~%\\/\\.\\w\\-\\(\\)]*)?(?:\\?[\\-\\+=&;%@\\.\\w]*)?(?:#\\S*)?";
    private final Pattern compiledPattern = Pattern.compile("\\(?(?:(?:[a-z]{2,9}:(?:\\/\\/)?)(?:[\\-;:&=\\+\\$,\\w]+@)?[a-z0-9\\-]+(?:\\.[a-z0-9\\-]+)+|www\\.[a-z0-9\\-]+(?:\\.[a-z0-9\\-]+)+)(?::\\d{1,5})?(?:\\/[\\+~%\\/\\.\\w\\-\\(\\)]*)?(?:\\?[\\-\\+=&;%@\\.\\w]*)?(?:#\\S*)?", 2);

    public ChatClickData(String message) {
        this.message = FontRenderer.stripColor(message);
    }

    public ArrayList<LinkData> getClickedUrls(FontRenderer fontRenderer) {
        return this.pullLinks(this.message, fontRenderer);
    }

    private ArrayList<LinkData> pullLinks(String text, FontRenderer fr) {
        ArrayList<LinkData> links = new ArrayList<LinkData>();
        Matcher m = this.compiledPattern.matcher(text);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            String urlStr = m.group();
            if (urlStr.charAt(0) == '(') {
                ++start;
                if (urlStr.endsWith(")")) {
                    --end;
                }
                urlStr = text.substring(start, end);
            }
            try {
                links.add(new LinkData(new URI(urlStr), fr.getWidth(text.substring(0, start)), fr.getWidth(text.substring(0, end))));
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
        return links;
    }

    public class LinkData {
        public URI url;
        public int x0;
        public int x1;

        public LinkData(URI textualLink, int x0, int x1) {
            this.url = textualLink;
            this.x0 = x0;
            this.x1 = x1;
        }
    }
}

