#Servidor python
import socket

puerto = 5408

with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as servidor_socket:
    servidor_socket.bind(('localhost', puerto))
    servidor_socket.listen()

    print(f"Servidor esperando conexiones en el puerto {puerto}...")
    conexion, direccion = servidor_socket.accept()

    with conexion, conexion.makefile('r') as entrada, conexion.makefile('w') as salida:
        mensaje_cliente = entrada.readline().strip()
        if mensaje_cliente == "requesting_home":
            respuesta = """<html><head><title>Saludo</title></head><body><h1>Bienvenido a la pagina de inicio</h1></body></html>"""
            salida.write(respuesta)

            # Guardamos la respuesta en un archivo llamado home.html
            with open("home.html", "w") as archivo:
                archivo.write(respuesta)
            print("Respuesta guardada en home.html")

#Cliente python
import socket

puerto = 5408

with socket.create_connection(('localhost', puerto)) as cliente_socket:
    with cliente_socket.makefile('r') as entrada, cliente_socket.makefile('w') as salida:
        # Enviamos mensaje al servidor
        salida.write("requesting_home\n")
        salida.flush()

        # Recibimos respuesta del servidor
        respuesta = entrada.readline().strip()
        print("Respuesta del servidor:\n" + respuesta)
