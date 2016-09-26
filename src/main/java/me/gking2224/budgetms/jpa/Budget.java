package me.gking2224.budgetms.jpa;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table (name="BUDGET")
public class Budget implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 2707606975268392692L;

    private Long id;
    
    private Long projectId;

    private String name;

    private String location;
    
    private Set<Role> roles = Collections.emptySet();
    
    public Budget() {
        super();
    }
    
    public Budget(String name, Long projectId) {
        this();
        this.name = name;
        this.projectId =projectId;
    }
    
    @OneToMany(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "budget_id")
    @JsonProperty("_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "project_id")
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transient
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    public void setRoles(Set<Role> roles) {
        if (roles != null) {
            roles.forEach(r -> {
                r.setBudget(this);
            });
        }
        this.roles = roles;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((projectId == null) ? 0 : projectId.hashCode());
        result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (projectId == null) {
            if (other.projectId != null)
                return false;
        } else if (!projectId.equals(other.projectId))
            return false;
        if (roles == null) {
            if (other.roles != null)
                return false;
        } else if (!roles.equals(other.roles))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Budget [id=%s, projectId=%s, name=%s, location=%s, roles=%s]",
                id, projectId, name, location, roles);
    }
}
