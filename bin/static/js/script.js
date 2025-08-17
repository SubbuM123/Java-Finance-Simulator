function sendNumber() {
    const ids = ["checkB", "saveB", "saveA",
                    "CD1B", "CD1A", "CD1M",
                    "CD2B", "CD2A", "CD2M",
                    "CD3B", "CD3A", "CD3M",  
                    "mmB", "mmA", "n"
    ];

    let jstring = {};

    for (const id of ids) {
        const k = document.getElementById(id);
        if (k) {
            jstring[id] = k.value;
        }
    }

    fetch("/multiply", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(jstring)
    })
    .then(response => response.text())
    .then(data => {
        document.getElementById("result").innerText = data;
    })
    .catch(error => console.error("Error:", error));
}
