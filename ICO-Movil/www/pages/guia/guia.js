const numMaxPasos = 9
const numMinPasos = 1

function siguientePaso() {
    const index =
        parseInt(document.getElementsByClassName("activated")[0].id) + 1

    if (index <= numMaxPasos) {
        document.getElementById(index - 1).classList = "bolas"
        document.getElementById(index).classList = "bolas activated"
        document.getElementById("frameBody").src =
            "./pasos/paso" + index + ".html"
    }
}

function anteriorPaso() {
    const index =
        parseInt(document.getElementsByClassName("activated")[0].id) - 1

    if (index >= numMinPasos) {
        document.getElementById(index + 1).classList = "bolas"
        document.getElementById(index).classList = "bolas activated"
        document.getElementById("frameBody").src =
            "./pasos/paso" + index + ".html"
    }
}

function cerrarVentana() {
    window.parent.document.getElementById(
        "container-frame-guia"
    ).style.display = "none"
}
