package services;

import encapsulations.Form;
import encapsulations.User;

public class FormServices extends ORMDBManage<Form>{

    private static FormServices instancia;

    private FormServices() {
        super(Form.class);
    }

    public static FormServices getInstancia(){
        if(instancia==null){
            instancia = new FormServices();
        }
        return instancia;
    }

}
