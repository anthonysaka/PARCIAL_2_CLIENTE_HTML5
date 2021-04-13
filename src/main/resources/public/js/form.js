
$(document).ready(function () {
    var webSocket;

    let connectiviy = () => {
        var online = window.navigator.onLine

        if (online) {
            $("#btnSyncData").show();
            $("#online").show();
            $("#offline").hide();

        } else {
            $("#offline").show();
            $("#btnSyncData").hide();
            $("#online").hide();
        }
    }
    setInterval(connectiviy(),10000); //check internet each 10 seconds


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
            const today = new Date();
            const date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
            const time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
            const dateTime = date +' '+ time;

            let formdata = {
                name: $("#name").val(),
                sector: $("#sector").val(),
                grade: $("#grade").val(),
                latitude: lat,
                longitude: long,
                user_creador: JSON.parse(JSON.parse(sessionStorage.getItem('user_logged')))['username'],
                user_id: JSON.parse(JSON.parse(sessionStorage.getItem('user_logged')))['id'],
                created_date: dateTime,
                sync_status: 0
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
                var markup = "<tr><td class=\"column1\">" + item.name + "</td><td class=\"column2\">" + item.sector + "</td><td class=\"column3\">" + item.grade + "</td><td class=\"column3\">" + item.created_date +
                    "</td><td class=\"column2\"><button type='button' class=\"btn btn-primary btn-md rounded \">Edit</button>" +
                    "</td><td class=\"column2\"><button type='button' class=\"btn btn-danger btn-md rounded \">Delete</button></td></tr>";
                $("table tbody").append(markup);
            })
        }
    }

    loadOnTableFormLocalStorage();

    let connectSocket = () => {
        webSocket = new WebSocket("ws://" + location.hostname + ":" + location.port + "/ws/syncDataForm");

        //indicando los eventos:
        webSocket.onopen  = function(e){ console.log("Conectado - status "+this.readyState); };
        webSocket.onclose = function(e){
            console.log("Desconectado - status "+this.readyState);
        };
    }

    // Make the function wait until the connection is made...
    function waitForSocketConnection(socket, callback){
        setTimeout(
            function () {
                if (socket.readyState === 1) {
                    console.log("Connection is made")
                    if (callback != null){
                        callback();
                    }
                } else {
                    console.log("wait for connection...")
                    waitForSocketConnection(socket, callback);
                }

            }, 5); // wait 5 milisecond for the connection...

    }

    $("#btnSyncData").click(function () {
        let auxData = localStorage.getItem('form_local_saved');
        let jsonForms;
        let flag = 0;

        if (auxData != null || auxData === "")  {
            jsonForms = JSON.parse(auxData);
            console.log(jsonForms)
            connectSocket();
            jsonForms.forEach((item,i) => {
                if (item.sync_status === 0) {
                    flag = 1;
                    waitForSocketConnection(webSocket, function(){
                        webSocket.send(JSON.stringify(item));
                        console.log(item)
                    });
                    item.sync_status = 1;
                }
            });
            //webSocket.close();

            console.log(jsonForms)

            let au = JSON.stringify(jsonForms)
            localStorage.removeItem('form_local_saved');
            localStorage.setItem("form_local_saved",au);

            if (flag === 0){
                alert("*** Nothing to Sync! ***")
            } else {
                alert("*** Sync Success! ***")
            }
        } else{
            alert("*** Nothing to Sync! ***")
        }
        setTimeout(function () {
            webSocket.close();
        },5000);
    });



});

