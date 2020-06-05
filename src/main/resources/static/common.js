function onMessage(event) {
    let data = JSON.parse(event.data);
    console.log(data);
    let nameSpan = document.getElementById("name");
    nameSpan.innerHTML = '';
    let nameText = document.createTextNode(data.firstName + ' ' + data.lastName);
    nameSpan.appendChild(nameText);

    let distanceSpan = document.getElementById("distance");
    distanceSpan.innerHTML = '';
    let distanceText = document.createTextNode(data.flightDistance);
    distanceSpan.appendChild(distanceText);

    let timeSpan = document.getElementById("flight-time");
    timeSpan.innerHTML = '';
    let timeText = document.createTextNode(data.flightHours)
    timeSpan.appendChild(timeText)
}

