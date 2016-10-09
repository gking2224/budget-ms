package me.gking2224.budgetms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import me.gking2224.common.web.View;

@Entity
@Table(name="ROLE")
public class Role implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 968143753916930608L;
    
    private Long id;
    
    private String name;
    
    private Budget budget;
    
    private BigDecimal rate;
    
    private String comment;
    
    private Long locationId;
    
    private List<BigDecimal> ftes = new ArrayList<BigDecimal>();
    
    private Set<RoleAllocation> allocations = new HashSet<RoleAllocation>();
    
    public Role() {
        
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_id")
    @JsonProperty("_id")
    @JsonView(View.Summary.class)
    public Long getId() {
        return id;
    }
    
    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    @ManyToOne
    @JoinColumn(name="budget_id", nullable=false)
    public Budget getBudget() {
        return budget;
        
    }

    @Column
    @JsonView(View.Summary.class)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column
    @JsonView(View.Summary.class)
    public String getComment() {
        return comment;
    }

    @Column
    @JsonView(View.Summary.class)
    public BigDecimal getRate() {
        return rate;
    }

    @Column(name="location_id")
    @JsonView(View.Summary.class)
    public Long getLocationId() {
        return locationId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((rate == null) ? 0 : rate.hashCode());
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
        
        Role other = (Role) obj;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        
        if (locationId == null) {
            if (other.locationId != null)
                return false;
        } else if (!locationId.equals(other.locationId))
            return false;
        
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        
        if (rate == null) {
            if (other.rate != null)
                return false;
        } else if (!rate.equals(other.rate))
            return false;
        
        return true;
    }
    
    public void setFtes(List<BigDecimal> ftes) {
        this.ftes = ftes;
    }
    
    @Column(name="fte") // needed?
    @ElementCollection(fetch=FetchType.LAZY)
    @CollectionTable(
        name = "role_fte",
        joinColumns=@JoinColumn(name = "role_id")
    )
    @OrderColumn(name="month", columnDefinition="TINYINT(4)")
    @OrderBy("month ASC")
    @JsonView(View.Summary.class)
    public List<BigDecimal> getFtes() {
        return ftes;
    }

    public void setAllocations(Set<RoleAllocation> allocations) {
        this.allocations = allocations;
    }

    @OneToMany(cascade={CascadeType.ALL}, targetEntity=RoleAllocation.class, fetch=FetchType.EAGER, orphanRemoval=true, mappedBy="role")
    @JsonView(View.Summary.class)
    public Set<RoleAllocation> getAllocations() {
        return allocations;
    }
    
    public void addAllocation(RoleAllocation ra) {
        ra.setRole(this);
        allocations.add(ra);
    }

    @Override
    public String toString() {
        return String.format(
                "Role [id=%s, name=%s, budget=%s, rate=%s, comment=%s, locationId=%s, ftes=%s, allocations=%s]",
                id, name, budget, rate, comment, locationId, ftes, allocations);
    }

    @Transient
    public Map<Long, RoleAllocation> getAllocationsById() {
        return getAllocations().stream().filter(ra -> ra.getId() != null).collect(Collectors.toMap(
                a-> {
                    return a.getId();
                },
                a -> {
                    return a;
                }
        ));
    }

    public void updateFrom(Role r) {
        this.name = r.name;
        this.comment = r.comment;
        this.locationId = r.locationId;
        this.rate = r.rate;
        this.ftes = r.ftes;

        r.allocations.iterator().forEachRemaining(a -> {
            a.setRole(r);
            if (a.getId() == null) {
                this.allocations.add(a);
            }
            else {
                this.getAllocationsById().get(a.getId()).updateFrom(a);
            }
        });
        Set<Long> ids = r.allocations.stream().map(RoleAllocation::getId).collect(Collectors.toSet());
        Iterator<RoleAllocation> it = this.allocations.iterator();
        while (it.hasNext()) {
            RoleAllocation next = it.next();
            if (next.getId() != null && !ids.contains(next.getId())) it.remove();
        };
        
    }
}
 