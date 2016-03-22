package com.primefaces.hibernate.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
 
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
 
@ManagedBean(name="language")
@SessionScoped
public class LanguageBean implements Serializable{
 
	private static final long serialVersionUID = 1L;
 
	private String localeCode;
 
	private static Map<String,Object> countries = new LinkedHashMap<String,Object>();
	static{
		//countries = new LinkedHashMap<String,Object>();
		countries.put("Español", new Locale("es", "ES"));
                countries.put("English", Locale.US);
	}
 
	public Map<String, Object> getCountriesInMap() {
		return countries;
	}
 
 
	public String getLocaleCode() {
		return localeCode;
	}
 
 
	public void setLocaleCode(String localeCode) {
		this.localeCode = localeCode;
	}
 
	//value change event listener
	public void countryLocaleCodeChanged(ValueChangeEvent e){
            String newLocaleValue = e.getNewValue().toString();
            setLocale(newLocaleValue);
	}
        
        public void usLocaleCodeChanged() {
            setLocale("en_US");
        }
        
        public void spainLocaleCodeChanged() {
            setLocale("es_ES");
        }
        
        private void setLocale(String newLocaleValue) {
            //loop country map to compare the locale code
            for (Map.Entry<String, Object> entry : countries.entrySet()) {
               if(entry.getValue().toString().equals(newLocaleValue)){
                    FacesContext.getCurrentInstance()
                            .getViewRoot().setLocale((Locale)entry.getValue());
              }
           }
        }
 
}
