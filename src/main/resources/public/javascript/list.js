let newButton = document.getElementById("newButton");
let listTable = document.getElementById("listTable");
newButton.onclick = function () {
    fetch("history")
        .then(result => result.json())
        .then(function (data) {
            for (let i = 0; i < data.length; i++) {
                let htmlRow = document.createElement("tr");
                listTable.appendChild(htmlRow);
                let firstNameColumn = document.createElement("td");
                let lastNameColumn = document.createElement("td");
                let addressColumn = document.createElement("td");
                let numberColumn = document.createElement("td");
                let balanceColumn = document.createElement("td");
                htmlRow.appendChild(firstNameColumn);
                htmlRow.appendChild(lastNameColumn);
                htmlRow.appendChild(addressColumn);
                htmlRow.appendChild(numberColumn);
                htmlRow.appendChild(balanceColumn);
                firstNameColumn.innerText = data[i].firstName;
                lastNameColumn.innerText = data[i].lastName;
                addressColumn.innerText = data[i].address;
                numberColumn.innerText = data[i].number;
                balanceColumn.innerText = data[i].balance;

            }
        })
}