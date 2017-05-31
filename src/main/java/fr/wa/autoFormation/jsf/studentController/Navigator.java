package fr.wa.autoFormation.jsf.studentController;

import javax.faces.bean.ManagedBean;

/**
 * Created by Said B on 29/05/2017.
 */

@ManagedBean
public class Navigator {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
