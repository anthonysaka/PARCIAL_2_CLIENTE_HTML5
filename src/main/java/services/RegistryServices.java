package services;

import encapsulations.Registry;


public class RegistryServices extends ORMDBManage<Registry>{

    private static services.RegistryServices instancia;

    private RegistryServices() {
        super(Registry.class);
    }

    public static services.RegistryServices getInstancia(){
        if(instancia==null){
            instancia = new services.RegistryServices();
        }
        return instancia;
    }

}

