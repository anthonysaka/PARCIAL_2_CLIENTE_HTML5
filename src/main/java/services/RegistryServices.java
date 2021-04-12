package services;

import encapsulations.Registry;

public class RegistryServices extends ORMDBManage<Registry> {

    private static RegistryServices instancia;

    private RegistryServices() {
        super(Registry.class);
    }

    public static RegistryServices getInstancia(){
        if(instancia==null){
            instancia = new RegistryServices();
        }
        return instancia;
    }


}
