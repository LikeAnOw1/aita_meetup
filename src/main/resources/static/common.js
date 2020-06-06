function onMessage(event) {
    let data = JSON.parse(event.data);
    console.log(data);
    setSpanText('name', data.first_name + ' ' + data.last_name)
    setSpanText("distance", data.flight_distance);
    setSpanText("flight-time", data.flight_hours);
}

function setSpanText(id, text) {
    let span = document.getElementById(id);
    span.innerHTML = '';
    let textNode = document.createTextNode(text);
    span.appendChild(textNode);
}

