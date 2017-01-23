package co.com.business.repository.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author PX60
 */
@Entity
@Table(name = "imagen", catalog = "business", schema = "")
@NamedQueries({
    @NamedQuery(name = "Imagen.findAll", query = "SELECT i FROM Imagen i")
    , @NamedQuery(name = "Imagen.findByIdImagen", query = "SELECT i FROM Imagen i WHERE i.idImagen = :idImagen")
    , @NamedQuery(name = "Imagen.findByImgPath", query = "SELECT i FROM Imagen i WHERE i.imgPath = :imgPath")
    , @NamedQuery(name = "Imagen.findByImgName", query = "SELECT i FROM Imagen i WHERE i.imgName = :imgName")
    , @NamedQuery(name = "Imagen.findByImgSize", query = "SELECT i FROM Imagen i WHERE i.imgSize = :imgSize")
    , @NamedQuery(name = "Imagen.findByImgType", query = "SELECT i FROM Imagen i WHERE i.imgType = :imgType")})
public class Imagen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_IMAGEN")
    private Long idImagen;
    @Basic(optional = false)
    @Column(name = "IMG_PATH")
    private String imgPath;
    @Basic(optional = false)
    @Column(name = "IMG_NAME")
    private String imgName;
    @Basic(optional = false)
    @Column(name = "IMG_SIZE")
    private String imgSize;
    @Basic(optional = false)
    @Column(name = "IMG_TYPE")
    private String imgType;
    @OneToMany(mappedBy = "idImg")
    private List<Clientes> clientesList;

    public Imagen() {
    }

    public Imagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public Imagen(Long idImagen, String imgPath, String imgName, String imgSize, String imgType) {
        this.idImagen = idImagen;
        this.imgPath = imgPath;
        this.imgName = imgName;
        this.imgSize = imgSize;
        this.imgType = imgType;
    }

    public Long getIdImagen() {
        return idImagen;
    }

    public void setIdImagen(Long idImagen) {
        this.idImagen = idImagen;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgSize() {
        return imgSize;
    }

    public void setImgSize(String imgSize) {
        this.imgSize = imgSize;
    }

    public String getImgType() {
        return imgType;
    }

    public void setImgType(String imgType) {
        this.imgType = imgType;
    }

    public List<Clientes> getClientesList() {
        return clientesList;
    }

    public void setClientesList(List<Clientes> clientesList) {
        this.clientesList = clientesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idImagen != null ? idImagen.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Imagen)) {
            return false;
        }
        Imagen other = (Imagen) object;
        if ((this.idImagen == null && other.idImagen != null) || (this.idImagen != null && !this.idImagen.equals(other.idImagen))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pruebas.entities.Imagen[ idImagen=" + idImagen + " ]";
    }
    
}