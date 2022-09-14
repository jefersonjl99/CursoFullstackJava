// Call the dataTables jQuery plugin
$(document).ready(function () {
  cargarUsuarios();
  $("#usuarios").DataTable();
  actualizarEmailUsuario();
});
function actualizarEmailUsuario(){
  document.getElementById("txt-email-user").outerHTML = localStorage.email;
}

function getHeader() {
  return {
    Accept: "application/json",
    "Content-Type": "application/json",
    Authorization: localStorage.token,
  };
}

async function cargarUsuarios() {
  const request = await fetch("api/usuarios", {
    method: "GET",
    headers: getHeader(),
  });
  const usuarios = await request.json();

  let listadoHtml = "";
  for (let usuario of usuarios) {
    let botonEliminar =
      '<a href="#" onclick="eliminarUsuario(' +
      usuario.id +
      ')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';

    usuario.telefono = usuario.telefono == null ? " " : usuario.telefono;

    let usuariohtml =
      "<tr><td>" +
      usuario.id +
      "</td><td>" +
      usuario.nombre +
      " " +
      usuario.apellido +
      "</td><td>" +
      usuario.email +
      "</td><td>" +
      usuario.telefono +
      "</td><td>" +
      botonEliminar +
      "</td></tr>";
    listadoHtml += usuariohtml;
  }

  console.log(listadoHtml);
  document.querySelector("#usuarios tbody").outerHTML = listadoHtml;
}

async function eliminarUsuario(id) {
  if (!confirm("Desea eliminar este usuario")) {
    return;
  }
  const request = await fetch("api/usuario/" + id, {
    method: "DELETE",
    headers: getHeader(),
  });

  location.reload();
}
