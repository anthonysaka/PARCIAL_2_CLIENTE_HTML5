package services;

import encapsulations.User;

public class UserServices extends ORMDBManage<User>{

    private static UserServices instancia;

    private UserServices() {
        super(User.class);
    }

    public static UserServices getInstancia(){
        if(instancia==null){
            instancia = new UserServices();
        }
        return instancia;
    }

    /**/

    public User checkLoginUser(User user) {
        User auxU = null;
        for (User u : UserServices.getInstancia().findAll()) {
            if (u.getUsername().equalsIgnoreCase(user.getUsername())
                    && u.getPassword().equals(user.getPassword())) {
                auxU = u;
                break;
            }
        }
        return auxU;
    }


}

