package org.quinnandrews.spring.data.specification.builder.application.data.guitarpedals;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Table(name = "guitar_pedal")
@Entity
public class GuitarPedal {

    @Id
    @Column(name = "id",
            columnDefinition = "BIGINT",
            nullable = false,
            updatable = false)
    private Long id;

    @Column(name = "name",
            columnDefinition = "VARCHAR(64)",
            nullable = false)
    private String name;

    @Column(name = "has_stereo_output",
            columnDefinition = "BOOLEAN",
            nullable = false)
    private Boolean hasStereoOutput;

    @Column(name = "date_purchased",
            columnDefinition = "DATE",
            nullable = false)
    private LocalDate datePurchased;

    @Column(name = "date_sold",
            columnDefinition = "DATE")
    private LocalDate dateSold;

    @Column(name = "used_value",
            columnDefinition = "INT",
            nullable = false)
    private Integer usedValue;

    @ManyToOne
    @JoinColumn(name="manufacturer_id",
                nullable=false,
                updatable=false)
    private Manufacturer manufacturer;

    @OneToMany
    @JoinColumn(name="guitar_pedal_id")
    private List<GuitarPedalTag> tags;

    public GuitarPedal() {
        // no-op
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Boolean getHasStereoOutput() {
        return hasStereoOutput;
    }

    public LocalDate getDatePurchased() {
        return datePurchased;
    }

    public LocalDate getDateSold() {
        return dateSold;
    }

    public Integer getUsedValue() {
        return usedValue;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public List<GuitarPedalTag> getTags() {
        return tags;
    }
}
