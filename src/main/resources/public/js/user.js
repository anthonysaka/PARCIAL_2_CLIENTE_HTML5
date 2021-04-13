$(document).ready(function () {

    let connectiviy = () => {
        var online = window.navigator.onLine

        if (online) {
            $("#btnAddUser").show();
            $("#online").show();
            $("#offline").hide();

        } else {
            $("#offline").show();
            $("#btnAddUser").hide();
            $("#online").hide();
        }
    }
    setInterval(connectiviy(),3000); //check internet each 10 seconds

    $("#UserNew").submit(function (event) {
        event.preventDefault();
        console.log("ENTRE")

        axios.post('http://localhost:7000/user/crear', {
            nombre: $("#nombre").val(),
            username: $("#username").val(),
            password: $("#password").val(),
            rol: $("#subject").val(),
        })
            .then((response) => {
                console.log(response);
                if (response.status === 200){
                    alert("SUCCESS" + response.data)
                    window.location.href = '/home'
                }
            }, (error) => {
                console.log(error);
                if (error.status === 404) {
                    alert("NO REGISTRADO" + response.data)
                } else {
                    alert("SERVER ERROR!\n" + error);
                }
            });
    });

});