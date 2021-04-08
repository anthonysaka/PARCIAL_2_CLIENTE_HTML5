
$("#UserNew").submit(function (event) {
    event.preventDefault();

    let User = {
        nombre: document.getElementById('nombre').value,
        user: document.getElementById('user').value,
        pasword:document.getElementById('password').value,
        rol: document.getElementById('rol').value
    }

    localStorage.setItem("user", JSON.stringify(User));
    console.log(localStorage.getItem("user"))
});