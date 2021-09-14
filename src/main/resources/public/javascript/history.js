let newButton = document.getElementById("sendB");
let listTable = document.getElementById("historyTable");
let accountNr = document.getElementById("accountNr")
newButton.onclick = function () {
    fetch("gethistory/" + accountNr.value)
        .then(result => result.json())
        .then(function (data) {
            for (let i = 0; i < data.length; i++) {
                let newRow = document.createElement("tr");
                listTable.appendChild(newRow);
                let numberColumn = document.createElement("td");
                let amountColumn = document.createElement("td");
                let number_toColumn = document.createElement("td");
                let timeColumn = document.createElement("td");
                newRow.appendChild(numberColumn);
                newRow.appendChild(amountColumn);
                newRow.appendChild(number_toColumn);
                newRow.appendChild(timeColumn);
                numberColumn.innerText = data[i].number;
                amountColumn.innerText = data[i].amount;
                number_toColumn.innerText = data[i].number_to;
                timeColumn.innerText = data[i].time;
            }
        })
}