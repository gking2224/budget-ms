package me.gking2224.budgetms.jpa;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
    
    private Set<Role> roles;
    
    public Budget() {
        super();
    }
    
    public Budget(String name) {
        this.name = name;
    }

    public Budget(long id, String name) {
        this(name);
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinColumn(name = "budget_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
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
}
