
$(document).ready(function () {
    $("#actualizar").click(function (event) {
        event.preventDefault();
        console.log("ENTRE")
        axios.get('http://localhost:7000/maps/actualizar')
            .then(function (response) {
                console.log(response);
                alert("SUCCESS" + response.data)
                window.location.href = '/maps'

            });


    });
});
