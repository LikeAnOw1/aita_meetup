function onMessage(event) {
    let data = JSON.parse(event.data);
    console.log(data);
    setSpanText('name', data.firstName + ' ' + data.lastName)
    setSpanText("distance", data.flightDistance);
    setSpanText("flight-time", data.flightHours);
}

function setSpanText(id, text) {
    let span = document.getElementById(id);
    span.innerHTML = '';
    let textNode = document.createTextNode(text);
    span.appendChild(textNode);
}

