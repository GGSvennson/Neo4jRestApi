package com.primefaces.hibernate.bean;

import java.io.Serializable;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "resourceBundleBean")
@ApplicationScoped
public class ResourceBundleBean implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private static final String resourceBundleName = "msgs";
    
    public ResourceBundleBean() {
    }
    
    public static String getResourceBundleString(String resourceBundleKey)
                                                        throws MissingResourceException {
    
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle bundle = 
            context.getApplication().getResourceBundle(
                context, resourceBundleName);
    
        return bundle.getString(resourceBundleKey);
    }
}
