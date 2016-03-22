package com.primefaces.hibernate.util;

import com.primefaces.hibernate.model.Users;
import java.io.IOException;
import javax.faces.context.FacesContext;
import javax.faces.context.PartialResponseWriter;
import javax.faces.context.ResponseWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HttpSessionUtil {
    
    public static HttpSession getSession(boolean flag) {
        return (HttpSession)
          FacesContext.
          getCurrentInstance().
          getExternalContext().
          getSession(flag);
    }
       
    public static HttpServletRequest getRequest() {
       return (HttpServletRequest) FacesContext.
          getCurrentInstance().
          getExternalContext().
               getRequest();
    }
 
    public static Users getUser() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return  (Users) session.getAttribute("user");
    }
    
    public static void redirect(String requestURI) throws IOException {

        FacesContext ctx = FacesContext.getCurrentInstance();
        
        //ELFlash.getFlash(ctx.getExternalContext(), true).doLastPhaseActions(ctx, true);
        
        HttpServletResponse resp = (HttpServletResponse) ctx.
              getExternalContext().getResponse();

        if (ctx.getPartialViewContext().isPartialRequest()) {
            //Handle partial request returning a redirection code in XML format
            if (getSession(true) instanceof HttpSession &&
                ctx.getResponseComplete()) {
                throw new IllegalStateException();
            }
            PartialResponseWriter pwriter;
            ResponseWriter writer = ctx.getResponseWriter();
            if (writer instanceof PartialResponseWriter) {
                pwriter = (PartialResponseWriter) writer;
            } else {
                pwriter = ctx.getPartialViewContext().getPartialResponseWriter();
            }

            resp.setContentType("text/html;charset=UTF-8"); //setResponseContentType("text/xml");
            //setResponseCharacterEncoding("UTF-8");
            resp.setHeader("Cache-Control", "no-cache");  //addResponseHeader("Cache-Control", "no-cache");

            pwriter.startDocument();
            pwriter.redirect(requestURI);
            pwriter.endDocument();
        } else {
            //Standard redirection behaviour
            resp.sendRedirect(requestURI);
        }
        
        ctx.responseComplete();
    }
}
