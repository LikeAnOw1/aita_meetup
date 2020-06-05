let eventSource = new EventSource("/api/rest/v1/guest");

eventSource.onmessage = (event) => onMessage(event);

eventSource.onerror = (event) => {
    console.log(event);
}

