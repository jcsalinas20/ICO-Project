# ICO-Project

ICO Hospital Project with collaborator @mmoralesl. 

* ## [WIKI](https://github.com/jcsalinas20/ICO-Movil/wiki)

* ## MongoDB

### Documents [Consultas](https://github.com/jcsalinas20/ICO-Project/tree/master/mongodb/Consultas)
```js
{
  id_consulta: Integer,
  id_doctor: Integer,
  id_paciente: Integer,
  id_direccion: Integer,
  consultas: Array [
    Object {
      hora: String,
      dia: String,
      asistido: Boolean,
      notas: String,
      notas_doc: String,
      num_consulta: Integer,
    }
  ]
}
```

### Documents [Doctores](https://github.com/jcsalinas20/ICO-Project/tree/master/mongodb/Doctores)
```js
{
  id_doctor: Integer,
  nombre: String,
  apellidos: String,
  username: String,
  password: md5(String),
  id_hospital: Integer,
  planta: Integer,
  sala: Integer,
  horarios: String(startHour-endHour),
  dias: Array [
    0: Integer(0-1),
    1: Integer(0-1),
    2: Integer(0-1),
    3: Integer(0-1),
    4: Integer(0-1),
    5: Integer(0-1),
    6: Integer(0-1),
  ]
}
```

### Documents [Hospitales](https://github.com/jcsalinas20/ICO-Project/tree/master/mongodb/Hospital)
```js
{
  id_hospital: Integer,
  nombre: String,
  direccion: String
}
```

### Documents [Medicamentos](https://github.com/jcsalinas20/ICO-Project/tree/master/mongodb/Medicamentos)
```js
{
  id: Integer,
  nombre: String,
  imagen: String(url)
}
```

### Documents [Pacientes](https://github.com/jcsalinas20/ICO-Project/tree/master/mongodb/Pacientes)
```js
{
  id_paciente: Integer,
  nombre: String,
  apellidos: String,
  dni: String,
  password: md5(String),
  token: String(autogeneration),
  expireToken: String(autogeneration),
  foto: String(url),
  primerInicioSesion: Boolean,
  fecha_nacimiento: String,
  genero: String(H-M),
  leng: String(cas-cat),
  medicamentos: Array [
    Object {
      id: Integer,
      dias: Object {
        lunes: Integer(0-1),
        martes: Integer(0-1),
        miercoles: Integer(0-1),
        jueves: Integer(0-1),
        viernes: Integer(0-1),
        sabado: Integer(0-1),
        domingo: Integer(0-1),
      },
      hora: Array [
        String
      ],
      pastillaTomada: Array [
        Boolean
      ]
    }
  ]
}
```

* ## [ICO-API](https://github.com/jcsalinas20/ICO-API)

### https://ico-project-api.herokuapp.com/

* ## [ICO-Desktop](https://github.com/jcsalinas20/ICO-Desktop)

### Login

![login](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/pc/login.PNG)

### Dashboard

![dashboard](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/pc/dashboard.PNG)

### Search

![search](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/pc/serach.PNG)

### Consultas

![consultas](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/pc/consultas.PNG)

### Medicamentos

![medicamentos](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/pc/medicamentos.PNG)

* ## [ICO-Mobile](https://github.com/jcsalinas20/ICO-Movil)

### Login

![login](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/login.PNG)

### Welcome

![welcome](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/welcome.PNG)

### Side Menu

![sidemenu](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/sidemenu.PNG)

### Home

![home](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/noticas.PNG)

### Consultas

![consultas](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/consultas.PNG)
![consulta](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/consulta.PNG)

### Medicamentos

![medicamentos](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/pastillas.PNG)

### Perfil

![perfil](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/perfil.PNG)

### Hospital ICO

![hospitalico](https://raw.githubusercontent.com/jcsalinas20/ICO-Project/master/images/movil/ayuda.PNG)
