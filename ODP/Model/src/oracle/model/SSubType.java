package oracle.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "SSubType.findAll", query = "select o from SSubType o") })
@Table(name = "\"s_sub_type\"")
public class SSubType implements Serializable {
    private static final long serialVersionUID = -6392129831506629787L;
    @Column(name = "status", nullable = false)
    private String status;
    @Id
    @Column(name = "sub_type_id", nullable = false)
    private int sub_type_id;
    @Column(name = "sub_type_name", nullable = false)
    private String sub_type_name;
    @Column(name = "type_id", nullable = false)
    private int type_id;

    public SSubType() {
    }

    public SSubType(String status, int sub_type_id, String sub_type_name, int type_id) {
        this.status = status;
        this.sub_type_id = sub_type_id;
        this.sub_type_name = sub_type_name;
        this.type_id = type_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSub_type_id() {
        return sub_type_id;
    }

    public void setSub_type_id(int sub_type_id) {
        this.sub_type_id = sub_type_id;
    }

    public String getSub_type_name() {
        return sub_type_name;
    }

    public void setSub_type_name(String sub_type_name) {
        this.sub_type_name = sub_type_name;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
