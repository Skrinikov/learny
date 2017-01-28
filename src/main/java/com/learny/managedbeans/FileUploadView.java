package com.learny.managedbeans;
 
import com.learny.learny.newpackage.semantria.SemantriaRequest;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
 
    public void handleFileUpload(FileUploadEvent event) {
        String fileContents = new String(event.getFile().getContents());
        
        FacesMessage message = new FacesMessage("Succesful", fileContents + " was submitted.");
        FacesContext.getCurrentInstance().addMessage(null, message);
        
        SemantriaRequest request = new SemantriaRequest();
        List<String> result = request.requestToSemantria(fileContents);
        
                
    }
}