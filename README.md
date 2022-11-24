# Awesome New Games Store

- [Architecture, use cases diagrams, descriptions, etc...](https://lucid.app/documents/embedded/96433f6b-1c33-42f8-83c4-fd91e4b9bf1d?invitationId=inv_89fc0898-9909-44bb-8504-364d9ff65afe#)


## About tests

 - *UT - Unit Test, eg. ProductStockUT - Unit Tests for Product Stock
 - *IT - Integration Test
 - *FT - Functional Test

Please browse to OrderingProductsFT as it demonstrates the whole required interaction between the caller and the service.

## Planned roadmap
1. Add data layer for stock management domain - simple RDB like MariaDB will suffice.
2. Add data layer for cart/order domain - simple RDB will suffice.
3. Substitute search controller in favour of Search Engine like Apache Solr built on Apache Lucene.
4. As scalability requires - separate stock, cart/order, search domains to separate services and integrate them with messaging queues.
5. Secure the endpoints by integrating into SSO/Keycloak/Federated authentication service.
6. Add micrometer for monitoring purposes.
7. Integrate Payment Service and extend current implementation of finalzing orders.
8. Integrate Order Realization Service to actually deliver the artifacts to customer.

## Improvement ideas
1. Have the domain services return data objects, not domain objects, so that only services and domain logic would be able to perform any operations on domain objects.

## Architecture Decision Record Log

### 1. Use Spring Boot/Java 17 as platform for application.
### 2. Develop service using Walking skeleton method to enable fast implementation of core logic without waiting for dependencies. 
1. Postpone any data layer integration as late as possible.
2. Postpone any service integration (via HTTP or MQ) as late as possible.

