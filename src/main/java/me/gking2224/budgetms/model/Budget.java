package me.gking2224.budgetms.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import me.gking2224.common.web.View;

@Entity
@Table
public class Budget implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2707606975268392692L;

    private Long id;
    
    private Long projectId;

    private String name;

    private String location;
    
    private int year;
    
    private Set<Role> roles = new HashSet<Role>();
    
    public Budget() {
        super();
    }
    
    public Budget(String name, Long projectId, int year) {
        this();
        this.name = name;
        this.projectId =projectId;
        this.year = year;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "budget_id")
    @JsonProperty("_id")
    @JsonView(View.Summary.class)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "project_id")
    @JsonView(View.Summary.class)
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column
    @JsonView(View.Summary.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    @JsonView(View.Summary.class)
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    
    @OneToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="budget")
    @JsonView(View.Detail.class)
    public Set<Role> getRoles() {
        return roles;
    }
    
    public void addRole(Role role) {
        role.setBudget(this);
        roles.add(role);
    }
    
    @Transient
    public Map<Long, Role> getRolesById() {
        return getRoles().stream().filter(r -> r.getId() != null).collect(Collectors.toMap(r -> r.getId(), r -> r));
    }

    public void setRoles(Set<Role> roles) {
//        if (roles != null) {
//            roles.forEach(r -> {
//                r.setBudget(this);
//            });
//        }
        this.roles = roles;
    }

    @Override
    public String toString() {
        return String.format("Budget [id=%s, projectId=%s, year=%s, name=%s, location=%s]",
                id, projectId, year, name, location);
    }

    public void updateFrom(Budget b) {
        this.name = b.name;
        this.projectId = b.projectId;
        b.roles.iterator().forEachRemaining(r -> {
            r.setBudget(this);
            if (r.getId() == null) {
                this.getRoles().add(r);
            }
            else {
                this.getRolesById().get(r.getId()).updateFrom(r);
            }
        });
        Set<Long> ids = b.roles.stream().map(Role::getId).collect(Collectors.toSet());
        Iterator<Role> it = this.roles.iterator();
        while (it.hasNext()) {
            Role next = it.next();
            if (next.getId() != null && !ids.contains(next.getId())) it.remove();
        };
        
    }

    @Column
    @JsonView(View.Summary.class)
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + year;
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
        Budget other = (Budget) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (year != other.year)
            return false;
        return true;
    }
}
