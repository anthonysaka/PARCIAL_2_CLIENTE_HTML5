
    $(document).ready(function () {

        $("#loginForm").submit(function (event) {
            event.preventDefault();
            console.log("ENTRE")
            axios.post('http://localhost:7000/authenticate', {
                username: $("#username").val(),
                password: $("#password").val(),
            })
            .then((response) => {
                console.log(response);
                if (response.status === 200){
                    alert("SUCCESS" + response.data)
                    sessionStorage.setItem('user_logged',JSON.stringify(response.data)); //Saving User Logged on session storage : Json String
                    window.location.href = '/home'
                }
            }, (error) => {
                console.log(error);
                if (error.status === 404) {
                    alert("CREDENCIALES INCORRECTAS!" + response.data)
                } else {
                    alert("SERVER ERROR!\n" + error);
                }
            });
        });

    });
