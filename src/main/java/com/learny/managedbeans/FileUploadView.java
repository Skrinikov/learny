package com.learny.managedbeans;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
 
    public void handleFileUpload(FileUploadEvent event) {
        String fileContents = new String(event.getFile().getContents());
        
        FacesMessage message = new FacesMessage("Succesful", fileContents + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}