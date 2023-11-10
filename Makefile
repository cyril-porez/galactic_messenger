runServer:
	cd ./Server/ && gradle build && cd .. && java -jar galactic_messenger_server.jar $(filter-out $@,$(MAKECMDGOALS))

runClient:
	cd ./galacticMessengerClient/ && gradle build && cd .. && java -jar galactic_messenger_client.jar $(filter-out $@,$(MAKECMDGOALS))