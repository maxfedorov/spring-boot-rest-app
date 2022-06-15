run:
	./mvnw clean package -DskipTests
	docker-compose -f docker-compose.yaml build && docker-compose -f docker-compose.yaml up -d

stop:
	docker-compose -f docker-compose.yaml down
