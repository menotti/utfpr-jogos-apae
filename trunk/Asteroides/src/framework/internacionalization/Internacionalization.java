/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package framework.internacionalization;

import java.util.*;

/**
 *
 * @author Luiz Philipe
 */
public class Internacionalization {

    public static Locale locale = Locale.getDefault();
    private static ResourceBundle rb = ResourceBundle.getBundle("framework.internacionalization.ResourceBundle", locale);

    public static void setLocale(Locale locale){
        Internacionalization.locale = locale;
        rb = ResourceBundle.getBundle("framework.internacionalization.ResourceBundle", locale);
    }

    public static String get(String parameter){
        return new String(new StringBuilder().append(rb.getString(parameter)));
    }
}
