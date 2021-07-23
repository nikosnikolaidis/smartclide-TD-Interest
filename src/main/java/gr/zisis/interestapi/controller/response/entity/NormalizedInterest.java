package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class NormalizedInterest {
    private String sha;
    private Integer revisionCount;
    private BigDecimal normalizedInterest;

    public NormalizedInterest() { }

    public NormalizedInterest(String sha, Integer revisionCount, BigDecimal normalizedInterest) {
        this.sha = sha;
        this.revisionCount = revisionCount;
        this.normalizedInterest = normalizedInterest;
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

    public BigDecimal getNormalizedInterest() {
        return normalizedInterest;
    }

    public void setNormalizedInterest(BigDecimal normalizedInterest) {
        this.normalizedInterest = normalizedInterest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalizedInterest that = (NormalizedInterest) o;
        return Objects.equals(sha, that.sha) && Objects.equals(revisionCount, that.revisionCount) && Objects.equals(normalizedInterest, that.normalizedInterest);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, revisionCount, normalizedInterest);
    }
}
