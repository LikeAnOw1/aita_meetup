let eventSource = new EventSource("/api/rest/v1/guests");

eventSource.onmessage = (event) => onMessage(event);

eventSource.onerror = (event) => {
    console.log(event);
}

