package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class HighInterestFile {
    private String sha;
    private Integer revisionCount;
    private String filePath;
    private BigDecimal interestEu;
    private BigDecimal interestPercentageOfProject;

    public HighInterestFile() { }

    public HighInterestFile(String sha, Integer revisionCount, String filePath, BigDecimal interestEu, BigDecimal interestPercentageOfProject) {
        this.sha = sha;
        this.revisionCount = revisionCount;
        this.filePath = filePath;
        this.interestEu = interestEu;
        this.interestPercentageOfProject = interestPercentageOfProject;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Integer getRevisionCount() {
        return revisionCount;
    }

    public void setRevisionCount(Integer revisionCount) {
        this.revisionCount = revisionCount;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public BigDecimal getInterestEu() {
        return interestEu;
    }

    public void setInterestEu(BigDecimal interestEu) {
        this.interestEu = interestEu;
    }

    public BigDecimal getInterestPercentageOfProject() {
        return interestPercentageOfProject;
    }

    public void setInterestPercentageOfProject(BigDecimal interestPercentageOfProject) {
        this.interestPercentageOfProject = interestPercentageOfProject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HighInterestFile that = (HighInterestFile) o;
        return Objects.equals(sha, that.sha) && Objects.equals(revisionCount, that.revisionCount) && Objects.equals(filePath, that.filePath) && Objects.equals(interestEu, that.interestEu) && Objects.equals(interestPercentageOfProject, that.interestPercentageOfProject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, revisionCount, filePath, interestEu, interestPercentageOfProject);
    }
}
