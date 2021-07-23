package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class InterestChangePerCommit {
    private String sha;
    private Integer revisionCount;
    private BigDecimal change;
    private BigDecimal changePercentage;

    public InterestChangePerCommit() { }

    public InterestChangePerCommit(String sha, Integer revisionCount, BigDecimal change, BigDecimal changePercentage) {
        this.sha = sha;
        this.revisionCount = revisionCount;
        this.change = change;
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

    public BigDecimal getChange() {
        return change;
    }

    public void setChange(BigDecimal change) {
        this.change = change;
    }

    public BigDecimal getChangePercentage() {
        return changePercentage;
    }

    public void setChangePercentage(BigDecimal changePercentage) {
        this.changePercentage = changePercentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InterestChangePerCommit that = (InterestChangePerCommit) o;
        return Objects.equals(sha, that.sha) && Objects.equals(revisionCount, that.revisionCount) && Objects.equals(change, that.change) && Objects.equals(changePercentage, that.changePercentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, revisionCount, change, changePercentage);
    }
}
