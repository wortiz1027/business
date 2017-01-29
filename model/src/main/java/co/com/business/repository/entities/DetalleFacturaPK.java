package co.com.business.repository.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author PX60
 */
@Embeddable
public class DetalleFacturaPK implements Serializable {

    private static final long serialVersionUID = 1L;
	
    @Basic(optional = false)
    @Column(name = "NUM_DETALLE")
    private long numDetalle;
    @Basic(optional = false)
    @Column(name = "NUMERO_FACTURA")
    private long numeroFactura;

    public DetalleFacturaPK() {
    }

    public DetalleFacturaPK(long numDetalle, long numeroFactura) {
        this.numDetalle = numDetalle;
        this.numeroFactura = numeroFactura;
    }

    public long getNumDetalle() {
        return numDetalle;
    }

    public void setNumDetalle(long numDetalle) {
        this.numDetalle = numDetalle;
    }

    public long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numDetalle;
        hash += (int) numeroFactura;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof DetalleFacturaPK)) {
            return false;
        }
        DetalleFacturaPK other = (DetalleFacturaPK) object;
        if (this.numDetalle != other.numDetalle) {
            return false;
        }
        if (this.numeroFactura != other.numeroFactura) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebas.entities.DetalleFacturaPK[ numDetalle=" + numDetalle + ", numeroFactura=" + numeroFactura + " ]";
    }
    
}