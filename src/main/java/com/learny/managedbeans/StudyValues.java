package com.learny.managedbeans;
 
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class StudyValues {
    
    @Inject
    private FileUploadView bean;
 
    public void handleFileUpload(FileUploadEvent event) {
        String fileContents = new String(event.getFile().getContents());
        
        FacesMessage message = new FacesMessage("Succesful", fileContents + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    public FileUploadView getBean() {
        return bean;
    }

    public void setBean(FileUploadView bean) {
        this.bean = bean;
    }
    
    
}