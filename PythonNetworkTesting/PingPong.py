import socket
import threading
import sys
import time


flagLogin = False

#Wait for incoming data from server
def receive(socket, signal):
    print("Recive thread created")
    while signal:
        try:
            data = socket.recv(40)
            print(str(data.decode("utf-8")))
        except:
            print("You have been disconnected from the server")
            signal = False
            break


#Menu
def commandList():
    return (
    "--------------------\n"+
    "-1 Pong\n"+
    "-2 StartGame\n"+
    "-3 Buy\n"+
    "-4 Production\n"+
    "--------------------\n")
# Commands
def buy():
    x = input("Coordinata x")
    y = input("Coordinata y")
    return '{"type":"BuyCard","content":{"x":'+ x +',"y":'+ y +',"position":1}}'

def production():
    pos = input("Posizione")
    return '{"type":"Production","content":{"position":'+ pos +'}}'  

def pong(socket,signal):
    print("Pong thread created")
    while signal:
        try:
            print("Pong sended")
            p =  '{"type":"Pong","content":{"index":0}}\n'
            socket.sendall(str.encode(p))
            time.sleep(3)
        except:
            print("Pong failed")  


#Start pong thread
def setLogged():
    print("setLogged")
    flagLogin = True
        
#Parsing inputs command
def parser(command):
    if command == '1':
        return '{"type":"Pong","content":{"index":0}}'
    if command == '2':
        setLogged()
        return '{"type":"StartGame","content":{}}'
    if command == '3':
        return buy()
    if command == '4':
        return production()

#ACTUAL CODE:

#Attempt connection to server
try:
    sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    sock.connect(("localhost", 1234))
    print("Connected")
except:
    print("Could not make a connection to the server")
    input("Press enter to quit")
    sys.exit(0)


#Login Message
message = '{"type":"Login","content":{"nickname":"Nicola"}}\n'
sock.sendall(str.encode(message))
print("Command: " + message)
input("click invio to continue")

#Create new thread to wait for data
receiveThread = threading.Thread(target = receive, args = (sock, True))
receiveThread.start()

#Start pong thread
pongThread = threading.Thread(target = pong, args =(sock,True))
pongThread.start()

command = input(commandList())
while True:
    message = parser(command) + '\n'
    sock.sendall(str.encode(message))
    print("Command: " + message)
    command = input(commandList())

