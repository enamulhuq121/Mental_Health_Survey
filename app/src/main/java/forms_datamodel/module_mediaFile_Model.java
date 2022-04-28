package forms_datamodel;

/**
 * Created by tasrul on 15-Apr-18.
 */

public class module_mediaFile_Model {
    String file_name,file_seq,file_type,file_path;

    public module_mediaFile_Model() {
        this.file_name ="";
        this.file_seq ="";
        this.file_type ="";
        this.file_path ="";
    }

    public module_mediaFile_Model(String file_name, String file_seq, String file_type, String file_path) {
        this.file_name = file_name;
        this.file_seq = file_seq;
        this.file_type = file_type;
        this.file_path = file_path;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_seq() {
        return file_seq;
    }

    public void setFile_seq(String file_seq) {
        this.file_seq = file_seq;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
