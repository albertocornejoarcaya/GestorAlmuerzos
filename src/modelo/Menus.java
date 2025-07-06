package modelo;

public class Menus extends Entidad {
    private TipoMenu tipo;
    private double precio;

    public Menus(String id, String descripcion, TipoMenu tipo,  double precio) {
        super(id, descripcion);
        this.tipo = tipo;
        this.precio =  precio;
    }

    public String getDescripcion()
    {
    	return nombre; 
    }
    
    public TipoMenu getTipo()
    { 
    	return tipo;
    }
    
    public void setTipo(TipoMenu tipo) 
    {
    	this.tipo = tipo;  
    
    }

    public double getPrecio() 
    {
    	return precio; 
    }
    public void setPrecio(double precio) { this.precio = precio; }



    public String toString() {
        return "#" + id + " " + nombre + " (" + tipo + ")";
    }
}
