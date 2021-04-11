
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
                grade: $("#grade").val(),
                latitude: lat,
                longitude: long,
                user_creador: JSON.parse(JSON.parse(sessionStorage.getItem('user_logged')))['username'],
            }
            confirm("*** DATOS A ENVIAR ***\n\n"+JSON.stringify(formdata)) ? savetolocalstorage(formdata) : null;

        });
    });

    //Function to save form data to localstorage
    let savetolocalstorage = (formdata) => {
        let auxForm = localStorage.getItem('form_local_saved');

        if (auxForm != null){
            let x = JSON.parse(auxForm)
            x.push(formdata)
            localStorage.setItem('form_local_saved',JSON.stringify(x));
        } else {
            localStorage.setItem('form_local_saved',JSON.stringify([formdata]));
        }

        loadOnTableFormLocalStorage();
    }

    let loadOnTableFormLocalStorage = () => {
        let auxData = localStorage.getItem('form_local_saved');
        let jsonForms;
        if (auxData != null) {
            jsonForms = JSON.parse(auxData);

            $("#tbodyLocalForm").empty();

            jsonForms.forEach(function (item,i){
                var markup = "<tr><td class=\"column1\">" + item.name + "</td><td class=\"column2\">" + item.sector + "</td><td class=\"column3\">" + item.grade +
                    "</td><td class=\"column2\"><button type='button' class=\"btn btn-primary btn-md rounded \">Edit</button>" +
                    "</td><td class=\"column2\"><button type='button' class=\"btn btn-danger btn-md rounded \">Delete</button></td></tr>";
                $("table tbody").append(markup);
            })
        }
    }
    loadOnTableFormLocalStorage();

});

