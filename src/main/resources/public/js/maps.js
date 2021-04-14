
$(document).ready(function () {
    var listReg = [];
    mapboxgl.accessToken ='pk.eyJ1IjoiYW50aG9ueXNha2EiLCJhIjoiY2tnbjBrZWR4MGkwNDJ0cGczb2UxNTE4YiJ9.WsEmhirejFVApuNz9Ivtlw';

    var map = new mapboxgl.Map({
        container: 'map',
        style: 'mapbox://styles/mapbox/streets-v11',
        center:  [ -70.81, 19.18], // starting position [lng, lat]  lat = 19.18659946222421, long = -70.81595130441177
        zoom: 8
    });

    let loadMarkeronMap = () => {

        axios.get('https://'+ location.hostname + ":" + location.port +'/loadmarker', {
        })
        .then((response) => {
            console.log(response);
            if (response.status === 200){

                console.log(response.data);
                response.data.forEach((item,i)=>{
                    listReg.push(JSON.parse(item))
                })
                console.log(listReg);
                var auxFeatures = [];

                for (var i = 0; i < listReg.length; i++) {
                    auxFeatures.push({
                        type: 'Feature',
                        geometry: {
                            type: 'Point',
                            coordinates: [listReg[i].longitude, listReg[i].latitude]
                        },
                        properties: {
                            title: listReg[i].user_creador,
                            description: listReg[i].sector + '\n' + listReg[i].created_date,
                        }
                    })
                }
                var geojson = {
                    type: 'FeatureCollection',
                    features: auxFeatures,
                };

                // add markers to map
                geojson.features.forEach(function(marker) {

                    // create a HTML element for each feature
                   // var el = document.createElement('div');
                    //el.className = 'marker';

                    // make a marker for each feature and add to the map
                    new mapboxgl.Marker({ color: 'black', rotation: 45 })
                        .setLngLat(marker.geometry.coordinates)
                        .setPopup(new mapboxgl.Popup({
                            offset: 25
                        }) // add popups
                            .setHTML('<h5>' + marker.properties.title + '</h5><strong>' + marker.properties.description + '</strong>'))
                        .addTo(map);
                });
                map.addControl(new mapboxgl.NavigationControl());

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

    let loadOnTableSyncForm = () => {
        axios.get('https://'+ location.hostname + ":" + location.port +'/loadmarker', {
        })
            .then((response) => {
                console.log(response);
                if (response.status === 200){
                    let list = [];
                    response.data.forEach((item,i)=>{
                        list.push(JSON.parse(item))
                    })

                    $("#tbodySyncForm").empty();
                    list.forEach(function (item,i){
                        var markup = "<tr><td class=\"column1\">" + item.name + "</td><td class=\"column2\">" + item.sector + "</td><td class=\"column3\">" + item.grade + "</td><td class=\"column3\">" +
                            item.latitude + "</td><td class=\"column3\">" + item.longitude + "</td><td class=\"column3\">" + item.user_creador +
                            "</td><td class=\"column3\">" + item.created_date +"</td></tr>";
                        $("table tbody").append(markup);
                    })

                }
            }, (error) => {
                console.log(error);
                if (error.status === 404) {

                } else {
                    alert("SERVER ERROR!\n" + error);
                }
            });

    }
    loadOnTableSyncForm();

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
