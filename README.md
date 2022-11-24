# Awesome New Games Store

- [Architecture, use cases diagrams, descriptions, etc...](https://lucid.app/documents/embedded/96433f6b-1c33-42f8-83c4-fd91e4b9bf1d?invitationId=inv_89fc0898-9909-44bb-8504-364d9ff65afe#)

# Description
This task was developed using [Walking Skeleton](https://wiki.c2.com/?WalkingSkeleton). It implements business logic as requested,
but integrates with nothing. It's self-contained and it's possible to deploy it even to production environment(though not recommend at this stage)
and continue with agile developoment of the project and continue gathering feedback and metrics from the behaviour of the service.

I made following simplifications:
1. Search functionality is most basic at this level. Search controller `/search` simply returns all products. 
End product would have to utilize something akin to Apache Solr based on Apache Lucene to perform product queries at scale.
2. There are three separate domains which don't know about one another. 
See diagrams in [Lucidchart]((https://lucid.app/documents/embedded/96433f6b-1c33-42f8-83c4-fd91e4b9bf1d?invitationId=inv_89fc0898-9909-44bb-8504-364d9ff65afe#)) for how they interact.
3. These domains communicate(if need be) via spring events, though this can be substituted to any messaging system as developoment progresses.
4. There is no data layer and all persistence is volatile by implementing InMemory repositories. There is a clear separation of domain and infrastructure layer, 
so changes to data access should not affect domain. 

If you see an area for improvement there is a good chance that I also saw it but made a decision to leave things as they are. 
Class names, class interactions, order of invocations, control flow, state machines or validating data - you name it. 
To address them all would be too time-consuming for this task in my assessment.
Please make note of such occurrences, so we can have a good discussion about them!

## Tech

- Spring Boot for application itself
- Maria DB for data layer
- NoSQL for Cart Data layer (I've seen such cassandra use in ecommerce)
- Rabbit MQ (AMQP) for messaging needs
- Docker for containerization
- Kubernetes & Kustomize as container orchestration and customization
- AWS as network and deployment environment
- Micrometer for gathering metrics of application runtime
- Grafana for visualization (or AWS equivalent)
- ELK stack for logs (or AWS equivalent)
- Prometheus for Alerting/visualization
- [Judge D](https://github.com/HLTech/judge-d) for contract testing
- Gatling for performance tests 

## About tests

To execute tests:

```
.\mvnw test
--or
.\mvnw.cmd test
```

Should result in:

```
Tests run: 19, Failures: 0, Errors: 0, Skipped: 0
```

Please browse to [OrderingProductsFunctionalTest](https://github.com/ndrwtrsk/angs/blob/master/src/test/java/xyz/torski/angs/domain/OrderingProductsFunctionalTest.java) as it demonstrates the whole required interaction between the caller and the service.

## Planned roadmap
1. Add data layer for stock management domain - simple RDB like MariaDB will suffice.
2. Add data layer for cart/order domain - simple RDB will suffice.
3. Substitute search controller in favour of Search Engine like Apache Solr built on Apache Lucene.
4. As scalability requires - separate stock, cart/order, search domains to separate services and integrate them with messaging queues.
5. Secure the endpoints by integrating into SSO/Keycloak/Federated authentication service.
6. Add micrometer for monitoring purposes.
7. Integrate Payment Service and extend current implementation of finalizing orders.
8. Integrate Order Realization Service to actually deliver the artifacts to customer.
9. Dockerize jar.

## Improvement ideas
1. Have the domain services return data objects, not domain objects, so that only services and domain logic would be able to perform any operations on domain objects.
2. Order/Cart domain - these two entities seem to permeate one another. Cart is necessary for Order to be created, but it's order that would widely known within other bounded contexts.

## Architecture Decision Record Log

### 1. Use Spring Boot/Java 17 as platform for application.
### 2. Develop service using Walking skeleton method to enable fast implementation of core logic without waiting for dependencies. 
1. Postpone any data layer integration as late as possible.
2. Postpone any service integration (via HTTP or MQ) as late as possible.

