const desplazamiento = document.getElementById("desplazamiento");
const texto = document.getElementById("texto");
const textoCifrado = document.getElementById("cifrado");
const descifrarBoton = document.getElementById("descifrarBoton");

function cifrado() {
    const textoIngresado = texto.value;
    const valorDesplazamiento = parseInt(desplazamiento.value); // El desplazamiento es el número exacto que el usuario ingresa

    textoCifrado.value = textoIngresado.split('').map(c => {
        let mayus = (c === c.toUpperCase()) ? true : false;
        let valorEntero = c.toLowerCase().charCodeAt(0);

        if (valorEntero >= 97 && valorEntero <= 122) {
            valorEntero = (valorEntero - 97 + valorDesplazamiento) % 26 + 97; // Asegurar que el resultado esté en el rango de letras minúsculas
        }

        let cifrado = String.fromCharCode(valorEntero);
        return mayus ? cifrado.toUpperCase() : cifrado;
    }).join('');
}

function descifrado() {
    const textoIngresado = document.getElementById("texto").value;
    const valorDesplazamiento = parseInt(document.getElementById("desplazamiento").value);

    document.getElementById("cifrado").value = textoIngresado.split('').map(c => {
        let mayus = (c === c.toUpperCase()) ? true : false;
        let valorEntero = c.toLowerCase().charCodeAt(0);

        if (valorEntero >= 97 && valorEntero <= 122) {
            valorEntero = (valorEntero - 97 - valorDesplazamiento + 26) % 26 + 97;
        }

        let descifrado = String.fromCharCode(valorEntero);
        return mayus ? descifrado.toUpperCase() : descifrado;
    }).join('');
}
// Llama a la función cifrado cuando el usuario ingresa texto o cambia el desplazamiento
texto.addEventListener("input", cifrado);
desplazamiento.addEventListener("input", cifrado);
descifrarBoton.addEventListener("click", descifrado)

