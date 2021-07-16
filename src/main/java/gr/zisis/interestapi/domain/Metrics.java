package gr.zisis.interestapi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author George Digkas <digasgeo@gmail.com>
 *
 */
@Entity
@Table(name = "metrics")
@XmlRootElement
public class Metrics implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "classes_num")
    private Integer classesNum;
    @Column(name = "complexity")
    private BigDecimal complexity;
    @Column(name = "dac")
    private Integer dac;
    @Column(name = "dit")
    private Integer dit;
    @Basic(optional = false)
    @Column(name = "file_path")
    private String filePath;
    @Basic(optional = false)
    @Column(name = "interest_eu")
    private BigDecimal interestEu;
    @Column(name = "lcom")
    private BigInteger lcom;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mid")
    private Integer mid;
    @Column(name = "mpc")
    private BigDecimal mpc;
    @Column(name = "nocc")
    private Integer nocc;
    @Column(name = "old_size1")
    private Integer oldSize1;
    @Column(name = "rfc")
    private BigDecimal rfc;
    @Basic(optional = false)
    @Column(name = "sha")
    private String sha;
    @Column(name = "size1")
    private Integer size1;
    @Column(name = "size2")
    private Integer size2;
    @Column(name = "wmc")
    private BigDecimal wmc;
    @Column(name = "nom")
    private BigInteger nom;
    @Column(name = "kappa")
    private BigDecimal kappa;
    @Basic(optional = false)
    @Column(name = "revision_count")
    private int revisionCount;
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne(optional = false)
    private Projects pid;

    public Metrics() {
    }

    public Metrics(Integer mid) {
        this.mid = mid;
    }

    public Metrics(Integer mid, String filePath, BigDecimal interestEu, String sha, int revisionCount) {
        this.mid = mid;
        this.filePath = filePath;
        this.interestEu = interestEu;
        this.sha = sha;
        this.revisionCount = revisionCount;
    }

    public Integer getClassesNum() {
        return classesNum;
    }

    public void setClassesNum(Integer classesNum) {
        this.classesNum = classesNum;
    }

    public BigDecimal getComplexity() {
        return complexity;
    }

    public void setComplexity(BigDecimal complexity) {
        this.complexity = complexity;
    }

    public Integer getDac() {
        return dac;
    }

    public void setDac(Integer dac) {
        this.dac = dac;
    }

    public Integer getDit() {
        return dit;
    }

    public void setDit(Integer dit) {
        this.dit = dit;
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

    public BigInteger getLcom() {
        return lcom;
    }

    public void setLcom(BigInteger lcom) {
        this.lcom = lcom;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public BigDecimal getMpc() {
        return mpc;
    }

    public void setMpc(BigDecimal mpc) {
        this.mpc = mpc;
    }

    public Integer getNocc() {
        return nocc;
    }

    public void setNocc(Integer nocc) {
        this.nocc = nocc;
    }

    public Integer getOldSize1() {
        return oldSize1;
    }

    public void setOldSize1(Integer oldSize1) {
        this.oldSize1 = oldSize1;
    }

    public BigDecimal getRfc() {
        return rfc;
    }

    public void setRfc(BigDecimal rfc) {
        this.rfc = rfc;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public Integer getSize1() {
        return size1;
    }

    public void setSize1(Integer size1) {
        this.size1 = size1;
    }

    public Integer getSize2() {
        return size2;
    }

    public void setSize2(Integer size2) {
        this.size2 = size2;
    }

    public BigDecimal getWmc() {
        return wmc;
    }

    public void setWmc(BigDecimal wmc) {
        this.wmc = wmc;
    }

    public BigInteger getNom() {
        return nom;
    }

    public void setNom(BigInteger nom) {
        this.nom = nom;
    }

    public BigDecimal getKappa() {
        return kappa;
    }

    public void setKappa(BigDecimal kappa) {
        this.kappa = kappa;
    }

    public int getRevisionCount() {
        return revisionCount;
    }

    public void setRevisionCount(int revisionCount) {
        this.revisionCount = revisionCount;
    }

    public Projects getPid() {
        return pid;
    }

    public void setPid(Projects pid) {
        this.pid = pid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mid != null ? mid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Metrics)) {
            return false;
        }
        Metrics other = (Metrics) object;
        if ((this.mid == null && other.mid != null) || (this.mid != null && !this.mid.equals(other.mid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "gr.zisis.interestapi.domain.Metrics[ mid=" + mid + " ]";
    }
    
}
