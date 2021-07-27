package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class InterestChangePerCommit {
    private String sha;
    private Integer revisionCount;
    private BigDecimal changeEu;
    private BigDecimal changeHours;
    private BigDecimal changePercentage;

    public InterestChangePerCommit() { }

    public InterestChangePerCommit(String sha, Integer revisionCount, BigDecimal changeEu, BigDecimal changeHours, BigDecimal changePercentage) {
        this.sha = sha;
        this.revisionCount = revisionCount;
        this.changeEu = changeEu;
        this.changeHours = changeHours;
        this.changePercentage = changePercentage;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getRevisionCount() {
        return revisionCount;
    }

    public void setRevisionCount(Integer revisionCount) {
        this.revisionCount = revisionCount;
    }

    public BigDecimal getChangeEu() {
        return changeEu;
    }

    public void setChangeEu(BigDecimal changeEu) {
        this.changeEu = changeEu;
    }

    public BigDecimal getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(BigDecimal changePercentage) {
        this.changePercentage = changePercentage;
    }

    public BigDecimal getChangeHours() {
        return changeHours;
    }

    public void setChangeHours(BigDecimal changeHours) {
        this.changeHours = changeHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestChangePerCommit that = (InterestChangePerCommit) o;
        return Objects.equals(sha, that.sha) && Objects.equals(revisionCount, that.revisionCount) && Objects.equals(changeEu, that.changeEu) && Objects.equals(changeHours, that.changeHours) && Objects.equals(changePercentage, that.changePercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, revisionCount, changeEu, changeHours, changePercentage);
    }
}
