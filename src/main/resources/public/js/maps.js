
$(document).ready(function () {

    mapboxgl.accessToken ='pk.eyJ1IjoiYW50aG9ueXNha2EiLCJhIjoiY2tnbjBrZWR4MGkwNDJ0cGczb2UxNTE4YiJ9.WsEmhirejFVApuNz9Ivtlw';

    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11',
        center:  [ -70.81, 19.18], // starting position [lng, lat]  lat = 19.18659946222421, long = -70.81595130441177
        zoom: 8
    });

    let loadMarkeronMap = () => {
        axios.get('http://localhost:7000/surveydata/maps', {
        })
        .then((response) => {
            console.log(response);
            if (response.status === 200){

            }
        }, (error) => {
            console.log(error);
            if (error.status === 404) {

            } else {
                alert("SERVER ERROR!\n" + error);
            }
        });

    }
    loadMarkeronMap();

    /*$("#actualizar").click(function (event) {
        event.preventDefault();
        console.log("ENTRE")
        axios.get('http://localhost:7000/maps/actualizar')
            .then(function (response) {
                console.log(response);
                alert("SUCCESS" + response.data)
                window.location.href = '/maps'

            });


    });*/

});
