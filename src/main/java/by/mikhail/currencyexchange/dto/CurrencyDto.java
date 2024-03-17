package by.mikhail.currencyexchange.dto;

import java.util.Objects;

public class CurrencyDto {
    private final Integer id;
    private final String name;
    private final String code;
    private final String sign;

    public CurrencyDto(Integer ID, String code, String fullName, String sign) {
        this.id = ID;
        this.code = code;
        name = fullName;
        this.sign = sign;
    }


    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSign() {
        return sign;
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
               "ID=" + id +
               ", Code='" + code + '\'' +
               ", FullName='" + name + '\'' +
               ", Sign='" + sign + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDto that = (CurrencyDto) o;
        return Objects.equals(id, that.id) && Objects.equals(code, that.code) && Objects.equals(name, that.name) && Objects.equals(sign, that.sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, code, name, sign);
    }
}
