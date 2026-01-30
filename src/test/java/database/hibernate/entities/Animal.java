package database.hibernate.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity  // ← Помечает класс как сущность БД
@Table(name = "animal")
@Data  // ← Lombok: геттеры, сеттеры, toString, equals, hashCode
@NoArgsConstructor  // ← Конструктор без параметров (обязателен для Hibernate!)
@AllArgsConstructor
@Builder
public class Animal {

    @Id  // ← Primary Key
    @Column(name = "id")  // ← Имя столбца в БД
    private Integer id;

    @Column(name = "name", length = 255, nullable = true)  // ← VARCHAR(255)
    private String name;

    @Column(name = "age")
    private Integer age;

    // ========== СВЯЗИ С ДРУГИМИ ТАБЛИЦАМИ ==========

    @ManyToOne(fetch = FetchType.EAGER)  // ← Связь Many-to-One
    @JoinColumn(name = "type", referencedColumnName = "id")  // ← FK столбец
    private Type type;  // ← Объект Type, а не int!

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sex", referencedColumnName = "id")
    private Sex sex;  // ← Объект Sex, а не int!

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "place", referencedColumnName = "id")
    private Places place;  // ← Объект Places, а не int!

    // ========== ДОПОЛНИТЕЛЬНЫЕ МЕТОДЫ ==========

    /**
     * Для удобства: установить type по ID
     */
    public void setTypeById(Integer typeId) {
        this.type = new Type();
        this.type.setId(typeId);
    }

    /**
     * Для удобства: получить type ID
     */
    public Integer getTypeId() {
        return type != null ? type.getId() : null;
    }
}

