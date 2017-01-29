package com.learny.managedbeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;

@ManagedBean
public class FileUploadView {

    @ManagedProperty("#{param.data}")
    private List<String> data = new ArrayList();

    public void handleFileUpload(FileUploadEvent event) throws IOException {
        String fileContents = new String(event.getFile().getContents());

        FacesMessage message = new FacesMessage("Succesful", fileContents + " was submitted.");
        FacesContext.getCurrentInstance().addMessage(null, message);

        //REQUEST THE API
        //data.add(Arrays.toString(event.getFile().getContents()));
        FacesContext.getCurrentInstance().getExternalContext().redirect("study.xhtml");

    }
}
