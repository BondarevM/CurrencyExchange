package by.mikhail.currencyexchange.entity;

public class Currency {
    private Integer id;
    private String name;
    private String code;
    private String sign;

    public Currency(Integer id, String code, String fullName, String sign) {
        this.id = id;
        this.code = code;
        name = fullName;
        this.sign = sign;
    }
    public Currency(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Currency{" +
               "id=" + id +
               ", Code='" + code + '\'' +
               ", FullName='" + name + '\'' +
               ", Sign='" + sign + '\'' +
               '}';
    }
}
