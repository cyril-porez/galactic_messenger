runServer:
	gradle build && java -jar galactic_messenger_server.jar $(filter-out $@,$(MAKECMDGOALS))

runClient:
	gradle build && java -jar galactic_messenger_client.jar $(filter-out $@,$(MAKECMDGOALS))