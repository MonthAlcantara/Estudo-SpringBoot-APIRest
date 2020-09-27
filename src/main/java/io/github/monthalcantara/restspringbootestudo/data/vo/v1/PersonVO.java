package io.github.monthalcantara.restspringbootestudo.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.ResourceSupport;

import java.io.Serializable;
import java.util.Objects;

@JsonPropertyOrder({"id", "address", "first_name", "last_name", "gender"}) // Para escolher a ordem como será organizado o recurso entregue ao client
public class PersonVO extends ResourceSupport implements Serializable {

    /*O resource Support ja possui uma especie de id que ele irá usar para fazer o retorno dos endpoints
     * dessa forma essa classe não usará o nome id mas sim key. Para não quebrar a conversão do DOZER,
     * já que não terei mais o id, posso usar a anotation @Mapping do DOZER e mapear key como "id"
     */
    @Mapping("id") // para mapear o atributo de conversão do DOZER
    @JsonProperty("id")// para mapear a forma como será entregue ao client
    private Long key;

    @JsonProperty("first_name") // para mapear a forma como será entregue ao client
    private String firstName;

    @JsonProperty("last_name") // para mapear a forma como será entregue ao client
    private String lastName;

    private String address;

    @JsonIgnore // Para não enviar ao client
    private String gender;

    public Long getKey() {
        return key;
    }

    public void setKey(Long key) {
        this.key = key;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonVO personVO = (PersonVO) o;
        return Objects.equals(key, personVO.key) &&
                Objects.equals(firstName, personVO.firstName) &&
                Objects.equals(lastName, personVO.lastName) &&
                Objects.equals(address, personVO.address) &&
                Objects.equals(gender, personVO.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, firstName, lastName, address, gender);
    }
}
