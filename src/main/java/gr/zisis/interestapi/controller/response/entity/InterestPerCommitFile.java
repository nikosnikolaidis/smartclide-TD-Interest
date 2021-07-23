package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;

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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((interestEu == null) ? 0 : interestEu.hashCode());
        result = prime * result + revisionCount;
        result = prime * result + ((sha == null) ? 0 : sha.hashCode());
        result = prime * result + ((filePath == null) ? 0 : filePath.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        InterestPerCommitFile other = (InterestPerCommitFile) obj;
        if (interestEu == null) {
            if (other.interestEu != null)
                return false;
        } else if (!interestEu.equals(other.interestEu))
            return false;
        if (revisionCount != other.revisionCount)
            return false;
        if (sha == null) {
            if (other.sha != null)
                return false;
        } else if (!sha.equals(other.sha))
            return false;
        if (filePath == null) {
            if (other.filePath != null)
                return false;
        } else if (!filePath.equals(other.filePath))
            return false;
        return true;
    }
}
