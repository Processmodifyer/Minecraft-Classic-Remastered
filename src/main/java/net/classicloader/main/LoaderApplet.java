package net.classicloader.main;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.classic.remastered.client.main.MinecraftApplet;

public class LoaderApplet
extends MinecraftApplet {
    private Map<String, String> parameters = new HashMap<String, String>();

    @Override
    public URL getDocumentBase() {
        try {
            return new URL("http://minecraft.net:80/play.jsp");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public URL getCodeBase() {
        try {
            return new URL("http://minecraft.net:80/");
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }
}

