package me.gking2224.budgetms.model;



import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@Entity
@Table(name="ROLE_ALLOCATION")
public class RoleAllocation implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 968143753916930608L;
    
    private Long id;
    
    private Role role;
    
    private BigDecimal rate;
    
    private String comment;
    
    private Long locationId;
    
    private Long resourceId;
    
    private List<AllocationFte> ftes = emptyList();
    
    public RoleAllocation() {
        
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "role_allocation_id")
    @JsonProperty("_id")
    public Long getId() {
        return id;
    }
    
    @ManyToOne
    @JoinColumn(name="role_id", nullable=false)
    @JsonBackReference
    public Role getRole() {
        return role;
        
    }

    @Column
    public String getComment() {
        return comment;
    }

    @Column
    public BigDecimal getRate() {
        return rate;
    }

    @Column(name = "location_id")
    public Long getLocationId() {
        return locationId;
    }

    @Column(name = "resource_id")
    public Long getResourceId() {
        return resourceId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }
    
    public void setFtes(List<AllocationFte> ftes) {
        this.ftes = unmodifiableList(ftes);
    }
    
    @JsonIgnore
    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(
        name = "role_allocation_fte",
        joinColumns=@JoinColumn(name = "role_allocation_id")
    )
    @OrderColumn(name="month")
    @OrderBy("month ASC")
    public List<AllocationFte> getFtes() {
        return ftes;
    }
    
    public void setActuals(List<BigDecimal> actuals) {
        List<AllocationFte> ftes = getFtes().stream().filter(f -> f.getType() != AllocationFte.AllocationType.ACTUALS).collect(Collectors.toList());
        ftes.addAll(convertToFtes(actuals, AllocationFte.AllocationType.ACTUALS));
        setFtes(ftes);
    }
    
    public void setForecast(List<BigDecimal> forecast) {
        List<AllocationFte> ftes = getFtes().stream().filter(f -> f.getType() != AllocationFte.AllocationType.FORECAST).collect(Collectors.toList());
        ftes.addAll(convertToFtes(forecast, AllocationFte.AllocationType.FORECAST));
        setFtes(ftes);
    }
    
    protected List<AllocationFte> convertToFtes(List<BigDecimal> ftes, AllocationFte.AllocationType type) {
        return IntStream.range(0, ftes.size()).mapToObj(
                month -> createFte(ftes.get(month), type)).collect(Collectors.toList());
        
    }
    
    protected AllocationFte createFte(BigDecimal value, AllocationFte.AllocationType type) {
        AllocationFte fte = new AllocationFte();
        fte.setType(type);
        fte.setFte(value);
        return fte;
    }
    
    @Transient
    @JsonInclude
    public List<BigDecimal> getActuals() {
        return unmodifiableList(
                getFtes().stream().filter(f -> AllocationFte.AllocationType.ACTUALS == f.getType()).map(AllocationFte::getFte).collect(Collectors.toList()));
    }
    
    @Transient
    @JsonInclude
    public List<BigDecimal> getForecast() {
        return unmodifiableList(
                getFtes().stream().filter(f -> AllocationFte.AllocationType.FORECAST == f.getType()).map(AllocationFte::getFte).collect(Collectors.toList()));
    }
    @Embeddable
    private static class AllocationFte {

        private AllocationType type;

        @Column
        private BigDecimal fte;
        
        public AllocationFte() {
            super();
        }

        @Column
        @Enumerated(EnumType.STRING)
        public AllocationType getType() {
            return type;
        }

        public void setType(AllocationType type) {
            this.type = type;
        }

        public BigDecimal getFte() {
            return fte;
        }

        public void setFte(BigDecimal fte) {
            this.fte = fte;
        }

        static enum AllocationType {
            FORECAST, ACTUALS;
        }
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());
        result = prime * result + ((ftes == null) ? 0 : ftes.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((locationId == null) ? 0 : locationId.hashCode());
        result = prime * result + ((rate == null) ? 0 : rate.hashCode());
        result = prime * result + ((resourceId == null) ? 0 : resourceId.hashCode());
        result = prime * result + ((role == null) ? 0 : role.hashCode());
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
        RoleAllocation other = (RoleAllocation) obj;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        if (ftes == null) {
            if (other.ftes != null)
                return false;
        } else if (!ftes.equals(other.ftes))
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
        if (rate == null) {
            if (other.rate != null)
                return false;
        } else if (!rate.equals(other.rate))
            return false;
        if (resourceId == null) {
            if (other.resourceId != null)
                return false;
        } else if (!resourceId.equals(other.resourceId))
            return false;
        if (role == null) {
            if (other.role != null)
                return false;
        } else if (!role.equals(other.role))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "RoleAllocation [id=%s, role=%s, rate=%s, comment=%s, locationId=%s, resourceId=%s, ftes=%s]",
                id, role, rate, comment, locationId, resourceId, ftes);
    }
}
