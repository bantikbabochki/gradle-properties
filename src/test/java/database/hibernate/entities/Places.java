package database.hibernate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "places")
public class Places {

    @Id
    @Column(name = "id")
    private Integer id;

    @Column(name = "row_number")
    private Integer row;

    @Column(name = "place_num")
    private Integer placeNum;

    @Column(name = "name")
    private String name;
}

