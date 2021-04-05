package controllers;


import com.google.gson.Gson;
import encapsulations.User;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import org.h2.util.json.JSONObject;
import org.jasypt.util.text.StrongTextEncryptor;
import services.UserServices;

import java.sql.SQLException;
import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.*;


public class MainController {
    Javalin app;
    static String tempURI = "";
    static int idInvoice = 0;

    public MainController(Javalin app) throws SQLException {
        this.app = app;

        User admin = new User("admin", "admin","admin","Anthony Sakamoto");
        UserServices.getInstancia().create(admin);

    }


    public void routesControl(){
        app.get("/", ctx -> ctx.redirect("/login"));

        app.routes(() -> {

            path("/login", () -> {

                get("/", ctx -> {
                    ctx.render("public/index.html");
                });


            });

            path("/authenticate", () -> {

               before("/", ctx -> {
                   Gson auxJson = new Gson();
                   User u = auxJson.fromJson(ctx.body(),User.class); //User object from client request
                   System.out.println(u.getUsername() + u.getPassword());
                   User auxUser = UserServices.getInstancia().checkLoginUser(u.getUsername(),u.getPassword());
                /*  String username = ctx.formParam("username");
                  String password = ctx.formParam("password");
                  User auxUser = UserServices.getInstancia().checkLoginUser(username,password);*/

                   if (auxUser == null){
                       ctx.status(404);
                       ctx.result("USURNAME OR PASSWORD INCORRECT!");// arreglara para mandar mensaje de error de credenciales

                   }else{
                       ctx.attribute("userFound", auxUser);
                       /*if (ctx.formParam("chkRemember") != null){
                           StrongTextEncryptor stE = new StrongTextEncryptor();
                           stE.setPassword("myEncryptionPassword");
                           String userEncryp = stE.encrypt(auxUser.getUsername());
                           ctx.cookie("user_remembered", userEncryp,604800);
                       }*/
                   }
               });

               post("/", ctx -> {
                   ctx.sessionAttribute("user_logged",ctx.attribute("userFound"));
                   Gson auxJson = new Gson();
                   String u = auxJson.toJson(ctx.sessionAttribute("user_logged"),User.class);
                   ctx.json(u);
               });

            });

            path("/home", () -> {

                get("/", ctx -> {
                    ctx.render("/templates/home.html");
                });

            });

            path("/formulario", () -> {

                get("/", ctx -> {
                    ctx.render("/templates/form.html");
                });

                get("/crear", ctx -> {
                    String nombre = ctx.formParam("nombre");
                    String sector = ctx.formParam("sector");
                    String academico = ctx.formParam("selected");

                    // Form form = new Form("","",);
                });


            });

            path("/user", ()->{
                get("/", ctx -> {
                    ctx.render("/templates/userNew.html");
                });


            });



        });

    }
}
