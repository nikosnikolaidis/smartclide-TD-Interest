package gr.zisis.interestapi.controller.response.entity;

import java.math.BigDecimal;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
public class CumulativeInterest {
	
	private String sha;
	private int revisionCount;
	private BigDecimal interestEu;
	
	public CumulativeInterest() { }
	
	public CumulativeInterest(String sha, int revisionCount, BigDecimal interestEu) {
		this.sha = sha;
		this.revisionCount = revisionCount;
		this.interestEu = interestEu;
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

	public void setRevisionCount(int revisionCount) {
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
		CumulativeInterest other = (CumulativeInterest) obj;
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
		return true;
	}
	
}
