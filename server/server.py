import socket
import os

# Obtiene la dirección IP de la PC servidor
hostname = socket.gethostname()
server_ip = socket.gethostbyname(hostname)

# Crea una lista de videos
video_list = [
    'vid-01.mp4',
    'vid-02.mp4',
    'vid-03.mp4',
    'vid-04.mp4',
    'vid-05.mp4',
    'vid-06.mp4',
    'vid-07.mp4',
    'vid-08.mp4',
    'vid-09.mp4',
    'vid-10.mp4'
]
current_video = 0

# Crea un socket TCP/IP
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

# Enlaza el socket a la dirección IP y puerto
server_address = (server_ip, 10000)
print(f'Iniciando en {server_address[0]} puerto {server_address[1]}')
sock.bind(server_address)

# Escucha por conexiones entrantes
sock.listen(1)

def next_video():
    global current_video
    current_video += 1
    if current_video >= len(video_list):
        current_video = 0

def prev_video():
    global current_video
    current_video -= 1
    if current_video < 0:
        current_video = len(video_list) - 1

while True:
    # Espera por una conexión
    print('Esperando por una conexión')
    connection, client_address = sock.accept()
    try:
        print(f'Conexión desde {client_address[0]}:{client_address[1]}')

        try:
            # Espera por una señal del cliente para pasar al siguiente video o volver al anterior
            signal = connection.recv(1024)
            signal = signal.decode()
            if signal == 'next':
                next_video()
            elif signal == 'prev':
                prev_video()
        except BrokenPipeError:
            print("Conexión cerrada por el cliente")
            break
        print("Sending video: ", video_list[current_video])
        # Envia el video actual al cliente
        with open(video_list[current_video], 'rb') as video:
            video_data = video.read()
        print("Size of video: ", len(video_data))
        connection.sendall(video_data)
        #connection.shutdown(socket.SHUT_WR)
    finally:
        # Cierra la conexión
        connection.close()

