package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public class CreateArticleForm extends EditArticleForm {

    @ValidFile
    private List<MultipartFile> files;

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

}
