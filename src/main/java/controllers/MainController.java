package controllers;


import com.google.gson.Gson;
import encapsulations.Form;
import encapsulations.FormDataTemplate;
import encapsulations.Registry;
import encapsulations.User;
import io.javalin.Javalin;
import io.javalin.http.sse.SseClient;
import io.javalin.plugin.json.JavalinJson;
import org.eclipse.jetty.websocket.api.Session;
import org.h2.util.json.JSONObject;
import org.jasypt.util.text.StrongTextEncryptor;
import services.FormServices;
import services.RegistryServices;
import services.UserServices;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static io.javalin.apibuilder.ApiBuilder.*;


public class MainController {
    Javalin app;
    static String tempURI = "";
    static int idInvoice = 0;

    //Creando el repositorio de las sesiones recibidas.
    public static List<Session> usuariosConectados = new ArrayList<>();
    public static List<SseClient> listaSseUsuario = new ArrayList<>();

    public MainController(Javalin app) throws SQLException {
        this.app = app;

        User admin = new User("admin", "admin","admin","Anthony Sakamoto");
        UserServices.getInstancia().modify(admin);

    }


    public void routesControl(){
        app.get("/", ctx -> ctx.redirect("/home"));

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

                before("/", ctx -> {
                    User auxUser = ctx.sessionAttribute("user_logged");
                    if (auxUser == null){
                        tempURI = ctx.req.getRequestURI();
                        ctx.redirect("/login");
                    }
                });

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

                post("/crear", ctx -> {
                    Gson auxJson = new Gson();
                    User user = auxJson.fromJson(ctx.body(),User.class); //User object from client request
                    User nuewUs = new User(user.getUsername(), user.getPassword(), user.getRol(), user.getNombre());
                    UserServices.getInstancia().create(nuewUs);
                    ctx.json(nuewUs);
                });


            });



        });

    }


    public void routesWsControl(){
        /**
         * Filtro para activarse antes de la llamadas al contexto.
         */
        app.wsBefore("/ws/syncDataForm", wsHandler -> {
            System.out.println("Filtro para WS antes de la llamada ws");
            //ejecutar cualquier evento antes...
        });

        app.ws("/ws/syncDataForm", ws -> {
            ws.onConnect(ctx -> {
                System.out.println("Conexión Iniciada - "+ctx.getSessionId());
                usuariosConectados.add(ctx.session);
            });

            ws.onMessage(ctx -> {
                //Puedo leer los header, parametros entre otros.
                /*ctx.headerMap();
                ctx.pathParamMap();
                ctx.queryParamMap();*/
                //
                System.out.println("Mensaje Recibido de "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.message());
                System.out.println("================================");
                //
                Gson auxGson = new Gson();
                //JSON string to Java object
                FormDataTemplate dfT = auxGson.fromJson(ctx.message(), FormDataTemplate.class);

                User auxUCreator = UserServices.getInstancia().find(dfT.getUser_creador());

                Form auxForm = new Form(dfT.getName(), dfT.getSector(), dfT.getGrade(),auxUCreator);
                FormServices.getInstancia().create(auxForm);

                java.util.Date date1=  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dfT.getCreated_date());
                Registry auxReg = new Registry(auxForm,dfT.getLatitude(), dfT.getLongitude(), date1);
                RegistryServices.getInstancia().create(auxReg);


            });

            ws.onBinaryMessage(ctx -> {
                System.out.println("Mensaje Recibido Binario "+ctx.getSessionId()+" ====== ");
                System.out.println("Mensaje: "+ctx.data().length);
                System.out.println("================================");
            });

            ws.onClose(ctx -> {
                System.out.println("Conexión Cerrada - "+ctx.getSessionId());
                usuariosConectados.remove(ctx.session);
            });

            ws.onError(ctx -> {
                System.out.println("Ocurrió un error en el WS");
            });
        });

        /**
         * Filtro para activarse despues de la llamadas al contexto.
         */
        app.wsAfter("/ws/syncDataForm", wsHandler -> {
            System.out.println("Filtro para WS despues de la llamada al WS");
            //ejecutar cualquier evento antes...
        });
    }
}
