document.addEventListener('DOMContentLoaded', function () {
  const helloButton = document.getElementById('helloBtn');
  const piButton = document.getElementById('piBtn');

  helloButton.addEventListener('click', function () {
    const name = document.getElementById('nameInput').value;

    fetch('/App/hello?name=' + name)
      .then((response) => response.text())
      .then((data) => {
        document.getElementById('result').innerText = data;
      })
      .catch((error) => console.error('Error:', error));
  });

  piButton.addEventListener('click', function () {
    fetch('/App/pi')
      .then((response) => response.text())
      .then((data) => {
        document.getElementById('result').innerText = 'PI = ' + data;
      })
      .catch((error) => console.error('Error:', error));
  });
});
