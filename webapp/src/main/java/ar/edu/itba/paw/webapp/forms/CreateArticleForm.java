package ar.edu.itba.paw.webapp.forms;

import ar.edu.itba.paw.webapp.forms.annotations.ValidFile;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


public class CreateArticleForm extends EditArticleForm {

    @ValidFile
    private List<byte[]> files;

    public List<byte[]> getFiles() {
        return files;
    }

    public void setFiles(List<byte[]> files) {
        this.files = files;
    }

}
