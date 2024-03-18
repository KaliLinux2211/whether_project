document.addEventListener('DOMContentLoaded', function() {
    fetchWeatherData();
});

function fetchWeatherData() {
    fetch('http://localhost:8080/weather') // Fetch data from backend
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Handle the received JSON data
            let weatherDataDiv = document.getElementById('weatherData');
            weatherDataDiv.innerHTML = ''; // Clear previous data
            data.forEach(entry => {
                let city = entry.city;
                let temperature = entry.temperature;
                let humidity = entry.humidity;
                weatherDataDiv.innerHTML += `<p>City: ${city}, Temperature: ${temperature}, Humidity: ${humidity}</p>`;
            });
        })
        .catch(error => console.error('Error:', error));
}






