function login (username, password) {
    console.log("ENTre")
    console.log(username)
    axios.post('http://localhost:7000/authenticate', {
        username: username,
        password: password
    })
    .then((response) => {
        console.log(response);
    }, (error) => {
        console.log(error);
    });
}