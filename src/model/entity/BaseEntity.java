package model.entity;

import repository.Identifiable;

import java.time.LocalDateTime;
import java.util.StringJoiner;

public abstract class BaseEntity implements Identifiable<Long> {
    private Long id;
    private LocalDateTime created;
    private LocalDateTime modified;

    public BaseEntity() {
        setCreated(LocalDateTime.now());
        setModified(LocalDateTime.now());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public LocalDateTime getCreated() {
        return created;
    }

    public BaseEntity setCreated(LocalDateTime created) {
        this.created = created;
        return this;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public BaseEntity setModified(LocalDateTime modified) {
        this.modified = modified;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity that = (BaseEntity) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
            return new StringJoiner(" | ", "", "")
                    .add("id=" + id)

                    .toString();

    }
}
