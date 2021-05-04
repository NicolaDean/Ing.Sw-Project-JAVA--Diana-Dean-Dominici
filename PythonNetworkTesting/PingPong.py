import socket
import threading
import sys

#Wait for incoming data from server
#.decode is used to turn the message in bytes to a string
def receive(socket, signal):
    while signal:
        try:
            data = socket.recv(40)
            print(str(data.decode("utf-8")))
        except:
            print("You have been disconnected from the server")
            signal = False
            break


#Attempt connection to server
try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(("localhost", 1234))
except:
    print("Could not make a connection to the server")
    input("Press enter to quit")
    sys.exit(0)

#Create new thread to wait for data
receiveThread = threading.Thread(target = receive, args = (sock, True))
receiveThread.start()

#Send data to server
#str.encode is used to turn the string message into bytes so it can be sent across the network
message = '{"type":"Login","content":{"nickname":"Nicola"}}\n'
sock.sendall(str.encode(message))
print("Command: " + message)
input("click a key to continue")

def commandList():
    return (
    "--------------------\n"+
    "-1 Pong\n"+
    "-2 StartGame\n"+
    "-3 Buy\n"+
    "-4 Production\n"+
    "--------------------\n")

def parser(command):
    if command == '1':
        return '{"type":"Pong","content":{"index":0}}'
    if command == '2':
        return '{"type":"StartGame","content":{}}'
    if command == '3':
        return '{"type":"BuyCard","content":{"x":1,"y":2,"position":1}}'
command = input(commandList())
while True:
    message = parser(command) + '\n'
    sock.sendall(str.encode(message))
    print("Command: " + message)
    command = input(commandList())
