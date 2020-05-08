let links = []
function getNoticias() {
    $.ajax({
        type: "GET",
        url: `https://api-ico.herokuapp.com/api/noticias`,
        crossDomain: true,
        dataType: "json"
    })
        .done(async function(res) {
            if (res) {
                var parent = document.getElementById("noticias")
                parent.innerHTML = ""
                for (let i = 0; i < res.length; i++) {
                    links.push(res[i].link)
                }
                for (let j = 0; j < res.length; j++) {
                    var noticia = res[j]
                    var div1 = document.createElement("div")
                    var div2 = document.createElement("div")
                    var div3 = document.createElement("div")
                    var div4 = document.createElement("div")
                    var div5 = document.createElement("div")
                    var img = document.createElement("img")
                    var div6 = document.createElement("div")
                    var p = document.createElement("p")
                    var div7 = document.createElement("div")
                    var a = document.createElement("a")

                    img.src = noticia.imagen
                    div5.appendChild(img)
                    div5.className = "card-image"
                    p.innerHTML = noticia.titulo
                    div6.appendChild(p)
                    div6.className = "card-content"
                    if (leng === "cat") {
                        a.innerHTML = "Veure Més ↑"
                    } else {
                        a.innerHTML = "Ver Más ↑"
                    }
                    a.addEventListener("click", function() {
                        mostrarNoticia(j)
                    })
                    div7.appendChild(a)
                    div7.className = "card-action"

                    div4.appendChild(div5)
                    div4.appendChild(div6)
                    div4.appendChild(div7)
                    div4.className = "card"

                    div3.appendChild(div4)
                    div3.className = "col s10"
                    div2.className = "col s1"

                    div1.appendChild(div2)
                    div1.appendChild(div3)
                    parent.appendChild(div1)
                }
                window.parent.document.getElementById(
                    "frame-preload"
                ).style.display = "none"
            }
        })
        .fail(function() {
            // LLAMAR AL METODO DEL PADRE
            activateToast("No se pudo establecer conexión con el servidor")
        })
}

async function mostrarNoticia(i) {
    // HACER VISIBLE UN CLOSE
    window.parent.document.getElementById("frameNoticia").src = links[i]
    await sleep(700)
    window.parent.document.getElementById("frameNoticia").style.display = "flex"
    window.parent.document.getElementById("menuNoticia").style.display = "block"
}

function sleep(ms) {
    return new Promise(resolve => {
        setTimeout(resolve, ms)
    })
}

function updateNews() {
    if (leng === "cat") {
        window.parent.document
            .getElementById("frame-preload")
            .contentWindow.document.getElementById("mensaje").innerHTML =
            "Actualitzant les notícies."
    } else {
        window.parent.document
            .getElementById("frame-preload")
            .contentWindow.document.getElementById("mensaje").innerHTML =
            "Actualizando las noticias."
    }
    window.parent.document.getElementById("frame-preload").style.display =
        "flex"
    getNoticias()
}
