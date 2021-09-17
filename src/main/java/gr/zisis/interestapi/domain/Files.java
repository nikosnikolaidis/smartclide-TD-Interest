package gr.zisis.interestapi.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "files")
@XmlRootElement
public class Files implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fid")
    private Long fid;
    @Basic(optional = false)
    @Column(name = "file_path")
    private String filePath;
    @Basic(optional = false)
    @Column(name = "sha")
    private String sha;
    @Basic(optional = false)
    @Lob
    @Column(name = "class_names", columnDefinition = "_text")
    private Serializable classNames;
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne(optional = false)
    private Projects pid;

    public Files() { }

    public Files(Long fid) {
        this.fid = fid;
    }

    public Files(Long fid, String filePath, Serializable classNames, Projects pid, String sha) {
        this.fid = fid;
        this.filePath = filePath;
        this.classNames = classNames;
        this.pid = pid;
        this.sha = sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Long getFid() {
        return fid;
    }

    public void setFid(Long fid) {
        this.fid = fid;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Serializable getClassNames() {
        return classNames;
    }

    public void setClassNames(Serializable classNames) {
        this.classNames = classNames;
    }

    public Projects getPid() {
        return pid;
    }

    public void setPid(Projects pid) {
        this.pid = pid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Files files = (Files) o;
        return Objects.equals(fid, files.fid) && Objects.equals(filePath, files.filePath) && Objects.equals(sha, files.sha) && Objects.equals(classNames, files.classNames) && Objects.equals(pid, files.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid, filePath, sha, classNames, pid);
    }

    @Override
    public String toString() {
        return "gr.zisis.interestapi.domain.Files[ fid=" + fid + " ]";
    }
}
