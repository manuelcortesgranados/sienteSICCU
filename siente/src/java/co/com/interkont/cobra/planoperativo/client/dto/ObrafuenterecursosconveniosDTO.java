package co.com.interkont.cobra.planoperativo.client.dto;
// Generated Jul 12, 2013 4:10:17 PM by Hibernate Tools 3.2.1.GA


import com.google.gwt.user.client.rpc.IsSerializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Obrafuenterecursosconvenios generated by hbm2java
 */
public class ObrafuenterecursosconveniosDTO implements IsSerializable {

    private int idobrafuenterecursos;
    private ObraDTO obra;
    private FuenterecursosconvenioDTO fuenterecursosconvenio;
    private RubroDTO rubro;
    private int tipoaporte;
    private int vigencia;
    private BigDecimal valor;
    private String descripcionaporte;
    private Set relacionobrafuenterecursoscontratos = new HashSet(0);

    public ObrafuenterecursosconveniosDTO() {
    }
     public ObrafuenterecursosconveniosDTO(int idobrafuenterecursos, ObraDTO obra, int tipoaporte, int vigencia, BigDecimal valor) {
       this.idobrafuenterecursos = idobrafuenterecursos;
       this.obra = obra;
       this.tipoaporte = tipoaporte;
       this.vigencia = vigencia;
       this.valor = valor;
     }

      public ObrafuenterecursosconveniosDTO(int tipoaporte,  BigDecimal valor,FuenterecursosconvenioDTO fuente,RubroDTO rubro) {
        this.tipoaporte = tipoaporte;
        this.valor = valor;
        this.fuenterecursosconvenio=fuente;
        this.rubro=rubro;
        
     }

    public ObrafuenterecursosconveniosDTO(int idobrafuenterecursos, ObraDTO obra, FuenterecursosconvenioDTO fuenterecursosconvenio, RubroDTO rubro, int tipoaporte, int vigencia, BigDecimal valor, String descripcionaporte, Set relacionobrafuenterecursoscontratos) {
        this.idobrafuenterecursos = idobrafuenterecursos;
        this.obra = obra;
        this.fuenterecursosconvenio = fuenterecursosconvenio;
        this.rubro = rubro;
        this.tipoaporte = tipoaporte;
        this.vigencia = vigencia;
        this.valor = valor;
        this.descripcionaporte = descripcionaporte;
        this.relacionobrafuenterecursoscontratos = relacionobrafuenterecursoscontratos;

    }
    
    

    /**
     * @return the idobrafuenterecursos
     */
    public int getIdobrafuenterecursos() {
        return idobrafuenterecursos;
    }

    /**
     * @param idobrafuenterecursos the idobrafuenterecursos to set
     */
    public void setIdobrafuenterecursos(int idobrafuenterecursos) {
        this.idobrafuenterecursos = idobrafuenterecursos;
    }

    /**
     * @return the obra
     */
    public ObraDTO getObra() {
        return obra;
    }

    /**
     * @param obra the obra to set
     */
    public void setObra(ObraDTO obra) {
        this.obra = obra;
    }

    /**
     * @return the fuenterecursosconvenio
     */
    public FuenterecursosconvenioDTO getFuenterecursosconvenio() {
        return fuenterecursosconvenio;
    }

    /**
     * @param fuenterecursosconvenio the fuenterecursosconvenio to set
     */
    public void setFuenterecursosconvenio(FuenterecursosconvenioDTO fuenterecursosconvenio) {
        this.fuenterecursosconvenio = fuenterecursosconvenio;
    }

    /**
     * @return the rubro
     */
    public RubroDTO getRubro() {
        return rubro;
    }

    /**
     * @param rubro the rubro to set
     */
    public void setRubro(RubroDTO rubro) {
        this.rubro = rubro;
    }

    /**
     * @return the tipoaporte
     */
    public int getTipoaporte() {
        return tipoaporte;
    }

    /**
     * @param tipoaporte the tipoaporte to set
     */
    public void setTipoaporte(int tipoaporte) {
        this.tipoaporte = tipoaporte;
    }

    /**
     * @return the vigencia
     */
    public int getVigencia() {
        return vigencia;
    }

    /**
     * @param vigencia the vigencia to set
     */
    public void setVigencia(int vigencia) {
        this.vigencia = vigencia;
    }

    /**
     * @return the valor
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * @param valor the valor to set
     */
    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    /**
     * @return the descripcionaporte
     */
    public String getDescripcionaporte() {
        return descripcionaporte;
    }

    /**
     * @param descripcionaporte the descripcionaporte to set
     */
    public void setDescripcionaporte(String descripcionaporte) {
        this.descripcionaporte = descripcionaporte;
    }

    /**
     * @return the relacionobrafuenterecursoscontratos
     */
    public Set getRelacionobrafuenterecursoscontratos() {
        return relacionobrafuenterecursoscontratos;
    }

    /**
     * @param relacionobrafuenterecursoscontratos the relacionobrafuenterecursoscontratos to set
     */
    public void setRelacionobrafuenterecursoscontratos(Set relacionobrafuenterecursoscontratos) {
        this.relacionobrafuenterecursoscontratos = relacionobrafuenterecursoscontratos;
    }
    
    
}
