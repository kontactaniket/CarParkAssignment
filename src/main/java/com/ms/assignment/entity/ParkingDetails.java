package com.ms.assignment.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ParkingDetails {
    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "parking-sequence-generator")
    @GenericGenerator(
            name = "parking-sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "parking_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            }
    )
    private Integer id;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "id")
    private Car car;
    @Column(name = "DATE_TIME_IN")
    private LocalDateTime inTime;
    @Column(name = "DATE_TIME_OUT")
    private LocalDateTime outTime;
    @Column(name = "IS_ACTIVE")
    private boolean isActive;
}
