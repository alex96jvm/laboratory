package dev.alex96jvm.laboratory.dto;

import java.io.Serializable;
import java.util.Objects;

public class LaborantDto implements Serializable {
    Long id;
    String name;
    Boolean statusHomework;
    Integer rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getStatusHomework() {
        return statusHomework;
    }

    public void setStatusHomeWork(Boolean statusHomework) {
        this.statusHomework = statusHomework;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaborantDto that = (LaborantDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
