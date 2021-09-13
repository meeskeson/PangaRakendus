let button = document.getElementById("submit");
let amount = document.getElementById("sum");
let address = document.getElementById("accountNr");

button.onclick = function () {
    fetch(/withdraw/ + address.value + "/" + amount.value,
        {
            method:'PUT',
        }
    ).then(result => result.text())
        .then(result => {
            alert(result);
            window.close();
        })
}