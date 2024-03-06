package by.mikhail.currencyexchange.dto;

import java.util.Objects;

public class CurrencyDto {
    private final Integer ID;
    private final String Code;
    private final String FullName;
    private final String Sign;

    public CurrencyDto(Integer ID, String code, String fullName, String sign) {
        this.ID = ID;
        Code = code;
        FullName = fullName;
        Sign = sign;
    }

    public Integer getID() {
        return ID;
    }

    public String getCode() {
        return Code;
    }

    public String getFullName() {
        return FullName;
    }

    public String getSign() {
        return Sign;
    }

    @Override
    public String toString() {
        return "CurrencyDto{" +
               "ID=" + ID +
               ", Code='" + Code + '\'' +
               ", FullName='" + FullName + '\'' +
               ", Sign='" + Sign + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrencyDto that = (CurrencyDto) o;
        return Objects.equals(ID, that.ID) && Objects.equals(Code, that.Code) && Objects.equals(FullName, that.FullName) && Objects.equals(Sign, that.Sign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ID, Code, FullName, Sign);
    }
}
