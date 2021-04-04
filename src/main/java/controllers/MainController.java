package controllers;


import com.google.gson.Gson;
import encapsulations.User;
import io.javalin.Javalin;
import io.javalin.plugin.json.JavalinJson;
import org.h2.util.json.JSONObject;
import services.UserServices;

import java.sql.SQLException;

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

                   User auxUser = UserServices.getInstancia().checkLoginUser(u.getUsername(),u.getPassword());
                  /* String username = ctx.formParam("username");
                   String password = ctx.formParam("password");
                   User auxUser = UserServices.getInstancia().checkLoginUser(username,password);*/

                   if (auxUser == null){
                        ctx.result("ERROR");
                    } else {
                        ctx.attribute("userFound", auxUser.getUsername());
                    }
               });

               post("/", ctx -> {
                   ctx.sessionAttribute("user_logged",ctx.attribute("userFound"));
                   ctx.redirect("/home");
               });
                after("/", ctx -> {
                    ctx.redirect("/home");
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

            });



        });

    }
}
