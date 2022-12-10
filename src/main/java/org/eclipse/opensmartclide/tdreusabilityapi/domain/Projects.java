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
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "projects")
@XmlRootElement
public class Projects implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "pid")
    private Long pid;
    @Basic(optional = false)
    @Column(name = "owner")
    private String owner;
    @Basic(optional = false)
    @Column(name = "repo")
    private String repo;
    @Basic(optional = false)
    @Column(name = "url")
    private String url;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pid")
    private Collection<Metrics> metricsCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pid")
    private Collection<Files> filesCollection;

    public Projects() { }

    public Projects(Long pid) {
        this.pid = pid;
    }

    public Projects(Long pid, String owner, String repo, String url) {
        this.pid = pid;
        this.owner = owner;
        this.repo = repo;
        this.url = url;
    }

    public Collection<Files> getFilesCollection() {
        return filesCollection;
    }

    public void setFilesCollection(Collection<Files> filesCollection) {
        this.filesCollection = filesCollection;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    @XmlTransient
    public Collection<Metrics> getMetricsCollection() {
        return metricsCollection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Projects projects = (Projects) o;
        return Objects.equals(pid, projects.pid) && Objects.equals(owner, projects.owner) && Objects.equals(repo, projects.repo) && Objects.equals(url, projects.url) && Objects.equals(metricsCollection, projects.metricsCollection) && Objects.equals(filesCollection, projects.filesCollection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid, owner, repo, url, metricsCollection, filesCollection);
    }

    public void setMetricsCollection(Collection<Metrics> metricsCollection) {
        this.metricsCollection = metricsCollection;
    }

    @Override
    public String toString() {
        return "org.eclipse.opensmartclide.tdreusabilityapi.domain.Projects[ pid=" + pid + " ]";
    }
    
}
