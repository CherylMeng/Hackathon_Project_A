package oracle.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQueries({ @NamedQuery(name = "SType.findAll", query = "select o from SType o") })
@Table(name = "\"s_type\"")
public class SType implements Serializable {
    private static final long serialVersionUID = -5622890440776043686L;
    @Column(name = "status", nullable = false)
    private int status;
    @Id
    @Column(name = "type_id", nullable = false)
    private int type_id;
    @Column(name = "type_name", nullable = false)
    private String type_name;

    public SType() {
    }

    public SType(int status, int type_id, String type_name) {
        this.status = status;
        this.type_id = type_id;
        this.type_name = type_name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
