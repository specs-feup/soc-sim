import zmq
import signal
import sys

context = None
socket = None

def cleanup():
	global context, socket
	if socket:
		socket.close()
		socket = None
	if context:
		context.term()
		context = None
	print("[SIM] ZeroMQ resources cleaned up")

def signal_handler(sig, frame):
	cleanup()

	sys.exit(0)


def loop():
	iv1 = 1
	iv2 = 2
	try:
		while True:
			socket.send_string(f"{iv1} {iv2}")

			reply = socket.recv_string()
			
			print(f"[SIM] Received: {reply}")

			parts = reply.split()
			if len(parts) == 2:
				riv1 = int(parts[0])
				riv2 = int(parts[1])
				
				print(f"Parsed: iv1={riv1}, iv2={riv2}")
			
			iv1 += 1
			iv2 += 1
	except zmq.ZMQError as e:
		print(f"[SIM] ZMQ error: {e}")
	finally:
		cleanup()


def main():
	global context, socket

	signal.signal(signal.SIGINT, signal_handler)
	signal.signal(signal.SIGTERM, signal_handler)

	context = zmq.Context()
	socket = context.socket(zmq.REQ)
	socket.connect("tcp://localhost:5556")

	print("[SIM] Running...")

	loop()

if __name__ == "__main__":
	main()