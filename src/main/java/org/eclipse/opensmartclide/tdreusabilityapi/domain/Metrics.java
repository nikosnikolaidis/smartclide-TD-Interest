/*******************************************************************************
 * Copyright (C) 2021-2022 University of Macedonia
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 ******************************************************************************/
package org.eclipse.opensmartclide.tdreusabilityapi.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.*;
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
    @Column(name = "interest_eu")
    private BigDecimal interestEu;
    @Column(name = "lcom")
    private BigInteger lcom;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "mid")
    private Long mid;
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
    private Long revisionCount;
    @Column(name = "interest_in_hours")
    private BigDecimal interestHours;
    @Column(name = "avg_interest_per_loc")
    private BigDecimal avgInterestPerLoc;
    @Column(name = "interest_in_avg_loc")
    private BigDecimal interestInAvgLoc;
    @Column(name = "sum_interest_per_loc")
    private BigDecimal sumInterestPerLoc;
    @Column(name = "cbo")
    private BigDecimal cbo;
    @JoinColumn(name = "pid", referencedColumnName = "pid")
    @ManyToOne(optional = false)
    private Projects pid;
    @JoinColumn(name = "fid", referencedColumnName = "fid")
    @ManyToOne(optional = false)
    private Files fid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "fid")
    private Collection<Files> filesCollection;

    public Metrics() {
    }

    public Metrics(Long mid) {
        this.mid = mid;
    }

    public Metrics(Long mid, Projects pid, Files fid, BigDecimal interestEu, String sha, Long revisionCount) {
        this.mid = mid;
        this.pid = pid;
        this.fid = fid;
        this.sha = sha;
        this.interestEu = interestEu;
        this.revisionCount = revisionCount;
    }

    public Metrics(Long mid, BigDecimal interestEu, String sha, Long revisionCount) {
        this.mid = mid;
        this.interestEu = interestEu;
        this.sha = sha;
        this.revisionCount = revisionCount;
    }

    public Files getFid() {
        return fid;
    }

    public void setFid(Files fid) {
        this.fid = fid;
    }

    public Collection<Files> getFilesCollection() {
        return filesCollection;
    }

    public void setFilesCollection(Collection<Files> filesCollection) {
        this.filesCollection = filesCollection;
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

    public Long getMid() {
        return mid;
    }

    public void setMid(Long mid) {
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

    public Long getRevisionCount() {
        return revisionCount;
    }

    public void setRevisionCount(Long revisionCount) {
        this.revisionCount = revisionCount;
    }

    public Projects getPid() {
        return pid;
    }

    public void setPid(Projects pid) {
        this.pid = pid;
    }

    public BigDecimal getInterestHours() {
        return interestHours;
    }

    public void setInterestHours(BigDecimal interestHours) {
        this.interestHours = interestHours;
    }

    public BigDecimal getAvgInterestPerLoc() {
        return avgInterestPerLoc;
    }

    public void setAvgInterestPerLoc(BigDecimal avgInterestPerLoc) {
        this.avgInterestPerLoc = avgInterestPerLoc;
    }

    public BigDecimal getInterestInAvgLoc() {
        return interestInAvgLoc;
    }

    public void setInterestInAvgLoc(BigDecimal interestInAvgLoc) {
        this.interestInAvgLoc = interestInAvgLoc;
    }

    public BigDecimal getSumInterestPerLoc() {
        return sumInterestPerLoc;
    }

    public void setSumInterestPerLoc(BigDecimal sumInterestPerLoc) {
        this.sumInterestPerLoc = sumInterestPerLoc;
    }

    public BigDecimal getCbo() {
        return cbo;
    }

    public void setCbo(BigDecimal cbo) {
        this.cbo = cbo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Metrics metrics = (Metrics) o;
        return Objects.equals(classesNum, metrics.classesNum) && Objects.equals(complexity, metrics.complexity) && Objects.equals(dac, metrics.dac) && Objects.equals(dit, metrics.dit) && Objects.equals(interestEu, metrics.interestEu) && Objects.equals(lcom, metrics.lcom) && Objects.equals(mid, metrics.mid) && Objects.equals(mpc, metrics.mpc) && Objects.equals(nocc, metrics.nocc) && Objects.equals(oldSize1, metrics.oldSize1) && Objects.equals(rfc, metrics.rfc) && Objects.equals(sha, metrics.sha) && Objects.equals(size1, metrics.size1) && Objects.equals(size2, metrics.size2) && Objects.equals(wmc, metrics.wmc) && Objects.equals(nom, metrics.nom) && Objects.equals(kappa, metrics.kappa) && Objects.equals(revisionCount, metrics.revisionCount) && Objects.equals(interestHours, metrics.interestHours) && Objects.equals(avgInterestPerLoc, metrics.avgInterestPerLoc) && Objects.equals(interestInAvgLoc, metrics.interestInAvgLoc) && Objects.equals(sumInterestPerLoc, metrics.sumInterestPerLoc) && Objects.equals(cbo, metrics.cbo) && Objects.equals(pid, metrics.pid) && Objects.equals(fid, metrics.fid) && Objects.equals(filesCollection, metrics.filesCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(classesNum, complexity, dac, dit, interestEu, lcom, mid, mpc, nocc, oldSize1, rfc, sha, size1, size2, wmc, nom, kappa, revisionCount, interestHours, avgInterestPerLoc, interestInAvgLoc, sumInterestPerLoc, cbo, pid, fid, filesCollection);
    }

    @Override
    public String toString() {
        return "org.eclipse.opensmartclide.tdreusabilityapi.domain.Metrics[ mid=" + mid + " ]";
    }
    
}
