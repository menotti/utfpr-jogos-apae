/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package game;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Kuroda
 */
public class GraphicsDisplay {
    public static void main(String[] args) {
        GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        DisplayMode[] dm = graphicsDevice.getDisplayModes();
        for (DisplayMode displayMode : dm) {
            System.out.println(displayMode.getWidth() + " " + displayMode.getHeight() + " "
                    + displayMode.getBitDepth() + " " + displayMode.getRefreshRate());
        }

        GraphicsDevice[] gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
        for (GraphicsDevice g : gd) {
            System.out.println(g.getDisplayMode().getWidth() + "x" + g.getDisplayMode().getHeight());
        }

        Map<String, DisplayMode> mapa = new TreeMap<String, DisplayMode>();
        // dois parâmetros no Generics!
        List<String> lista = new ArrayList<String>();
        // apenas um tipo no Generics!
        for (Iterator it = mapa.keySet().iterator(); it.hasNext();) {

             Object key = it.next();
             Object item = mapa.get(key);
             System.out.println("Key " + key + " Item " + item);
        }
    }
}
