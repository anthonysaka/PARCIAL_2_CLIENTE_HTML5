$(document).ready(function () {
    $("#UserNew").submit(function (event) {
        event.preventDefault();

        let User = {
            nombre: $("#nombre").val(),
            user: $("#user").val(),
            pasword: $("#password").val(),
            subject: $("#subject").val(),
        }
        confirm("*** DATOS A ENVIAR ***\n\n"+JSON.stringify(User)) ? saveUserLocal(User) : null;
        window.location.href = '/home'

    });

    let saveUserLocal = (User) => {
        let auxForm = localStorage.getItem('user_registered_saved');

        if (auxForm != null){
            let x = JSON.parse(auxForm)
            x.push(User)
            localStorage.setItem('user_registered_saved',JSON.stringify(x));
        } else {
            localStorage.setItem('user_registered_saved',JSON.stringify([User]));
        }
    }

});