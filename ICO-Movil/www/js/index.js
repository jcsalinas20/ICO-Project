/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

$(document).ready(function() {
    $("#submit").click(authLogin)

    $(".tabs").tabs()
})

function peticion(URL, token) {
    $.ajax({
        type: "GET",
        url: URL,
        crossDomain: true,
        dataType: "json"
    })
        .done(async function(res) {
            if (res.mensaje === "ERROR, no se encontró el Paciente.") {
                console.log(res.mensaje)
                return
            } else {
                window.location.href = `./pages/home/home.html?token=${token}`
                return
            }
        })
        .fail(function() {
            return false
        })
}

function authLogin() {
    document
        .getElementById("frame-preload")
        .contentWindow.document.getElementById("mensaje").innerHTML =
        "Comprobando la autenticación del usuario."
    document.getElementById("frame-preload").style.display = "block"

    var input = $("#user")
    letras = input.val().replace("/", "")
    input.val(letras)

    var input = $("#pass")
    letras = input.val().replace("/", "")
    input.val(letras)

    login(
        document.getElementById("user").value,
        document.getElementById("pass").value
    )
}

function login(user, pass) {
    $.ajax({
        type: "GET",
        url: `https://api-ico.herokuapp.com/api/paciente/auth/${user}/${pass}`,
        crossDomain: true,
        dataType: "json"
    })
        .done(function(msg) {
            document.getElementById("frame-preload").style.display = "none"
            M.toast({
                html: msg.mensaje
            })
            if (msg.mensaje === "El login se realizó correctamente.") {
                localStorage.setItem("jwt", msg.token)
                location.href = `./pages/home/home.html?token=${msg.token}`
            }
        })
        .fail(function() {
            document.getElementById("frame-preload").style.display = "none"
            M.toast({
                html: "No se pudo establecer conexión con el servidor"
            })
        })
}

var app = {
    // Application Constructor
    initialize: function() {
        document.addEventListener(
            "deviceready",
            this.onDeviceReady.bind(this),
            false
        )
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent("deviceready")
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id)
        var listeningElement = parentElement.querySelector(".listening")
        var receivedElement = parentElement.querySelector(".received")

        listeningElement.setAttribute("style", "display:none;")
        receivedElement.setAttribute("style", "display:block;")

        console.log("Received Event: " + id)
    }
}

app.initialize()
