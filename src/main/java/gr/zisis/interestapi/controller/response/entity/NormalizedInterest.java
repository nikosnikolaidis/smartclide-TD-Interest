package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class NormalizedInterest {
    private String sha;
    private Integer revisionCount;
    private BigDecimal normalizedInterestEu;
    private BigDecimal normalizedInterestHours;

    public NormalizedInterest() { }

    public NormalizedInterest(String sha, Integer revisionCount, BigDecimal normalizedInterestEu, BigDecimal normalizedInterestHours) {
        this.sha = sha;
        this.revisionCount = revisionCount;
        this.normalizedInterestEu = normalizedInterestEu;
        this.normalizedInterestHours = normalizedInterestHours;
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

    public BigDecimal getNormalizedInterestEu() {
        return normalizedInterestEu;
    }

    public void setNormalizedInterestEu(BigDecimal normalizedInterestEu) {
        this.normalizedInterestEu = normalizedInterestEu;
    }

    public BigDecimal getNormalizedInterestHours() {
        return normalizedInterestHours;
    }

    public void setNormalizedInterestHours(BigDecimal normalizedInterestHours) {
        this.normalizedInterestHours = normalizedInterestHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NormalizedInterest that = (NormalizedInterest) o;
        return Objects.equals(sha, that.sha) && Objects.equals(revisionCount, that.revisionCount) && Objects.equals(normalizedInterestEu, that.normalizedInterestEu) && Objects.equals(normalizedInterestHours, that.normalizedInterestHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sha, revisionCount, normalizedInterestEu, normalizedInterestHours);
    }
}
