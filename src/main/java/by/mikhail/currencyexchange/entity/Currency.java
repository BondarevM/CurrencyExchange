package by.mikhail.currencyexchange.entity;

public class Currency {
    private Integer id;
    private String Code;
    private String FullName;
    private String Sign;

    public Currency(Integer id, String code, String fullName, String sign) {
        this.id = id;
        Code = code;
        FullName = fullName;
        Sign = sign;
    }
    public Currency(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getSign() {
        return Sign;
    }

    public void setSign(String sign) {
        Sign = sign;
    }

    @Override
    public String toString() {
        return "Currency{" +
               "id=" + id +
               ", Code='" + Code + '\'' +
               ", FullName='" + FullName + '\'' +
               ", Sign='" + Sign + '\'' +
               '}';
    }
}
