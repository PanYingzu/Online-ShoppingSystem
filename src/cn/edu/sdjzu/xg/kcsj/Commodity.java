package cn.edu.sdjzu.xg.kcsj;

import util.IdService;

import java.io.Serializable;

public class Commodity implements Comparable<Commodity>, Serializable {

    private Integer id;
    private String name;
    private String description;
    private double price;
    private String type;
    {
        this.id = IdService.getId();
    }
    public Commodity(Integer id,String name,String description,double price,String type){
        this(name,description,price,type);
        this.id = id;
    }
    public Commodity(String name,String description,double price,String type){
        this.name = name;
        this.description = description;
        this.price = price;
        this.type =type;
    }

    public int getId() {
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Commodity other = (Commodity) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
    /**
     * Constructs a <code>String</code> with all attributes
     * in name = value format.
     *
     * @return a <code>String</code> representation
     * of this object.
     */
    public String toString()
    {
        final String TAB = "    ";

        String retValue = "";

        retValue = "Commodity( "
                + super.toString() + TAB
                + "id = " + this.id + TAB
                + "name = " + this.name + TAB
                + "description = " + this.description + TAB
                + "price = " + this.price + TAB
                + "type = " + this.type + TAB
                + " )";

        return retValue;
    }

    @Override
    public int compareTo(Commodity o) {
        return this.id - o.getId();
    }
}
