package com.learny.managedbeans;
 
import com.learny.learny.Interpreter;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
 
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
 
@ManagedBean
public class FileUploadView {
 
    private List<String> data = new ArrayList<>();
    
    public void handleFileUpload(FileUploadEvent event) {
        String fileContents = new String(event.getFile().getContents());
        
        /*FacesMessage message = new FacesMessage("Succesful", fileContents + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, message);*/
        
        Interpreter itr = new Interpreter();
        
        data = itr.analyze(fileContents);
        if(data.size() == 0)
            data.add("No values were found.");
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
  
}