package validation.dto;

import validation.anotation.*;

import java.time.LocalDate;
import java.util.Objects;

public class CustomerDto {

    @Length(min = 2,max = 30)
    private String name;
    @Email
    private String email;
    @Adulthood
    private LocalDate birthday;
    @Min(0)
    @Max(100)
    private int discountRate;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public CustomerDto(String name, String email, LocalDate birthday, int discountRate) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.discountRate = discountRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerDto that = (CustomerDto) o;
        return discountRate == that.discountRate && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(birthday, that.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, birthday, discountRate);
    }

    @Override
    public String toString() {
        return "CustomerDto{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", discountRate=" + discountRate +
                '}';
    }
}
