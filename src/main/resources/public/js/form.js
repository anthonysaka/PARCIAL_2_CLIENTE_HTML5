
$(document).ready(function () {

    geolocator.config({
        language: "en",
    });

    var options = {
        enableHighAccuracy: false,
        timeout: 0,
        maximumWait: 0,     // max wait time for desired accuracy
        maximumAge: 0,          // disable cache
        desiredAccuracy: 30,    // meters
        fallbackToIP: true,     // fallback to IP if Geolocation fails or rejected
        addressLookup: false,    // requires Google API key if true
        timezone: false,         // requires Google API key if true
    };

    $("#surveyForm").submit(function (event) {
        event.preventDefault();
        console.log("ENTREFORM")
        let lat = 0;
        let long = 0;
        let aux = false;
        geolocator.locate(options, (err, location) => {
            if (err) return console.log(err);
            console.log(location);
            lat =  location.coords.latitude;
            long =  location.coords.longitude;

            let formdata = {
                name: $("#name").val(),
                sector: $("#sector").val(),
                subject: $("#subject").val(),
                latitude: lat,
                longitude: long,
                user_creador: JSON.parse(JSON.parse(sessionStorage.getItem('user_logged')))['username'],
            }
            confirm("*** DATOS A ENVIAR ***\n\n"+JSON.stringify(formdata)) ? savetolocalstorage(formdata) : null;

        });

        let savetolocalstorage = (formdata) => {
            let auxForm = localStorage.getItem('form_local_saved');

            if (auxForm != null){
                let x = JSON.parse(auxForm)
                x.push(formdata)
                localStorage.setItem('form_local_saved',JSON.stringify(x));
            } else {
                localStorage.setItem('form_local_saved',JSON.stringify([formdata]));
            }
        }





    });






});

