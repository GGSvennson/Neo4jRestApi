package com.primefaces.hibernate.util;

import javax.faces.context.FacesContext;

public class FacesUtil {

    // Getters -----------------------------------------------------------------------------------

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    // Setters -----------------------------------------------------------------------------------

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
}