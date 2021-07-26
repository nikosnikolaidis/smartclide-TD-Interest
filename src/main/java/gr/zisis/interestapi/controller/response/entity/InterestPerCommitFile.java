package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class InterestPerCommitFile {
    private String sha;
    private String filePath;
    private Integer revisionCount;
    private BigDecimal interestEu;

    public InterestPerCommitFile() { }

    public InterestPerCommitFile(String sha, String filePath, Integer revisionCount, BigDecimal interestEu) {
        this.sha = sha;
        this.filePath = filePath;
        this.revisionCount = revisionCount;
        this.interestEu = interestEu;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getRevisionCount() {
        return revisionCount;
    }

    public void setRevisionCount(Integer revisionCount) {
        this.revisionCount = revisionCount;
    }

    public BigDecimal getInterestEu() {
        return interestEu;
    }

    public void setInterestEu(BigDecimal interestEu) {
        this.interestEu = interestEu;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestPerCommitFile that = (InterestPerCommitFile) o;
        return Objects.equals(sha, that.sha) && Objects.equals(filePath, that.filePath) && Objects.equals(revisionCount, that.revisionCount) && Objects.equals(interestEu, that.interestEu);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, filePath, revisionCount, interestEu);
    }
}
